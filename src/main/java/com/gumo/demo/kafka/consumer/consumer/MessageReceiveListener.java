package com.gumo.demo.kafka.consumer.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

/*	@Autowired
	private AntiTencentMsgHandler antiTencentMsgHandler;


	private void sleep() {
		try {
			Thread.sleep(RETRY_INTEVAL);
		} catch (Exception e) {
			log.error("线程sleep异常：", e);
		}
	}

	*//**
	 * 同步人员
	 *//*

	@ConditionalOnBean(name = {"commonContainerFactory"},value ={ConcurrentKafkaListenerContainerFactory.class} )
	@KafkaListener(id = "campusFaceSubscribeListen",
			topics = "campus-face-subscribe",
			containerFactory = "commonContainerFactory")
	public void campusFaceListen(List<ConsumerRecord> consumerRecords, Acknowledgment ack) {
		long startTime = System.currentTimeMillis();
		boolean success = false;
		while (!success) {
			try {
				antiTencentMsgHandler.syncCampusFace(consumerRecords);
				ack.acknowledge();
				success = true;
			} catch (Exception e) {
				log.error("MessageReceiveListener_campusFaceSubscribeListen：e: {}", e);
				sleep();
			}
		}
		log.info("campus-face-subscribe kafka message end ：{}   ", System.currentTimeMillis() - startTime);
	}*/
}

