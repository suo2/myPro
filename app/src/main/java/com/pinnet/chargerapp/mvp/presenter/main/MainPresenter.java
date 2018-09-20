package com.pinnet.chargerapp.mvp.presenter.main;

import android.widget.Toast;

import com.pinnet.chargerapp.base.BaseRxPresenter;
import com.pinnet.chargerapp.mvp.contract.main.MainContract;
import com.pinnet.chargerapp.mvp.model.DataManager;
import com.pinnet.chargerapp.mvp.model.beans.BaseBean;
import com.pinnet.chargerapp.mvp.model.beans.charger.StartChargerBean;
import com.pinnet.chargerapp.mvp.model.http.exception.NoParentException;
import com.pinnet.chargerapp.mvp.model.http.requestbody.ChargerRequestBody;
import com.pinnet.chargerapp.utils.RxUtils;
import com.pinnet.chargerapp.widget.CommonSubscriber;

import javax.inject.Inject;

/**
 * @author P00558
 * @date 2018/6/15
 */

public class MainPresenter extends BaseRxPresenter<MainContract.View> implements MainContract.Presenter {
    private DataManager mDataManager;

    @Inject
    MainPresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
    }

    @Override
    public void onRequestUnfinishedOrder() {
        ChargerRequestBody requestBody = new ChargerRequestBody();
        requestBody.userAccount = mDataManager.getLoginedUserId();
        addSubscribe(mDataManager.fetchGetUnfinishedOrder(requestBody)
                .compose(RxUtils.<BaseBean<StartChargerBean>>rxSchedulerHelper())
                .compose(RxUtils.<StartChargerBean>handleResultNoParent())
                .subscribeWith(new CommonSubscriber<StartChargerBean>(mView, false) {
                    @Override
                    public void onNext(StartChargerBean startChargerBean) {
                        mView.onRequestUnfinishedOrderResult(startChargerBean, true);
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);

                        if (e instanceof NoParentException) {//没有未完成的订单
                            mView.onRequestUnfinishedOrderResult(null, true);
                        } else {
                            mView.onRequestUnfinishedOrderResult(null, false);
                        }

                    }
                }));
    }

    @Override
    public void onQueryImcompleteOrder() {
        mView.showLoading();
        ChargerRequestBody body = new ChargerRequestBody();
        body.userAccount = mDataManager.getLoginedUserId();
        addSubscribe(mDataManager.fetchGetCheckAbnormalOrder(body)
                .compose(RxUtils.<BaseBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<BaseBean>(mView, false, false, false) {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        if (baseBean != null && baseBean.isSuccess() && null != baseBean.getData()) {
                            mView.onQueryImcompleteOrderResult(true);
                        } else {
                            mView.onQueryImcompleteOrderResult(false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.onQueryImcompleteOrderResult(false);
                    }
                }));
    }

    @Override
    public void onCheckChargePileStatus(String chargePileNum) {
        ChargerRequestBody body = new ChargerRequestBody();
        body.chargePileNum = chargePileNum;
        addSubscribe(mDataManager.fetchCheckChargePileStatus(body)
                .compose(RxUtils.<BaseBean>rxSchedulerHelper())
                .compose(RxUtils.handleResultBase())
                .subscribeWith(new CommonSubscriber<BaseBean>(mView, false) {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        if (baseBean.getSuccess()) {
                            mView.onCheckChargePileStatusResult(true);
                        }
                    }
                }));
    }
}
