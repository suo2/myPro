package com.pinnet.chargerapp.mvp.model.http.api;

import com.pinnet.chargerapp.mvp.model.beans.BaseBean;
import com.pinnet.chargerapp.mvp.model.beans.LoginBean;
import com.pinnet.chargerapp.mvp.model.http.requestbody.LoginRequestBody;
import com.pinnet.chargerapp.mvp.model.http.requestbody.RegisterRequestBody;
import com.pinnet.chargerapp.mvp.model.http.requestbody.SendIdentifyRequestBody;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author P00558
 * @date 2018/4/12
 * 登录相关API
 */

public interface LoginApis {
    /**
     * 密码登录
     *
     * @param requestBody
     * @return
     */
    @POST("micronetApp/loginByPasswd")
    Flowable<BaseBean<LoginBean>> login(@Body LoginRequestBody requestBody);
    /**
     * 验证码登录
     *
     * @param requestBody
     * @return
     */
    @POST("micronetApp/loginByVerCode")
    Flowable<BaseBean<LoginBean>> loginByVerCode(@Body LoginRequestBody requestBody);

    /**
     * 发送验证码
     *
     * @param requestBody
     * @return
     */
    @POST("micronetApp/sendVerCode")
    Flowable<BaseBean> sendIdentifyCode(@Body SendIdentifyRequestBody requestBody);

    /**
     * 注册
     *
     * @param requestBody
     * @return
     */
    @POST("micronetApp/userRegister")
    Flowable<BaseBean<LoginBean>> register(@Body RegisterRequestBody requestBody);

    /**
     * 重置密码
     *
     * @param requestBody
     * @return
     */
    @POST("/micronetApp/resetPasswd")
    Flowable<BaseBean> resetPassword(@Body RegisterRequestBody requestBody);
}
