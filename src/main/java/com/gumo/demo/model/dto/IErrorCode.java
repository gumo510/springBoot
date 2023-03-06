package com.gumo.demo.model.dto;

/**
 * 封装API的错误码
 *
 * @author gumo
 * @date 2021/12/15
 */
public interface IErrorCode {
    long getCode();

    String getMessage();
}
