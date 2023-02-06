package com.gumo.demo.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class SmsBuilder {
    @Autowired
    private List<SmsService> smsServiceList;
    @Value("${vesionbook.send.sms.platform.type:ALIYUN}")
    private String platformType;
    private static SmsService smsService;

    public String getPlatformType() {
        return platformType;
    }

    public static SmsService getSmsService() {
        return smsService;
    }

    @PostConstruct
    public void init() {
        smsServiceList.forEach(obj -> {
            if (platformType.equals(obj.getName())) {
                smsService = obj;
            }
        });
    }
}