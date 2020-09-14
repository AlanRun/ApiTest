package com.alan.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnTargetParameter {

    /**
     * 定义注解的一个元素 并给定默认值
     * @return
     */
    String value() default "我是定义在参数上的注解元素value的默认值";
}
