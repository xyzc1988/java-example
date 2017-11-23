package io.github.xyzc1988.common.bean;

/**
 * 状态枚举
 * <p>所有状态用此枚举进行赋值</p>
 *
 */
public enum StatusEnum {

    /**
     * 成功
     */
    SUCCESS("success",200),
    /**
     * 失败
     */
    ERROR("error",500),
    /**
     * 未登录
     */
    NOT_LOGIN("notlogin",401),
    /**
     * 权限不足
     */
    UN_AUTHORIZED("unauthorized",401),
    ;

    /**
     * 字符串代码
     */
    private String status;
    /**
     * 数字代码
     */
    private Integer code;

    /**
     * 构造函数
     * @param status 字符串代码
     * @param code 数字代码
     */
    private StatusEnum(String status, Integer code) {
        this.status = status;
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return this.status;
    }

    public Integer getCode() {
        return code;
    }
}
