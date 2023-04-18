package com.gumo.demo.utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gumo
 * date:2023-03-03 16:49
 * Description: openAI 接口文档：
 * https://platform.openai.com/docs/api-reference/chat/create
 */
@UtilityClass
@Slf4j
public class OpenAi {
    /**
     * 聊天端点
     */
    private static final String CHAT_URL = "https://api.openai.com/v1/chat/completions";

    /**
     * 生成图片
     */
    private static final String IMAGES_URL = "https://api.openai.com/v1/images/generations";

    /**
     * api密匙
     */
    private static final String apiKey = "Bearer 自己创建的API KEY";

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * 发送消息
     *
     * @param txt 内容
     * @return {@link String}
     */
    public String chat(String txt) {
        JSONObject object = new JSONObject();
        List<Map<String, String>> dataList = new ArrayList<>();
        dataList.add(new HashMap<String, String>(){{
            put("role", "user");
            put("content", txt);
        }});
        object.set("model", "gpt-3.5-turbo");
        object.set("messages", dataList);
        JSONObject message = null;
        try {
            /**
             * {
             *      "model": "gpt-3.5-turbo",
             *      "messages": [{"role": "user", "content": "Say this is a test!"}]
             * }
             */
            String body = HttpRequest.post(CHAT_URL)
                .header("Authorization", apiKey)
                .header("Content-Type", "application/json")
                .body(JSON.toJSONString(object))
                .execute()
                .body();
            log.info("OpenAi_chat req: {}, resp: {}", JSON.toJSONString(object), body);
            JSONObject jsonObject = JSONUtil.parseObj(body);
            JSONArray choices = jsonObject.getJSONArray("choices");
            JSONObject result = choices.get(0, JSONObject.class, Boolean.TRUE);
            message = result.getJSONObject("message");
        } catch (Exception e) {
            log.error("OpenAi_chat error: {}", e.getMessage());
        }
        return message.getStr("content");
    }

    /**
     * 生成图片
     * {
     *   "prompt": "A cute baby sea otter",
     *   "n": 2,
     *   "size": "1024x1024"
     * }
     */
    public String generateImages(String prompt, int n, String size) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", apiKey);
        // We are including only some of the parameters to the json request
        String requestJson = "{\"prompt\":\"" + prompt + "\",\"n\":" + n + "\",\"size\":" + size + "}";
        HttpEntity< String > request = new HttpEntity < > (requestJson, headers);
        ResponseEntity< String > response = restTemplate.postForEntity(IMAGES_URL, request, String.class);
        return response.getBody();
    }

    public static void main(String[] args) {
        String txt = "Say this is a test!";
        String prompt = "A cute baby taking sunbath !";
        System.out.println(generateImages(prompt, 1, "1024x1024"));
    }
}
