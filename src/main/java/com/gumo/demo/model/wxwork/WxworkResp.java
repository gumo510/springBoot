package com.gumo.demo.model.wxwork;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WxworkResp {

    @JsonProperty("errcode")
    private Long errCode;

    @JsonProperty("errmsg")
    private String errMsg;
}
