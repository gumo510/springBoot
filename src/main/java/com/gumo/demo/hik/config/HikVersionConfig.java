package com.gumo.demo.hik.config;

import com.hikvision.artemis.sdk.config.ArtemisConfig;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 海康isc平台配置
 */
@Component
@Data
@Configuration
public class HikVersionConfig {

    public static final Logger log = LoggerFactory.getLogger(HikVersionConfig.class);


    @Value("${hikang.isc.ip:192.168.12.45}")
    private String IP;

    @Value("${hikang.isc.prot:54433}")
    private String PORT;

    @Value("${hikang.isc.app.key:25492151}")
    private String appKey;

    @Value("${hikang.isc.app.secret:7yHy0Ua54ZW4lzlnz5C1}")
    private String appSecret;

    @Value("${hikang.isc.event.types:930335}")
    private String eventTypeStr;

    @Value("${hikang.isc.event.dest:https://ip:port/api/device/hik/isc/onEventNotify/1.0}")
    private String eventDest;

    @Value("${hikang.isc.camera.get:1000}")
    private Integer cameraGetSize;

    public static final String ARTEMIS_PATH = "/artemis/";

    @PostConstruct
    public void init() {
        log.info("init Hik config start...");
        initHikVersionConfig();
    }

    public void initHikVersionConfig() {
        log.info("load hikang config!");
        ArtemisConfig.appKey = appKey;
        ArtemisConfig.appSecret = appSecret;
        ArtemisConfig.host = IP + ":" + PORT;
    }
}
