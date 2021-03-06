package io.github.xyzc1988.common.utils;


import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * Session操作工具类
 */
@Component
public class SessionUtil {

    @Resource
    private HttpSession httpSession;

    //静态方法初始化类
    private static SessionUtil instance;

    static {
        instance = new SessionUtil();
    }

    public <E> void save(SessionEnum sessionEnu, E object) {
        httpSession.setAttribute(sessionEnu.key, object);
    }

    @SuppressWarnings("unchecked")
    public <E> E getObject(SessionEnum sessionEnum) {
        if (httpSession.getAttribute(sessionEnum.key) == null) return null;
        return (E) httpSession.getAttribute(sessionEnum.key);
    }

    public void remove(SessionEnum sessionEnu) {
        httpSession.removeAttribute(sessionEnu.key);
    }


    public enum SessionEnum {
        /**
         * 验证码
         */
        IMG_CODE("loginImgcode"),
        /**
         * 当前登录用户
         */
        CURRENT_USER("currentUser"),
        /**
         * 当前登录用户所拥有的所有菜单MAP
         */
        CURRENT_USER_MENU_MAP("currentUserMenuMap"),
        /**
         * 当前登录用户所拥有的所有菜单名称Set
         */
        CURRENT_USER_MENU_SET("currentUserMenuSet"),

        /**
         * 当前登录用户的恶意代码缓存map，退出失效
         */
        CURRENT_USER_YARA_MAP("currentUserYaraMap"),;

        private SessionEnum(String key) {
            this.key = key;
        }

        private String key;
        private boolean disable = false;

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

}
