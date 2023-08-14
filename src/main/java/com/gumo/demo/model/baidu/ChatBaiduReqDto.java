package com.gumo.demo.model.baidu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

/**
 * @author gumo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatBaiduReqDto {

    private List<Messages> messages;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Messages {

        private String role = "user";

        private String content;
    }

    public ChatBaiduReqDto(String content) {
        Messages messages = new Messages();
        messages.setContent(content);
        this.messages = Collections.singletonList(messages);
    }

}
