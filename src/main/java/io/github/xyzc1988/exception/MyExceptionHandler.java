package io.github.xyzc1988.exception;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangcheng on 2017/11/18 13:58.
 * 将此类注册为spring管理的bean
 */
public class MyExceptionHandler implements HandlerExceptionResolver {


    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
                                         Exception ex) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("ex", ex);

        // 根据不同错误转向不同页面
        if(ex instanceof ApiException) {
            return new ModelAndView("/WEB-INF/error/api-error.jsp", model);
        }else  {
            return new ModelAndView("/WEB-INF/error/error.jsp", model);
        }
    }
}