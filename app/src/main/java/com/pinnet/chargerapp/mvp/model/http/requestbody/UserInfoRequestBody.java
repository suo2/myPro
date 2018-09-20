package com.pinnet.chargerapp.mvp.model.http.requestbody;

import java.io.Serializable;

/**
 * @author P00558
 * @date 2018/4/14
 */

public class UserInfoRequestBody implements Serializable{
    /**
     * 用户ID
     */
    public String userId;
    /**
     * 手机号
     */
    public String phoneNum;
    /**
     * 用户头像
     */
    public String userAvatar;
    /**
     * 昵称
     */
    public String nickName;
    /**
     * 地址
     */
    public String address;
}
