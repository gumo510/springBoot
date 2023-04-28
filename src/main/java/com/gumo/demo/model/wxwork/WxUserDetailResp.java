package com.gumo.demo.model.wxwork;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WxUserDetailResp extends WxworkResp {

    private String userid;

    private String mobile;

    private String gender;

    private String email;

    private String avatar;

    @JsonProperty("qr_code")
    private String qrCode;

    @JsonProperty("biz_mail")
    private String bizMail;

    private String address;

    public WxUserDetailResp(String errMsg) {
        super(-1L, errMsg);
    }
}
