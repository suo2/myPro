package com.pinnet.chargerapp.mvp.contract.charger;

import android.app.Activity;

import com.pinnet.chargerapp.base.IBasePresenter;
import com.pinnet.chargerapp.base.IBaseView;
import com.pinnet.chargerapp.mvp.model.beans.charger.ChargerPayMentBean;

/**
 * @author P00558
 * @date 2018/4/18
 * 订单支付
 */

public interface OrderPayContract {
    interface View extends IBaseView {
        Activity getActivity();

        /**
         * 获取支付方式
         *
         * @return
         */
        String getPayType();

        /**
         * 支付订单信息返回
         *
         * @param payMentBean
         */
        void onPaymentResult(ChargerPayMentBean payMentBean);
    }

    interface Presenter extends IBasePresenter<View> {
        /**
         * 获取支付订单信息
         */
        void onPayMent();
    }
}
