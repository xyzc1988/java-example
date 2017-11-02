package io.github.xyzc1988.common.bean;

/**
 * 返回状态封装类
 * Created by zhangcheng on 2017/8/31.
 */
public class Result<T> {

    /**
     * 返回状态
     */
    private boolean status = true;

    /**
     * 返回信息
     */
    private String message;

    /**
     * 携带数据
     */
    private T data;


    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
