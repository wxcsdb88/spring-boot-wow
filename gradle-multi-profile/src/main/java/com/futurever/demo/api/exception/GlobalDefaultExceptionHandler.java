package com.futurever.demo.api.exception;

import com.futurever.demo.api.common.ResponseData;
import com.futurever.demo.api.constants.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;

import static com.futurever.demo.api.utils.IPUtils.getIpAddr;
import static com.futurever.demo.api.utils.IPUtils.getLocalRealIp;

/**
 * description:
 *
 * @author : wxcsdb88
 * @since : 2017/10/10 15:20
 */
@RestController
@ControllerAdvice
public class GlobalDefaultExceptionHandler {
    private static Logger logger = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResponseData defaultErrorHandler(HttpServletRequest request, HttpServletResponse response, Exception ex) throws Exception {
        ResponseData responseData = new ResponseData();
        String queryString = request.getQueryString();
        queryString = URLDecoder.decode(queryString, "UTF-8");
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
            responseData.setCode(Response.INVALID_PARAMETER);
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

    //参数类型不匹配
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    @ResponseBody
    public ResponseData requestMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        ResponseData<Boolean> responseData = new ResponseData<>();

        String msg = String.format("参数类型不匹配, 参数 %s 值为 %s, 类型应该为 %s", ex.getName(), ex.getValue(),
                ex.getRequiredType() == null ? ex.getRequiredType() : ex.getRequiredType().getSimpleName());
        logger.error(msg);
        responseData.set(Response.INVALID_PARAMETER, msg, false);
        return responseData;
    }

    //缺少参数异常
    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseBody
    public ResponseData requestMissingServletRequestParameter(MissingServletRequestParameterException ex) {
        ResponseData<Boolean> responseData = new ResponseData<>();
        String msg = String.format("缺少必要参数,参数名称为%s", ex.getParameterName());
        logger.error(msg);
        responseData.set(Response.INVALID_PARAMETER, msg, false);
        return responseData;
    }
}
