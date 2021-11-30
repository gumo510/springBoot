//package com.gumo.demo.kafka.producer;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.cloud.stream.annotation.EnableBinding;
//import org.springframework.messaging.support.MessageBuilder;
//
//import javax.annotation.Resource;
//
///**
// * 消息发送者
// */
//@EnableBinding(MessageSource.class)
//public class MessageSender {
//
//    private static Logger LOG = LogManager.getLogger(MessageSender.class);
//
//    @Resource
//    private MessageSource source;
//
//    /**
//     * 授权的kafka数据
//     */
//    public void sendHikPersonAuth(String json) {
//        try {
//            source.pushHikPersonAuth().send(MessageBuilder.withPayload(json).build());
//            LOG.info("kafka推送授权");
//        } catch (Exception e) {
//            LOG.error("sendHikPersonAuth error", e.getMessage());
//        }
//
//    }
//}
