package com.pinnet.chargerapp.mvp.presenter.charger;

import com.pinnet.chargerapp.base.BaseRxPresenter;
import com.pinnet.chargerapp.mvp.contract.charger.ChargerHomeContract;
import com.pinnet.chargerapp.mvp.model.DataManager;
import com.pinnet.chargerapp.mvp.model.beans.BaseBean;
import com.pinnet.chargerapp.mvp.model.beans.LoginBean;
import com.pinnet.chargerapp.mvp.model.beans.charger.ChargerStationBean;
import com.pinnet.chargerapp.mvp.model.beans.charger.StartChargerBean;
import com.pinnet.chargerapp.mvp.model.http.exception.EmptyException;
import com.pinnet.chargerapp.mvp.model.http.requestbody.ChargerRequestBody;
import com.pinnet.chargerapp.utils.RxUtils;
import com.pinnet.chargerapp.widget.CommonSubscriber;

import java.util.List;

import javax.inject.Inject;

/**
 * @author P00558
 * @date 2018/4/3
 * 首页
 */

public class ChargerHomePresenter extends BaseRxPresenter<ChargerHomeContract.View> implements ChargerHomeContract.Presenter {
    private DataManager mDataManager;

    @Inject
    ChargerHomePresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void onRequestStationList() {
        onRequestStationList(false);
    }

    @Override
    public void onRequestStationList(final boolean showState) {
        addSubscribe(mDataManager.fetchChargerStationList(mView.getRequestBody())
                .compose(RxUtils.<BaseBean<List<ChargerStationBean>>>rxSchedulerHelper())
                .compose(RxUtils.<List<ChargerStationBean>>handleListResult())
                .subscribeWith(new CommonSubscriber<List<ChargerStationBean>>(mView, showState) {
                    @Override
                    public void onNext(List<ChargerStationBean> chargerStationBeen) {
                        if (chargerStationBeen != null) {
                            mView.onRequestStationListResult(chargerStationBeen);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if (e instanceof EmptyException) {
                            if (showState) {
                                mView.stateEmpty();
                            } else {
                                mView.onRequestStationListResult(null);
                            }
                        }
                    }
                })
        );
    }

    @Override
    public void onRequestUnfinishedOrder() {
        if (!mDataManager.getUserIsLogined()) {
            return;
        }
        ChargerRequestBody requestBody = new ChargerRequestBody();
        requestBody.userAccount = mDataManager.getLoginedUserId();
        addSubscribe(mDataManager.fetchGetUnfinishedOrder(requestBody)
                .compose(RxUtils.<BaseBean<StartChargerBean>>rxSchedulerHelper())
                .compose(RxUtils.<StartChargerBean>handleResult())
                .subscribeWith(new CommonSubscriber<StartChargerBean>(mView, false, false) {
                    @Override
                    public void onNext(StartChargerBean startChargerBean) {
                        mView.onRequestUnfinishedOrderResult(startChargerBean);
                    }
                }));
    }
}
