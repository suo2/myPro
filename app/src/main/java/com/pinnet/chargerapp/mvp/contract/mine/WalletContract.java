package com.pinnet.chargerapp.mvp.contract.mine;

import com.pinnet.chargerapp.base.IBasePresenter;
import com.pinnet.chargerapp.base.IBaseView;
import com.pinnet.chargerapp.mvp.model.beans.BaseBean;

/**
 * @author P00558
 * @date 2018/4/11
 */

public interface WalletContract {
    interface View extends IBaseView {
        /**
         * 请求返回
         *
         * @param baseBean
         */
        void onRequestResult(BaseBean baseBean);
    }

    interface Presenter extends IBasePresenter<View> {
        /**
         * 请求网络数据
         */
        void onRequestData();
    }
}
