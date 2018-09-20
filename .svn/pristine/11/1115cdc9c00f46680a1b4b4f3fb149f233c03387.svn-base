package com.pinnet.chargerapp.mvp.presenter.charger;

import com.pinnet.chargerapp.base.BaseRxPresenter;
import com.pinnet.chargerapp.mvp.contract.charger.ChargerChargingContract;
import com.pinnet.chargerapp.mvp.model.DataManager;
import com.pinnet.chargerapp.mvp.model.beans.BaseBean;
import com.pinnet.chargerapp.mvp.model.beans.LoginBean;
import com.pinnet.chargerapp.mvp.model.beans.charger.ChargerStatusBean;
import com.pinnet.chargerapp.mvp.model.http.requestbody.ChargerRequestBody;
import com.pinnet.chargerapp.utils.RxUtils;
import com.pinnet.chargerapp.widget.CommonSubscriber;


import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author P00558
 * @date 2018/4/21
 * 充电中
 */

public class ChargerChargingPresenter extends BaseRxPresenter<ChargerChargingContract.View> implements ChargerChargingContract.Presenter {
    DataManager mDataManager;

    @Inject
    ChargerChargingPresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
    }

    @Override
    public void onRequestProgress() {
        final ChargerRequestBody body = new ChargerRequestBody();
        addSubscribe(Flowable.interval(0, 10, TimeUnit.SECONDS).subscribe(new Consumer<Long>() {
            @Override
            public void accept(@NonNull Long aLong) throws Exception {
                addSubscribe(mDataManager.fetchChargerProgress(body)
                        .compose(RxUtils.<BaseBean<LoginBean>>rxSchedulerHelper())
                        .compose(RxUtils.<LoginBean>handleResult())
                        .subscribeWith(new CommonSubscriber<LoginBean>(mView, false) {
                            @Override
                            public void onNext(LoginBean loginBean) {
                                mView.onRequestProgressResult();
                            }
                        }));
            }
        }));
    }

    @Override
    public void onCountDown(final long times) {
        addSubscribe(Flowable.interval(0, 1, TimeUnit.SECONDS)
                .take(times / 1000)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        mView.onUpdateCotunDownView(times - (aLong + 1) * 1000);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {

                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                }));
    }

    @Override
    public void stopCharger() {
        unSubscribe();
        addSubscribe(mDataManager.fetchStopCharging(mView.getStopChargerRequestBody())
                .compose(RxUtils.<BaseBean>rxSchedulerHelper())
                .compose(RxUtils.handleResultBase())
                .subscribeWith(new CommonSubscriber<BaseBean>(mView, false) {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        if (baseBean != null && baseBean.isSuccess()) {
                            mView.onStopChargerResult(true);
                        }else{
                            mView.onStopChargerResult(false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.onStopChargerResult(false);
                    }
                })

        );
    }
}
