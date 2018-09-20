package com.pinnet.chargerapp.mvp.presenter.mine;

import com.pinnet.chargerapp.base.BaseRxPresenter;
import com.pinnet.chargerapp.mvp.contract.mine.ChargingRecordContract;
import com.pinnet.chargerapp.mvp.model.DataManager;
import com.pinnet.chargerapp.mvp.model.beans.BaseBean;
import com.pinnet.chargerapp.mvp.model.beans.charger.OrderDetailBean;
import com.pinnet.chargerapp.mvp.model.beans.mine.ChargerRecordBean;
import com.pinnet.chargerapp.mvp.model.http.requestbody.ChargerRequestBody;
import com.pinnet.chargerapp.utils.RxUtils;
import com.pinnet.chargerapp.widget.CommonSubscriber;

import java.util.List;

import javax.inject.Inject;

/**
 * @author P00558
 * @date 2018/4/9
 */

public class ChargingRecordPresenter extends BaseRxPresenter<ChargingRecordContract.View> implements ChargingRecordContract.Presenter {
    private DataManager mDataManager;

    @Inject
    ChargingRecordPresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
    }

    @Override
    public void getChargerRecords() {
        unSubscribe();
        boolean showErrorState;
        if ("1".equals(mView.onGetRequestBody().pageNum)) {
            showErrorState = true;
        } else {
            showErrorState = false;
        }
        addSubscribe(mDataManager.fetchGetChargeRecords(mView.onGetRequestBody())
                .compose(RxUtils.<BaseBean<List<ChargerRecordBean>>>rxSchedulerHelper())
                .compose(RxUtils.<List<ChargerRecordBean>>handleListResult())
                .subscribeWith(new CommonSubscriber<List<ChargerRecordBean>>(mView, showErrorState, false) {
                    @Override
                    public void onNext(List<ChargerRecordBean> orderDetailBeanList) {
                        mView.stateMain();
                        mView.onGetChargerRecordsResult(orderDetailBeanList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.onChangeRefreshState();
                    }
                }));
    }
}
