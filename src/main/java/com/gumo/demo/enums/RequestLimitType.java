package com.gumo.demo.enums;

/**
 * <p>
 * 请求限制类型
 * </p>
 *
 * @author pbb
 * @since 2024-01-08 14:18
 */
public enum RequestLimitType {

    QPS, // 每秒限流
    CONCURRENCY, // 同时处理的请求数限流
    ;
}
