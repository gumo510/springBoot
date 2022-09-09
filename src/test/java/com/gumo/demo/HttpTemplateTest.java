package com.gumo.demo;

import com.gumo.demo.entity.UserReq;
import com.gumo.demo.utils.ExcelUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

public class HttpTemplateTest {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void postForEntity() {

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
//                headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
//                HttpEntity<String> request = new HttpEntity(JSON.toJSONString(user), headers);
//                ResponseEntity<String> reponse = restTemplate.postForEntity(url, request, String.class);
                System.out.println("同步结束");
            });
            System.out.println(">>>>>oooooo");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
