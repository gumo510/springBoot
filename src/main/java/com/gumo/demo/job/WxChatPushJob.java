package com.gumo.demo.job;

import com.gumo.demo.service.IWeChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author gumo
 */
@Slf4j
@Component
public class WxChatPushJob {

    @Autowired
    private IWeChatService weChatService;

    //每日 早上8点 定时推送
    @Scheduled(cron = "0 0 8 * * ?")
    public void scheduledPush(){
        log.info("scheduledPush_start...");
        weChatService.sendWeChatMessage();
    }
}
