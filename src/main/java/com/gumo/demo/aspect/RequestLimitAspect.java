package com.gumo.demo.aspect;

import com.google.common.util.concurrent.RateLimiter;
import com.gumo.demo.annotation.RequestLimit;
import com.gumo.demo.enums.CommonResultCodeEnum;
import com.gumo.demo.enums.RequestLimitHandler;
import com.gumo.demo.enums.RequestLimitType;
import com.gumo.demo.exception.HttpRequestException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

/**
 * <p>
 * 方法日志打印
 * </p>
 *
 * @author pbb
 * @since 2024-01-08 14:21
 */
@Aspect
@Component
@Slf4j
public class RequestLimitAspect {
    private static final Map<String, Semaphore> concurrencySemaphores = new ConcurrentHashMap<>();
    private static final Map<String, RateLimiter> qpsRateLimiters = new ConcurrentHashMap<>();

    @Around("@annotation(limit)")
    public Object intercept(ProceedingJoinPoint joinPoint, RequestLimit limit) throws Throwable {
        switch (limit.limitType()) {
            case QPS:
                return qpsLimit(joinPoint, limit);
            case CONCURRENCY:
                return concurrencyLimit(joinPoint, limit);
            default:
                throw new HttpRequestException(CommonResultCodeEnum.REQUEST_LIMIT_TYPE_ERROR);
        }
    }

    /**
     * 并发处理限流实现
     *
     * @param joinPoint
     * @param limit
     * @return
     */
    private Object concurrencyLimit(ProceedingJoinPoint joinPoint, RequestLimit limit) throws Throwable {
        String apiCode = limit.apiCode();
        long timeout = limit.timeout();
        int value = limit.value();

        Semaphore semaphore = concurrencySemaphores.computeIfAbsent(apiCode, k -> {
            //第一次建立apiCode需打印设置的限流值，用于后续相同apicode但是其他属性不同导致的问题追溯
            log.info("接口限流参数初始化：ApiCode={} | LimitType={} | Value={} | Timeout={} | Unit={}", apiCode, limit.limitType().name(), value, limit.timeout(), limit.unit().name());
            return new Semaphore(value);
        });

        if ((timeout == 0 && semaphore.tryAcquire()) || (timeout != 0 && semaphore.tryAcquire(timeout, limit.unit()))) {
            try {
                // 处理业务逻辑
                return joinPoint.proceed();
            } finally {
                semaphore.release(); // 释放信号量许可
            }

        } else {
            //根据处理方式 处理
            log.debug("【Concurrency Request Limit】 | ApiCode = {}", apiCode);
            return limitHandler(joinPoint, limit);
        }
    }

    /**
     * QPS限流实现
     *
     * @param joinPoint
     * @param limit
     * @return
     */
    private Object qpsLimit(ProceedingJoinPoint joinPoint, RequestLimit limit) throws Throwable {
        String apiCode = limit.apiCode();
        long timeout = limit.timeout();
        int value = limit.value();

        RateLimiter qpsRateLimiter = qpsRateLimiters.computeIfAbsent(apiCode, k -> {
            //第一次建立apiCode需打印设置的限流值，用于后续相同apicode但是其他属性不同导致的问题追溯
            log.info("接口限流参数初始化：ApiCode={} | LimitType={} | Value={} | Timeout={} | Unit={}", apiCode, limit.limitType().name(), value, limit.timeout(), limit.unit().name());
            return RateLimiter.create(value);
        });

        if ((timeout == 0 && qpsRateLimiter.tryAcquire()) || (timeout != 0 && qpsRateLimiter.tryAcquire(timeout, limit.unit()))) {
            // 处理业务逻辑
            return joinPoint.proceed();
        } else {
            //根据处理方式 处理
            log.debug("【Qps Request Limit】 | ApiCode = {}", apiCode);
            return limitHandler(joinPoint, limit);
        }
    }

    /**
     * 请求限制后,针对不同处理方式进行处理
     *
     * @param joinPoint
     * @param limit
     * @return
     */
    private Object limitHandler(ProceedingJoinPoint joinPoint, RequestLimit limit) {
        RequestLimitHandler requestLimitHandler = limit.limitHandler();
        RequestLimitType requestLimitType = limit.limitType();
        String retDefineV = limit.retDefineV();

        switch (requestLimitHandler) {
            case RET_DEFINE_V:
                // 获取目标方法的返回值类型
                MethodSignature signature = (MethodSignature) joinPoint.getSignature();
                Class<?> returnType = signature.getReturnType();
                return retDefineVLimitHandler(returnType, retDefineV, requestLimitType);
            case EXCEPTION:
                return exceptionLimitHandler(requestLimitType);
            default:
                //返回异常
                throw new HttpRequestException(CommonResultCodeEnum.REQUEST_LIMIT_HANDLER_ERROR);
        }
    }

    /**
     * 自定义返回值处理
     *
     * @param retType
     * @param retDefineV
     * @return
     */
    private Object retDefineVLimitHandler(Class<?> retType, String retDefineV, RequestLimitType limitType) {
        //1.如果目标方法的返回值为非基本数据类型（以及包装类）或者String（其余统一回归到异常返回处理）;2.如果defineV的值转换失败，也回退到异常处理
        try {
            if (retType == boolean.class || retType == Boolean.class) {
                return Boolean.valueOf(retDefineV);
            } else if (retType == byte.class || retType == Byte.class) {
                return Byte.valueOf(retDefineV);
            } else if (retType == short.class || retType == Short.class) {
                return Short.valueOf(retDefineV);
            } else if (retType == int.class || retType == Integer.class) {
                return Integer.valueOf(retDefineV);
            } else if (retType == long.class || retType == Long.class) {
                return Long.valueOf(retDefineV);
            } else if (retType == float.class || retType == Float.class) {
                return Float.valueOf(retDefineV);
            } else if (retType == double.class || retType == Double.class) {
                return Double.valueOf(retDefineV);
            } else if (retType == char.class || retType == Character.class) {
                return retDefineV.charAt(0);
            } else if (retType == String.class) {
                return retDefineV;
            }
        } catch (Exception e) {
            log.error("限流自定义返回值处理器处理注解的定义值异常！返回类型：{}，定义值：{}", retType, retDefineV);
        }

        //回归到异常处理
        return exceptionLimitHandler(limitType);
    }

    /**
     * 异常处理器
     *
     * @param requestLimitType
     */
    private Object exceptionLimitHandler(RequestLimitType requestLimitType) {
        switch (requestLimitType) {
            case QPS:
                throw new HttpRequestException(CommonResultCodeEnum.REQUEST_QPS_LIMIT);
            case CONCURRENCY:
                throw new HttpRequestException(CommonResultCodeEnum.REQUEST_CONCURRENCY_LIMIT);
            default:
                throw new HttpRequestException(CommonResultCodeEnum.REQUEST_LIMIT_TYPE_ERROR);
        }
    }
}
