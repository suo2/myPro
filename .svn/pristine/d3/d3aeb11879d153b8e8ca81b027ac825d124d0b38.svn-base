package com.pinnet.chargerapp.mvp.presenter.login;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.pinnet.chargerapp.R;
import com.pinnet.chargerapp.app.ChargerApp;
import com.pinnet.chargerapp.base.BaseRxPresenter;
import com.pinnet.chargerapp.mvp.contract.login.LoginContract;
import com.pinnet.chargerapp.mvp.model.DataManager;
import com.pinnet.chargerapp.mvp.model.beans.BaseBean;
import com.pinnet.chargerapp.mvp.model.beans.LoginBean;
import com.pinnet.chargerapp.mvp.model.http.requestbody.LoginRequestBody;
import com.pinnet.chargerapp.mvp.model.http.requestbody.SendIdentifyRequestBody;
import com.pinnet.chargerapp.utils.LogUtils;
import com.pinnet.chargerapp.utils.MD5Util;
import com.pinnet.chargerapp.utils.RxUtils;
import com.pinnet.chargerapp.utils.ToastUtils;
import com.pinnet.chargerapp.utils.Validator;
import com.pinnet.chargerapp.widget.CommonSubscriber;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * @author P00558
 * @date 2018/4/8
 */

public class LoginPresenter extends BaseRxPresenter<LoginContract.View> implements LoginContract.Presenter {
    private DataManager mDataManager;

    @Inject
    LoginPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void onLogin() {
        if (!Validator.isMobile(mView.getUserName())) {
            ToastUtils.getInstance().showMessage(ChargerApp.getInstance().getString(R.string.login_tip_phone_error));
            return;
        }
        if (TextUtils.isEmpty(mView.getPassWord())) {
            ToastUtils.getInstance().showMessage(ChargerApp.getInstance().getString(R.string.login_tip_password_empty));
            return;
        }
        mView.showLoading();
        LoginRequestBody body = new LoginRequestBody();
        body.phoneNum = mView.getUserName();
        body.passwd = MD5Util.encrypt(MD5Util.encrypt(mView.getPassWord()));
        LogUtils.getInstance().i(MD5Util.encrypt(MD5Util.encrypt(mView.getPassWord())));
        addSubscribe(mDataManager.fetchLoginInfo(body)
                .compose(RxUtils.<BaseBean<LoginBean>>rxSchedulerHelper())
                .compose(RxUtils.<LoginBean>handleResult())
                .subscribeWith(new CommonSubscriber<LoginBean>(mView, false) {
                    @Override
                    public void onNext(LoginBean loginBean) {
                        mDataManager.setLoginedUserId(loginBean.getUserid());
                        mDataManager.setLoginedUserName(loginBean.getNickName());
                        mDataManager.setLoginedAccount(loginBean.getPhoneNum());
                        mView.onLogined();
                    }
                }));
    }

    @Override
    public void onSendIdentifyCode() {
        if (!Validator.isMobile(mView.getUserName())) {
            ToastUtils.getInstance().showMessage(ChargerApp.getInstance().getString(R.string.login_tip_phone_error));
            return;
        }
        final long count = 59;
        SendIdentifyRequestBody requestBody = new SendIdentifyRequestBody();
        requestBody.phoneNum = mView.getUserName();
        addSubscribe(mDataManager.fetchSendIdentifyCodeInfo(requestBody)
                .compose(RxUtils.<BaseBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<BaseBean>(mView, false) {
                    @Override
                    public void onNext(BaseBean baseBean) {

                    }
                }));

        addSubscribe(Observable
                .interval(0, 1, TimeUnit.SECONDS)
                .take(count)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        return count - aLong;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mView.updateIdentifyingCodeView(false, count);
                    }
                }).subscribe(new Consumer<Long>() {
                                 @Override
                                 public void accept(Long aLong) throws Exception {
                                     mView.updateIdentifyingCodeView(false, aLong);
                                 }

                             }
                        , new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable throwable) throws Exception {
                                mView.updateIdentifyingCodeView(true, 0);
                            }
                        }, new Action() {
                            @Override
                            public void run() throws Exception {
                                mView.updateIdentifyingCodeView(true, 0);
                            }
                        }));
    }

    @Override
    public void onLoginByVerCode() {
        if (!Validator.isMobile(mView.getUserName())) {
            ToastUtils.getInstance().showMessage(ChargerApp.getInstance().getString(R.string.login_tip_phone_error));
            return;
        }
        if (TextUtils.isEmpty(mView.getIdentifyinyCode())) {
            ToastUtils.getInstance().showMessage(ChargerApp.getInstance().getString(R.string.login_tip_identifying_empty));
            return;
        }
        mView.showLoading();
        LoginRequestBody body = new LoginRequestBody();
        body.phoneNum = mView.getUserName();
        body.verCode = mView.getIdentifyinyCode();
        addSubscribe(mDataManager.fetchLoginByVerCodeInfo(body)
                .compose(RxUtils.<BaseBean<LoginBean>>rxSchedulerHelper())
                .compose(RxUtils.<LoginBean>handleResult())
                .subscribeWith(new CommonSubscriber<LoginBean>(mView, false) {
                    @Override
                    public void onNext(LoginBean loginBean) {
                        mDataManager.setLoginedUserId(loginBean.getUserid());
                        mDataManager.setLoginedUserName(loginBean.getNickName());
                        mDataManager.setLoginedAccount(loginBean.getPhoneNum());
                        mView.onLogined();
                    }
                }));
    }
}
