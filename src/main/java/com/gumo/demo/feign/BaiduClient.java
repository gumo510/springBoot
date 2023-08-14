package com.gumo.demo.feign;


import com.gumo.demo.constants.BaiduConstant;
import com.gumo.demo.feign.configuration.WxworkClientInterceptor;
import com.gumo.demo.feign.fallback.BaiduClientFallbackFactory;
import com.gumo.demo.feign.fallback.WxworkClientFallbackFactory;
import com.gumo.demo.model.baidu.AccessTokenBaiduResp;
import com.gumo.demo.model.baidu.ChatBaiduReqDto;
import com.gumo.demo.model.baidu.ChatBaiduResp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
  * Description：百度大模型接口 https://cloud.baidu.com/doc/WENXINWORKSHOP/s/jlil56u11
  * @author Gumo
  * @date 2023/3/28 9:56
  * @version 1.0
  */
@FeignClient(name = BaiduConstant.BAIDU_SERVER_NAME,
        url = BaiduConstant.BAIDU_URL,
        fallbackFactory = BaiduClientFallbackFactory.class)
public interface BaiduClient {
    
    /**
      * Description：获取access_token
      * @param clientId API Key
      * @param clientSecret Secret Key
      * @author Gumo
      * @date 2023/3/28 9:57
      */
    @GetMapping("/oauth/2.0/token")
    AccessTokenBaiduResp getAccessToken(@RequestParam("grant_type") String grantType, @RequestParam("client_id") String clientId,
                                        @RequestParam("client_secret") String clientSecret);


    /**
     * Description：发起一次对话请求
     * @param accessToken 调用接口凭证
     * @param dto 请求参数
     * @return com.intellif.vesionbook.dto.wxwork.WxUserDetailResp 用户信息
     * @author Gumo
     * @date 2023/3/28 11:17
     */
    @PostMapping(value = "/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/completions", consumes = MediaType.APPLICATION_JSON_VALUE)
    ChatBaiduResp chatCompletions(@RequestParam("access_token") String accessToken, @RequestBody ChatBaiduReqDto dto);
}
