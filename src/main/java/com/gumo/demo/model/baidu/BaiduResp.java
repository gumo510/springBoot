package com.gumo.demo.model.baidu;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaiduResp {

    @JsonProperty("error_code")
    private Long errCode;

    @JsonProperty("error_msg")
    private String errMsg;
}
