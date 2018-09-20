package com.pinnet.chargerapp.mvp.contract.login;

import com.pinnet.chargerapp.base.IBaseView;
import com.pinnet.chargerapp.base.IBasePresenter;

/**
 * @author P00558
 * @date 2018/4/8
 */

public interface RegisterContract {
    interface View extends IBaseView {
        /**
         *
         */
        String getPhoneNumber();

        String getIdentifyinyCode();

        String getPassword();

        String getPasswordAgain();

        void updateIdentifyingCodeView(boolean enable, long times);

        void onRegistered();
    }

    interface Presenter extends IBasePresenter<View> {
        void onSendIdentifyCode();

        void onRegister();

        void onResetPassWord();
    }
}
