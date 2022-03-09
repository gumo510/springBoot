package com.gumo.demo.enums;

/**
 * The offset commit behavior enumeration.
 */
public enum KafkaAckMode {

    /**
     * AckMode模式	作用
     * MANUAL	当每一批poll()的数据被消费者监听器（ListenerConsumer）处理之后, 手动调用Acknowledgment.acknowledge()后提交
     * MANUAL_IMMEDIATE	手动调用Acknowledgment.acknowledge()后立即提交
     * RECORD	当每一条记录被消费者监听器（ListenerConsumer）处理之后提交
     * BATCH	当每一批poll()的数据被消费者监听器（ListenerConsumer）处理之后提交
     * TIME	当每一批poll()的数据被消费者监听器（ListenerConsumer）处理之后，距离上次提交时间大于TIME时提交
     * COUNT	当每一批poll()的数据被消费者监听器（ListenerConsumer）处理之后，被处理record数量大于等于COUNT时提交
     * COUNT_TIME	TIME或COUNT　有一个条件满足时提交
     */

    /**
     * Commit after each record is processed by the listener.
     */
    RECORD,

    /**
     * Commit whatever has already been processed before the next poll.
     */
    BATCH,

    /**
     * Commit pending updates after
     */
    TIME,

    /**
     * Commit pending updates after
     */
    COUNT,

    /**
     * Commit pending updates after
     */
    COUNT_TIME,

    /**
     * User takes responsibility for acks using an
     */
    MANUAL,

    /**
     * User takes responsibility for acks using an
     */
    MANUAL_IMMEDIATE;

    private KafkaAckMode() { /* compiled code */ }

}

