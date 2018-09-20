package com.pinnet.chargerapp.mvp.presenter.charger;

import com.pinnet.chargerapp.base.BaseRxPresenter;
import com.pinnet.chargerapp.mvp.contract.charger.ChargerMethodContract;
import com.pinnet.chargerapp.mvp.model.DataManager;
import com.pinnet.chargerapp.mvp.model.beans.BaseBean;
import com.pinnet.chargerapp.mvp.model.beans.charger.ChargerPileInfoBean;
import com.pinnet.chargerapp.mvp.model.beans.charger.OrderDetailBean;
import com.pinnet.chargerapp.mvp.model.beans.charger.StartChargerBean;
import com.pinnet.chargerapp.mvp.model.http.requestbody.ChargerRequestBody;
import com.pinnet.chargerapp.utils.RxUtils;
import com.pinnet.chargerapp.widget.CommonSubscriber;

import javax.inject.Inject;

/**
 * @author P00558
 * @date 2018/4/21
 */

public class ChargerMethodPresenter extends BaseRxPresenter<ChargerMethodContract.View> implements ChargerMethodContract.Presenter {
    private DataManager mDataManager;

    @Inject
    ChargerMethodPresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
    }

    @Override
    public void onQueryImcompleteOrder() {
        ChargerRequestBody body = new ChargerRequestBody();
        body.userAccount = mDataManager.getLoginedUserId();
        addSubscribe(mDataManager.fetchGetCheckAbnormalOrder(body)
                .compose(RxUtils.<BaseBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<BaseBean>(mView, false) {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        if (baseBean != null && baseBean.isSuccess() && null != baseBean.getData()) {
                            mView.onQueryImcompleteOrderResult();
                        }
                    }
                }));
    }

    @Override
    public void onStartCharging() {
        mView.showLoading();
        addSubscribe(mDataManager.fetchStartCharging(mView.getChargerRequestBody())
                .compose(RxUtils.<BaseBean<StartChargerBean>>rxSchedulerHelper())
                .compose(RxUtils.<StartChargerBean>handleResult())
                .subscribeWith(new CommonSubscriber<StartChargerBean>(mView, false) {
                    @Override
                    public void onNext(StartChargerBean bean) {
                        mView.onStartChargingResult(bean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.onStartChargingResult(null);
                    }
                }));
    }

    @Override
    public void onRequestUnfinishedOrder() {
        if (!mDataManager.getUserIsLogined()) {
            return;
        }
        mView.showLoading();
        ChargerRequestBody requestBody = new ChargerRequestBody();
        requestBody.userAccount = mDataManager.getLoginedUserId();
        addSubscribe(mDataManager.fetchGetUnfinishedOrder(requestBody)
                .compose(RxUtils.<BaseBean<StartChargerBean>>rxSchedulerHelper())
                .compose(RxUtils.<StartChargerBean>handleResult())
                .subscribeWith(new CommonSubscriber<StartChargerBean>(mView, false, false, false) {
                    @Override
                    public void onNext(StartChargerBean startChargerBean) {
                        mView.onRequestUnfinishedOrderResult(startChargerBean);
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.onRequestUnfinishedOrderResult(null);
                    }
                }));
    }

    @Override
    public void onGetChargePileInfo(String chargePileNum) {
        ChargerRequestBody requestBody = new ChargerRequestBody();
        requestBody.chargePileNum = chargePileNum;
        addSubscribe(mDataManager.fetchGetChargePileInfo(requestBody)
                .compose(RxUtils.<BaseBean<ChargerPileInfoBean>>rxSchedulerHelper())
                .compose(RxUtils.<ChargerPileInfoBean>handleResult())
                .subscribeWith(new CommonSubscriber<ChargerPileInfoBean>(mView, false) {
                    @Override
                    public void onNext(ChargerPileInfoBean chargerPileInfoBean) {
                        mView.onGetChargePileInfoResult(chargerPileInfoBean);
                    }
                }));

    }

}
