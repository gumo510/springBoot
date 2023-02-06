package com.gumo.demo.dto.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;


@AllArgsConstructor
@Data
@Accessors(chain = true)
public class BaseDataRespVo {
    private static final long serialVersionUID = 1150113732670378528L;

    /**
     * 返回码
     */
    private int respCode = 10000000;

    /**
     * 返回标志, success fail
     */
    private String respMark = "SUCCESS";

    /**
     * 返回说明信息
     */
    private String respMessage;

    /**
     * 返回数据
     */
    private Object data;


    public BaseDataRespVo() {
    }


    public BaseDataRespVo(Object data) {
        this.data = data;
    }

    public BaseDataRespVo(int respCode) {
        this.respCode = respCode;
    }

    public BaseDataRespVo(Object data, int respCode) {
        this.data= data;
        this.respCode = respCode;
    }

    public BaseDataRespVo(int respCode, String respMessage) {
        this.respCode = respCode;
        this.respMessage = respMessage;
    }

    public BaseDataRespVo(Object data, int respCode, String respMessage) {
        this.data = data;
        this.respCode = respCode;
        this.respMessage = respMessage;
    }

    public BaseDataRespVo(Object data, int respCode, String respMessage, String respMark) {
        this.data = data;
        this.respCode = respCode;
        this.respMessage = respMessage;
        this.respMark = respMark;
    }

    public BaseDataRespVo(int respCode, String respMessage, String respMark) {
        this.respCode = respCode;
        this.respMessage = respMessage;
        this.respMark = respMark;
    }

    public void setRespCode(int respCode) {
        this.respCode = respCode;
        if (10000000 == respCode) {
            this.setRespMark("SUCCESS");
        }

    }

    public BaseDataRespVo success(){
        this.respMark = "SUCCESS";
        return this;
    }

    public BaseDataRespVo failed(){
        this.respMark = "FAIL";
        return this;
    }

    public static <T> BaseDataRespVoBuilder builder() {
        return new BaseDataRespVoBuilder();
    }

    public static class BaseDataRespVoBuilder {
        private int respCode = 10000000;
        private String respMark = "SUCCESS";
        private String respMessage;
        private Object data;

        BaseDataRespVoBuilder() {
        }

        public BaseDataRespVoBuilder respCode(int respCode) {
            this.respCode = respCode;
            return this;
        }

        public BaseDataRespVoBuilder respMark(String respMark) {
            this.respMark = respMark;
            return this;
        }

        public BaseDataRespVoBuilder respMessage(String respMessage) {
            this.respMessage = respMessage;
            return this;
        }

        public BaseDataRespVoBuilder data(Object data) {
            this.data = data;
            return this;
        }

        public BaseDataRespVo build() {
            return new BaseDataRespVo(this.respCode, this.respMark, this.respMessage, this.data);
        }

        @Override
        public String toString() {
            return "BaseDataRespVo.BaseDataRespVoBuilder(respCode=" + this.respCode + ", respMark=" + this.respMark + ", respMessage=" + this.respMessage + ", data=" + this.data + ")";
        }
    }
}
