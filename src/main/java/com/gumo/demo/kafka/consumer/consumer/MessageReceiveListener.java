package com.gumo.demo.kafka.consumer.consumer;

import com.gumo.demo.constants.KafkaConstants;
import com.gumo.demo.kafka.consumer.handler.AntiTencentMsgHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Value("${kafka.receive.retry.milliseconds:60000}")
	private long RETRY_INTEVAL;

	@Autowired
	private AntiTencentMsgHandler antiTencentMsgHandler;


	private void sleep() {
		try {
			Thread.sleep(RETRY_INTEVAL);
		} catch (Exception e) {
			log.error("线程sleep异常：", e);
		}
	}

	@KafkaListener(topics = KafkaConstants.CAMPUS_FACE_SUBSCRIBE, containerFactory = "campusFaceContainerFactory")
	public void campusFaceListen(ConsumerRecord<?, ?> consumerRecord, Acknowledgment ack) {
		long startTime = System.currentTimeMillis();
		boolean success = false;
		while (!success) {
			try {
				antiTencentMsgHandler.syncCampusFace(consumerRecord);
				ack.acknowledge(); //手动提交
				success = true;
			} catch (Exception e) {
				e.printStackTrace();
				log.error("MessageReceiveListener_campusFaceSubscribeListen：e: {}", e);
//				sleep();
			}
		}
		log.info("campus-face-subscribe kafka message end ：{}   ", System.currentTimeMillis() - startTime);
	}

/*	@KafkaListener(topics = KafkaConstants.CAMPUS_FACE_SUBSCRIBE_LIST, containerFactory = "campusFaceContainerFactory")
	public void campusFaceListListen(List<ConsumerRecord<?, ?>> consumerRecords, Acknowledgment ack) {
		long startTime = System.currentTimeMillis();
		boolean success = false;
		while (!success) {
			try {
				antiTencentMsgHandler.syncCampusFace(consumerRecords);
				ack.acknowledge(); //手动提交
				success = true;
			} catch (Exception e) {
				e.printStackTrace();
				log.error("MessageReceiveListener_campusFaceSubscribeListen：e: {}", e);
//				sleep();
			}
		}
		log.info("campus-face-subscribe kafka message end ：{}   ", System.currentTimeMillis() - startTime);
	}*/
}

