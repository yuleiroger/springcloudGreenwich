package com.roger.springcloudGreenwich.annotation;

import com.roger.springcloudGreenwich.enums.LimitType;

import java.lang.annotation.*;

/**
 * Created by admin on 2019/9/18.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Limit {
    /**
     * 资源的名字
           *
           * @return String
     */
     String name() default "";

    /**
           * 资源的key
     28      *
     29      * @return String
     30      */
    String key() default "";

    /**
     34      * Key的prefix
     35      *
     36      * @return String
     37      */
     String prefix() default "";

    /**
     41      * 给定的时间段
     42      * 单位秒
     43      *
     44      * @return int
     45      */
     int period();

    /**
     49      * 最多的访问限制次数
     50      *
     51      * @return int
     52      */
     int count();

    /**
     56      * 类型
     57      *
     58      * @return LimitType
     59      */
     LimitType limitType() default LimitType.CUSTOMER;
}
