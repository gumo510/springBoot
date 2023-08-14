package com.gumo.demo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gumo.demo.feign.BaiduClient;
import com.gumo.demo.model.baidu.AccessTokenBaiduResp;
import com.gumo.demo.model.baidu.ChatBaiduReqDto;
import com.gumo.demo.model.baidu.ChatBaiduResp;
import com.gumo.demo.utils.XunFeiYunUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@SpringBootTest
public class FileTest {

    // 试题存放的位置
    String data_set_path = "C:\\Users\\gumo\\Desktop\\apiTestDoc(include datasets)\\datasets\\";
    // 答案生成的位置
    String target_path = "C:\\Users\\gumo\\Desktop\\apiTestDoc(include datasets)\\baidu\\";
    // 文件名
    String fileName = "mmlu.json";

    String accessToken = "24.267539c8cae0a4cd080a38871985d7b8.2592000.1693731687.282335-37017088";

    // 创建 ObjectMapper 对象
    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private BaiduClient baiduClient;

    @Test
    public void uploadJson() {

        try {
            // 从 input.json 文件中读取 JSON 数据
            // String dir = System.getProperty("user.dir");
            String inputPath = data_set_path + fileName;
            JsonNode inputJsonNode = objectMapper.readTree(new File(inputPath));

            ObjectNode outputJsonNode = buildOutputJsonNode(inputJsonNode);

            // 将修改后的 JSON 数据写入到 output.json 文件中
            String outputPath = target_path + fileName;
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(outputPath), outputJsonNode);

            System.out.println("JSON 文件已成功读取、修改并写入。");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ObjectNode buildOutputJsonNode(JsonNode inputJsonNode) {
        // 创建 JSON 对象转换为输出文件结构
        ObjectNode outputJsonNode = objectMapper.createObjectNode();
        outputJsonNode.put("api_name", "testBaidu");
        outputJsonNode.put("concurrency", true);
        ObjectNode paramsNode = objectMapper.createObjectNode();
        paramsNode.put("max_length", 767);
        paramsNode.put("num_beams", 1);
        paramsNode.put("do_sample", true);
        paramsNode.put("top_p", 0.9);
        paramsNode.put("temperature", 0.95);
        paramsNode.put("max_gen_length", 256);
        outputJsonNode.set("params", paramsNode);

        // 多线程调用
        ExecutorService pool = Executors.newFixedThreadPool(10);
        List<CompletableFuture<ObjectNode>> futures = new ArrayList<>();
        for (JsonNode inputElement : inputJsonNode) {
            CompletableFuture<ObjectNode> future = CompletableFuture.supplyAsync(() -> {
                ObjectMapper mapper = new ObjectMapper();
                ObjectNode answerElement = mapper.createObjectNode();
                answerElement.put("id", inputElement.get("id").asInt());
                answerElement.put("q", inputElement.get("q").asText());
                answerElement.put("gt", inputElement.get("gt").asText());
                // Call your API here
                String answer = callBaiduAnswer(inputElement.get("q").asText());
                answerElement.put("answer", answer);
                return answerElement;
            }, pool);
            futures.add(future);
        }
        // 等待线程执行结束
        List<ObjectNode> results = futures.stream().map(CompletableFuture::join).collect(Collectors.toList());

        ArrayNode answersNode = objectMapper.createArrayNode();
        for (ObjectNode result : results) {
            answersNode.add(result);
        }
        outputJsonNode.set("answers", answersNode);
        return outputJsonNode;
    }

    @Test
    public void test2() {
        callBaiduAnswer("介绍一下你自己");
    }

    private String callBaiduAnswer(String question) {
        System.out.println(question);
        String prefix = "你是一个答题助手,可以专业的简洁的输出问题的答案,请阅读下面选择题,简洁的输出正确选项前的字母不需要输出答案和多余的字符: ";
        ChatBaiduReqDto chatBaiduReqDto = new ChatBaiduReqDto(prefix + question);

        // 调用百度接口获取结果
        ChatBaiduResp chatBaiduResp = null;
        String answer = "";
        try {
            chatBaiduResp = baiduClient.chatCompletions(accessToken, chatBaiduReqDto);
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);
//            HttpEntity<Object> requestEntity = new HttpEntity<>(chatBaiduReqDto, headers);
//            String url = "https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/completions?access_token={access_token}";
//            ResponseEntity<ChatBaiduResp> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, ChatBaiduResp.class, accessToken);
//            chatBaiduResp = responseEntity.getBody();
            if(Objects.isNull(chatBaiduResp.getErrCode())){
                return chatBaiduResp.getResult();
            }
            if(chatBaiduResp.getErrCode() == 100 || chatBaiduResp.getErrCode() == 110){
                AccessTokenBaiduResp tokenResp = baiduClient.getAccessToken("client_credentials", "rdGPGbUHtAdWIGhqVgxG6xk7", "YaqjpbZdkCmrO6PntmiDOYtWeSBmFU82");
                accessToken = tokenResp.getAccessToken();
                answer = callBaiduAnswer(question);
            }else if (chatBaiduResp.getErrCode() == 18){
                // 超过QPS超限额重试
                Thread.sleep(500);
                answer = callBaiduAnswer(question);
            }else {
                answer = chatBaiduResp.getErrMsg();
            }
        } catch (Exception e) {
            System.out.println(e);
            answer = callBaiduAnswer(question);
        }

        System.out.println("返回结果为：" + answer);
        System.out.println("###########");
        return answer;
    }

    private String callXunfeiAnswer(String question) {
        String answer = "返回结果错误";
        try {
            String prefix = "你是一个答题助手,可以专业的简洁的输出问题的答案,请阅读下面选择题,简洁的输出正确选项前的字母不需要输出答案和多余的字符: ";
            CompletableFuture<String> completableFuture = XunFeiYunUtil.fetchAnswer( prefix + question);
            answer = completableFuture.get();
            System.out.println("返回结果为：" + answer);
            System.out.println("###########");
            // 限流报错重试
            if(answer.contains("AppIdQpsOverFlowError")){
                Thread.sleep(1000);
                answer = callXunfeiAnswer(question);
            }
        } catch (Exception e) {
            System.out.println(e);
            answer = String.valueOf(e);
//            answer = callXunfeiAnswer(question);
        }
        return answer;
    }
}
