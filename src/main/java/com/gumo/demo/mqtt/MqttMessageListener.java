package com.gumo.demo.mqtt;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * @Author: gumo
 * @Date: 2022/8/12 15:50
 */
@Slf4j
public class MqttMessageListener implements IMqttMessageListener {

    /**
     * 处理消息
     *
     * @param topic       主题
     * @param mqttMessage 消息
     */
    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        log.info("MQTT实时订阅主题[{}]发来消息[{}]", topic, new String(mqttMessage.getPayload()));
    }
}

