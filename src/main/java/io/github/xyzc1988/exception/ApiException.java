package io.github.xyzc1988.exception;

/**
 * Created by zhangcheng on 2017/11/18 14:14.
 */
public class ApiException extends RuntimeException {

    private boolean status;

    public ApiException() {
        super();
    }


    public ApiException(String message) {
        super(message);
    }


    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }


    public ApiException(boolean success, String message) {
        super(message);
        this.setStatus(success);
    }


    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}