package com.gumo.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.gumo.demo.constants.DoorConst;
import com.gumo.demo.model.req.UserReq;
import com.gumo.demo.utils.ExcelUtil;
import com.gumo.demo.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.Base64Utils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@SpringBootTest(classes = DemoApplication.class)
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
        buildHeaders(headers);
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

    @Test
    public void postFile(String base64Str){
        try {
            byte[] faceBytes = Base64Utils.decodeFromString(base64Str);
            InputStream inputStream = new ByteArrayInputStream(faceBytes);
            MultipartFile multipartFile = new MockMultipartFile(ContentType.APPLICATION_OCTET_STREAM.toString(), inputStream);

            Map<String,String> headerMap = new HashMap();
            headerMap.put("token", token);
            Map<String, String> otherParams = new HashMap();
            otherParams.put("type","0");
            String result = HttpUtil.postResultMultipartFile(url, multipartFile, headerMap, otherParams);
            log.info("IsolatedWorkPersonServiceImpl_postFile, result = {}",  result);
            JSONObject response = JSONObject.parseObject(result);
        } catch (Exception e) {
            log.error("IsolatedWorkPersonServiceImpl_postFile, error = {}", e);
            e.printStackTrace();
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

    @Test
    public void getChannels() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(DoorConst.Header.ACCEPT, MediaType.APPLICATION_JSON.toString());
        httpHeaders.setContentType(MediaType.parseMediaType(DoorConst.VehicleChannel.MEDIA_TYPE));
//        httpHeaders.add(DoorConst.VehicleChannel.ACCESS_Token, getToken());
        HttpEntity<String> httpEntity = new HttpEntity<>("", httpHeaders);
        log.info("getChannels httpHeaders: {}, url:{}", JSONObject.toJSONString(httpEntity), url);
        ResponseEntity<JSONObject> entity = restTemplate.postForEntity(url, httpEntity, JSONObject.class);
        log.info("getChannels entity: {}", JSONObject.toJSONString(entity));
        JSONObject result = entity.getBody();
    }

/*    private String getToken() throws Exception {
        String token = redisUtil.get(DoorConst.Token.TOKEN_KEY, 0);
        if (StringUtils.isBlank(token) || config.getExpires() == 0) {
            // 设置请求头
            HttpHeaders headers = new HttpHeaders();
            headers.add(DoorConst.Header.CONTENT_TYPE, DoorConst.Token.MEDIA_TYPE);
            // 设置请求参数
            MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap();
            postParameters.add(DoorConst.Token.LOGIN_NAME, config.getLoginName());
            postParameters.add(DoorConst.Token.PASSWORD, config.getPssWord());
            postParameters.add(DoorConst.Token.LOGIN_TYPE, config.getLoginType());
            postParameters.add(DoorConst.Token.BOX_ID, config.getBoxId());

            HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(postParameters, headers);
            log.info("getToken httpHeaders: {}, url:{}", JSONObject.toJSONString(httpEntity), config.getTokenUrl());
            ResponseEntity<DoorTokenRespVo> entity = restTemplate.postForEntity(config.getTokenUrl(), httpEntity, DoorTokenRespVo.class);
            log.info("getToken entity: {}", JSONObject.toJSONString(entity));
            DoorTokenRespVo doorTokenRespVo = entity.getBody();

            if (Objects.nonNull(doorTokenRespVo) && doorTokenRespVo.getStatus() == 1 && Objects.nonNull(doorTokenRespVo.getData())) {
                token = doorTokenRespVo.getData().getToken();
                if (StringUtils.isNotBlank(token)) {
                    redisUtil.setex(DoorConst.Token.TOKEN_KEY, config.getExpires(), token);
                }
            } else {
                log.error("Request Vehicle Channel Token Error: {}", doorTokenRespVo);
                throw new Exception("Request Vehicle Channel Token Error: " + doorTokenRespVo);
            }
        }
        return token;
    }*/

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
