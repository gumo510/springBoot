package com.gumo.demo.feign.fallback;

import com.gumo.demo.feign.WxworkClient;
import com.gumo.demo.model.wxwork.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * Description：企业微信服务降级工厂类
 *
 * @author：Gumo
 * @date：2023/3/28 16:26
 * @version: 1.0
 */
@Slf4j
@Component
public class WxworkClientFallbackFactory implements FallbackFactory<WxworkClient> {

    @Override
    public WxworkClient create(Throwable throwable) {
        return new WxworkClient() {

            @Override
            public AccessTokenResp getAccessToken(String corpId, String corpSecret) {
                log.error("企业微信feign-获取AccessToken异常 >>> corpId:{}, corpSecret:{}, 异常信息:{}", corpId, corpSecret, throwable.getMessage());
                return new AccessTokenResp(throwable.getMessage());
            }

            @Override
            public WxCurUserTicket authorUser(String accessToken, String code) {
                log.error("企业微信feign-获取登录用户信息异常 >>> accessToken:{}, code:{}, 异常信息:{}", accessToken, code, throwable.getMessage());
                return new WxCurUserTicket(throwable.getMessage());
            }

            @Override
            public WxUserDetailResp getUserDetail(String accessToken, WxUserDetailReqDto dto) {
                log.error("企业微信feign-获取用户详情异常 >>> accessToken:{}, dto:{}, 异常信息:{}", accessToken, dto, throwable.getMessage());
                return new WxUserDetailResp(throwable.getMessage());
            }

            @Override
            public WxUserIdResp getUserId(String accessToken, WxUserIdReqDto dto) {
                log.error("企业微信feign-获取用户ID异常 >>> accessToken:{}, dto:{}, 异常信息:{}", accessToken, dto, throwable.getMessage());
                return new WxUserIdResp(throwable.getMessage());
            }

            @Override
            public WxMessageResp sendMessage(String accessToken, WxMessageReqDto dto) {
                log.error("企业微信feign-发送消息异常 >>> accessToken:{}, dto:{}, 异常信息:{}", accessToken, dto, throwable.getMessage());
                return new WxMessageResp(throwable.getMessage());
            }
        };
    }
}
