package io.github.xyzc1988.interceptor;

import com.alibaba.fastjson.JSON;
import io.github.xyzc1988.annotation.Auth;
import io.github.xyzc1988.exception.PermissionException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangcheng on 2017/11/16.
 */
public class AuthorityInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(AuthorityInterceptor.class);

    @Resource
    private HttpSession httpSession;

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
                             HttpServletResponse response, Object handler) throws IOException {

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod HandlerMethod = (HandlerMethod) handler;
        //获取注解
        Auth auth = HandlerMethod.getMethodAnnotation(Auth.class);
        if (auth == null) {
            return true;
        }

        String requestUri = request.getRequestURI();

        logger.info("进入权限拦截器:{}", requestUri);
        //采用ajax 提交的
        String ajaxHeader = request.getHeader("x-requested-with");
        String accept = request.getHeader("accept");
        /*  XMLHttpRequest application/json */
        if (StringUtils.isNotBlank(ajaxHeader) && StringUtils.isNotBlank(accept)) {
            throw new PermissionException();
            // response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            // try (OutputStream out = response.getOutputStream();
            //      PrintWriter pw = new PrintWriter(new OutputStreamWriter(out, "utf-8"));) {
            //     Map result = new HashMap();
            //     result.put("status", "error");
            //     result.put("msg", "没有权限");
            //     pw.write(JSON.toJSONString(result));
            // }
        } else {
            logger.info("interceptor：跳转到loggerin页面！");
            // request.getRequestDispatcher("/WEB-INF/error/error.jsp").forward(request,response);
            response.sendRedirect("https://www.baidu.com/?redirect=" + request.getRequestURL());

        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception e) throws Exception {

    }
}
