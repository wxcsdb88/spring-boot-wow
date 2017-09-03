package com.futurever.elm.api.exception;

/**
 * Created by wxcsdb88 on 2017/8/25 9:50.
 * Service 层异常
 */
public class BusinessException extends BaseException {

    private static final long serialVersionUID = 7699879065066162048L;

    /************************** debug info end

     * @param
    code
     * @param
    message
     * @param
    data
     * @param cause
     **************************/
    public BusinessException(Integer code, String message, Object data, Throwable cause) {
        super(code, message, data, cause);
    }

    public BusinessException(Integer code, String message, Object data) {
        super(code, message, data);
    }

    public BusinessException(Integer code, String message) {
        super(code, message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
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
