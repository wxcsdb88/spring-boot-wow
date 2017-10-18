package com.futurever.demo.api.exception;

import com.futurever.demo.api.common.RequestDO;
import com.futurever.demo.api.common.ResponseData;
import com.futurever.demo.api.constants.Response;
import com.futurever.demo.api.utils.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.ClassUtils;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;

import static com.futurever.demo.api.utils.IpUtils.getClientAddr;

/**
 * description: 全局异常参数格式化处理
 *
 * @author wxcsdb88
 * @since 2017/10/10 15:20
 */
@RestController
@ControllerAdvice
public class GlobalDefaultExceptionHandler {
    private static Logger logger = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResponseData defaultErrorHandler(HttpServletRequest request, HttpServletResponse response, Exception ex) throws Exception {
        ResponseData responseData = new ResponseData();
        RequestDO requestDO = RequestUtils.getCurrentRequestDO(request);
        String queryString = request.getQueryString();
        if (queryString != null && !"".equals(queryString)) {
            queryString = URLDecoder.decode(queryString, "UTF-8");
        }
        String url = request.getRequestURL().toString();
        String remoteAddr = getClientAddr(request);
        String token = request.getHeader("token");
        String method = request.getMethod();
        String outputLog = "defaultErrorHandler [method={} url={}  code={} from={} to={} cost={}ms parameters=({})] msg=({})";
        String msg = ex.getCause() == null ? ex.getMessage(): ex.getCause().getMessage();
        logger.error(outputLog, method, url, response.getStatus(), remoteAddr, requestDO.getLocalAddr(), String.format("%1$.3f", 0.00), queryString, msg);

        if (ex instanceof org.springframework.web.servlet.NoHandlerFoundException) {
            responseData.setCode(Response.NO_HANDLER_FOUND_EXCEPTION);
            String outInfo = "No handler found for %s %s";
            responseData.setMsg(String.format(outInfo, method, url));
        } else if (ex instanceof NullPointerException) {
            responseData.setCode(Response.INTERNAL_SERVER_ERROR);
            String outInfo = "Error null value for  %s %s";
            responseData.setMsg(String.format(outInfo, method, url));
        } else if (ex instanceof org.springframework.web.bind.MissingServletRequestParameterException) {
            // required parameter in spring annotation
            responseData.setCode(Response.INVALID_PARAMETER);
            String outInfo = ex.getMessage();
            responseData.setMsg(String.format(outInfo, ex.getMessage() == null ? "" : ex.getMessage()));
        } else if (ex instanceof org.springframework.web.HttpRequestMethodNotSupportedException) {
            responseData.setCode(Response.METHOD_ERROR);
            responseData.setMsg(ex.getMessage() == null ? "" : ex.getMessage());
        } else if (ex instanceof org.springframework.web.HttpMediaTypeNotAcceptableException) {
            //HttpMediaTypeNotAcceptableException
            responseData.setCode(Response.METHOD_ERROR);
            responseData.setMsg(ex.getMessage() == null ? "" : ex.getMessage());
        } else {
            responseData.setCode(Response.INTERNAL_SERVER_ERROR);
            String outInfo = "service error with message: %s";
            responseData.setMsg(ex.getMessage());
        }
        return responseData;
    }

    // *********************************************** DefaultHandlerExceptionResolver start ***********************************************
    /**
     * HTTP HttpRequestMethodNotSupportedException
     * 请求方法不匹配
     *
     * @param request {@link HttpServletRequest}
     * @param ex      {@link HttpRequestMethodNotSupportedException}
     * @return {@link ResponseData}
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseBody
    public ResponseData httpRequestMethodNotSupportedExceptionHandler(HttpServletRequest request, HttpRequestMethodNotSupportedException ex) {
        ResponseData responseData = new ResponseData<>();
        RequestDO requestDO = RequestUtils.getCurrentRequestDO(request);

        String resultMsg = String.format("HttpRequestMethodNotSupportedException: 传入值 [%s], 允许值 %s", ex.getMethod(), ex.getSupportedHttpMethods());
        responseData.setError(resultMsg);
        String outputLog = "httpRequestMethodNotSupportedExceptionHandler [method={} url={}  code={} from={} to={} parameters=({}) msg=({})]";
        logger.error(outputLog, requestDO.getMethod(), requestDO.getRequestURL(), Response.ERROR, requestDO.getClientAddr(), requestDO.getLocalAddr(), requestDO.getQueryString(), resultMsg);
        return responseData;
    }

    /**
     * HTTP HttpMediaTypeNotSupportedException
     *
     * @param request {@link HttpServletRequest}
     * @param ex      {@link HttpMediaTypeNotSupportedException}
     * @return {@link ResponseData}
     */
    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    @ResponseBody
    public ResponseData httpMediaTypeNotSupportedExceptionHandler(HttpServletRequest request, HttpMediaTypeNotSupportedException ex) {
        ResponseData responseData = new ResponseData<>();
        RequestDO requestDO = RequestUtils.getCurrentRequestDO(request);
        String resultMsg = String.format("HttpMediaTypeNotSupportedException: 传入值 [%s], 允许值 %s", ex.getContentType(), ex.getSupportedMediaTypes());
        responseData.setError(resultMsg);
        String outputLog = "httpMediaTypeNotSupportedExceptionHandler [method={} url={}  code={} from={} to={} parameters=({}) msg=({})]";
        logger.error(outputLog, requestDO.getMethod(), requestDO.getRequestURL(), Response.ERROR, requestDO.getClientAddr(), requestDO.getLocalAddr(), requestDO.getQueryString(), resultMsg);
        return responseData;
    }

    /**
     * HTTP HttpMediaTypeNotAcceptable
     *
     * @param request {@link HttpServletRequest}
     * @param ex      {@link HttpMediaTypeNotAcceptableException}
     * @return {@link ResponseData}
     */
    @ExceptionHandler({HttpMediaTypeNotAcceptableException.class})
    @ResponseBody
    public ResponseData httpMediaTypeNotAcceptableExceptionHandler(HttpServletRequest request, HttpMediaTypeNotAcceptableException ex) {
        ResponseData responseData = new ResponseData<>();
        RequestDO requestDO = RequestUtils.getCurrentRequestDO(request);
        String resultMsg = String.format("HttpMediaTypeNotAcceptable: 允许值 %s", ex.getSupportedMediaTypes());
        responseData.setError(resultMsg);
        String outputLog = "httpMediaTypeNotAcceptableExceptionHandler [method={} url={}  code={} from={} to={} parameters=({}) msg=({})]";
        logger.error(outputLog, requestDO.getMethod(), requestDO.getRequestURL(), Response.ERROR, requestDO.getClientAddr(), requestDO.getLocalAddr(), requestDO.getQueryString(), resultMsg);
        return responseData;
    }

    /**
     * HTTP MissingPathVariableException
     *
     * @param request {@link HttpServletRequest}
     * @param ex      {@link MissingPathVariableException}
     * @return {@link ResponseData}
     */
    @ExceptionHandler({MissingPathVariableException.class})
    @ResponseBody
    public ResponseData missingPathVariableExceptionHandler(HttpServletRequest request, MissingPathVariableException ex) {
        ResponseData responseData = new ResponseData<>();
        RequestDO requestDO = RequestUtils.getCurrentRequestDO(request);
        String resultMsg = String.format("MissingPathVariableException:  缺失路径参数 %s， 类型 %s", ex.getVariableName(),
                ex.getParameter().getParameterType().getSimpleName());
        responseData.setError(resultMsg);
        String outputLog = "missingPathVariableExceptionHandler [method={} url={}  code={} from={} to={} parameters=({}) msg=({})]";
        logger.error(outputLog, requestDO.getMethod(), requestDO.getRequestURL(), Response.ERROR, requestDO.getClientAddr(), requestDO.getLocalAddr(), requestDO.getQueryString(), resultMsg);
        return responseData;
    }

    /**
     * HTTP MissingServletRequestParameterException
     *
     * @param request {@link HttpServletRequest}
     * @param ex      {@link MissingServletRequestParameterException}
     * @return {@link ResponseData}
     */
    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseBody
    public ResponseData missingServletRequestParameterExceptionHandler(HttpServletRequest request, MissingServletRequestParameterException ex) {
        ResponseData responseData = new ResponseData<>();
        RequestDO requestDO = RequestUtils.getCurrentRequestDO(request);
        String resultMsg = String.format("MissingServletRequestParameterException:  缺失请求参数 %s， 类型 %s", ex.getParameterName(),
                ex.getParameterType());
        responseData.setError(resultMsg);
        String outputLog = "missingServletRequestParameterExceptionHandler [method={} url={}  code={} from={} to={} parameters=({}) msg=({})]";
        logger.error(outputLog, requestDO.getMethod(), requestDO.getRequestURL(), Response.ERROR, requestDO.getClientAddr(), requestDO.getLocalAddr(), requestDO.getQueryString(), resultMsg);
        return responseData;
    }

    /**
     * HTTP ServletRequestBindingException
     *
     * @param request {@link HttpServletRequest}
     * @param ex      {@link ServletRequestBindingException}
     * @return {@link ResponseData}
     */
    @ExceptionHandler({ServletRequestBindingException.class})
    @ResponseBody
    public ResponseData servletRequestBindingExceptionHandler(HttpServletRequest request, ServletRequestBindingException ex) {
        ResponseData responseData = new ResponseData<>();
        RequestDO requestDO = RequestUtils.getCurrentRequestDO(request);
        String resultMsg = String.format("ServletRequestBindingException:  请求绑定异常 %s", ex.getCause());
        responseData.setError(resultMsg);
        String outputLog = "servletRequestBindingExceptionHandler [method={} url={}  code={} from={} to={} parameters=({}) msg=({})]";
        logger.error(outputLog, requestDO.getMethod(), requestDO.getRequestURL(), Response.ERROR, requestDO.getClientAddr(), requestDO.getLocalAddr(), requestDO.getQueryString(), resultMsg);
        return responseData;
    }

    /**
     * HTTP ConversionNotSupportedException
     *
     * @param request {@link HttpServletRequest}
     * @param ex      {@link ConversionNotSupportedException}
     * @return {@link ResponseData}
     */
    @ExceptionHandler({ConversionNotSupportedException.class})
    @ResponseBody
    public ResponseData conversionNotSupportedExceptionHandler(HttpServletRequest request, ConversionNotSupportedException ex) {
        ResponseData responseData = new ResponseData<>();
        RequestDO requestDO = RequestUtils.getCurrentRequestDO(request);
        String resultMsg = String.format("ConversionNotSupportedException:  属性 %s, 值 %s, 类型 %s, 目标类型 %s, " +
                        "错误码 %s", ex.getPropertyName(), ex.getValue(), ClassUtils.getDescriptiveType(ex.getValue()),
                ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : ex.getCause(), ex.getErrorCode());
        responseData.setError(resultMsg);
        String outputLog = "conversionNotSupportedExceptionHandler [method={} url={}  code={} from={} to={} parameters=({}) msg=({})]";
        logger.error(outputLog, requestDO.getMethod(), requestDO.getRequestURL(), Response.ERROR, requestDO.getClientAddr(), requestDO.getLocalAddr(), requestDO.getQueryString(), resultMsg);
        return responseData;
    }

    /**
     * HTTP TypeMismatchException
     *
     * @param request {@link HttpServletRequest}
     * @param ex      {@link TypeMismatchException}
     * @return {@link ResponseData}
     */
    @ExceptionHandler({TypeMismatchException.class})
    @ResponseBody
    public ResponseData typeMismatchExceptionHandler(HttpServletRequest request, TypeMismatchException ex) {
        ResponseData responseData = new ResponseData<>();
        RequestDO requestDO = RequestUtils.getCurrentRequestDO(request);
        String resultMsg = String.format("TypeMismatchException:  属性 %s, 值 %s, 类型 %s, 目标类型 %s, " +
                        "错误码 %s", ex.getPropertyName(), ex.getValue(), ClassUtils.getDescriptiveType(ex.getValue()),
                ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : ex.getCause(), ex.getErrorCode());
        responseData.setError(resultMsg);
        String outputLog = "typeMismatchExceptionHandler [method={} url={}  code={} from={} to={} parameters=({}) msg=({})]";
        logger.error(outputLog, requestDO.getMethod(), requestDO.getRequestURL(), Response.ERROR, requestDO.getClientAddr(), requestDO.getLocalAddr(), requestDO.getQueryString(), resultMsg);
        return responseData;
    }

    /**
     * HTTP HttpMessageNotReadableException
     *
     * @param request {@link HttpServletRequest}
     * @param ex      {@link HttpMessageNotReadableException}
     * @return {@link ResponseData}
     */
    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseBody
    public ResponseData httpMessageNotReadableExceptionHandler(HttpServletRequest request, HttpMessageNotReadableException ex) {
        ResponseData responseData = new ResponseData<>();
        RequestDO requestDO = RequestUtils.getCurrentRequestDO(request);
        String resultMsg = String.format("HttpMessageNotReadableException:  %s ", ex.getCause());
        responseData.setError(resultMsg);
        String outputLog = "httpMessageNotReadableExceptionHandler [method={} url={}  code={} from={} to={} parameters=({}) msg=({})]";
        logger.error(outputLog, requestDO.getMethod(), requestDO.getRequestURL(), Response.ERROR, requestDO.getClientAddr(), requestDO.getLocalAddr(), requestDO.getQueryString(), resultMsg);
        return responseData;
    }

    /**
     * HTTP HttpMessageNotWritableException
     *
     * @param request {@link HttpServletRequest}
     * @param ex      {@link HttpMessageNotWritableException}
     * @return {@link ResponseData}
     */
    @ExceptionHandler({HttpMessageNotWritableException.class})
    @ResponseBody
    public ResponseData httpMessageNotWritableExceptionHandler(HttpServletRequest request, HttpMessageNotWritableException ex) {
        ResponseData responseData = new ResponseData<>();
        RequestDO requestDO = RequestUtils.getCurrentRequestDO(request);
        String resultMsg = String.format("HttpMessageNotWritableException:  %s ", ex.getCause());
        responseData.setError(resultMsg);
        String outputLog = "httpMessageNotWritableExceptionHandler [method={} url={}  code={} from={} to={} parameters=({}) msg=({})]";
        logger.error(outputLog, requestDO.getMethod(), requestDO.getRequestURL(), Response.ERROR, requestDO.getClientAddr(), requestDO.getLocalAddr(), requestDO.getQueryString(), resultMsg);
        return responseData;
    }

    /**
     * HTTP MethodArgumentNotValidException
     *
     * @param request {@link HttpServletRequest}
     * @param ex      {@link MethodArgumentNotValidException}
     * @return {@link ResponseData}
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    public ResponseData methodArgumentNotValidExceptionHandler(HttpServletRequest request, MethodArgumentNotValidException ex) {
        ResponseData responseData = new ResponseData<>();
        RequestDO requestDO = RequestUtils.getCurrentRequestDO(request);
        String resultMsg = String.format("MethodArgumentNotValidException:  Validation failed for argument at " +
                        "index %s in method: %s, with %s error(s) %s", ex.getParameter().getParameterIndex(),
                ex.getParameter().getExecutable().toGenericString(), ex.getBindingResult().getErrorCount(),
                ex.getBindingResult().getAllErrors());
        responseData.setError(resultMsg);
        String outputLog = "methodArgumentNotValidExceptionHandler [method={} url={}  code={} from={} to={} parameters=({}) msg=({})]";
        logger.error(outputLog, requestDO.getMethod(), requestDO.getRequestURL(), Response.ERROR, requestDO.getClientAddr(), requestDO.getLocalAddr(), requestDO.getQueryString(), resultMsg);
        return responseData;
    }

    /**
     * HTTP MissingServletRequestPartException
     *
     * @param request {@link HttpServletRequest}
     * @param ex      {@link MissingServletRequestPartException}
     * @return {@link ResponseData}
     */
    @ExceptionHandler({MissingServletRequestPartException.class})
    @ResponseBody
    public ResponseData missingServletRequestPartExceptionHandler(HttpServletRequest request, MissingServletRequestPartException ex) {
        ResponseData responseData = new ResponseData<>();
        RequestDO requestDO = RequestUtils.getCurrentRequestDO(request);
        String resultMsg = String.format("MissingServletRequestPartException:  request part %s 丢失",
                ex.getRequestPartName());
        responseData.setError(resultMsg);
        String outputLog = "missingServletRequestPartExceptionHandler [method={} url={}  code={} from={} to={} parameters=({}) msg=({})]";
        logger.error(outputLog, requestDO.getMethod(), requestDO.getRequestURL(), Response.ERROR, requestDO.getClientAddr(), requestDO.getLocalAddr(), requestDO.getQueryString(), resultMsg);
        return responseData;
    }

    /**
     * HTTP BindException
     *
     * @param request {@link HttpServletRequest}
     * @param ex      {@link BindException}
     * @return {@link ResponseData}
     */
    @ExceptionHandler({BindException.class})
    @ResponseBody
    public ResponseData bindExceptionHandler(HttpServletRequest request, BindException ex) {
        ResponseData responseData = new ResponseData<>();
        RequestDO requestDO = RequestUtils.getCurrentRequestDO(request);
        String resultMsg = String.format("BindException:  %s", ex.getAllErrors());
        responseData.setError(resultMsg);
        String outputLog = "bindExceptionHandler [method={} url={}  code={} from={} to={} parameters=({}) msg=({})]";
        logger.error(outputLog, requestDO.getMethod(), requestDO.getRequestURL(), Response.ERROR, requestDO.getClientAddr(), requestDO.getLocalAddr(), requestDO.getQueryString(), resultMsg);
        return responseData;
    }

    /**
     * HTTP NoHandlerFoundException
     *
     * @param request {@link HttpServletRequest}
     * @param ex      {@link NoHandlerFoundException}
     * @return {@link ResponseData}
     */
    @ExceptionHandler({NoHandlerFoundException.class})
    @ResponseBody
    public ResponseData noHandlerFoundExceptionHandler(HttpServletRequest request, NoHandlerFoundException ex) {
        ResponseData responseData = new ResponseData<>();
        RequestDO requestDO = RequestUtils.getCurrentRequestDO(request);
        String resultMsg = String.format("NoHandlerFoundException:  [%s] %s 不存在匹配路径 ", ex.getHttpMethod(),
                ex.getRequestURL());
        responseData.setError(resultMsg);
        String outputLog = "noHandlerFoundExceptionHandler [method={} url={}  code={} from={} to={} parameters=({}) msg=({})]";
        logger.error(outputLog, requestDO.getMethod(), requestDO.getRequestURL(), Response.ERROR, requestDO.getClientAddr(), requestDO.getLocalAddr(), requestDO.getQueryString(), resultMsg);
        return responseData;
    }

    /**
     * HTTP AsyncRequestTimeoutException
     *
     * @param request {@link HttpServletRequest}
     * @param ex      {@link AsyncRequestTimeoutException}
     * @return {@link ResponseData}
     */
    @ExceptionHandler({AsyncRequestTimeoutException.class})
    @ResponseBody
    public ResponseData asyncRequestTimeoutExceptionHandler(HttpServletRequest request, AsyncRequestTimeoutException ex) {
        ResponseData responseData = new ResponseData<>();
        RequestDO requestDO = RequestUtils.getCurrentRequestDO(request);
        String resultMsg = String.format("NoHandlerFoundException:  请求超时 %s ", ex.getCause());
        responseData.setError(resultMsg);
        String outputLog = "asyncRequestTimeoutExceptionHandler [method={} url={}  code={} from={} to={} parameters=({}) msg=({})]";
        logger.error(outputLog, requestDO.getMethod(), requestDO.getRequestURL(), Response.ERROR, requestDO.getClientAddr(), requestDO.getLocalAddr(), requestDO.getQueryString(), resultMsg);
        return responseData;
    }

    // *********************************************** DefaultHandlerExceptionResolver end ***********************************************

    /**
     * MethodArgumentTypeMismatchException
     *
     * @param request {@link HttpServletRequest}
     * @param ex      {@link MethodArgumentTypeMismatchException}
     * @return {@link ResponseData}
     */
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    @ResponseBody
    public ResponseData methodArgumentTypeMismatchExceptionHandler(HttpServletRequest request, MethodArgumentTypeMismatchException ex) {
        ResponseData responseData = new ResponseData<>();
        RequestDO requestDO = RequestUtils.getCurrentRequestDO(request);
        String resultMsg = String.format("参数类型不匹配, 参数 %s(%s), 类型应该为 %s", ex.getName(), ex.getValue(),
                ex.getRequiredType() == null ? ex.getRequiredType() : ex.getRequiredType().getSimpleName());
        String outputLog = "methodArgumentTypeMismatchExceptionHandler [method={} url={}  code={} from={} to={} parameters=({}) msg=({})]";
        logger.error(outputLog, requestDO.getMethod(), requestDO.getRequestURL(), Response.ERROR, requestDO.getClientAddr(), requestDO.getLocalAddr(), requestDO.getQueryString(), resultMsg);
        responseData.setError(Response.INVALID_PARAMETER, resultMsg);
        return responseData;
    }

    /**
     * IllegalArgumentException
     *
     * @param request {@link HttpServletRequest}
     * @param ex      {@link IllegalArgumentException}
     * @return {@link ResponseData}
     */
    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseBody
    public ResponseData illegalArgumentExceptionHandler(HttpServletRequest request, HttpServletResponse response, IllegalArgumentException ex) {
        ResponseData responseData = new ResponseData<>();
        RequestDO requestDO = RequestUtils.getCurrentRequestDO(request);
        String resultMsg = String.format("非法参数 %s", ex.getMessage());
        String outputLog = "illegalArgumentExceptionHandler [method={} url={}  code={} from={} to={} parameters=({}) msg=({})]";
        logger.error(outputLog, requestDO.getMethod(), requestDO.getRequestURL(), Response.ERROR, requestDO.getClientAddr(), requestDO.getLocalAddr(), requestDO.getQueryString(), resultMsg);
        responseData.setError(Response.INVALID_PARAMETER, resultMsg);
        return responseData;
    }

    /**
     * IOException
     *
     * @param request {@link HttpServletRequest}
     * @param ex      {@link IOException}
     * @return {@link ResponseData}
     */
    @ExceptionHandler({IOException.class})
    @ResponseBody
    public ResponseData ioExceptionHandler(HttpServletRequest request, HttpServletResponse response, IOException ex) {
        ResponseData responseData = new ResponseData<>();
        RequestDO requestDO = RequestUtils.getCurrentRequestDO(request);
        String resultMsg = String.format("IO异常 %s", ex.getMessage());
        String outputLog = "ioExceptionHandler [method={} url={}  code={} from={} to={} parameters=({}) msg=({})]";
        logger.error(outputLog, requestDO.getMethod(), requestDO.getRequestURL(), Response.ERROR, requestDO.getClientAddr(), requestDO.getLocalAddr(), requestDO.getQueryString(), resultMsg);
        responseData.setError(Response.INVALID_PARAMETER, resultMsg);
        return responseData;
    }

}
