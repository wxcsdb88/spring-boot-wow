package com.futurever.elm.api.exception;

import com.futurever.elm.api.common.ResponseData;
import com.futurever.elm.api.constants.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.futurever.elm.api.utils.IPUtils.getIpAddr;
import static com.futurever.elm.api.utils.IPUtils.getLocalRealIp;

/**
 * Created by wxcsdb88 on 2017/8/11 13:13.
 */
@RestController
@ControllerAdvice
public class GlobalDefaultExceptionHandler {
    private static Logger logger = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);

    @ExceptionHandler(value = AuthorizationException.class)
    @ResponseBody
    public Object authorizationExceptionHandler(HttpServletRequest request, AuthorizationException e) throws Exception {
        ResponseData responseData = new ResponseData();
        String uri = request.getRequestURL().toString();
        String remoteAddr = getIpAddr(request);
        String method = request.getMethod();
        String output_log = "authorizationExceptionHandler [{} uri={} code={} from={} to={}] msg=({})";
        logger.error(output_log, method, uri, e.getCode(), remoteAddr, getLocalRealIp(), e.getMessage());
        responseData.setOK(e.getCode(), e.getMessage(), null);
        return responseData;
    }

    @ExceptionHandler(value = ApiException.class)
    @ResponseBody
    public Object apiExceptionHandler(HttpServletRequest request, ApiException e) throws Exception {
        ResponseData responseData = new ResponseData();
        String uri = request.getRequestURL().toString();
        String remoteAddr = getIpAddr(request);
        String method = request.getMethod();
        String output_log = "apiExceptionHandler [{} uri={} code={} from={} to={}] msg=({})";
        logger.error(output_log, method, uri, e.getCode(), remoteAddr, getLocalRealIp(), e.getMessage());
        responseData.setOK(e.getCode(), e.getMessage(), null);
        return responseData;
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResponseData defaultErrorHandler(HttpServletRequest request, HttpServletResponse response, Exception ex) throws Exception {
        ResponseData responseData = new ResponseData();
        String queryString = request.getQueryString();
        String uri = request.getRequestURL().toString();
        String remoteAddr = getIpAddr(request);
        String token = request.getHeader("token");
        String method = request.getMethod();
        String output_log = "defaultErrorHandler [method={} uri={}  code={} from={} to={} cost={}ms parameters=({})] msg=({})";
        logger.error(output_log, method, uri, response.getStatus(), remoteAddr, getLocalRealIp(), String.format("%1$.3f", 0.00), queryString, ex);

        if (ex instanceof org.springframework.web.servlet.NoHandlerFoundException) {
            responseData.setCode(Response.NO_HANDLER_FOUND_EXCEPTION);
            String out_info = "No handler found for %s %s";
            responseData.setMsg(String.format(out_info, method, uri));
        } else if (ex instanceof NullPointerException) {
            responseData.setCode(Response.SERVICE_EXCEPTION);
            String out_info = "Error null value for  %s %s";
            responseData.setMsg(String.format(out_info, method, uri));
        } else if (ex instanceof org.springframework.web.bind.MissingServletRequestParameterException) {
            // required parameter in spring annotation
            responseData.setCode(Response.MISSING_REQUIRED_PARAMETER);
            String out_info = ex.getMessage();
            responseData.setMsg(String.format(out_info, ex.getMessage() == null ? "" : ex.getMessage()));
        } else if (ex instanceof org.springframework.web.HttpRequestMethodNotSupportedException) {
            responseData.setCode(Response.METHOD_ERROR);
            responseData.setMsg(ex.getMessage() == null ? "" : ex.getMessage());
        } else if (ex instanceof org.springframework.web.HttpMediaTypeNotAcceptableException) {
            //HttpMediaTypeNotAcceptableException
            responseData.setCode(Response.METHOD_ERROR);
            responseData.setMsg(ex.getMessage() == null ? "" : ex.getMessage());
        } else {
            responseData.setCode(Response.SERVICE_EXCEPTION);
            String out_info = "service error with message: %s";
            responseData.setMsg(ex.getMessage());
        }
        return responseData;
    }
}
