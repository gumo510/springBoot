//package com.gumo.demo.utils;
//
//import com.alibaba.fastjson.JSONObject;
//import com.intellif.vesionbook.building.exception.NoAuthenticationException;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import java.lang.reflect.Field;
//import java.lang.reflect.InvocationHandler;
//import java.lang.reflect.Proxy;
//import java.util.Map;
//
///**
// * permission util
// * @author yangsz
// * @since 2.3.0
// */
//public class SecurityUtils {
//    public static final String TOKEN_TYPE = "Bearer ";
//
//    /**
//     * validate permission unit on rest controller
//     */
//    static boolean validPermissionUnit(PermissionUnit unit){
//        return !unit.module().equals(PermissionModule.OVERWRITE);
//    }
//
//    /**
//     * replace permission's module with permission unit
//     */
//    @SuppressWarnings("unchecked")
//    static void replaceOverwritePermissionWithUnit(Permission permission, PermissionUnit unit){
//        if(permission.module()==PermissionModule.OVERWRITE){
//            try {
//                InvocationHandler invocationHandler = Proxy.getInvocationHandler(permission);
//                Field value = invocationHandler.getClass().getDeclaredField("memberValues");
//                value.setAccessible(true);
//                Map<String, Object> memberValues = (Map<String, Object>) value.get(invocationHandler);
//                memberValues.put("module", unit.module());
//            }catch (Exception e){
//                // ignore
//                e.printStackTrace();
//            }
//        }
//    }
//
//    /**
//     * get request authentication from redis cache
//     */
//    public static UserAuthentication getRequestAuthentication(StringRedisTemplate redisTemplate){
//        String userInfoJson = redisTemplate.opsForValue().get(getRequestToken());
//        if (StringUtils.isBlank(userInfoJson)) {
//            throw new NoAuthenticationException("expired or illegal user identity!");
//        } else {
//            try {
//                if (userInfoJson.indexOf("{") > 0) {
//                    userInfoJson = userInfoJson.substring(userInfoJson.indexOf("{"));
//                }
//                return JSONObject.parseObject(userInfoJson, UserAuthentication.class);
//            } catch (Exception e) {
//                throw new NoAuthenticationException("illegal user authentication!",e);
//            }
//        }
//    }
//
//    public static String getRequestToken(){
//        if(RequestContextHolder.getRequestAttributes() != null){
//            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//            HttpServletRequest request = requestAttributes.getRequest();
//            String reqToken = request.getHeader("Authorization");
//            String token = request.getHeader("token");
//            if (StringUtils.isBlank(reqToken)) {
//                reqToken = token;
//            }
//            if (StringUtils.isBlank(reqToken)) {
//                reqToken="";
////            throw new NoAuthenticationException("illegal token!");
//            } else {
//                if (reqToken.contains(TOKEN_TYPE)) {
//                    reqToken = reqToken.replace(TOKEN_TYPE, "").trim();
//                }
//            }
//            return reqToken;
//        }
//        return null;
//    }
//
//}
