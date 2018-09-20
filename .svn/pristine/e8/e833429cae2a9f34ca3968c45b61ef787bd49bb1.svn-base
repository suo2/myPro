package com.pinnet.chargerapp.mvp.contract.charger;

import com.pinnet.chargerapp.base.IBasePresenter;
import com.pinnet.chargerapp.base.IBaseView;
import com.pinnet.chargerapp.mvp.model.beans.charger.ChargerPileInfoBean;
import com.pinnet.chargerapp.mvp.model.beans.charger.ChargingBean;
import com.pinnet.chargerapp.mvp.model.beans.charger.StartChargerBean;
import com.pinnet.chargerapp.mvp.model.http.requestbody.ChargerRequestBody;

/**
 * @author P00558
 * @date 2018/4/21
 */

public interface ChargerMethodContract {
    interface View extends IBaseView {
        /**
         * 查询是否存在未完成订单 结果
         */
        void onQueryImcompleteOrderResult();

        /**
         * 开始充电返回结果
         *
         * @param startChargerBean
         */
        void onStartChargingResult(StartChargerBean startChargerBean);

        ChargerRequestBody getChargerRequestBody();

        void onRequestUnfinishedOrderResult(StartChargerBean startChargerBean);

        void onGetChargePileInfoResult(ChargerPileInfoBean chargerPileInfoBean);

    }

    interface Presenter extends IBasePresenter<View> {
        /**
         * 查询是否存在未完成订单
         */
        void onQueryImcompleteOrder();

        void onStartCharging();

        void onRequestUnfinishedOrder();

        void onGetChargePileInfo(String chargePileNum);
    }
}
