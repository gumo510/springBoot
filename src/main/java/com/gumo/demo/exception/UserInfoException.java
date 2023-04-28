package com.gumo.demo.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserInfoException extends Exception{
    public UserInfoException() {
        super();
    }

    public UserInfoException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "错误描述：" + this.getMessage();
    }
}
