package com.pinnet.chargerapp.mvp.contract.charger;

import com.pinnet.chargerapp.base.IBasePresenter;
import com.pinnet.chargerapp.base.IBaseView;
import com.pinnet.chargerapp.mvp.model.beans.charger.ChargerStatusBean;

import java.util.List;

/**
 * @author P00558
 * @date 2018/4/10
 */

public interface ChargeStatusContract {
    interface View extends IBaseView {
        void onRequestChargeListResult(List<ChargerStatusBean> stationDetailBean);

        void onRequestMuzzleStatusResult();
    }

    interface Presenter extends IBasePresenter<View> {
        void onRequestChargeList(String sid);

        void onRequestMuzzleStatus();
    }
}
