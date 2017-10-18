package com.futurever.demo.api.constants;

/**
 * description:
 *
 * @author : wxcsdb88
 * @since : 2017/10/10 15:20
 */
public interface Response {
    /**
     * 默认成功消息
     */
    String DEFAULT_OK_MSG = "操作成功!";
    String DEFAULT_OK_MSG_EN = "operation success!";
    /**
     * 参数错误消息
     */
    String DEFAULT_PARAMETER_ERROR_MSG = "参数错误!";
    String DEFAULT_PARAMETER_ERROR_MSG_EN = "parameter error!";
    /**
     * 默认失败消息
     */
    String DEFAULT_ERROR_MSG = "操作失败!";
    String DEFAULT_ERROR_MSG_EN = "operation failed!";

    //const HTTP_STATUS_CODE_OK = 200 // browser use!
    //1位(类型,1~9)+2位(项目编号,01~99)+3位(具体错误码)
    // response data code 业务级错误
    /**
     * 正确操作
     */
    int OK = 0;
    int ERROR = 202100;

    /**
     * 403 无权访问
     */
    int NO_PRIVILEGE = 202403;


    /**
     * 400 Bad Request
     * HttpMessageNotReadableException
     * MissingServletRequestParameterException
     * TypeMismatchException
     */
    int BAD_REQUEST = 102400;
    int INVALID_PARAMETER = BAD_REQUEST;

    /**
     * 404 Not Found
     * NoSuchRequestHandlingMethodException
     */
    int NO_FOUND = 102404;
    int NO_HANDLER_FOUND_EXCEPTION = NO_FOUND;

    /**
     * 405 Method Not Allowed
     * HttpMessageNotWritableException
     */
    int METHOD_NOT_ALLOWED = 102405;
    int METHOD_ERROR = METHOD_NOT_ALLOWED;

    /**
     * 406 Not Acceptable
     * HttpMediaTypeNotAcceptableException
     */
    int NOT_ACCEPTABLE = 102406;

    /**
     * 415 Unsupported_Media_Type
     * HttpMediaTypeNotSupportedException
     */
    int UN_SUPPORTED_MEDIA_TYPE = 102415;
    /**
     * 500 Internal Server Error
     * ConversionNotSupportedException
     * HttpMessageNotWritableException
     */
    int INTERNAL_SERVER_ERROR = 102500;
    int DEFAULT_ERROR_CODE = INTERNAL_SERVER_ERROR;

}

