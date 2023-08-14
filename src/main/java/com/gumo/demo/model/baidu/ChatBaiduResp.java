package com.gumo.demo.model.baidu;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gumo.demo.model.wxwork.WxworkResp;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChatBaiduResp extends BaiduResp {

    private String id;

    private String object;

    private String created;

    private String result;

    @JsonProperty("is_truncated")
    private Boolean isTruncated;

    @JsonProperty("need_clear_history")
    private Boolean needClearHistory;

    public ChatBaiduResp(String errMsg) {
        super(-1L, errMsg);
    }
}
