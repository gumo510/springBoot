package com.gumo.demo.annotation;

import org.springframework.boot.logging.LogLevel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * 用于打印方法的耗时，是否异常，入参，返回值等
 * </p>
 *
 * @author pbb
 * @since 2024-01-08 14:19
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogPrint {

    LogLevel logLevel() default LogLevel.DEBUG; //打印的日志级别 （目前只打印debug，info设置别的级别退回使用debug）

    boolean args() default false; // 是否打印请求参数

    boolean result() default false; // 是否打印返回值

    int maxLength() default 1024; // 打印长度限制,分别作用于入参和返回值（若超过就截取打印）
}
