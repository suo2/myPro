package com.pinnet.chargerapp.mvp.presenter.mine;

import android.text.TextUtils;

import com.pinnet.chargerapp.app.Constants;
import com.pinnet.chargerapp.base.BaseRxPresenter;
import com.pinnet.chargerapp.mvp.contract.mine.MineHomeContract;
import com.pinnet.chargerapp.mvp.model.DataManager;
import com.pinnet.chargerapp.mvp.model.beans.BaseBean;
import com.pinnet.chargerapp.mvp.model.beans.mine.PersonalInfoBean;
import com.pinnet.chargerapp.mvp.model.http.RetrofitHelper;
import com.pinnet.chargerapp.mvp.model.http.requestbody.UserInfoRequestBody;
import com.pinnet.chargerapp.utils.RxUtils;
import com.pinnet.chargerapp.utils.Utils;
import com.pinnet.chargerapp.widget.CommonSubscriber;

import java.io.File;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * @author P00558
 * @date 2018/5/8
 */

public class MineHomePresenter extends BaseRxPresenter<MineHomeContract.View> implements MineHomeContract.Presenter {
    private DataManager mDataManager;
    private String userHeaderImgName;
    private String userHeaderPath;

    @Inject
    MineHomePresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
        userHeaderPath = Constants.PATH_PICTURE_USERIMAGE + File.separator + mDataManager.getLoginedUserId();
    }

    @Override
    public void onQueryPersonalInfo() {
        UserInfoRequestBody body = new UserInfoRequestBody();
        body.userId = mDataManager.getLoginedUserId();
        addSubscribe(mDataManager.fetchQueryPersonalInfo(body)
                .compose(RxUtils.<BaseBean<PersonalInfoBean>>rxSchedulerHelper())
                .compose(RxUtils.<PersonalInfoBean>handleResult())
                .subscribeWith(new CommonSubscriber<PersonalInfoBean>(mView, false) {
                    @Override
                    public void onNext(PersonalInfoBean bean) {
                        if (bean!=null){
                            mView.updatePersonalInfo(bean);
                        }
                    }
                }));
    }

    @Override
    public void onDownloadFile(String userHeaderUpdateTime) {
        if (!TextUtils.isEmpty(userHeaderUpdateTime)) {
            userHeaderImgName = userHeaderUpdateTime + ".jpg";
            File file = new File(userHeaderPath, userHeaderImgName);
            if (file.exists()) {
                mView.onLoadUserHeader(userHeaderPath + File.separator + userHeaderImgName);
                return;
            }
        }
        addSubscribe(mDataManager.fetchDownloadLatestFeature(mDataManager.getLoginedUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doAfterNext(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(@NonNull ResponseBody responseBody) throws Exception {
                        Utils.writeResponseBodyToDisk(userHeaderPath, userHeaderImgName, responseBody);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(@NonNull ResponseBody responseBodyResponse) throws Exception {
                        mView.onLoadUserHeader(userHeaderPath + File.separator + userHeaderImgName);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {

                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                }));
    }


}
