package com.gumo.demo.controller;

import com.gumo.demo.model.dto.CommonResult;
import com.gumo.demo.service.ISendMailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 邮箱测试接口
 * Created by gumo on 2021/12/15.
 */
@Api(tags = "MailController")
@RestController
@RequestMapping("/mail")
public class MailController {

    @Autowired
    private ISendMailService sendMailService;

    @GetMapping("/send")
    @ApiOperation("发送邮件")
    public CommonResult sendMail() {
        sendMailService.assambleSendMail("开始", "结束");
        return CommonResult.success(null);
    }
}

