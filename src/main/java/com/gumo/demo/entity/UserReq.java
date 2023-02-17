package com.gumo.demo.entity;

import com.alibaba.fastjson.JSON;
import com.gumo.demo.utils.ExcelUtil;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotBlank;
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
    @Length(max = 10, message = "账号长度不超过10")
    private String login;

    @NotBlank(message = "密码不能为空")
    @Length(max = 20, message = "密码长度不超过20")
    private String password;

    @NotBlank(message = "所属单位不能为空")
    private String teamOrgId;

    @NotBlank(message = "所属单位名称不能为空")
    private String teamOrgName;

}
