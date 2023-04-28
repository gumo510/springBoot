package com.gumo.demo.model.wxwork;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WxUserIdResp extends WxworkResp {

    @JsonProperty("userid")
    private String userId;

    public WxUserIdResp(String errMsg) {
        super(-1L, errMsg);
    }
}
