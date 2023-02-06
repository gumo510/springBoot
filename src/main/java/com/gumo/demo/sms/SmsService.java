package com.gumo.demo.sms;


import com.gumo.demo.dto.vo.BaseDataRespVo;
import com.gumo.demo.dto.vo.BuilderSendSmsVo;

public interface SmsService{
    String getName();

    BaseDataRespVo sendSmsCaptcha(BuilderSendSmsVo req);

    /**
     * 私有云定制：专用协议
     *
     * @param req
     * @return
     */
    BaseDataRespVo sendSmsCaptchaByProtocol(BuilderSendSmsVo req, String protocolName, int expireMin);

    /**
     * 通用短信
     *
     * @param vo
     * @return
     */
    BaseDataRespVo sendSmsByTemplateId(BuilderSendSmsVo vo);

    /**
     * 获取4位随机数
     */
    default String getRandomSix() {
        return Double.toString(Math.random()).substring(2, 6);
    }
}
