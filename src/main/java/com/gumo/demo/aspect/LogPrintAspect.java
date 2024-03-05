package com.gumo.demo.aspect;

import com.alibaba.fastjson.JSON;
import com.gumo.demo.annotation.LogPrint;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * <p>
 * 请求限制AOP
 * </p>
 *
 * @author pbb
 * @since 2024-01-08 14:21
 */
@Aspect
@Component
@Slf4j
public class LogPrintAspect {

    @Around("@annotation(logPrint)")
    public Object logPrint(ProceedingJoinPoint joinPoint, LogPrint logPrint) throws Throwable {
        LogLevel logLevel = logPrint.logLevel();

        StringBuilder logMessage = new StringBuilder("LogPrint【Method=" + joinPoint.getSignature().toShortString() + "】");

        long startTime = System.currentTimeMillis();
        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String exceptionStackTrace = sw.toString();
            log.error(logMessage + ",Exception caught:" + exceptionStackTrace);
            throw e;
        }
        long endTime = System.currentTimeMillis();

        if (logPrint.args()) {
            logMessage.append(getArgumentString(joinPoint.getArgs(), logPrint.maxLength()));
        }
        if (logPrint.result()) {
            logMessage.append(getResultString(result, logPrint.maxLength()));
        }

        logMessage.append(",【Time Consuming=").append(endTime - startTime).append("ms】");

        if (logLevel.equals(LogLevel.INFO)) {
            log.info(logMessage.toString());
        } else {
            log.debug(logMessage.toString());
        }

        return result;
    }

    private String getArgumentString(Object[] args, int maxLength) {
        StringBuilder argMessage = new StringBuilder();
        int num = 1;
        for (Object arg : args) {
            if (argMessage.length() >= maxLength) {
                break;
            }
            if (arg instanceof HttpServletResponse) {
                continue;
            }
            if (arg instanceof HttpServletRequest) {
                continue;
            }
            argMessage.append(",【Arg_").append(num).append("=").append(JSON.toJSONString(arg)).append("】");
            num++;
        }
        return argMessage.toString();
    }

    private String getResultString(Object result, int maxLength) {
        StringBuilder resultMessage = new StringBuilder(",【Result=");
        String resultStr = JSON.toJSONString(result);
        if (resultStr.length() > maxLength) {
            return resultMessage.append(resultStr, 0, maxLength).append("】").toString();
        }
        return resultMessage.append(resultStr).append("】").toString();
    }

}
