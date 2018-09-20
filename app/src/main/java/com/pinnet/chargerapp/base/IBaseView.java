package com.pinnet.chargerapp.base;

/**
 * @author P00558
 * @date 2018/4/2
 */

public interface IBaseView {
    void showErrorMsg(String msg);

    /**
     * 出错
     */
    void stateError();

    /**
     * 加载中
     */
    void stateLoading();

    /**
     * 主页面
     */
    void stateMain();

    /**
     * 空数据
     */
    void stateEmpty();

    void onRefresh();

    void dismissLoading();

    void showLoading();

    /**
     * 在另外的手机发生了登录
     */
    void onThirdLogined(int errorCode);
}
