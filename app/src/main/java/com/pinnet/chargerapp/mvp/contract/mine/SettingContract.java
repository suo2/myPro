package com.pinnet.chargerapp.mvp.contract.mine;

import android.content.Context;

import com.pinnet.chargerapp.base.IBasePresenter;
import com.pinnet.chargerapp.base.IBaseView;

/**
 * @author P00558
 * @date 2018/4/9
 */

public interface SettingContract {
    interface View extends IBaseView {
    }

    interface Presenter extends IBasePresenter<View> {
        void onLoginOut();

        String getCacheSize(Context context);

        void clearCache(Context context);
    }
}
