package io.github.xyzc1988.exception;

import io.github.xyzc1988.common.bean.Result;
import io.github.xyzc1988.common.bean.StatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangcheng on 2017/11/18 14:14.
 * @ControllerAdvice + @ExceptionHandler 实现全局的 Controller 层的异常处理
 */
@ControllerAdvice
public class ApiExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);

    /**
     * 处理所有不可知的异常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    Result handleException(Exception e){
        logger.error("拦截异常", e);
        Result result = new Result();
        result.setMessage("操作失败！");
        result.setStatus(false);
        return result;
    }

    /**
     * 处理所有业务异常
     * @param e
     * @return
     */
    @ExceptionHandler(ApiException.class)
    @ResponseBody
    Result handleApiException(ApiException e){
        logger.error("拦截业务异常", e);
        Result result = new Result();
        result.setMessage(e.getMessage());
        result.setStatus(false);
        return result;
    }

    /**
     * 处理AJAX请求无权限异常
     * @param e
     * @return
     */
    @ExceptionHandler(PermissionException.class)
    @ResponseBody
    Map handlePermissionException(PermissionException e){
        logger.error("拦截权限异常");
        Map result = new HashMap();
        result.put("status", StatusEnum.UN_AUTHORIZED.getStatus());
        result.put("msg", "你没有权限访问该模块!");
        return result;
    }
}