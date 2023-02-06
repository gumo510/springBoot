package com.gumo.demo.sms.impl;

import com.gumo.demo.dto.vo.BaseDataRespVo;
import com.gumo.demo.dto.vo.BuilderSendSmsVo;
import com.gumo.demo.enums.CommonResultCodeEnum;
import com.gumo.demo.enums.SmsPlatfromEnum;
import com.gumo.demo.sms.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 */
@Service
@Slf4j
public class YidongSmsServiceImpl implements SmsService {

    @Override
    public String getName() {
        return SmsPlatfromEnum.CHINA_MOBILE.getType();
    }

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
    public BaseDataRespVo sendSmsByTemplateId(BuilderSendSmsVo vo) {

        return new BaseDataRespVo(null, CommonResultCodeEnum.SUCCESS.getResultCode(), "发送成功", "SUCCESS");
    }

}
