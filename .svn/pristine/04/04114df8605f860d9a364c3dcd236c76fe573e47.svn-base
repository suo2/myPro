package com.pinnet.chargerapp.mvp.model.beans;

/**
 * @author P00558
 * @date 2018/4/12
 */

public class BaseBean<T> {
    /**
     * {
     * "success": true,
     * "data": {
     * "userid": 3,
     * "phoneNum": "18782128920",
     * "userAddr": null,
     * "userAvatar": null,
     * "nickName": null
     * },
     * "failCode": 0,
     * "params": null,
     * "message": null,
     * "buildCode": "2"
     * }
     */
    public boolean success;
    public String failCode;
    public String[] params;
    public String message;
    public String buildCode;
    public T data;

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getFailCode() {
        return failCode;
    }

    public void setFailCode(String failCode) {
        this.failCode = failCode;
    }

    public String[] getParams() {
        return params;
    }

    public void setParams(String[] params) {
        this.params = params;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBuildCode() {
        return buildCode;
    }

    public void setBuildCode(String buildCode) {
        this.buildCode = buildCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean getError() {
        return !success;
    }
}
