package io.github.xyzc1988.annotation;

import java.lang.annotation.*;

/**
 * Created by zhangcheng on 2017/11/17 17:16.
 *
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Auth {

    String value() default "auth";
}
