package com.gumo.demo.kafka.consumer.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * kafka监听器处理逻辑
 *
 */
@Component
@Slf4j
public class AntiTencentMsgHandler {

/*    @Autowired
    private IIsolatedWorkPersonService isolatedWorkPersonService;

    public void syncCampusFace(List<ConsumerRecord> consumerRecords) {
        List<CampusFaceCaptureReq> filterFaces = null;
        for (ConsumerRecord<?, ?> record : consumerRecords) {
            if (record.value() != null) {
                log.info("开始人员信息，offset = {}", record.offset());
                filterFaces = JSON.parseArray(record.value().toString(), CampusFaceCaptureReq.class);
                log.info("接收到人员信息：{}", JSON.toJSONString(filterFaces));
                try {
                    isolatedWorkPersonService.syncCampusFace(filterFaces);
                } catch (Exception e) {
                    log.error("receivePersonfiles saveArchive error:{}",e);
                }
            }
        }
    }*/
}
