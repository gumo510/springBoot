package com.gumo.demo.model.alibaba;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlibabaResp {

    @JsonProperty("error_code")
    private Long errCode;

    @JsonProperty("error_msg")
    private String errMsg;
}
