package com.pinnet.chargerapp.mvp.contract.mine;

import com.pinnet.chargerapp.base.IBaseView;
import com.pinnet.chargerapp.base.IBasePresenter;
import com.pinnet.chargerapp.mvp.model.beans.mine.ChargerRecordBean;
import com.pinnet.chargerapp.mvp.model.http.requestbody.ChargerRequestBody;

import java.util.List;

/**
 * @author P00558
 * @date 2018/4/9
 */

public interface ChargingRecordContract {
    interface View extends IBaseView {
        void onGetChargerRecordsResult(List<ChargerRecordBean> beanList);

        ChargerRequestBody onGetRequestBody();

        void onChangeRefreshState();
    }

    interface Presenter extends IBasePresenter<View> {
        void getChargerRecords();
    }
}
