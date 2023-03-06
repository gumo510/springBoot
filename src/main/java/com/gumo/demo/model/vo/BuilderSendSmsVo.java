package com.gumo.demo.model.vo;

import lombok.Data;

import java.util.List;

@Data
public class BuilderSendSmsVo {
    /**
     * 发送人
     */
    private String sender;
    /**
     * 接收人
     */
    private String[] sendTo;
    /**
     * 短信内容（阿里云平台）
     */
    private String content;
    /**
     * 参数
     */
    private String jsonParams;
    /**
     * 短信模板Id
     */
    private String templateId;
    /**
     * 短信内容参数
     */
    private List<String> param;
    /**
     * 租户名称 -- 为多租户短信准备
     */
    private String gardenName;
}
