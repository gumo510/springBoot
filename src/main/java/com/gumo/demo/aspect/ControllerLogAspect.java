package com.gumo.demo.aspect;

import com.gumo.demo.annotation.OperateLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.sql.SQLException;

/**
 * AOP实现日志
 *
 * @author hy
 *
 */
@Component
@Aspect
public class ControllerLogAspect {

    /**
     * 环绕通知记录日志通过注解匹配到需要增加日志功能的方法
     *
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("@annotation(com.gumo.demo.annotation.OperateLog)")
    public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
        // 1.方法执行前的处理，相当于前置通知
        // 获取到类名
//        String targetName = pjp.getTarget().getClass().getName();
        // 获取方法签名
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        // 获取方法
        Method method = methodSignature.getMethod();
        // 获取到参数
        Object[] parameter = pjp.getArgs();

        // 获取方法上面的注解
        OperateLog operateLog = method.getAnnotation(OperateLog.class);
        // 获取操作描述的属性值
        String operateMethod = operateLog.operateMethod();
        String description = operateLog.description();
        // 创建一个日志对象(准备记录日志)
//        OnlineApiLog onlineApiLog = new OnlineApiLog();
//        onlineApiLog.setMethod(operateMethod);
//        onlineApiLog.setRemark(description);
//        onlineApiLog.setReq(JSONObject.toJSONString(parameter));
        // 整合了Struts，所有用这种方式获取session中属性(亲测有效)
//        User user = (User) ServletActionContext.getRequest().getSession().getAttribute("userinfo");//获取session中的user对象进而获取操作人名字
//        logtable.setOperateor(user.getUsername());// 设置操作人

        Object result = null;
        try {
            //让代理方法执行
            result = pjp.proceed();
            // 2.相当于后置通知(方法成功执行之后走这里)
//            onlineApiLog.setResp(JSONObject.toJSONString(result));// 设置操作结果
            if(result != null){
//                onlineApiLog.setRespCode(result.getRespCode());
//                onlineApiLog.setRespMessage(result.getRespMessage());
            }
        } catch (SQLException e) {
            // 3.相当于异常通知部分
//            onlineApiLog.setRespCode(RespCode.SERVER_EXCEPTION_CODE);
//            onlineApiLog.setResp(e.toString());// 设置操作结果
        } finally {
            // 4.相当于最终通知
//            onlineApiLogService.save(onlineApiLog);// 添加日志记录
        }
        return result;
    }


    /**
     * 获取真实的IP地址
     *
     * @param request
     * @return
     */
    private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
