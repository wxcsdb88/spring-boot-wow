package com.futurever.demo.api.constants;

/**
 * description:
 *
 * @author : wxcsdb88
 * @since : 2017/10/10 15:20
 */
public interface Response {
    // response msg
    String DEFAULT_OK_MSG = "operation success!";
    String DEFAULT_PARAMETER_ERROR_MSG = "parameter error!";
    String DEFAULT_ERROR_MSG = "operation failed!";

    //const HTTP_STATUS_CODE_OK = 200 // browser use!
    //1位(类型,1~9)+2位(项目编号,01~99)+3位(具体错误码)
    // response data code 业务级错误
    int OK = 0; // 正确操作
    int ERROR = 202100;
    int INVALID_PARAMETER = 202400; // 请求参数错误
    int METHOD_ERROR = 20405; // request method 错误
    int NO_PRIVILEGE = 202403; // 无权访问
    int NO_HANDLER_FOUND_EXCEPTION = 102404;// 404 error
    int SERVICE_EXCEPTION = 102500;// 500 error
    int DEFAULT_ERROR_CODE = SERVICE_EXCEPTION;

}

