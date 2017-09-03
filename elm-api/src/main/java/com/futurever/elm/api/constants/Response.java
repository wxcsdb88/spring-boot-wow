package com.futurever.elm.api.constants;

/**
 * Created by wxcsdb88 on 2017/8/18 15:34.
 * 01 unicontract
 * 02 unicontract-backend
 * 03 unichain
 */
public interface Response {
    // response msg
    String DEFAULT_OK_MSG = "operation success!";
    String DEFAULT_PARAMETER_ERROR_MSG = "parameter error!";
    String DEFAULT_ERROR_MSG = "operation failed!";

    //unicontract-backend 03
    //const HTTP_STATUS_CODE_OK = 200 // browser use!
    //1位(类型,1~9)+2位(项目编号,01~99)+3位(具体错误码)
    // response data code 业务级错误
    int OK = 0; // 正确操作
    // 001~099 HTTP request relation
    int CONTENT_TYPE_ERROR = 202001; // Content-Type 错误
    int METHOD_ERROR = 202002; // request method 错误
    int MISSING_REQUIRED_PARAMETER = 202003; // 必须参数丢失, token, sign, timestamp
    int INVALID_HEADERS = 202004; // 请求头错误
    int INVALID_PARAMETER = 202005; // 请求参数错误
    int INVALID_TIMESTAMP = 202006; // 请求参数 timestamp 非法
    int INVALID_TOKEN = 202007; // 请求参数 token 非法
    int INVALID_SIGN = 202008; // 请求参数 sign 非法
    int INVALID_APPID = 202009; // 请求参数 appId 非法
    int LOGIN_TIMEOUT = 202010; // 登录超时或者未登录，需要重新登录
    int ACCOUNT_LOCKED = 202011; // 账户已锁定或者未激活

    // controller PARAMETER_ERROR 101~199
    int PARAMETER_COUNT_ERROR = 202101; // 请求参数个数错误
    int PARAMETER_TYPE__ERROR = 202102; // 请求参数类型/格式 错误
    int PARAMETER_VALUE_ERROR = 202103; // 请求参数值错误, 值为空 或者范围等限制
    int PARAMETER_LENGTH_ERROR = 202104; // 请求参数长度错误
    int CONTRACT_MODEL_ERROR = 202105; // 合约 model 错误, invalid contractModel.Validate() false
    int CONTRACT_SIGNATURE_ERROR = 202106; // 合约签名错误

    // DB ERROR 201~299
    int DB_CONN_ERROR = 202201; // 数据库连接错误
    int DB_TIMEOUT_ERROR = 202202; // 数据库超时错误
    int DB_OP_ERROR = 202203; // 数据库操作错误

    // INTERNAL ERROR 301~399
    int INTERNAL_ERROR = 202301; // 程序内部处理错误
    int PROTO_ERROR = 202302; // proto处理错误

    // 系统级错误代码 101~999
    int SYSTEM_ERROR = 102101;// 系统错误
    int SERVICE_UNAVAILABLE = 102102; // 服务暂停
    int EMOTE_SERVICE_ERROR = 102103; // 远程服务错误
    int IP_LIMIT = 102104;// IP限制不能请求该资源
    int PERMISSION_DENIED = 102105; // 该资源需要appkey拥有授权
    int MISSING_APPKEY = 102106; // 缺少source (appkey) 参数
    int UNSUPPORT_MEDIATYPE = 102107; // 不支持的MediaType
    int JOB_EXPIRED = 102108; // 任务超时
    int RPC_ERROR = 102109; // RPC错误
    int ILLEGAL_REQUEST = 102110;// 非法请求
    int IP_REQUESTS_OUT_OF_RATE_LIMIT = 102111; // IP请求频次超过上限
    int APP_REQUESTS_OUT_OF_RATE_LIMIT = 102112;// 应用请求频次超过上限
    int NO_HANDLER_FOUND_EXCEPTION = 102404;// 404 error
    int SERVICE_EXCEPTION = 102500;// 500 error

    int DEFAULT_ERROR_CODE = SERVICE_EXCEPTION;

}
