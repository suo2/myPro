package com.pinnet.chargerapp.mvp.model.beans;

/**
 * @author P00558
 * @date 2018/4/12
 */

public class LoginBean {
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

    String userid;
    String phoneNum;
    String userAddr;
    String nickName;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getUserAddr() {
        return userAddr;
    }

    public void setUserAddr(String userAddr) {
        this.userAddr = userAddr;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
