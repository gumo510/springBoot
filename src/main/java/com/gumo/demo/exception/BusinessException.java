package com.gumo.demo.exception;

import com.gumo.demo.enums.CommonResultCodeEnum;
import lombok.Data;

/**
 * 基本业务异常
 *
 * @Date 2019/6/14 15:21
 * @Author yangsz
 */
@Data
public class BusinessException extends Exception {
    protected CommonResultCodeEnum resultCodeEnum;
    protected String presentMessage;

    public BusinessException(CommonResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.resultCodeEnum = resultCodeEnum;
    }

    public BusinessException(CommonResultCodeEnum resultCodeEnum, Throwable throwable) {
        super(resultCodeEnum.getMessage(), throwable);
        this.resultCodeEnum = resultCodeEnum;
    }

    public BusinessException(CommonResultCodeEnum resultCodeEnum, String message) {
        super(message);
        this.presentMessage = message;
        this.resultCodeEnum = resultCodeEnum;
    }

    public BusinessException(CommonResultCodeEnum resultCodeEnum, String message, Throwable throwable) {
        super(message, throwable);
        this.presentMessage = message;
        this.resultCodeEnum = resultCodeEnum;
    }

    @Override
    public String toString() {
        if (resultCodeEnum != null) {
            String s = getClass().getName();
            if (presentMessage != null)
                return s + ": " + "errorCode = " + resultCodeEnum.getResultCode() + " ,errorMessage = " + presentMessage + "";
            else {
                return s + ": " + "errorCode = " + resultCodeEnum.getResultCode() + " ,errorMessage = " + resultCodeEnum.getMessage() + "";
            }
        } else return super.toString();
    }

}
