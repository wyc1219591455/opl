package me.zhengjie.modules.opl.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @program: eladmin
 * @description: 工单的log注解
 * @author: ming.cao
 * @create: 2021-02-04 11:30
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OrderLog {
    long id() default 0;
    String value() default "";
}
