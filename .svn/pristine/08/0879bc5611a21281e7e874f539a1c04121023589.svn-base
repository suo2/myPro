package com.pinnet.chargerapp.mvp.contract.mine;

import com.pinnet.chargerapp.base.IBasePresenter;
import com.pinnet.chargerapp.base.IBaseView;
import com.pinnet.chargerapp.mvp.model.beans.mine.PersonalInfoBean;

/**
 * @author P00558
 * @date 2018/5/8
 */

public interface MineHomeContract {
    interface View extends IBaseView {
        void updatePersonalInfo(PersonalInfoBean bean);

        void onLoadUserHeader(String path);
    }

    interface Presenter extends IBasePresenter<View> {
        void onQueryPersonalInfo();

        void onDownloadFile(String userHeaderUpdateTime);
    }
}
