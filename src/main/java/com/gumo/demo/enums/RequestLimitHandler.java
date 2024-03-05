package com.gumo.demo.enums;

/**
 * <p>
 * 请求限制处理方式
 * </p>
 *
 * @author pbb
 * @since 2024-01-08 14:18
 */
public enum RequestLimitHandler {

    EXCEPTION, // 抛异常
    RET_DEFINE_V, // 返回定义值
    ;
}
