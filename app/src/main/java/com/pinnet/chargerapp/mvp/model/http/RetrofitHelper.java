package com.pinnet.chargerapp.mvp.model.http;

import com.pinnet.chargerapp.mvp.model.beans.BaseBean;
import com.pinnet.chargerapp.mvp.model.beans.LoginBean;
import com.pinnet.chargerapp.mvp.model.beans.charger.ChargerPayMentBean;
import com.pinnet.chargerapp.mvp.model.beans.charger.ChargerPileInfoBean;
import com.pinnet.chargerapp.mvp.model.beans.charger.ChargerStationBean;
import com.pinnet.chargerapp.mvp.model.beans.charger.ChargerStationDetailBean;
import com.pinnet.chargerapp.mvp.model.beans.charger.ChargerStatusBean;
import com.pinnet.chargerapp.mvp.model.beans.charger.ChargingBean;
import com.pinnet.chargerapp.mvp.model.beans.charger.OrderDetailBean;
import com.pinnet.chargerapp.mvp.model.beans.charger.StartChargerBean;
import com.pinnet.chargerapp.mvp.model.beans.mine.ChargerRecordBean;
import com.pinnet.chargerapp.mvp.model.beans.mine.PersonalInfoBean;
import com.pinnet.chargerapp.mvp.model.http.api.ChargerApi;
import com.pinnet.chargerapp.mvp.model.http.api.LoginApis;
import com.pinnet.chargerapp.mvp.model.http.api.MineApis;
import com.pinnet.chargerapp.mvp.model.http.requestbody.ChargerRequestBody;
import com.pinnet.chargerapp.mvp.model.http.requestbody.LoginRequestBody;
import com.pinnet.chargerapp.mvp.model.http.requestbody.RegisterRequestBody;
import com.pinnet.chargerapp.mvp.model.http.requestbody.SendIdentifyRequestBody;
import com.pinnet.chargerapp.mvp.model.http.requestbody.UserInfoRequestBody;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * @author P00558
 * @date 2018/4/12
 */

public class RetrofitHelper implements HttpsHelper {
    LoginApis loginApis;
    MineApis mineApis;
    ChargerApi chargerApi;

    @Inject
    public RetrofitHelper(LoginApis loginApis, MineApis mineApis, ChargerApi chargerApi) {
        this.loginApis = loginApis;
        this.mineApis = mineApis;
        this.chargerApi = chargerApi;
    }


    @Override
    public Flowable<BaseBean<LoginBean>> fetchLoginInfo(LoginRequestBody requestBody) {
        return loginApis.login(requestBody);
    }

    @Override
    public Flowable<BaseBean<LoginBean>> fetchLoginByVerCodeInfo(LoginRequestBody requestBody) {
        return loginApis.loginByVerCode(requestBody);
    }

    @Override
    public Flowable<BaseBean> fetchSendIdentifyCodeInfo(SendIdentifyRequestBody requestBody) {
        return loginApis.sendIdentifyCode(requestBody);
    }

    @Override
    public Flowable<BaseBean<LoginBean>> fetchRegisterInfo(RegisterRequestBody requestBody) {
        return loginApis.register(requestBody);
    }

    @Override
    public Flowable<BaseBean> fetchResetPassword(RegisterRequestBody requestBody) {
        return loginApis.resetPassword(requestBody);
    }

    @Override
    public Flowable<BaseBean<PersonalInfoBean>> fetchPersonalInfoSet(UserInfoRequestBody requestBody) {
        return mineApis.personalInfoSet(requestBody);
    }

    @Override
    public Flowable<BaseBean<PersonalInfoBean>> fetchQueryPersonalInfo(UserInfoRequestBody requestBody) {
        return mineApis.queryPersonalInfo(requestBody);
    }

    @Override
    public Flowable<BaseBean> fetchLoginOut() {
        return mineApis.loginOut();
    }

    @Override
    public Flowable<BaseBean> fetchUploadFile(String userId, MultipartBody.Part file) {
        return mineApis.uploadFile(userId, file);
    }

    @Override
    public Flowable<BaseBean<LoginBean>> fetchChargerProgress(ChargerRequestBody requestBody) {
        return chargerApi.chargerProgress(requestBody);
    }

    @Override
    public Flowable<BaseBean<List<ChargerStationBean>>> fetchChargerStationList(ChargerRequestBody requestBody) {
        return chargerApi.getChargerStationList(requestBody);
    }

    @Override
    public Flowable<BaseBean<ChargerStationDetailBean>> fetchChargerStationById(ChargerRequestBody requestBody) {
        return chargerApi.getChargeStationById(requestBody);
    }

    @Override
    public Flowable<BaseBean<List<ChargerStatusBean>>> fetchChargeListByStationId(ChargerRequestBody requestBody) {
        return chargerApi.getChargeListByStationId(requestBody);
    }

    @Override
    public Flowable<BaseBean<StartChargerBean>> fetchGetUnfinishedOrder(ChargerRequestBody requestBody) {
        return chargerApi.getUnfinishedOrder(requestBody);
    }

    @Override
    public Flowable<BaseBean<StartChargerBean>> fetchStartCharging(ChargerRequestBody requestBody) {
        return chargerApi.startCharging(requestBody);
    }

    @Override
    public Flowable<BaseBean> fetchStopCharging(ChargerRequestBody requestBody) {
        return chargerApi.stopCharging(requestBody);
    }

    @Override
    public Flowable<BaseBean<ChargerPileInfoBean>> fetchGetChargePileInfo(ChargerRequestBody requestBody) {
        return chargerApi.getChargePileInfo(requestBody);
    }

    @Override
    public Flowable<BaseBean> fetchCheckChargePileStatus(ChargerRequestBody requestBody) {
        return chargerApi.checkChargePileStatus(requestBody);
    }

    @Override
    public Flowable<ResponseBody> fetchDownloadStationImage(String sId) {
        return chargerApi.downloadStationImage(sId);
    }

    @Override
    public Flowable<BaseBean<ChargerPayMentBean>> fetchPayment(ChargerRequestBody requestBody) {
        return chargerApi.payment(requestBody);
    }

    @Override
    public Flowable<BaseBean<OrderDetailBean>> fetchGetOrderInfoByUserId(ChargerRequestBody requestBody) {
        return chargerApi.getOrderInfoByUserId(requestBody);
    }

    @Override
    public Flowable<BaseBean<OrderDetailBean>> fetchGetOrderInfoByOrderId(ChargerRequestBody requestBody) {
        return chargerApi.getOrderInfoByOrderId(requestBody);
    }

    @Override
    public Flowable<BaseBean> fetchGetCheckAbnormalOrder(ChargerRequestBody requestBody) {
        return chargerApi.getCheckAbnormalOrder(requestBody);
    }

    @Override
    public Flowable<ResponseBody> fetchDownloadLatestFeature(String userId) {
        return mineApis.downloadLatestFeature(userId);
    }

    @Override
    public Flowable<BaseBean<List<ChargerRecordBean>>> fetchGetChargeRecords(ChargerRequestBody requestBody) {
        return mineApis.getChargeRecords(requestBody);
    }


}
