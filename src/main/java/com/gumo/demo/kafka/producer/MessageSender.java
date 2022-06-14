package com.gumo.demo.kafka.producer;

import com.alibaba.fastjson.JSON;
import com.gumo.demo.constants.KafkaConstants;
import com.gumo.demo.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * 消息发送者
 */
@Component
@Slf4j
public class MessageSender {
    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void send(){

        User u= new User();
        u.setUserName("test");
        u.setPassWord("123456");
        u.setRealName("admin");
        try {
            kafkaTemplate.send(KafkaConstants.CAMPUS_FACE_SUBSCRIBE, JSON.toJSONString(u));
            log.info("kafka推送人员信息");
        } catch (Exception e) {
            log.error("send error", e.getMessage());
        }
    }
}
