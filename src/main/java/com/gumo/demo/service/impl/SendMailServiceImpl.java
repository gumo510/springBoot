package com.gumo.demo.service.impl;


import com.gumo.demo.dto.vo.MailVO;
import com.gumo.demo.service.ISendMailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Date;
import java.util.Objects;

/**
 * @Author hy
 * @Date 2022/2/16 10:10
 * @Version 1.0
 */
@Service
@Slf4j
public class SendMailServiceImpl implements ISendMailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${send.mail.from:2245594396@qq.com}")
    private String sendMailFrom;

    @Value("${send.mail.to:hu.yong@intellif.com}") // 测试用
    private String sendMailTo;

    @Value("${send.mail.cc:2245594396@qq.com}") // 测试用
    private String sendMailCc;

    @Value("${send.mail.subject:邮件通知}")
    private String mailSubject;

    @Value("${send.mail.pattern:S-E时间内测试邮件内容!}")
    private String mailPattern;

    @Value("${send.mail.switch:false}")
    private Boolean sendMailSwitch;

    @Override
    public void sendMail(MailVO mailVO) {
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mailSender.createMimeMessage(), true);//true表示支持复杂类型
            messageHelper.setFrom(mailVO.getFrom());//邮件发信人
            messageHelper.setTo(mailVO.getTo().split(","));//邮件收件人
            if (Objects.nonNull(mailVO.getCc())) {
                messageHelper.setCc(mailVO.getCc().split(",")); // 抄送
            }
            messageHelper.setSentDate(mailVO.getSentDate());
            messageHelper.setSubject(mailVO.getSubject());
            messageHelper.setText(mailVO.getText());
            mailSender.send(messageHelper.getMimeMessage());
            log.info("邮件发送成功!");
        } catch (MessagingException e) {
            log.error("发送邮件失败!", e);
        }
    }

    @Override
    public void assambleSendMail(String startTime, String endTime) {
        // 1.将相关线路发送到相关邮箱
        MailVO mailVO = new MailVO();
        mailVO.setFrom(sendMailFrom);
        mailVO.setTo(sendMailTo);
        mailVO.setSentDate(new Date());
        mailVO.setCc(null);
        mailVO.setSubject(mailSubject);
        String content = mailPattern.replace("S", startTime).replace("E", endTime);
        mailVO.setText(content);
        this.sendMail(mailVO);
    }
}
