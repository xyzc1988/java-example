package io.github.xyzc1988.listener;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Created by zhangcheng on 2017/11/19.
 */
public class UserLogoutListener implements HttpSessionListener {
    protected final Log log = LogFactory.getLog(super.getClass());

    public void sessionCreated(HttpSessionEvent event) {
        this.log.error("session created. id = " + event.getSession().getId());
    }

    public void sessionDestroyed(HttpSessionEvent event) {
        this.log.error("session destroyed.id = " + event.getSession().getId());
        HttpSession session = event.getSession();
        ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
    }
}