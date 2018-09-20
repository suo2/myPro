package com.pinnet.chargerapp.mvp.contract.main;

import com.pinnet.chargerapp.base.IBasePresenter;
import com.pinnet.chargerapp.base.IBaseView;
import com.pinnet.chargerapp.mvp.model.beans.charger.StartChargerBean;
import com.pinnet.chargerapp.mvp.model.http.requestbody.ChargerRequestBody;

/**
 * @author P00558
 * @date 2018/4/2
 */

public interface MainContract {
    interface View extends IBaseView {
        /**
         * 查询是否存在未完成订单 结果
         *
         * @param hasImcompleteOrder true 有，false  没有
         */
        void onQueryImcompleteOrderResult(boolean hasImcompleteOrder);

        /**
         * 请求是否有正在充电的订单 返回结果
         *
         * @param startChargerBean
         */
        void onRequestUnfinishedOrderResult(StartChargerBean startChargerBean, boolean isSuccess);

        void onCheckChargePileStatusResult(boolean enable);
    }

    interface Presenter extends IBasePresenter<View> {

        /**
         * 请求是否有正在充电的订单
         */
        void onRequestUnfinishedOrder();

        /**
         * 查询是否存在未完成订单
         */
        void onQueryImcompleteOrder();

        /**
         * 检查充电桩状态
         *
         * @param chargePileNum
         */
        void onCheckChargePileStatus(String chargePileNum);
    }
}
