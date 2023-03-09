package com.gumo.demo.config;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;

/**
 1. @author gumo
 2. @date 2022/6/29 20:42
 */
@Data
@Slf4j
@Configuration
public class MqttConfiguration {

    @Value("${mqtt.host:tcp://localhost:1883}")
    String host;
    @Value("${mqtt.username:admin}")
    String username;
    @Value("${mqtt.password:Introcks1234}")
    String password;
    @Value("${mqtt.clientId:ClientId_local}")
    String clientId;
    @Value("${mqtt.timeout:10}")
    int timeOut;
    @Value("${mqtt.keepalive:20}")
    int keepAlive;

    private ConcurrentHashMap<String, MqttClient> clientMap = new ConcurrentHashMap<>();

    /**
     * 初始化客户端
     */
    public MqttClient getInstance(String clientId) {
        MqttClient client = null;
        String key = clientId;
        if (clientMap.get(key) == null) {
            try {
                client = new MqttClient(host, clientId);
                // MQTT配置对象
                MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
                // 设置自动重连, 其它具体参数可以查看MqttConnectOptions
                mqttConnectOptions.setAutomaticReconnect(true);
                // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
                // mqttConnectOptions.setCleanSession(true);
                mqttConnectOptions.setUserName(username);
                mqttConnectOptions.setPassword(password.toCharArray());
                // mqttConnectOptions.setServerURIs(new String[]{url});
                // 设置超时时间 单位为秒
                mqttConnectOptions.setConnectionTimeout(timeOut);
                // 设置会话心跳时间 单位为秒
                mqttConnectOptions.setKeepAliveInterval(keepAlive);
                // 设置“遗嘱”消息的话题，若客户端与服务器之间的连接意外中断，服务器将发布客户端的“遗嘱”消息。
                // mqttConnectOptions.setWill("willTopic", "offline".getBytes(), 2, false);

                if (!client.isConnected()) {
                    client.connect(mqttConnectOptions);
                }
                log.info("MQTT创建client成功={}", JSONObject.toJSONString(client));
                clientMap.put(key, client);
            } catch (MqttException e) {
                log.error("MQTT连接消息服务器[{}]失败", key + "-" + host);
            }
        } else {
            client = clientMap.get(key);
            log.info("MQTT从map里获取到client={}", JSONObject.toJSONString(client));
            if (!client.isConnected()) {
                // 如果缓存里的client已经断开，则清除该缓存，再重新创建客户端连接
                clientMap.remove(key);
                this.getInstance(clientId);
            }
        }
        return client;
    }

}

