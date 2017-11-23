package io.github.xyzc1988.exception;

/**
 * Created by zhangcheng on 2017/11/18 14:14.
 */
public class PermissionException extends RuntimeException {

    private boolean status;

    public PermissionException() {
        super();
    }


    public PermissionException(String message) {
        super(message);
    }


    public PermissionException(String message, Throwable cause) {
        super(message, cause);
    }


    public PermissionException(boolean success, String message) {
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