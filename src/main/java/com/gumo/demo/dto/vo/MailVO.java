package com.gumo.demo.dto.vo;

import lombok.Data;

import java.util.Date;

/**
 * @Author hy
 * @Date 2022/2/16 10:10
 * @Version 1.0
 */
@Data
public class MailVO {
    private String from;//邮件发送人
    private String to;//邮件接收人（多个邮箱则用逗号","隔开）
    private String subject;//邮件主题
    private String text;//邮件内容
    private Date sentDate;//发送时间
    private String cc;//抄送（多个邮箱则用逗号","隔开）
    private String bcc;//密送（多个邮箱则用逗号","隔开）
    private String status;//状态
    private String error;//报错信息
}
