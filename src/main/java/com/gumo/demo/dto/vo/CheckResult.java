package com.gumo.demo.dto.vo;


import io.jsonwebtoken.Claims;
import lombok.Data;

/**
 * 检查结果
 *
 * @author gumo
 * @date 2021/07/22
 */

@Data
public class CheckResult {

    /**
     * 成功
     */
    private Boolean success;

    /**
     * 错误编码
     */
    private String errMessage;

    /**
     * 索赔
     */
    private Claims claims;
}
