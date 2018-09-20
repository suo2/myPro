package com.pinnet.chargerapp.mvp.presenter.charger;

import com.pinnet.chargerapp.base.BaseRxPresenter;
import com.pinnet.chargerapp.mvp.contract.charger.RoutePlanContract;
import com.pinnet.chargerapp.mvp.model.DataManager;
import com.pinnet.chargerapp.mvp.model.beans.charger.RouteHistoryBean;
import com.pinnet.chargerapp.utils.RxUtils;
import com.pinnet.chargerapp.widget.CommonSubscriber;

import java.util.List;

import javax.inject.Inject;

/**
 * @author P00558
 * @date 2018/4/10
 */

public class RoutePlanPresenter extends BaseRxPresenter<RoutePlanContract.View> implements RoutePlanContract.Preseter {
    private DataManager mDataManager;

    @Inject
    RoutePlanPresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
    }

    @Override
    public void addRouteHistory(RouteHistoryBean bean) {
        addSubscribe(mDataManager.insertRouteHistory(bean)
                .compose(RxUtils.<Boolean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<Boolean>(mView, false) {
                    @Override
                    public void onNext(Boolean aBoolean) {

                    }
                }));
    }

    @Override
    public void onQueryRouteHistory() {
        addSubscribe(mDataManager.getUserRouteHistory(mDataManager.getLoginedUserId())
                .compose(RxUtils.<List<RouteHistoryBean>>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<List<RouteHistoryBean>>(mView) {
                    @Override
                    public void onNext(List<RouteHistoryBean> beanList) {
                        if (beanList == null) {
                            mView.stateEmpty();
                        } else if (beanList.size() == 0) {
                            mView.stateEmpty();
                        } else {
                            mView.onQueryRouteHistoryResult(beanList);
                        }
                    }
                }));
    }
}
