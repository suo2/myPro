package com.pinnet.chargerapp.mvp.presenter.mine;

import com.pinnet.chargerapp.base.BaseRxPresenter;
import com.pinnet.chargerapp.mvp.contract.mine.PersonalInfoModifyContract;
import com.pinnet.chargerapp.mvp.model.DataManager;
import com.pinnet.chargerapp.mvp.model.beans.BaseBean;
import com.pinnet.chargerapp.mvp.model.beans.mine.PersonalInfoBean;
import com.pinnet.chargerapp.mvp.model.http.requestbody.UserInfoRequestBody;
import com.pinnet.chargerapp.utils.RxUtils;
import com.pinnet.chargerapp.utils.ToastUtils;
import com.pinnet.chargerapp.widget.CommonSubscriber;

import javax.inject.Inject;

/**
 * @author P00558
 * @date 2018/4/17
 */

public class PersonalInfoModifyPresenter extends BaseRxPresenter<PersonalInfoModifyContract.View> implements PersonalInfoModifyContract.Presenter {
    DataManager mDataManager;

    @Inject
    PersonalInfoModifyPresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
    }

    @Override
    public void onModify() {
        addSubscribe(mDataManager.fetchPersonalInfoSet(mView.getUserInfoRequestBody())
                .compose(RxUtils.<BaseBean<PersonalInfoBean>>rxSchedulerHelper())
                .compose(RxUtils.<PersonalInfoBean>handleResult())
                .subscribeWith(new CommonSubscriber<PersonalInfoBean>(mView, false) {
                    @Override
                    public void onNext(PersonalInfoBean bean) {
                        if (bean != null) {
                            mView.onModifyResult(bean);
                        }
                    }
                }));
    }
}
