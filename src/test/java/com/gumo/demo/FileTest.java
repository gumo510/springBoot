package com.gumo.demo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gumo.demo.utils.XunFeiYunUtil;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.concurrent.CompletableFuture;

public class FileTest {

    // 试题存放的位置
    String data_set_path = "C:\\Users\\gumo\\Desktop\\apiTestDoc(include datasets)\\datasets\\";
    // 答案生成的位置
    String target_path = "C:\\Users\\gumo\\Desktop\\apiTestDoc(include datasets)\\xfyun\\";
    // 文件名
    String fileName = "agieval.json";

    // 创建 ObjectMapper 对象
    ObjectMapper objectMapper = new ObjectMapper();

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
        outputJsonNode.put("api_name", "testXunFeiYun");
        outputJsonNode.put("concurrency", true);
        ObjectNode paramsNode = objectMapper.createObjectNode();
        paramsNode.put("max_length", 767);
        paramsNode.put("num_beams", 1);
        paramsNode.put("do_sample", true);
        paramsNode.put("top_p", 0.9);
        paramsNode.put("temperature", 0.95);
        paramsNode.put("max_gen_length", 256);
        outputJsonNode.set("params", paramsNode);

        ArrayNode answersNode = objectMapper.createArrayNode();
        for (JsonNode inputElement : inputJsonNode) {
            ObjectNode answerElement = objectMapper.createObjectNode();
            answerElement.put("id", inputElement.get("id").asInt());
            answerElement.put("q", inputElement.get("q").asText());
            answerElement.put("gt", inputElement.get("gt").asText());

            // 调用讯飞接口
            System.out.println(inputElement);
            String answer = callXunfeiAnswer(inputElement.get("q").asText());
            answerElement.put("answer", answer);
            answersNode.add(answerElement);
        }
        outputJsonNode.set("answers", answersNode);
        return outputJsonNode;
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

    private String callWenXinAnswer(String question) {
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
        }
        return answer;
    }
}
