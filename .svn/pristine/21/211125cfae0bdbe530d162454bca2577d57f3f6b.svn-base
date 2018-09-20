package com.pinnet.chargerapp.mvp.contract.charger;

import com.pinnet.chargerapp.base.IBasePresenter;
import com.pinnet.chargerapp.base.IBaseView;
import com.pinnet.chargerapp.mvp.model.beans.charger.OrderDetailBean;

/**
 * @author P00558
 * @date 2018/4/23
 */

public interface OrderDetailContract {
    interface View extends IBaseView {
        void onRequestOrderDetailResult(OrderDetailBean detailBean);

        int getOrderDetailType();

        String getOrderId();
    }

    interface Presenter extends IBasePresenter<View> {
        void onRequestOrderDetail();
    }
}
