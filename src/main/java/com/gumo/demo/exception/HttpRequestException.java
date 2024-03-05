package com.gumo.demo.exception;

import com.gumo.demo.enums.CommonResultCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author mudong.xiao
 * @title: HttpRequestException
 * @description: TODO
 * @date 2023/12/15 13:55
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class HttpRequestException extends RuntimeException {
    protected CommonResultCodeEnum resultCodeEnum;
    protected String presentMessage;

    public HttpRequestException(CommonResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.resultCodeEnum = resultCodeEnum;
    }

    public HttpRequestException(CommonResultCodeEnum resultCodeEnum, Throwable throwable) {
        super(resultCodeEnum.getMessage(), throwable);
        this.resultCodeEnum = resultCodeEnum;
    }
}
