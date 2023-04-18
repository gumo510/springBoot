package com.gumo.demo.controller;


import com.gumo.demo.model.vo.BaseDataRespVo;
import com.gumo.demo.service.IWeChatService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author gumo
 * @since 2021-10-28
 */
@RestController
@Slf4j
@RequestMapping("/wechat")
public class WeChatController {

    @Autowired
    private IWeChatService weChatService;

    /**
     * 验证服务器地址的有效性
     */
    @Value("${wechat.TOKEN:hello}")
    private  String TOKEN;

    @GetMapping("/checkSignature")
    public String checkSignature(@RequestParam(value = "signature") String signature,
                                 @RequestParam(value = "timestamp") String timestamp,
                                 @RequestParam(value = "nonce") String nonce,
                                 @RequestParam(value = "echostr") String echostr){

        String[] params = new String[]{nonce,timestamp,TOKEN};
        Arrays.sort(params);
        String signatureResult = DigestUtils.sha1Hex(params[0]+params[1] + params[2]);
        //校验签名
        if(!signatureResult.equals(signature)) {
            throw new RuntimeException("signature is not the same wechat signature is " + signature + " signatureResult is " + signatureResult);
        }
        return echostr;

    }

    @GetMapping("push")
    public BaseDataRespVo push(){
        BaseDataRespVo respVo = weChatService.sendWeChatMessage();
        return respVo;
    }
}
