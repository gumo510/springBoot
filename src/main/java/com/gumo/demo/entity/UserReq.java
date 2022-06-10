package com.gumo.demo.entity;

import com.alibaba.fastjson.JSON;
import com.gumo.demo.utils.ExcelUtil;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author gumo
 * @since 2021-10-19 11:00
 */
@Data
public class UserReq {

    @NotBlank(message = "账号不能为空")
    @Length(max = 32, message = "账号长度不超过10")
    private String login;

    @NotBlank(message = "密码不能为空")
    @Length(max = 32, message = "密码长度不超过20")
    private String password;

    @NotBlank(message = "所属单位不能为空")
    private String teamOrgId;

    @NotBlank(message = "所属单位名称不能为空")
    private String teamOrgName;

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
//                ResponseEntity<String> reponse = restTemplate.postForEntity(url, request, String.class);
                System.out.println("同步结束");
            });
            System.out.println(">>>>>oooooo");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
