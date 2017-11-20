package io.github.xyzc1988.interceptor;

import com.alibaba.fastjson.JSON;
import io.github.xyzc1988.annotation.Auth;
import io.github.xyzc1988.common.bean.Result;
import io.github.xyzc1988.exception.PermissionException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangcheng on 2017/11/16.
 */
public class MyInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(MyInterceptor.class);
    /**
     * 在业务处理器处理请求之前被调用
     * 如果返回false
     * 从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
     * 如果返回true
     * 执行下一个拦截器,直到所有的拦截器都执行完毕
     * 再执行被拦截的Controller
     * 然后进入拦截器链,
     * 从最后一个拦截器往回执行所有的postHandle()
     * 接着再从最后一个拦截器往回执行所有的afterCompletion()
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        logger.info("进入拦截器 preHandle");
        String requestUri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String url = requestUri.substring(contextPath.length());

        logger.info("requestUri:" + requestUri);
        logger.info("contextPath:" + contextPath);
        logger.info("url:" + url);

        if (handler instanceof DefaultServletHttpRequestHandler) {
            logger.info("DefaultServlet处理");
            return true;
        }

        HandlerMethod handler2 = (HandlerMethod) handler;
        //获取注解
        Auth auth = handler2.getMethodAnnotation(Auth.class);
        if (auth == null) {
            return true;
        }else {
            //采用ajax 提交的
            String isAjax = request.getHeader("x-requested-with");
            String accept = request.getHeader("accept");
        /*    XMLHttpRequest application/json*/
            if (StringUtils.isNotBlank(isAjax) &&StringUtils.isNotBlank(accept)) {
                response.setCharacterEncoding("utf-8");
                response.setContentType("application/json; charset=utf-8");
                OutputStream out = response.getOutputStream();
                PrintWriter pw = new PrintWriter(new OutputStreamWriter(out, "utf-8"));
                Map result = new HashMap();
                result.put("status", "error");
                result.put("msg", "没有权限");
                pw.write(JSON.toJSONString(result));
                pw.flush();
                pw.close();
              /*  request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request,response);*/
            } else {
                logger.info("interceptor：跳转到loggerin页面！");
               /* request.getRequestDispatcher("/WEB-INF/error/error.jsp").forward(request,response);*/
                response.sendRedirect("https://www.baidu.com/?redirect=" + request.getRequestURL());

            }
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception e) throws Exception {

    }
}
