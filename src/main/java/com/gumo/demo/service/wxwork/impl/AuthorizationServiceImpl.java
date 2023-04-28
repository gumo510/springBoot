package com.gumo.demo.service.wxwork.impl;


import com.google.common.collect.Maps;
import com.gumo.demo.config.WxworkConfig;
import com.gumo.demo.constants.WxworkConstant;
import com.gumo.demo.exception.WxworkException;
import com.gumo.demo.feign.WxworkClient;
import com.gumo.demo.model.wxwork.*;
import com.gumo.demo.service.wxwork.AuthorizationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Objects;

/**
 * Description：企业微信授权服务
 *
 * @author：Gumo
 * @date：2023/3/28 13:52
 * @version: 1.0
 */
@Slf4j
@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    @Autowired
    private WxworkClient wxworkClient;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private WxworkConfig config;

    private final HashMap<Long, String> corpMap = Maps.newHashMap();

    @PostConstruct
    public void initCorpMap() {
        log.info("init initCorpMap start...");
        corpMap.put(config.getAgentId(), config.getCorpSecret());
        corpMap.put(config.getVisitorAgentId(), config.getVisitorCorpSecret());
    }


    @Override
    public WxUserDetailResp getCurUserMobile(String code, Long agentId) throws WxworkException {
        // 先用code换取用户身份
        WxCurUserTicket userTicket = wxworkClient.authorUser(getAccessToken(agentId), code);
        if (!Objects.equals(userTicket.getErrCode(), 0L)) {
            log.error("获取用户身份失败，code：{}，errCode：{}，errMsg：{}", code, userTicket.getErrCode(), userTicket.getErrMsg());
            throw new WxworkException(userTicket.getErrCode(), userTicket.getErrMsg());
        }
        // 再用用户身份换取用户详情
        String ticket = userTicket.getUserTicket();
        WxUserDetailResp userDetail = wxworkClient.getUserDetail(getAccessToken(agentId), new WxUserDetailReqDto(ticket));
        if (!Objects.equals(userDetail.getErrCode(), 0L)) {
            log.error("获取用户详情失败，code：{}，errCode：{}，errMsg：{}", code, userDetail.getErrCode(), userDetail.getErrMsg());
            throw new WxworkException(userDetail.getErrCode(), userDetail.getErrMsg());
        }
        return userDetail;
    }

    @Override
    public String getUserIdByMobile(String mobile, Long agentId) throws WxworkException {
        if (StringUtils.isBlank(mobile)) {
            return null;
        }
        WxUserIdResp userId = wxworkClient.getUserId(getAccessToken(agentId), new WxUserIdReqDto(mobile));
        if (!Objects.equals(userId.getErrCode(), 0L)) {
            log.error("通过手机号获取用户ID失败，mobile：{}，errCode：{}，errMsg：{}", mobile, userId.getErrCode(), userId.getErrMsg());
            throw new WxworkException(userId.getErrCode(), userId.getErrMsg());
        }
        return userId.getUserId();
    }

    @Override
    public String getAccessToken(Long agentId) throws WxworkException {
        String accessToken = redisTemplate.opsForValue().get(WxworkConstant.APP_ACCESS_TOKEN_KEY + agentId);
        if (StringUtils.isBlank(accessToken)) {
            AccessTokenResp tokenResp = wxworkClient.getAccessToken(config.getCorpId(), corpMap.get(agentId));
            if (!Objects.equals(tokenResp.getErrCode(), 0L)) {
                log.error("获取access_token失败，errCode：{}，errMsg：{}", tokenResp.getErrCode(), tokenResp.getErrMsg());
                throw new WxworkException(tokenResp.getErrCode(), tokenResp.getErrMsg());
            }
            accessToken = tokenResp.getAccessToken();
            redisTemplate.opsForValue().set(WxworkConstant.APP_ACCESS_TOKEN_KEY + agentId, accessToken, tokenResp.getExpiresIn() - 10L);
        }
        return accessToken;
    }

    @Override
    public String getAccessToken(WxworkTextCardMessageVo vo) throws WxworkException {
        String accessToken = redisTemplate.opsForValue().get(WxworkConstant.APP_ACCESS_TOKEN_KEY + vo.getAgentId());
        if (StringUtils.isBlank(accessToken)) {
            AccessTokenResp tokenResp = wxworkClient.getAccessToken(vo.getCorpId(), vo.getCorpSecret());
            if (!Objects.equals(tokenResp.getErrCode(), 0L)) {
                log.error("获取access_token失败，errCode：{}，errMsg：{}", tokenResp.getErrCode(), tokenResp.getErrMsg());
                throw new WxworkException(tokenResp.getErrCode(), tokenResp.getErrMsg());
            }
            accessToken = tokenResp.getAccessToken();
            redisTemplate.opsForValue().set(WxworkConstant.APP_ACCESS_TOKEN_KEY + vo.getAgentId(), accessToken, tokenResp.getExpiresIn() - 10L);
        }
        return accessToken;
    }

}
