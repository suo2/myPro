package com.pinnet.chargerapp.mvp.contract.charger;

import com.pinnet.chargerapp.base.IBasePresenter;
import com.pinnet.chargerapp.base.IBaseView;
import com.pinnet.chargerapp.mvp.model.beans.charger.ChargerStationDetailBean;
import com.pinnet.chargerapp.mvp.model.http.requestbody.ChargerRequestBody;

/**
 * @author P00558
 * @date 2018/4/10
 * 电站详情
 */

public interface StationDetailContract {
    interface View extends IBaseView {
        /**
         * 获取电站详情数据返回
         */
        void onRequestStationDetailResult(ChargerStationDetailBean stationDetailBean);

        void onDownLoadStationImageResult(String path);

        ChargerRequestBody getRequestBody();
    }

    interface Presenter extends IBasePresenter<View> {
        /**
         * 获取电站详情数据
         */
        void onRequestStationDetail(String sId);

        void onDownLoadStationImage(String sId);
    }
}
