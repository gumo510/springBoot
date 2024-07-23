package com.gumo.demo.bizlog.config;


import com.mzt.logapi.beans.Operator;
import com.mzt.logapi.service.IOperatorGetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 获取操作人信息配置
 * </p>
 *
 * @author Evan
 * @since 2024-03-19 16:10
 */
@Configuration
@Slf4j
public class LogRecordConfiguration {

    @Bean
    public IOperatorGetService operatorGetService() {
        return () -> {
            try {
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                //通过工具类获取上下文的用户信息，如果没有获取到，返回null
               /* UserInfoExt userInfo = authorityUtil.getUserInfo(request.getHeader("Token"));
                if (Objects.nonNull(userInfo)) {
                    UserVo currUser = userInfo.getUserVo();
                    return new Operator(currUser.getId().toString());
                }*/
                return new Operator("1");
            } catch (Exception e) {
                log.warn("当前操作日志无法获取到操作人信息");
            }
            return new Operator("Unknown");
        };
    }

}
