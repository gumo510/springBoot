package com.gumo.demo.model.alibaba;

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
public class ChatAlibabaReqDto {

    private String model;

    private Input input;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Input {

        private String prompt;
    }

    public ChatAlibabaReqDto(String model, String content) {
        Input input = new Input();
        input.setPrompt(content);
        this.input = input;
        this.model = model;
    }

}
