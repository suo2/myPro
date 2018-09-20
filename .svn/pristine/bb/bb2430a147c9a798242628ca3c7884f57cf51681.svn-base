package com.pinnet.chargerapp.mvp.presenter.charger;

import com.pinnet.chargerapp.app.Constants;
import com.pinnet.chargerapp.base.BaseRxPresenter;
import com.pinnet.chargerapp.mvp.contract.charger.OrderDetailContract;
import com.pinnet.chargerapp.mvp.model.DataManager;
import com.pinnet.chargerapp.mvp.model.beans.BaseBean;
import com.pinnet.chargerapp.mvp.model.beans.charger.ChargerStatusBean;
import com.pinnet.chargerapp.mvp.model.beans.charger.OrderDetailBean;
import com.pinnet.chargerapp.mvp.model.http.requestbody.ChargerRequestBody;
import com.pinnet.chargerapp.utils.RxUtils;
import com.pinnet.chargerapp.widget.CommonSubscriber;

import java.util.List;

import javax.inject.Inject;

/**
 * @author P00558
 * @date 2018/4/23
 * 订单详情
 */

public class OrderDetialPresenter extends BaseRxPresenter<OrderDetailContract.View> implements OrderDetailContract.Presenter {
    private DataManager mDataManager;

    @Inject
    OrderDetialPresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
    }

    @Override
    public void onRequestOrderDetail() {
        mView.stateLoading();
        if (mView.getOrderDetailType() == Constants.ORDER_DETAIL_TYPE_PAY||mView.getOrderDetailType() == Constants.ORDER_DETAIL_TYPE_UNPAY) {
            ChargerRequestBody requestBody = new ChargerRequestBody();
            requestBody.userId = mDataManager.getLoginedUserId();
            addSubscribe(mDataManager.fetchGetOrderInfoByUserId(requestBody)
                    .compose(RxUtils.<BaseBean<OrderDetailBean>>rxSchedulerHelper())
                    .compose(RxUtils.<OrderDetailBean>handleResult())
                    .subscribeWith(new CommonSubscriber<OrderDetailBean>(mView) {
                        @Override
                        public void onNext(OrderDetailBean orderDetailBean) {
                            mView.stateMain();
                            mView.onRequestOrderDetailResult(orderDetailBean);
                        }
                    }));
        } else {
            ChargerRequestBody requestBody = new ChargerRequestBody();
            requestBody.orderId = mView.getOrderId();
            addSubscribe(mDataManager.fetchGetOrderInfoByOrderId(requestBody)
                    .compose(RxUtils.<BaseBean<OrderDetailBean>>rxSchedulerHelper())
                    .compose(RxUtils.<OrderDetailBean>handleResult())
                    .subscribeWith(new CommonSubscriber<OrderDetailBean>(mView) {
                        @Override
                        public void onNext(OrderDetailBean orderDetailBean) {
                            mView.stateMain();
                            mView.onRequestOrderDetailResult(orderDetailBean);
                        }
                    }));
        }

    }
}
