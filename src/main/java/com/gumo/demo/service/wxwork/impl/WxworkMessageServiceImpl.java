package com.gumo.demo.service.wxwork.impl;


import com.gumo.demo.config.WxworkConfig;
import com.gumo.demo.exception.WxworkException;
import com.gumo.demo.feign.WxworkClient;
import com.gumo.demo.model.wxwork.WxMessageResp;
import com.gumo.demo.model.wxwork.WxworkTextCardMessageVo;
import com.gumo.demo.service.wxwork.AuthorizationService;
import com.gumo.demo.service.wxwork.WxworkMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Description:
 *
 * @author: Gumo
 * @date: 2023/3/28 13:53
 * @version: 1.0
 */
@Slf4j
@Service
public class WxworkMessageServiceImpl implements WxworkMessageService {

    private final AuthorizationService authorizationService;

    private final WxworkClient wxworkClient;

    private final WxworkConfig config;

    @Override
    public void sendTextCardMessage(WxworkTextCardMessageVo vo) throws WxworkException {
        String accessToken = authorizationService.getAccessToken(vo.getAgentId());
        WxMessageResp wxMessageResp = wxworkClient.sendMessage(accessToken, vo.toReqDto());
        if (!Objects.equals(wxMessageResp.getErrCode(), 0L)) {
            log.error("发送企业微信消息失败，错误码：{}，错误信息：{}", wxMessageResp.getErrCode(), wxMessageResp.getErrMsg());
            throw new WxworkException(wxMessageResp.getErrCode(), wxMessageResp.getErrMsg());
        }
    }

    @Override
    public void sendTextCardMessage2(WxworkTextCardMessageVo vo) throws WxworkException {
        String accessToken = authorizationService.getAccessToken(vo);
        WxMessageResp wxMessageResp = wxworkClient.sendMessage(accessToken, vo.toReqDto());
        if (!Objects.equals(wxMessageResp.getErrCode(), 0L)) {
            log.error("发送企业微信消息失败，错误码：{}，错误信息：{}", wxMessageResp.getErrCode(), wxMessageResp.getErrMsg());
            throw new WxworkException(wxMessageResp.getErrCode(), wxMessageResp.getErrMsg());
        }
    }

    @Autowired
    public WxworkMessageServiceImpl(AuthorizationService authorizationService, WxworkClient wxworkClient, WxworkConfig config) {
        this.authorizationService = authorizationService;
        this.wxworkClient = wxworkClient;
        this.config = config;
    }
}
