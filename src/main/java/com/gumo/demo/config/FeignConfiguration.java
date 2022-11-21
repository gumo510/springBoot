//package com.gumo.demo.config;
//
//import com.intellif.vesionbook.building.security.SecurityUtils;
//import feign.RequestInterceptor;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang.StringUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.context.annotation.Bean;
//
///**
// * @author yangsz
// * @since 2.3.0
// */
//@Slf4j
//public class FeignConfiguration {
//
//    @Bean
//    public RequestInterceptor tokenRequestInterceptor() {
//        return requestTemplate -> {
//            try {
//                String token = SecurityUtils.getRequestToken();
//                if (StringUtils.isNotBlank(token)) {
//                    requestTemplate.header("token", token);
//                    requestTemplate.header("Authorization", SecurityUtils.TOKEN_TYPE + token);
//                }
//            }catch (Exception e){
//                log.warn("未找到token：{}",e);
//            }
//        };
//    }
//
//}
