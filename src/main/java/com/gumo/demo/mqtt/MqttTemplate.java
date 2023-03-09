package com.gumo.demo.mqtt;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Throwables;
import com.gumo.demo.config.MqttConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * @Author: gumo
 * @Date: 2022/8/12 15:47
 */
@Slf4j
@Component
public class MqttTemplate {

    @Autowired
    private MqttConfiguration mqttFactory;

    /**
     * 发送消息
     *
     * @param topic 主题
     * @param data  消息内容
     */
    public void send(String clientId, String topic, Object data) {
        // 获取客户端实例
        MqttClient client = mqttFactory.getInstance(clientId);
        try {
            // 转换消息为json字符串
            String json = JSONObject.toJSONString(data);
            log.info("MQTT主题[{}]发送消息...\r\n{}", topic, json);
            client.publish(topic, new MqttMessage(json.getBytes(StandardCharsets.UTF_8)));
        } catch (MqttException e) {
            log.error("MQTT主题[{}]发送消息失败,{}", topic, Throwables.getStackTraceAsString(e));
        }
    }

    /**
     * 订阅主题
     *
     * @param topic    主题
     * @param listener 消息监听处理器
     */
    public void subscribe(String clientId, String topic, IMqttMessageListener listener) {
        MqttClient client = mqttFactory.getInstance(clientId);
        try {
            log.info("MQTT订阅主题[{}]...", topic);
            client.subscribe(topic, listener);
        } catch (MqttException e) {
            log.error("MQTT订阅主题[{}]失败,{}", topic, Throwables.getStackTraceAsString(e));
        }
    }

}
