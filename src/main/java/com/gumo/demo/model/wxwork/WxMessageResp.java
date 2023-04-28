package com.gumo.demo.model.wxwork;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WxMessageResp extends WxworkResp {

    @JsonProperty("msgid")
    private String msgId;

    public WxMessageResp(String errMsg) {
        super(-1L, errMsg);
    }
}
