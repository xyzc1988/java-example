package io.github.xyzc1988.exception;

/**
 * Created by zhangcheng on 2017/11/18 14:14.
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message){
        super(message);
    }
}