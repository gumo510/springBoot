package com.gumo.demo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * 根据参数类型包含orgId字段插入下级所有机构id
 * </p>
 *
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FillOrgId {

    String getFiled() default "";

    String setFiled() default "";

    Class<?> type();
}
