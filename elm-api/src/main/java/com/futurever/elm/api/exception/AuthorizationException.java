package com.futurever.elm.api.exception;

/**
 * Created by wxcsdb88 on 2017/5/23 23:58.
 * <p>
 * 验证相关异常处理
 */
public class AuthorizationException extends BaseException {

    private static final long serialVersionUID = 2181363919153145332L;

    /************************** debug info end

     * @param
    code
     * @param
    message
     * @param
    data
     * @param cause
     **************************/
    public AuthorizationException(Integer code, String message, Object data, Throwable cause) {
        super(code, message, data, cause);
    }

    public AuthorizationException(Integer code, String message, Object data) {
        super(code, message, data);
    }

    public AuthorizationException(Integer code, String message) {
        super(code, message);
    }

    public AuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthorizationException() {
        super();
    }

    public AuthorizationException(String message) {
        super(message);
    }

    @Override
    public Integer getCode() {
        return super.getCode();
    }

    @Override
    public Object getData() {
        return super.getData();
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public Throwable getCause() {
        return super.getCause();
    }

    @Override
    public String getClassName() {
        return super.getClassName();
    }

    @Override
    public String getFileName() {
        return super.getFileName();
    }

    @Override
    public String getMethodName() {
        return super.getMethodName();
    }

    @Override
    public Integer getLineNumber() {
        return super.getLineNumber();
    }
}
