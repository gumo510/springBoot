package com.gumo.demo.kafka.consumer.handler;

import com.alibaba.fastjson.JSON;
import com.gumo.demo.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * kafka监听器处理逻辑
 *
 */
@Component
@Slf4j
public class AntiTencentMsgHandler {

    public void syncCampusFace(ConsumerRecord<?, ?> record) {
        if (record.value() != null) {
            log.info("开始人员信息，offset = {}", record.offset());
            User filterFaces = JSON.parseObject(record.value().toString(), User.class);
            log.info("接收到人员信息：{}", JSON.toJSONString(filterFaces));
            try {
//                    isolatedWorkPersonService.syncCampusFace(filterFaces);
            } catch (Exception e) {
                log.error("syncCampusFace saveArchive error:{}",e);
            }
        }
    }

    public void syncCampusFace(List<ConsumerRecord<?, ?>> consumerRecords) {
        List<User> filterFaces = null;
        for (ConsumerRecord<?, ?> record : consumerRecords) {
            if (record.value() != null) {
                log.info("开始人员信息，offset = {}", record.offset());
                filterFaces = JSON.parseArray(record.value().toString(), User.class);
                log.info("接收到人员信息：{}", JSON.toJSONString(filterFaces));
                try {
//                    isolatedWorkPersonService.syncCampusFace(filterFaces);
                } catch (Exception e) {
                    log.error("syncCampusFace saveArchive error:{}",e);
                }
            }
        }
    }
}
