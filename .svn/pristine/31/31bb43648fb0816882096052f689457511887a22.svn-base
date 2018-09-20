package com.pinnet.chargerapp.mvp.presenter.mine;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.pinnet.chargerapp.app.Constants;
import com.pinnet.chargerapp.base.BaseRxPresenter;
import com.pinnet.chargerapp.mvp.contract.mine.PersonalInfoContract;
import com.pinnet.chargerapp.mvp.model.DataManager;
import com.pinnet.chargerapp.mvp.model.beans.BaseBean;
import com.pinnet.chargerapp.mvp.model.beans.mine.PersonalInfoBean;
import com.pinnet.chargerapp.mvp.model.http.requestbody.UserInfoRequestBody;
import com.pinnet.chargerapp.utils.ImageFactory;
import com.pinnet.chargerapp.utils.LogUtils;
import com.pinnet.chargerapp.utils.RxUtils;
import com.pinnet.chargerapp.utils.SystemUtil;
import com.pinnet.chargerapp.utils.ToastUtils;
import com.pinnet.chargerapp.utils.Utils;
import com.pinnet.chargerapp.widget.CommonSubscriber;


import java.io.File;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * @author P00558
 * @date 2018/4/11
 * 个人信息
 */

public class PersonalInfoPresenter extends BaseRxPresenter<PersonalInfoContract.View> implements PersonalInfoContract.Presenter {
    private DataManager mDataManager;
    private String userHeaderImgName;
    private String userHeaderPath;

    @Inject
    PersonalInfoPresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
        userHeaderPath = Constants.PATH_PICTURE_USERIMAGE + File.separator + mDataManager.getLoginedUserId();
    }

    @Override
    public void onQueryPersonalInfo() {
        UserInfoRequestBody body = new UserInfoRequestBody();
        body.userId = mDataManager.getLoginedUserId();
        addSubscribe(mDataManager.fetchQueryPersonalInfo(body)
                .compose(RxUtils.<BaseBean<PersonalInfoBean>>rxSchedulerHelper())
                .compose(RxUtils.<PersonalInfoBean>handleResult()).subscribeWith(new CommonSubscriber<PersonalInfoBean>(mView, false) {
                    @Override
                    public void onNext(PersonalInfoBean bean) {
                        if (bean!=null){
                            mView.updatePersonalInfo(bean);
                        }
                    }
                }));
    }

    @Override
    public void onResetPersonalInfo() {
        UserInfoRequestBody body = new UserInfoRequestBody();
        body.userId = mDataManager.getLoginedUserId();
        body.address = mView.getAddress();
        body.nickName = mView.getNickName();
        addSubscribe(mDataManager.fetchPersonalInfoSet(body)
                .compose(RxUtils.<BaseBean<PersonalInfoBean>>rxSchedulerHelper())
                .compose(RxUtils.<PersonalInfoBean>handleResult()).subscribeWith(new CommonSubscriber<PersonalInfoBean>(mView, false) {
                    @Override
                    public void onNext(PersonalInfoBean bean) {
                        if (bean!=null){
                            mView.updatePersonalInfo(bean);
                        }
                    }
                }));
    }

    @Override
    public void onCompressFile(final String filePath) {
        addSubscribe(Observable
                .create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> e) throws Exception {
                        File file = new File(filePath);
                        String mCompressPicPath;
                        Bitmap targetBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                        int degree = SystemUtil.getPictureDegree(file.getAbsolutePath());
                        if (targetBitmap == null) {
                            e.onComplete();
                        }
                        if (degree != 0) {
                            targetBitmap = SystemUtil.rotaingPic(degree, targetBitmap);
                        }
                        String dir = Constants.PATH_PICTURE + File.separator + "Compress";
                        File dirFile = new File(dir);
                        if (!dirFile.exists()) {
                            dirFile.mkdirs();
                        }
                        File myCaptureFile = new File(dir, "userImage_compress.jpg");
                        ImageFactory.compressBitmap(targetBitmap, 500, myCaptureFile);

                        e.onNext(myCaptureFile.getAbsolutePath());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String filePath) throws Exception {
                        ToastUtils.getInstance().showMessage("压缩完成");
                        mView.onCompressed(filePath);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {

                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        ToastUtils.getInstance().showMessage("压缩失败");
                    }
                }));
    }

    @Override
    public void onUploadImg(final String filePath) {
        // 创建 RequestBody，用于封装 请求RequestBody
        File file = new File(filePath);
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("imgFile", file.getName(), requestFile);
        addSubscribe(mDataManager.fetchUploadFile(mDataManager.getLoginedUserId(), body)
                .compose(RxUtils.<BaseBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<BaseBean>(mView, false) {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        onQueryPersonalInfo();
                        ToastUtils.getInstance().showMessage("图片上传成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        ToastUtils.getInstance().showMessage("图片上传失败");
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
