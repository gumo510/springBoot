package com.gumo.demo.constants;

/**
 * Description: 道尔车牌系统对接相关常量
 * @version: 1.0
 */
public interface DoorConst {

    interface Token {
        String TOKEN_KEY = "vehicle_channel_token";
        String MEDIA_TYPE = "application/x-www-form-urlencoded; charset=UTF-8";
        String LOGIN_NAME = "loginName";
        String PASSWORD = "pssWord";
        String LOGIN_TYPE = "loginType";
        String BOX_ID = "boxId";
    }

    interface VehicleChannel {
        String ACCESS_Token = "accessToken";
        String MEDIA_TYPE = "application/json; charset=UTF-8";
    }

    interface Header {
        String ACCEPT = "Accept";
        String CONTENT_TYPE = "Content-Type";
    }
}
