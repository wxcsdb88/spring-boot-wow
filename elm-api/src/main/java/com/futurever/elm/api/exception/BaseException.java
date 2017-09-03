package com.futurever.elm.api.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wxcsdb88 on 2017/8/25 9:29.
 * 异常封装, 自定义的异常如果想获得行数等信息必须继承 BaseException
 */
public class BaseException extends RuntimeException {
    private static final long serialVersionUID = 4260602878793498888L;

    private static Logger logger = LoggerFactory.getLogger(BaseException.class);

    // 异常码
    private Integer code;
    // 具体出错的数据或者其他相关数据
    private Object data;

    private String message;

    private Throwable cause;

    /************************** debug info start **************************/
    private String className;
    private String fileName;
    private String methodName;
    private Integer lineNumber;

    /************************** debug info end **************************/

    public BaseException(Integer code, String message, Object data, Throwable cause) {
//        super(message, cause);
        this.code = code;
        this.message = message;
        this.data = data;
        this.cause = cause;

        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        int realStackNum = 0;
        String declaringClass = this.getClass().getName();
        String invokeClassName = this.getClass().getSimpleName();
        for (StackTraceElement stackTraceElement : stackTraceElements) {
//            if (declaringClass.equals(stackTraceElement.getClassName()) &&
//                    !"getStackTrace".equals(stackTraceElement.getMethodName()) &&
//                    !"<init>".equals(stackTraceElement.getMethodName())) {
//                break;
//            }
            if (!"getStackTrace".equals(stackTraceElement.getMethodName()) &&
                    !"<init>".equals(stackTraceElement.getMethodName())) {
                break;
            }
            realStackNum++;
        }
        fileName = stackTraceElements[realStackNum].getFileName();
        methodName = stackTraceElements[realStackNum].getMethodName();
        className = stackTraceElements[realStackNum].getClassName();
        lineNumber = stackTraceElements[realStackNum].getLineNumber();
        String out = "{} {}.{}({}:{})";
        logger.error(out, invokeClassName, className, methodName, fileName, lineNumber);
    }

    public BaseException(Integer code, String message, Object data) {
        this(code, message, data, null);
    }

    public BaseException(Integer code, String message) {
        this(code, message, null, null);
    }

    public BaseException(String message, Throwable cause) {
        this(null, message, null, cause);
    }

    public BaseException() {
    }

    public BaseException(String message) {
        this(null, message, null, null);
    }

    // 需要抛出的异常，慎用
//    public BaseException(Throwable cause) {
//        super(cause);
//    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public Throwable getCause() {
        return cause;
    }

    public void setCause(Throwable cause) {
        this.cause = cause;
    }

    public String getClassName() {
        return className;
    }

    public String getFileName() {
        return fileName;
    }

    public String getMethodName() {
        return methodName;
    }

    public Integer getLineNumber() {
        return lineNumber;
    }
}
