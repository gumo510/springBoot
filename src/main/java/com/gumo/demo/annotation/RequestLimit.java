package com.gumo.demo.annotation;


import com.gumo.demo.enums.RequestLimitHandler;
import com.gumo.demo.enums.RequestLimitType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 用在controller方法或普通方法上，用于限制处理的请求
 * </p>
 *
 * @author pbb
 * @since 2024-01-08 14:19
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestLimit {

    String apiCode(); // 唯一编号（接口方法的话建议使用path，普通方法建议使用类的全路径名+方法名）

    RequestLimitType limitType() default RequestLimitType.CONCURRENCY; // 限流类型，默认QPS

    int value() default 1; //限流值，默认每秒1个 或者 同时处理请求数

    long timeout() default 0; // 等待超时时间

    TimeUnit unit() default TimeUnit.SECONDS; //等待超时单位

    RequestLimitHandler limitHandler() default RequestLimitHandler.EXCEPTION; //请求限制后处理方式，默认抛异常

    String retDefineV() default ""; //针对返回定义值的限制处理器，只支持基本数据类型以及String类型
}
