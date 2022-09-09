package com.gumo.demo.kafka.consumer;

import com.alibaba.fastjson.JSON;
import com.gumo.demo.constants.KafkaConstants;
import com.gumo.demo.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

/**
 * kafka监听器
 *
 * @author Dai.sha
 * @since 2019/10/9 15:29
 */
@Component
@Slf4j
public class MessageReceiveListener {

	@KafkaListener(topics = KafkaConstants.CAMPUS_FACE_SUBSCRIBE, containerFactory = "campusFaceContainerFactory")
	public void campusFaceListen(ConsumerRecord<?, ?> consumerRecord, Acknowledgment ack) {
		long startTime = System.currentTimeMillis();
		boolean success = false;
		while (!success) {
			try {
				log.info("开始人员信息，offset = {}", consumerRecord.offset());
				User filterFaces = JSON.parseObject(consumerRecord.value().toString(), User.class);
//				antiTencentMsgHandler.syncCampusFace(consumerRecord);
				log.info("接收到人员信息：{}", JSON.toJSONString(filterFaces));
				ack.acknowledge(); //手动提交
				success = true;
			} catch (Exception e) {
				e.printStackTrace();
				log.error("MessageReceiveListener_campusFaceSubscribeListen：e: {}", e);
			}
		}
		log.info("campus-face-subscribe kafka message end ：{}   ", System.currentTimeMillis() - startTime);
	}

	@KafkaListener(topics = KafkaConstants.OPERATE_SYNC_USER_INFO)
	public void instructResultListen(ConsumerRecord<String, String> record, Acknowledgment ack) {
		log.info("instructResultListen And Offset: {}", record.offset());
		try {
//			instructResultService.handleInstructResult(JSONObject.parseObject(record.value()));
			ack.acknowledge();
		} catch (Exception e) {
			log.error("consumer exception :{}", e.getMessage());
		}
	}
}

