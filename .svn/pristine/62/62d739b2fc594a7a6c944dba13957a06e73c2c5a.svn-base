package com.pinnet.chargerapp.mvp.contract.login;

import com.pinnet.chargerapp.base.IBasePresenter;
import com.pinnet.chargerapp.base.IBaseView;

/**
 * @author P00558
 * @date 2018/4/8
 */

public interface LoginContract {
    interface View extends IBaseView {

        String getUserName();

        String getPassWord();

        String getIdentifyinyCode();

        /**
         * 登录返回
         */
        void onLogined();

        /**
         * 发送验证码倒计时
         * @param enable
         * @param times
         */
        void updateIdentifyingCodeView(boolean enable, long times);
    }

    interface Presenter extends IBasePresenter<View> {
        /**
         * 普通登录
         */
        void onLogin();

        /**
         * 发送验证码
         */
        void onSendIdentifyCode();

        /**
         * 验证码登录
         */
        void onLoginByVerCode();
    }
}
