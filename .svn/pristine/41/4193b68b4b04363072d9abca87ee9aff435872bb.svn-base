package com.pinnet.chargerapp.mvp.contract.charger;

import com.pinnet.chargerapp.base.IBasePresenter;
import com.pinnet.chargerapp.base.IBaseView;
import com.pinnet.chargerapp.mvp.model.http.requestbody.ChargerRequestBody;

/**
 * @author P00558
 * @date 2018/4/21
 * 充电进度
 */

public interface ChargerChargingContract {
    interface View extends IBaseView {
        /**
         * 充电进度返回结果
         */
        void onRequestProgressResult();

        /**
         * 更新倒计时控件
         *
         * @param time
         */
        void onUpdateCotunDownView(long time);

        /**
         * 停止充电返回结果
         */
        void onStopChargerResult(boolean isSuccess);

        /**
         * 获取 请求参数
         *
         * @return 请求参数
         */
        ChargerRequestBody getStopChargerRequestBody();
    }

    interface Presenter extends IBasePresenter<View> {
        /**
         * 查询充电进度
         */
        void onRequestProgress();

        /**
         * 倒计时
         */
        void onCountDown(long times);

        /**
         * 结束充电
         */
        void stopCharger();
    }
}
