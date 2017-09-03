package com.futurever.elm.api.exception;

/**
 * Created by wxcsdb88 on 2017/8/24 13:13.
 * <p>
 * Controller 层抛出异常处理
 */
public class ApiException extends BaseException {
    private static final long serialVersionUID = -2024180461665981041L;


    /************************** debug info end

     * @param
    code
     * @param
    message
     * @param
    data
     * @param cause
     **************************/
    public ApiException(Integer code, String message, Object data, Throwable cause) {
        super(code, message, data, cause);
    }

    public ApiException(Integer code, String message, Object data) {
        super(code, message, data);
    }

    public ApiException(Integer code, String message) {
        super(code, message);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiException() {
        super();
    }

    public ApiException(String message) {
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
