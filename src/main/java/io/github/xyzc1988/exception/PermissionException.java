package io.github.xyzc1988.exception;

/**
 * Created by zhangcheng on 2017/11/18 11:03.
 */
public class PermissionException extends RuntimeException{

    public PermissionException() {
        super();
    }


    public PermissionException(String message) {
        super(message);
    }


    public PermissionException(String message, Throwable cause) {
        super(message, cause);
    }


    public PermissionException(Throwable cause) {
        super(cause);
    }


    protected PermissionException(String message, Throwable cause,
                                  boolean enableSuppression,
                                  boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
