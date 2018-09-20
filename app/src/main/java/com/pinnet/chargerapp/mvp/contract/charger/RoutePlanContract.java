package com.pinnet.chargerapp.mvp.contract.charger;

import com.pinnet.chargerapp.base.IBasePresenter;
import com.pinnet.chargerapp.base.IBaseView;
import com.pinnet.chargerapp.mvp.model.beans.charger.RouteHistoryBean;

import java.util.List;

/**
 * @author P00558
 * @date 2018/4/10
 */

public interface RoutePlanContract {
    interface View extends IBaseView {
        void onQueryRouteHistoryResult(List<RouteHistoryBean> beanList);
    }

    interface Preseter extends IBasePresenter<View> {
        void addRouteHistory(RouteHistoryBean bean);

        void onQueryRouteHistory();
    }
}
