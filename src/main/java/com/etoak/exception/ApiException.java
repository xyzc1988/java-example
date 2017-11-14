package com.etoak.exception;

/**
 * Created by zhangcheng on 2017/11/14 10:56.
 */
public class ApiException extends RuntimeException {

    /**
     * 错误编码
     */
    private String errorCode;

    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.setErrorCode(errorCode);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
