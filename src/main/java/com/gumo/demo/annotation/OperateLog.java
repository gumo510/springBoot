package com.gumo.demo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * 标识方法用于操作日志打印
 * </p>
 *
 * @author hy
 * @since 2021-08-20 11:43
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OperateLog {

    String operateMethod();

    String description() default "";
}
