package com.gumo.demo.controller;

import com.gumo.demo.model.dto.CommonResult;
import com.gumo.demo.mqtt.MqttMessageListener;
import com.gumo.demo.mqtt.MqttTemplate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * MQTT测试接口
 * Created by gumo on 2021/12/15.
 */
@Api(tags = "MqttController")
@RestController
@RequestMapping("/mqtt")
public class MqttController {

    @Autowired
    private MqttTemplate mqttTemplate;

    @PostMapping("/listener")
    @ApiOperation("MQTT订阅主题")
    public CommonResult listener(@RequestParam(value = "clientId", required = false, defaultValue = "ClientId_local") String clientId,
                                 @RequestParam(value = "topic", required = false, defaultValue = "test") String topic) {
        mqttTemplate.subscribe(clientId, topic, new MqttMessageListener());
        return CommonResult.success(null);
    }

    @PostMapping("/send")
    @ApiOperation("MQTT发送消息")
    public CommonResult send(@RequestParam(value = "clientId", required = false, defaultValue = "ClientId_local") String clientId,
                             @RequestParam(value = "topic", required = false, defaultValue = "test") String topic,
                             @RequestBody Object data) {
        mqttTemplate.send(clientId, topic, data);
        return CommonResult.success(null);
    }
}

