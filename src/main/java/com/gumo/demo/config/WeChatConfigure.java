package com.gumo.demo.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Data
@Configuration
public class WeChatConfigure {

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Value("${WeChat.AccessUrl:https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s}")
    public String Access_URL;

    @Value("${WeChat.SendUrl:https://api.weixin.qq.com/cgi-bin/message/template/send?access_token={0}}")
    public String Send_URL;

    @Value("${WeChat.AppID:wx53927af9d7974ae6}")
    public String App_ID;

    @Value("${WeChat.AppSecret:9afd0b38e0b8f4666d92e11beb1a052e}")
    public String App_Secret;

    @Value("${WeChat.OpenID:ofW1C6aVzFL97B-MJQoXb3XwDKjc}")     //gril
//    @Value("${WeChat.OpenID:ofW1C6f3PtMANUFC59g9U_EpAZGQ}")   //boy
    public String Open_ID;

    @Value("${WeChat.TemplateID:wOLgQ6XqwXaoFMxDEMGqh7AaM_OAqmW7Yn8YFJjA4bY}")
    public String Template_ID;

    @Value("${WeChat.TopColor:#173177}")
    public String Top_Color;

    @Value("${WeChat.WeatherAPI:https://apis.tianapi.com/tianqi/index?key=cde9cc0dc841bce1664d957173320ad2&city=440307&type=1}")
    public String Weather_API;

    @Value("${WeChat.RainbowAPI:https://apis.tianapi.com/caihongpi/index?key=cde9cc0dc841bce1664d957173320ad2}")
    public String Rainbow_API;

    @Value("${WeChat.BoyBirthday:1998-05-10}")
    public String Boy_Birthday;

    @Value("${WeChat.GirlBirthday:2000-05-20}")
    public String Girl_Birthday;

    @Value("${WeChat.LoveDay:2019-02-14}")
    public String Love_Day;
}
