package com.pinnet.chargerapp.mvp.model;

import com.pinnet.chargerapp.mvp.model.beans.BaseBean;
import com.pinnet.chargerapp.mvp.model.beans.LoginBean;
import com.pinnet.chargerapp.mvp.model.beans.charger.ChargerPayMentBean;
import com.pinnet.chargerapp.mvp.model.beans.charger.ChargerPileInfoBean;
import com.pinnet.chargerapp.mvp.model.beans.charger.ChargerStationBean;
import com.pinnet.chargerapp.mvp.model.beans.charger.ChargerStationDetailBean;
import com.pinnet.chargerapp.mvp.model.beans.charger.ChargerStatusBean;
import com.pinnet.chargerapp.mvp.model.beans.charger.ChargingBean;
import com.pinnet.chargerapp.mvp.model.beans.charger.OrderDetailBean;
import com.pinnet.chargerapp.mvp.model.beans.charger.RouteHistoryBean;
import com.pinnet.chargerapp.mvp.model.beans.charger.StartChargerBean;
import com.pinnet.chargerapp.mvp.model.beans.mine.ChargerRecordBean;
import com.pinnet.chargerapp.mvp.model.beans.mine.PersonalInfoBean;
import com.pinnet.chargerapp.mvp.model.http.HttpsHelper;
import com.pinnet.chargerapp.mvp.model.http.RetrofitHelper;
import com.pinnet.chargerapp.mvp.model.http.requestbody.ChargerRequestBody;
import com.pinnet.chargerapp.mvp.model.http.requestbody.LoginRequestBody;
import com.pinnet.chargerapp.mvp.model.http.requestbody.RegisterRequestBody;
import com.pinnet.chargerapp.mvp.model.http.requestbody.SendIdentifyRequestBody;
import com.pinnet.chargerapp.mvp.model.http.requestbody.UserInfoRequestBody;
import com.pinnet.chargerapp.mvp.model.sharepref.SharePrefUtilHelper;
import com.pinnet.chargerapp.mvp.sqlitehelper.ISQLiteHelper;
import com.pinnet.chargerapp.mvp.sqlitehelper.ISQLiteHelperImpl;
import com.pinnet.chargerapp.utils.SharePrefUtils;

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

public class DataManager implements HttpsHelper, SharePrefUtilHelper, ISQLiteHelper {
    private HttpsHelper mHttpsHelper;
    private SharePrefUtilHelper mSharePrefUtils;
    private ISQLiteHelperImpl mISQLiteHelperImpl;

    @Inject
    public DataManager(RetrofitHelper httpsHelper, SharePrefUtils sharePrefUtilHelper, ISQLiteHelperImpl sqLiteHelper) {
        this.mHttpsHelper = httpsHelper;
        mSharePrefUtils = sharePrefUtilHelper;
        this.mISQLiteHelperImpl = sqLiteHelper;
    }


    @Override
    public Flowable<BaseBean<LoginBean>> fetchLoginInfo(LoginRequestBody requestBody) {
        return mHttpsHelper.fetchLoginInfo(requestBody);
    }

    @Override
    public Flowable<BaseBean<LoginBean>> fetchLoginByVerCodeInfo(LoginRequestBody requestBody) {
        return mHttpsHelper.fetchLoginByVerCodeInfo(requestBody);
    }

    @Override
    public Flowable<BaseBean> fetchSendIdentifyCodeInfo(SendIdentifyRequestBody requestBody) {
        return mHttpsHelper.fetchSendIdentifyCodeInfo(requestBody);
    }

    @Override
    public Flowable<BaseBean<LoginBean>> fetchRegisterInfo(RegisterRequestBody requestBody) {
        return mHttpsHelper.fetchRegisterInfo(requestBody);
    }

    @Override
    public Flowable<BaseBean> fetchResetPassword(RegisterRequestBody requestBody) {
        return mHttpsHelper.fetchResetPassword(requestBody);
    }

    @Override
    public Flowable<BaseBean<PersonalInfoBean>> fetchPersonalInfoSet(UserInfoRequestBody requestBody) {
        return mHttpsHelper.fetchPersonalInfoSet(requestBody);
    }

    @Override
    public Flowable<BaseBean<PersonalInfoBean>> fetchQueryPersonalInfo(UserInfoRequestBody requestBody) {
        return mHttpsHelper.fetchQueryPersonalInfo(requestBody);
    }

    @Override
    public Flowable<BaseBean> fetchLoginOut() {
        return mHttpsHelper.fetchLoginOut();
    }

    @Override
    public Flowable<BaseBean> fetchUploadFile(String userId, MultipartBody.Part file) {
        return mHttpsHelper.fetchUploadFile(userId, file);
    }

    @Override
    public Flowable<BaseBean<LoginBean>> fetchChargerProgress(ChargerRequestBody requestBody) {
        return mHttpsHelper.fetchChargerProgress(requestBody);
    }

    @Override
    public Flowable<BaseBean<List<ChargerStationBean>>> fetchChargerStationList(ChargerRequestBody requestBody) {
        return mHttpsHelper.fetchChargerStationList(requestBody);
    }

    @Override
    public Flowable<BaseBean<ChargerStationDetailBean>> fetchChargerStationById(ChargerRequestBody requestBody) {
        return mHttpsHelper.fetchChargerStationById(requestBody);
    }

    @Override
    public Flowable<BaseBean<List<ChargerStatusBean>>> fetchChargeListByStationId(ChargerRequestBody requestBody) {
        return mHttpsHelper.fetchChargeListByStationId(requestBody);
    }

    @Override
    public Flowable<BaseBean<StartChargerBean>> fetchGetUnfinishedOrder(ChargerRequestBody requestBody) {
        return mHttpsHelper.fetchGetUnfinishedOrder(requestBody);
    }

    @Override
    public Flowable<BaseBean<StartChargerBean>> fetchStartCharging(ChargerRequestBody requestBody) {
        return mHttpsHelper.fetchStartCharging(requestBody);
    }

    @Override
    public Flowable<BaseBean> fetchStopCharging(ChargerRequestBody requestBody) {
        return mHttpsHelper.fetchStopCharging(requestBody);
    }

    @Override
    public Flowable<BaseBean<ChargerPileInfoBean>> fetchGetChargePileInfo(ChargerRequestBody requestBody) {
        return mHttpsHelper.fetchGetChargePileInfo(requestBody);
    }

    @Override
    public Flowable<BaseBean> fetchCheckChargePileStatus(ChargerRequestBody requestBody) {
        return mHttpsHelper.fetchCheckChargePileStatus(requestBody);
    }

    @Override
    public Flowable<ResponseBody> fetchDownloadStationImage(String sId) {
        return mHttpsHelper.fetchDownloadStationImage(sId);
    }

    @Override
    public Flowable<BaseBean<ChargerPayMentBean>> fetchPayment(ChargerRequestBody requestBody) {
        return mHttpsHelper.fetchPayment(requestBody);
    }

    @Override
    public Flowable<BaseBean<OrderDetailBean>> fetchGetOrderInfoByUserId(ChargerRequestBody requestBody) {
        return mHttpsHelper.fetchGetOrderInfoByUserId(requestBody);
    }

    @Override
    public Flowable<BaseBean<OrderDetailBean>> fetchGetOrderInfoByOrderId(ChargerRequestBody requestBody) {
        return mHttpsHelper.fetchGetOrderInfoByOrderId(requestBody);
    }

    @Override
    public Flowable<BaseBean> fetchGetCheckAbnormalOrder(ChargerRequestBody requestBody) {
        return mHttpsHelper.fetchGetCheckAbnormalOrder(requestBody);
    }

    @Override
    public Flowable<ResponseBody> fetchDownloadLatestFeature(String userId) {
        return mHttpsHelper.fetchDownloadLatestFeature(userId);
    }

    @Override
    public Flowable<BaseBean<List<ChargerRecordBean>>> fetchGetChargeRecords(ChargerRequestBody requestBody) {
        return mHttpsHelper.fetchGetChargeRecords(requestBody);
    }


    @Override
    public String getLoginedUserName() {
        return mSharePrefUtils.getLoginedUserName();
    }

    @Override
    public void setLoginedUserName(String userName) {
        mSharePrefUtils.setLoginedUserName(userName);
    }

    @Override
    public boolean getUserIsLogined() {
        return mSharePrefUtils.getUserIsLogined();
    }

    @Override
    public void setLoginedUserId(String userId) {
        mSharePrefUtils.setLoginedUserId(userId);
    }

    @Override
    public String getLoginedUserId() {
        return mSharePrefUtils.getLoginedUserId();
    }

    @Override
    public void setLoginedTokenId(String tokenId) {
        mSharePrefUtils.setLoginedTokenId(tokenId);
    }

    @Override
    public String getLoginedTokenId() {
        return mSharePrefUtils.getLoginedTokenId();
    }

    /**
     * 清空Share 保存的登录信息
     */
    @Override
    public void onLoginOut() {
        mSharePrefUtils.onLoginOut();
    }

    @Override
    public void setLoginedAccount(String phoneNumber) {
        mSharePrefUtils.setLoginedAccount(phoneNumber);
    }

    @Override
    public String getLoginedAccount() {
        return mSharePrefUtils.getLoginedAccount();
    }

    @Override
    public Flowable<Boolean> insertRouteHistory(RouteHistoryBean bean) {
        return mISQLiteHelperImpl.insertRouteHistory(bean);
    }

    @Override
    public Flowable<List<RouteHistoryBean>> getUserRouteHistory(String userId) {
        return mISQLiteHelperImpl.getUserRouteHistory(userId);
    }
}
