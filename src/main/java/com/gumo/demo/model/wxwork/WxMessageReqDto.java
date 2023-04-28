package com.gumo.demo.model.wxwork;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class WxMessageReqDto {

    @JsonProperty("touser")
    private String toUser;

    @JsonProperty("msgtype")
    private String msgType;

    @JsonProperty("agentid")
    private Long agentId;

    @JsonProperty("textcard")
    private Text textCard;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Text {

        private String title;

        private String description;

        private String url;
    }
}
