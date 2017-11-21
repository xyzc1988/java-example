package io.github.xyzc1988.exception;

import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
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
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            try(OutputStream out = response.getOutputStream();
                PrintWriter pw = new PrintWriter(new OutputStreamWriter(out, "utf-8"));){
                Map result = new HashMap();
                result.put("status", "error");
                result.put("msg", "没有权限");
                // response.setStatus(HttpStatus.UNAUTHORIZED.value());
                pw.write(JSON.toJSONString(result));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }else  {
            return new ModelAndView("/WEB-INF/error/error.jsp", model);
        }
    }
}