package com.gumo.demo.feign.fallback;

import com.gumo.demo.feign.BaiduClient;
import com.gumo.demo.model.baidu.AccessTokenBaiduResp;
import com.gumo.demo.model.baidu.ChatBaiduReqDto;
import com.gumo.demo.model.baidu.ChatBaiduResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * Description：百度大模型服务降级工厂类
 *
 * @author：Gumo
 * @date：2023/3/28 16:26
 * @version: 1.0
 */
@Slf4j
@Component
public class AlibabaClientFallbackFactory implements FallbackFactory<BaiduClient> {

    @Override
    public BaiduClient create(Throwable throwable) {
        return new BaiduClient() {

            @Override
            public AccessTokenBaiduResp getAccessToken(String grantType, String clientId, String clientSecret) {
                log.error("百度大模型feign-获取AccessToken异常 >>> clientId:{}, clientSecret:{}, 异常信息:{}", clientId, clientSecret, throwable.getMessage());
                return new AccessTokenBaiduResp(throwable.getMessage());
            }

            @Override
            public ChatBaiduResp chatCompletions(String accessToken, ChatBaiduReqDto dto) {
                log.error("百度大模型feign-调用对话接口 >>> accessToken:{}, dto:{}, 异常信息:{}", accessToken, dto, throwable.getMessage());
                return new ChatBaiduResp(throwable.getMessage());
            }
        };
    }
}
