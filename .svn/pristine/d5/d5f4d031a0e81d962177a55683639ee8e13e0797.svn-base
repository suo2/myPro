package com.pinnet.chargerapp.mvp.model.http.exception;

/**
 * @author P00558
 * @date 2018/6/1
 * 单点登录-多次登录异常
 */

public class MostLoginException extends Exception {
    private int code;

    public MostLoginException(String msg) {
        super(msg);
    }

    public MostLoginException(String msg, int code) {
        super(msg);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
