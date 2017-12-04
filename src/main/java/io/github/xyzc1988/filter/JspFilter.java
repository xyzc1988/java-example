/**
 *
 */
package io.github.xyzc1988.filter;

import io.github.xyzc1988.common.utils.SessionUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 主要判断访问的jsp页面是否登录
 * @author liwei
 * <p><B>last update </B> by liwei @ 2016-3-7</p>
 */
public class JspFilter implements Filter {

    private List<String> excludeNames = new ArrayList<>();

    /**
     * 判断是否登录
     * <p>判断session有没有当前登录用户的对象</p>
     *
     * @author liwei
     *
     * @return
     */
    private boolean isLogin(HttpServletRequest request){
        HttpSession session = request.getSession();
        if (session.getAttribute(SessionUtil.SessionEnum.CURRENT_USER.getKey()) == null) return false;
        return true;
    }
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        excludeNames.addAll(Arrays.asList(filterConfig.getInitParameter("excludes").split(",")));
    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (request.getRequestURI() != null) {
            if (excludeNames.contains(new File(request.getRequestURI()).getName())) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }
        if(!isLogin(request)){
            response.sendRedirect(request.getContextPath()+"/login.jsp");
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
        return;
    }
    @Override
    public void destroy() {

    }

}
