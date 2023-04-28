package com.gumo.demo.service.wxwork;


import com.gumo.demo.exception.WxworkException;
import com.gumo.demo.model.wxwork.WxUserDetailResp;
import com.gumo.demo.model.wxwork.WxworkTextCardMessageVo;

/**
 * Description：企业微信授权接口
 *
 * @author：Gumo
 * @date：2023/3/28 10:13
 * @version: 1.0
 */
public interface AuthorizationService {

    /**
     * Description：通过登录临时code获取用户手机号
     * @param code 临时登录凭证
     * @return java.lang.String
     * @author Gumo
     * @date 2023/3/28 10:17
     */
    WxUserDetailResp getCurUserMobile(String code, Long agentId) throws WxworkException;

    /**
      * Description：通过手机号获取用户ID
      * @param mobile 手机号
      * @return java.lang.String 用户ID
      * @author Gumo
      * @date 2023/3/28 10:20
      */
    String getUserIdByMobile(String mobile, Long agentId) throws WxworkException;

    /**
      * Description：获取应用 access_token
      * @return java.lang.String 调用接口凭证
      * @author Gumo
      * @date 2023/3/28 10:15
      */
    String getAccessToken(Long agentId) throws WxworkException;

    /**
     * Description：根据应用ID获取access_token
     * @return java.lang.String 调用接口凭证
     * @author Gumo
     * @date 2023/3/28 10:15
     */
    String getAccessToken(WxworkTextCardMessageVo vo) throws WxworkException;
}
