package com.pinnet.chargerapp.mvp.contract.mine;

import com.pinnet.chargerapp.base.IBasePresenter;
import com.pinnet.chargerapp.base.IBaseView;
import com.pinnet.chargerapp.mvp.model.beans.mine.PersonalInfoBean;

import java.io.File;

/**
 * @author P00558
 * @date 2018/4/11
 */

public interface PersonalInfoContract {
    interface View extends IBaseView {
        String getAddress();

        String getNickName();

        void updatePersonalInfo(PersonalInfoBean bean);

        void onCompressed(String filePath);

        void onLoadUserHeader(String path);
    }

    interface Presenter extends IBasePresenter<View> {
        void onQueryPersonalInfo();

        void onResetPersonalInfo();

        void onCompressFile(String filePath);

        void onUploadImg(String fileFath);

        void onDownloadFile(String userHeaderUpdateTime);
    }
}
