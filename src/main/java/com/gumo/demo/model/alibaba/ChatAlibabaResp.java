package com.gumo.demo.model.alibaba;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChatAlibabaResp extends AlibabaResp {

    @JsonProperty("request_id")
    private String request_id;

    private JSONObject output;

    private JSONObject usage;

    public ChatAlibabaResp(String errMsg) {
        super(-1L, errMsg);
    }
}
