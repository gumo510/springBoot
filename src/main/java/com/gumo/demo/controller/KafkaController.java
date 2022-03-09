package com.gumo.demo.controller;

import com.gumo.demo.dto.vo.CommonResult;
import com.gumo.demo.kafka.producer.MessageSender;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private MessageSender messageSender;

    @PostMapping("/sendToDefaultTopic")
    @ApiOperation("向默认主题发送消息")
    public CommonResult sendToDefaultTopic() {
        messageSender.send();
        return CommonResult.success(null);
    }

    @PostMapping("/sendToTopic")
    @ApiOperation("向指定主题发送消息")
    public CommonResult sendToTopic(String payload, String topic) {
//        messageSender.sendToKafka(payload, topic);
        return CommonResult.success(null);
    }
}

