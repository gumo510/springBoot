package com.gumo.demo.exception;

public class WxworkException extends Exception {

    private static final long serialVersionUID = 1L;

    private final Long errCode;

    public WxworkException(Long  errCode) {
        super();
        this.errCode = errCode;
    }

    public WxworkException(Long errCode, String message) {
        super(message);
        this.errCode = errCode;
    }

    public Long getErrCode() {
        return errCode;
    }
}
