package com.gumo.demo.sms.impl;

import com.gumo.demo.dto.vo.BaseDataRespVo;
import com.gumo.demo.dto.vo.BuilderSendSmsVo;
import com.gumo.demo.enums.CommonResultCodeEnum;
import com.gumo.demo.enums.SmsPlatfromEnum;
import com.gumo.demo.sms.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AlioyunSmsServiceImpl implements SmsService {

    @Override
    public String getName() { return SmsPlatfromEnum.ALIBABA_CLOUD.getType(); }

    @Override
    public BaseDataRespVo sendSmsCaptcha(BuilderSendSmsVo req) {

        return new BaseDataRespVo(null, CommonResultCodeEnum.SUCCESS.getResultCode(), "发送成功", "SUCCESS");
    }

    @Override
    public BaseDataRespVo sendSmsCaptchaByProtocol(BuilderSendSmsVo req, String protocolName, int expireMin) {

        return new BaseDataRespVo(null, CommonResultCodeEnum.SUCCESS.getResultCode(), "发送成功", "SUCCESS");
    }

    /**
     * 通用短信
     *
     * @param vo
     * @return
     */
    @Override
    public BaseDataRespVo sendSmsByTemplateId(BuilderSendSmsVo vo){
//        SmsHelper helper = new SmsHelper();
//        helper.setSendTo(vo.getSendTo());
//        helper.setContent(vo.getContent());
//        helper.setTemplateId(vo.getTemplateId());
//        IMessageClient.sendSmsByTemplateId(helper);
        return new BaseDataRespVo(null, CommonResultCodeEnum.SUCCESS.getResultCode(), "发送成功", "SUCCESS");
    }


}
