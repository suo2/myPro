package com.pinnet.chargerapp.mvp.contract.mine;

import com.pinnet.chargerapp.base.IBasePresenter;
import com.pinnet.chargerapp.base.IBaseView;
import com.pinnet.chargerapp.mvp.model.beans.mine.PersonalInfoBean;
import com.pinnet.chargerapp.mvp.model.http.requestbody.UserInfoRequestBody;

/**
 * @author P00558
 * @date 2018/4/17
 */

public interface PersonalInfoModifyContract {
    interface View extends IBaseView {
        String getInputString();

        void onModifyResult(PersonalInfoBean bean);

        UserInfoRequestBody getUserInfoRequestBody();
    }

    interface Presenter extends IBasePresenter<View> {
        void onModify();
    }
}
