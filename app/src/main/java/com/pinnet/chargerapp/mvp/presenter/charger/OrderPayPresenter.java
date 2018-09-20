package com.pinnet.chargerapp.mvp.presenter.charger;

import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.pinnet.chargerapp.base.BaseRxPresenter;
import com.pinnet.chargerapp.mvp.contract.charger.OrderPayContract;
import com.pinnet.chargerapp.mvp.model.DataManager;
import com.pinnet.chargerapp.mvp.model.beans.BaseBean;
import com.pinnet.chargerapp.mvp.model.beans.charger.ChargerPayMentBean;
import com.pinnet.chargerapp.mvp.model.beans.charger.ChargerStatusBean;
import com.pinnet.chargerapp.mvp.model.http.requestbody.ChargerRequestBody;
import com.pinnet.chargerapp.ui.charger.pay.PayResult;
import com.pinnet.chargerapp.utils.RxUtils;
import com.pinnet.chargerapp.widget.CommonSubscriber;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author P00558
 * @date 2018/4/18
 */

public class OrderPayPresenter extends BaseRxPresenter<OrderPayContract.View> implements OrderPayContract.Presenter {
    private DataManager mDataManager;

    @Inject
    OrderPayPresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
    }

    @Override
    public void onPayMent() {
        mView.showLoading();
        ChargerRequestBody requestBody = new ChargerRequestBody();
        requestBody.userId = mDataManager.getLoginedUserId();
        requestBody.payType = mView.getPayType();
        addSubscribe(mDataManager.fetchPayment(requestBody)
                .compose(RxUtils.<BaseBean<ChargerPayMentBean>>rxSchedulerHelper())
                .compose(RxUtils.<ChargerPayMentBean>handleResult())
                .subscribeWith(new CommonSubscriber<ChargerPayMentBean>(mView, false) {
                    @Override
                    public void onNext(ChargerPayMentBean payMentBean) {
                        if (payMentBean != null) {
                            mView.onPaymentResult(payMentBean);
                        }

                    }
                }));
    }
}
