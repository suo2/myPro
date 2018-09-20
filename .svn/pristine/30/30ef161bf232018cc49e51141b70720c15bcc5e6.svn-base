package com.pinnet.chargerapp.mvp.presenter.login;

import android.text.TextUtils;

import com.pinnet.chargerapp.R;
import com.pinnet.chargerapp.app.ChargerApp;
import com.pinnet.chargerapp.base.BaseRxPresenter;
import com.pinnet.chargerapp.mvp.contract.login.RegisterContract;
import com.pinnet.chargerapp.mvp.model.DataManager;
import com.pinnet.chargerapp.mvp.model.beans.BaseBean;
import com.pinnet.chargerapp.mvp.model.beans.LoginBean;
import com.pinnet.chargerapp.mvp.model.http.requestbody.RegisterRequestBody;
import com.pinnet.chargerapp.mvp.model.http.requestbody.SendIdentifyRequestBody;
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
 * 注册
 */

public class RegisterPresenter extends BaseRxPresenter<RegisterContract.View> implements RegisterContract.Presenter {
    private DataManager mDataManager;

    @Inject
    RegisterPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void onSendIdentifyCode() {
        if (!Validator.isMobile(mView.getPhoneNumber())) {
            ToastUtils.getInstance().showMessage(ChargerApp.getInstance().getString(R.string.login_tip_phone_error));
            return;
        }
        final long count = 59;
        SendIdentifyRequestBody requestBody = new SendIdentifyRequestBody();
        requestBody.phoneNum = mView.getPhoneNumber();
        addSubscribe(mDataManager.fetchSendIdentifyCodeInfo(requestBody)
                .compose(RxUtils.<BaseBean>rxSchedulerHelper())
                .compose(RxUtils.handleResultBase())
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
    public void onRegister() {
        if (!Validator.isMobile(mView.getPhoneNumber())) {
            ToastUtils.getInstance().showMessage(ChargerApp.getInstance().getString(R.string.login_tip_phone_error));
            return;
        }
        if (TextUtils.isEmpty(mView.getIdentifyinyCode())) {
            ToastUtils.getInstance().showMessage(ChargerApp.getInstance().getString(R.string.login_tip_identifying_empty));
            return;
        }
        if (!Validator.checkPasword(mView.getPassword())) {
            ToastUtils.getInstance().showMessage(ChargerApp.getInstance().getString(R.string.login_tip_password_error));
            return;
        }
        if (!Validator.checkPasword(mView.getPasswordAgain())) {
            ToastUtils.getInstance().showMessage(ChargerApp.getInstance().getString(R.string.login_tip_password_error));
            return;
        }
        if (!mView.getPassword().equals(mView.getPasswordAgain())) {
            ToastUtils.getInstance().showMessage(ChargerApp.getInstance().getString(R.string.login_tip_password_different));
            return;
        }
        mView.showLoading();
        RegisterRequestBody requestBody = new RegisterRequestBody();
        requestBody.phoneNum = mView.getPhoneNumber();
        requestBody.verCode = mView.getIdentifyinyCode();
        requestBody.passwd = MD5Util.encrypt(MD5Util.encrypt(mView.getPassword()));
        addSubscribe(mDataManager.fetchRegisterInfo(requestBody)
                .compose(RxUtils.<BaseBean<LoginBean>>rxSchedulerHelper())
                .compose(RxUtils.<LoginBean>handleResult())
                .subscribeWith(new CommonSubscriber<LoginBean>(mView, false) {
                    @Override
                    public void onNext(LoginBean loginBean) {
                        mDataManager.setLoginedUserId(loginBean.getUserid());
                        mDataManager.setLoginedUserName(loginBean.getNickName());
                        mView.onRegistered();
                    }
                }));
    }

    @Override
    public void onResetPassWord() {
        if (TextUtils.isEmpty(mView.getPhoneNumber())) {
            ToastUtils.getInstance().showMessage(ChargerApp.getInstance().getString(R.string.login_tip_phone_empty));
            return;
        }
        if (TextUtils.isEmpty(mView.getIdentifyinyCode())) {
            ToastUtils.getInstance().showMessage(ChargerApp.getInstance().getString(R.string.login_tip_identifying_empty));
            return;
        }
        if (!Validator.checkPasword(mView.getPassword())) {
            ToastUtils.getInstance().showMessage(ChargerApp.getInstance().getString(R.string.login_tip_password_error));
            return;
        }
        if (!Validator.checkPasword(mView.getPasswordAgain())) {
            ToastUtils.getInstance().showMessage(ChargerApp.getInstance().getString(R.string.login_tip_password_error));
            return;
        }
        if (!mView.getPassword().equals(mView.getPasswordAgain())) {
            ToastUtils.getInstance().showMessage(ChargerApp.getInstance().getString(R.string.login_tip_password_different));
            return;
        }
        mView.showLoading();
        RegisterRequestBody requestBody = new RegisterRequestBody();
        requestBody.phoneNum = mView.getPhoneNumber();
        requestBody.verCode = mView.getIdentifyinyCode();
        requestBody.passwd = MD5Util.encrypt(MD5Util.encrypt(mView.getPassword()));
        addSubscribe(mDataManager.fetchResetPassword(requestBody)
                .compose(RxUtils.<BaseBean>rxSchedulerHelper())
                .compose(RxUtils.handleResultBase())
                .subscribeWith(new CommonSubscriber<BaseBean>(mView, false) {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        mView.onRegistered();
                    }
                })
        );
    }
}
