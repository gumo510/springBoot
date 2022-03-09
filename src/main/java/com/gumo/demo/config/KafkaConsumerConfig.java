package com.gumo.demo.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author hy
 * @since 2022-03-08 15:24
 */
@Component
@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Value("${spring.kafka.consumer.second.bootstrap.servers:192.168.12.59:9092}")
    private String servers;
    @Value("${spring.kafka.consumer.enable-auto-commit:false}")
    private Boolean autoCommit;
    @Value("${spring.kafka.consumer.auto-offset-reset:earliest}")
    private String offsetReset;
    @Value("${kafka.orgCache.consumer.group.id:group-test}")
    private String orgCacheGroupId;

    @Bean(name = "campusFaceContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<?, ?> dispatchStatisticsContainerFactory() {
        return getConcurrentKafkaListenerContainerFactory(orgCacheGroupId);
    }

    private ConcurrentKafkaListenerContainerFactory<?, ?> getConcurrentKafkaListenerContainerFactory(String groupId) {
        ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        Map<String, Object> props = new HashMap<>(6);
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, autoCommit);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, offsetReset);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        factory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(props));
        //当使用手动提交时必须设置ackMode为MANUAL,否则会报错No Acknowledgment available as an argument, the listener container must have a MANUAL AckMode to populate the Acknowledgment.
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        // 下面两个条件哪个先满足，就会先使用那个
//        factory.getContainerProperties().setAckCount(10);//达到10条数据的时候提交一次
//        factory.getContainerProperties().setAckTime(10000);//10s提交一次
        return factory;
    }
}
