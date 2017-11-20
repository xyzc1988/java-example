package io.github.xyzc1988.exception;

import com.etoak.exception.ApiException;
import io.github.xyzc1988.common.bean.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by zhangcheng on 2017/11/18 14:14.
 * @ControllerAdvice + @ExceptionHandler 实现全局的 Controller 层的异常处理
 */
@ControllerAdvice
public class ApiExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiExceptionHandler.class);

    /**
     * 处理所有不可知的异常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    Result handleException(Exception e){
        LOGGER.error(e.getMessage(), e);

        Result result = new Result();
        result.setMessage("操作失败！");
        return result;
    }

    /**
     * 处理所有业务异常
     * @param e
     * @return
     */
    @ExceptionHandler(ApiException.class)
    @ResponseBody
    Result handleBusinessException(ApiException e){
        LOGGER.error(e.getMessage(), e);

        Result result = new Result();
        result.setMessage(e.getMessage());
        return result;
    }
}