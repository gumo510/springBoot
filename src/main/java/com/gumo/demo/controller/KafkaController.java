package com.gumo.demo.controller;

import com.gumo.demo.model.dto.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Kafka测试接口
 * Created by gumo on 2021/12/15.
 */
@Api(tags = "KafkaController")
@RestController
@RequestMapping("/kafka")
public class KafkaController {

    @PostMapping("/sendToDefaultTopic")
    @ApiOperation("向默认主题发送消息")
    public CommonResult sendToDefaultTopic() {
        return CommonResult.success(null);
    }

    @PostMapping("/sendToTopic")
    @ApiOperation("向指定主题发送消息")
    public CommonResult sendToTopic(String payload, String topic) {
        return CommonResult.success(null);
    }
}

