package com.pinnet.chargerapp.mvp.presenter.charger;

import com.pinnet.chargerapp.base.BaseRxPresenter;
import com.pinnet.chargerapp.mvp.contract.charger.ChargeStatusContract;
import com.pinnet.chargerapp.mvp.model.DataManager;
import com.pinnet.chargerapp.mvp.model.beans.BaseBean;
import com.pinnet.chargerapp.mvp.model.beans.charger.ChargerStationDetailBean;
import com.pinnet.chargerapp.mvp.model.beans.charger.ChargerStatusBean;
import com.pinnet.chargerapp.mvp.model.http.requestbody.ChargerRequestBody;
import com.pinnet.chargerapp.utils.RxUtils;
import com.pinnet.chargerapp.widget.CommonSubscriber;

import java.util.List;

import javax.inject.Inject;

/**
 * @author P00558
 * @date 2018/4/10
 */

public class ChargeStatusPresenter extends BaseRxPresenter<ChargeStatusContract.View> implements ChargeStatusContract.Presenter {
    private DataManager mDataManager;

    @Inject
    ChargeStatusPresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
    }

    @Override
    public void onRequestChargeList(String sid) {
        mView.stateLoading();
        ChargerRequestBody body = new ChargerRequestBody();
        body.sid = sid;
        addSubscribe(mDataManager.fetchChargeListByStationId(body)
                .compose(RxUtils.<BaseBean<List<ChargerStatusBean>>>rxSchedulerHelper())
                .compose(RxUtils.<List<ChargerStatusBean>>handleResult())
                .subscribeWith(new CommonSubscriber<List<ChargerStatusBean>>(mView) {
                    @Override
                    public void onNext(List<ChargerStatusBean> stationDetailBean) {
                        if (stationDetailBean != null) {
                            mView.stateMain();
                            mView.onRequestChargeListResult(stationDetailBean);
                        } else {
                            mView.stateEmpty();
                        }


                    }
                }));
    }

    @Override
    public void onRequestMuzzleStatus() {

    }
}
