package io.github.xyzc1988.annotation;

import org.apache.ibatis.annotations.ResultType;

import java.lang.annotation.*;

/**
 * Created by zhangcheng on 2017/11/17 17:16.
 */

@Target(ElementType.METHOD)  //注明注解中有方法
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Auth {
    /**
     * 默认的是page
     * @return
     */
    String value() default "auth";
}
