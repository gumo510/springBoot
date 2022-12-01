package com.gumo.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.gumo.demo.entity.UserReq;
import com.gumo.demo.utils.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class HttpTemplateTest {

    @Autowired
    private RestTemplate restTemplate;

    private String url = "url";

    private String token = "token";

    /**
     * 1. post 请求使用body 参数和headers
     * HttpEntity<String> request = new HttpEntity(JSONObject.toJSONString(paramMap), headers);
     * ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(url, request, String.class);
     */
    @Test
    public void postBody() {
        Map<String,String> paramMap = Maps.newHashMap();
        paramMap.put("name","姓名");
        paramMap.put("phone","手机号");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
//        buildHeaders(headers);
        HttpEntity<String> request = new HttpEntity(JSONObject.toJSONString(paramMap), headers);
        log.info("url:{},params:{}",url, JSONObject.toJSONString(paramMap));
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, request, String.class);
        log.info("HttpTemplateTest_postBody: {}",JSONObject.toJSONString(responseEntity));
        if (responseEntity.getBody() != null) {
            JSONObject jsonObject = JSONObject.parseObject(responseEntity.getBody());
            if (jsonObject != null  && 10000 == jsonObject.getInteger("code")) {
                JSONObject data = jsonObject.getJSONObject("data");
            }
        }
    }

    /**
     * 2. post 请求 使用params 参数：url 拼接真实请求地址  "?pageNo=1&pageSize=5"
     * ResponseEntity<String> responseEntity = restTemplate.postForEntity(url,null, String.class);
     */
    @Test
    public void postParam() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        log.info("url:{}", url);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url,null, String.class);
        log.info("SyncYouDianServiceImpl_getAccessToken: {}", JSONObject.toJSONString(responseEntity));
        if (responseEntity.getBody() != null) {
            JSONObject jsonObject = JSONObject.parseObject(responseEntity.getBody());
            if (jsonObject != null && jsonObject.getInteger("code") != null && 10000 == jsonObject.getInteger("code")) {
                String string = jsonObject.getJSONObject("data").getString("access_token");
            }
        }
    }

    /**
     * 3. get 请求使用 params 参数
     * 直接使用exchange 设置 方法，请求头 ，参数
     * url 拼接真实请求地址  "?pageNo=1&pageSize=5"
     * ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class, paramMap);
     */
    @Test
    public void getParam() {
        Map<String,Object> paramMap = Maps.newHashMap();
        paramMap.put("index",1);
        paramMap.put("size",1000);
        paramMap.put("fieldModel",0);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.set("access_token", token);
        HttpEntity<String> httpEntity = new HttpEntity(null, headers);
        log.info("url:{},params:{}",url, JSONObject.toJSONString(paramMap));
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class, paramMap);
        log.info("HttpTemplateTest_getParam: {}",JSONObject.toJSONString(responseEntity));
        if (responseEntity.getBody() != null) {
            JSONObject jsonObject = JSONObject.parseObject(responseEntity.getBody());
            if (jsonObject != null  && 10000 == jsonObject.getInteger("code")) {
                JSONObject data = jsonObject.getJSONObject("data");
            }
        }
    }

    public static void main(String[] args) {
        String dir = System.getProperty("user.dir");
        String excelPath = dir+ File.separator+"/doc/用户.xlsx";
        try {
            List<UserReq> excelEntities = ExcelUtil.readExcel2Bean(new FileInputStream(new File(excelPath)), UserReq.class);
            String url = "http://10.128.9.3:8762/ifaas-bus-application/base/user/save";
            RestTemplate restTemplate = new RestTemplate();
            excelEntities.stream().forEach(user -> {
                System.out.println("同步开始");
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
                HttpEntity<String> request = new HttpEntity(JSON.toJSONString(user), headers);
                ResponseEntity<String> reponse = restTemplate.postForEntity(url, request, String.class);
                System.out.println("同步结束");
            });
            System.out.println(">>>>>oooooo");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void buildHeaders(HttpHeaders headers) {
        String nonce = UUID.randomUUID().toString().trim().replaceAll("-", "");
        String timestamp = String.valueOf(System.currentTimeMillis());
        headers.set("appKey","appKey");
        headers.set("appSecretVersion","appSecretVersion");
        headers.set("nonce",nonce);
        headers.set("timestamp",timestamp);
    }
}
