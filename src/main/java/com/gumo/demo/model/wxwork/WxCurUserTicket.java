package com.gumo.demo.model.wxwork;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WxCurUserTicket extends WxworkResp {

    @JsonProperty("userid")
    private String userId;

    @JsonProperty("user_ticket")
    private String userTicket;

    @JsonProperty("expires_in")
    private Long expiresIn;

    public WxCurUserTicket(String errMsg) {
        super(-1L, errMsg);
    }
}
