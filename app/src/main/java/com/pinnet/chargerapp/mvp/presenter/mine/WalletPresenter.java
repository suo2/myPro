package com.pinnet.chargerapp.mvp.presenter.mine;

import com.pinnet.chargerapp.base.BaseRxPresenter;
import com.pinnet.chargerapp.mvp.contract.mine.WalletContract;
import com.pinnet.chargerapp.mvp.model.DataManager;

import javax.inject.Inject;

/**
 * @author P00558
 * @date 2018/4/11
 */

public class WalletPresenter extends BaseRxPresenter<WalletContract.View> implements WalletContract.Presenter {
    DataManager mDataManager;

    @Inject
    WalletPresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
    }

    @Override
    public void onRequestData() {

    }
}
