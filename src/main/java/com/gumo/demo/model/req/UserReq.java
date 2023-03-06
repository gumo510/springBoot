package com.gumo.demo.model.req;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

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
