package com.pinnet.chargerapp.mvp.contract.charger;

import com.pinnet.chargerapp.base.IBasePresenter;
import com.pinnet.chargerapp.base.IBaseView;
import com.pinnet.chargerapp.mvp.model.beans.charger.ChargerStationBean;
import com.pinnet.chargerapp.mvp.model.beans.charger.StartChargerBean;
import com.pinnet.chargerapp.mvp.model.http.requestbody.ChargerRequestBody;

import java.util.List;

/**
 * @author P00558
 * @date 2018/4/3
 */

public interface ChargerHomeContract {
    interface View extends IBaseView {
        void onRequestStationListResult(List<ChargerStationBean> stationBeanList);

        void onRequestUnfinishedOrderResult(StartChargerBean startChargerBean);

        ChargerRequestBody getRequestBody();
    }

    interface Presenter extends IBasePresenter<View> {
        void onRequestStationList();

        /**
         * 请求电站列表
         *
         * @param showState 是否显示加载状态
         */
        void onRequestStationList(boolean showState);

        /**
         * 请求是否有正在充电的订单
         */
        void onRequestUnfinishedOrder();

    }
}
