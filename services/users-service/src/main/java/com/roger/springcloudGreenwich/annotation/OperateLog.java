package com.roger.springcloudGreenwich.annotation;

import java.lang.annotation.*;

/**
 * Created by admin on 2019/11/4.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface OperateLog {
    /**
     * 资源的名字
     *
     * @return String
     */
    String name() default "";

    String prefix() default "";

    String key() default "";
}
