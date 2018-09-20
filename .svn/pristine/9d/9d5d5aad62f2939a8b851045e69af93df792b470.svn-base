package com.pinnet.chargerapp.mvp.presenter.charger;

import com.pinnet.chargerapp.app.Constants;
import com.pinnet.chargerapp.base.BaseRxPresenter;
import com.pinnet.chargerapp.mvp.contract.charger.StationDetailContract;
import com.pinnet.chargerapp.mvp.model.DataManager;
import com.pinnet.chargerapp.mvp.model.beans.BaseBean;
import com.pinnet.chargerapp.mvp.model.beans.charger.ChargerStationBean;
import com.pinnet.chargerapp.mvp.model.beans.charger.ChargerStationDetailBean;
import com.pinnet.chargerapp.mvp.model.http.requestbody.ChargerRequestBody;
import com.pinnet.chargerapp.utils.RxUtils;
import com.pinnet.chargerapp.utils.Utils;
import com.pinnet.chargerapp.widget.CommonSubscriber;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * @author P00558
 * @date 2018/4/10
 */

public class StationDetailPresenter extends BaseRxPresenter<StationDetailContract.View> implements StationDetailContract.Presenter {
    private DataManager mDataManager;

    @Inject
    StationDetailPresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
    }

    @Override
    public void onRequestStationDetail(String sId) {
        mView.stateLoading();
        addSubscribe(mDataManager.fetchChargerStationById(mView.getRequestBody())
                .compose(RxUtils.<BaseBean<ChargerStationDetailBean>>rxSchedulerHelper())
                .compose(RxUtils.<ChargerStationDetailBean>handleResult())
                .subscribeWith(new CommonSubscriber<ChargerStationDetailBean>(mView) {
                    @Override
                    public void onNext(ChargerStationDetailBean stationDetailBean) {
                        if (stationDetailBean != null) {
                            mView.stateMain();
                            mView.onRequestStationDetailResult(stationDetailBean);
                        } else {
                            mView.stateEmpty();
                        }
                    }
                }));
    }

    @Override
    public void onDownLoadStationImage(String sId) {
        final String fileName = sId + ".jpg";
        File file = new File(Constants.PATH_CACHE_PICTURE, fileName);
        if (file.exists()) {
            mView.onDownLoadStationImageResult(Constants.PATH_CACHE_PICTURE + File.separator + fileName);
            return;
        }
        addSubscribe(mDataManager.fetchDownloadStationImage(sId)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doAfterNext(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(@NonNull ResponseBody responseBody) throws Exception {
                        Utils.writeResponseBodyToDisk(Constants.PATH_CACHE_PICTURE, fileName, responseBody);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(@NonNull ResponseBody responseBodyResponse) throws Exception {
                        mView.onDownLoadStationImageResult(Constants.PATH_CACHE_PICTURE + File.separator + fileName);
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
