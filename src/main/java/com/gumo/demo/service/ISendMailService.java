package com.gumo.demo.service;


import com.gumo.demo.dto.vo.MailVO;

/**
 * @Author Liusy
 * @Date 2022/2/16 10:09
 * @Version 1.0
 */
public interface ISendMailService {

    void sendMail(MailVO vo);

    void assambleSendMail(String startTime, String endTime);
}
