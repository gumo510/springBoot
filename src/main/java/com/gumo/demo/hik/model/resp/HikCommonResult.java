package com.gumo.demo.hik.model.resp;

import com.gumo.demo.enums.ResultCodeEnum;
import lombok.Data;

/**
 * 海康通用返回对象
 * @param <T>
 */
@Data
public class HikCommonResult<T> {

    /**
     * 返回码
     * 0表示订阅成功，其他表示失败
     */
    private String code;
    /**
     * 返回描述
     * success表示成功，其他表示失败
     */
    private String msg;
    /**
     * 返回数据
     */
    private T data;

    protected HikCommonResult() {
    }

    protected HikCommonResult(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> HikCommonResult<T> success(T data) {
        return new HikCommonResult<T>("0", "success", data);
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     * @param  message 提示信息
     */
    public static <T> HikCommonResult<T> success(T data, String message) {
        return new HikCommonResult<T>("0", message, data);
    }

    /**
     * 失败返回结果
     *
     */
    public static <T> HikCommonResult<T> failed() {
        return new HikCommonResult<T>("0x01b0f000", "failed", null);
    }

    /**
     * 失败返回结果
     *
     * @param message 错误信息
     */
    public static <T> HikCommonResult<T> failed(String message) {
        return new HikCommonResult<T>("0x01b0f000", message, null);
    }
}
