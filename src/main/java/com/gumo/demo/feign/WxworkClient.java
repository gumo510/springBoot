package com.gumo.demo.feign;


import com.gumo.demo.constants.WxworkConstant;
import com.gumo.demo.feign.configuration.WxworkClientInterceptor;
import com.gumo.demo.feign.fallback.WxworkClientFallbackFactory;
import com.gumo.demo.model.wxwork.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
  * Description：企业微信接口
  * @author Gumo
  * @date 2023/3/28 9:56
  * @version 1.0
  */
@FeignClient(name = WxworkConstant.WXWORK_SERVER_NAME,
        url = WxworkConstant.WXWORK_URL,
        fallbackFactory = WxworkClientFallbackFactory.class,
        configuration = WxworkClientInterceptor.class)
public interface WxworkClient {
    
    /**
      * Description：获取access_token
      * @param corpId 企业ID
      * @param corpSecret 企业应用的凭证密钥
      * @return com.intellif.vesionbook.dto.wxwork.AccessTokenResp
      * @author Gumo
      * @date 2023/3/28 9:57
      */
    @GetMapping("/gettoken")
    AccessTokenResp getAccessToken(@RequestParam("corpid") String corpId, @RequestParam("corpsecret") String corpSecret);

    /**
      * Description：通过code获取当前用户信息
      * @param accessToken 调用接口凭证
      * @param code 临时登录凭证
      * @return com.intellif.vesionbook.dto.wxwork.WxCurUserTicket
      * @author Gumo
      * @date 2023/3/28 9:58
      */
    @GetMapping("/auth/getuserinfo")
    WxCurUserTicket authorUser(@RequestParam("access_token") String accessToken, @RequestParam("code") String code);

    /**
      * Description：通过用户ticket获取用户信息
      * @param accessToken 调用接口凭证
      * @param dto 请求参数
      * @return com.intellif.vesionbook.dto.wxwork.WxUserDetailResp 用户信息
      * @author Gumo
      * @date 2023/3/28 11:17
      */
    @PostMapping("/auth/getuserdetail")
    WxUserDetailResp getUserDetail(@RequestParam("access_token") String accessToken, @RequestBody WxUserDetailReqDto dto);

    /**
      * Description：通过手机号获取企业微信用户ID
      * @param accessToken 调用接口凭证
      * @param dto 请求参数
      * @return com.intellif.vesionbook.dto.wxwork.WxUserIdResp
      * @author Gumo
      * @date 2023/3/28 9:59
      */
    @PostMapping("/user/getuserid")
    WxUserIdResp getUserId(@RequestParam("access_token") String accessToken, @RequestBody WxUserIdReqDto dto);

    /**
      * Description：发送卡片消息
      * @param accessToken 调用接口凭证
      * @param dto 请求参数
      * @return com.intellif.vesionbook.dto.wxwork.WxMessageResp
      * @author Gumo
      * @date 2023/3/28 9:59
      */
    @PostMapping("/message/send")
    WxMessageResp sendMessage(@RequestParam("access_token") String accessToken, @RequestBody WxMessageReqDto dto);
}
