package io.github.xyzc1988.common.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author liwei
 * <p><B>last update </B> by liwei @ 2014-12-16</p>
 */
@Component
public enum SessionUtil {

    /**
     * 验证码
     */
    IMG_CODE("login.imgcode"),

    /**
     * 当前登录用户
     */
    CURRENT_USER("current.user"),

    /**
     * 当前登录用户所拥有的所有菜单
     */
    CURRENT_USER_MENU("current.user.menu"),
    /**
     * 当前登录用户所拥有的所有菜单MAP
     */
    CURRENT_USER_MENU_MAP("current.user.menu.map"),

    /**
     * 当前登录用户所拥有的所有菜单树
     */
    CURRENT_USER_MENU_TREE("current.user.menu.tree"),

    /**
     * 当前登录用户的恶意代码缓存map，退出失效
     */
    CURRENT_USER_YARA_MAP("current.user.yara.map"),
    ;

    /**
     *
     */
    private SessionUtil(String key) {
        this.key = key;
    }

    @Autowired
    private HttpSession session;

    private String key;
    private boolean disable = false;

    public <E> void save(E object){
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
        session.setAttribute(this.key, object);
    }

    @SuppressWarnings("unchecked")
    public <E> E getObject(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        if (session.getAttribute(this.key) == null) return null;
        return (E)session.getAttribute(this.key);
    }

    public void remove(){
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
        session.removeAttribute(this.key);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }
}
