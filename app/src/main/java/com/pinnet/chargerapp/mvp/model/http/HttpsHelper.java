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
import com.pinnet.chargerapp.mvp.model.http.requestbody.ChargerRequestBody;
import com.pinnet.chargerapp.mvp.model.http.requestbody.LoginRequestBody;
import com.pinnet.chargerapp.mvp.model.http.requestbody.RegisterRequestBody;
import com.pinnet.chargerapp.mvp.model.http.requestbody.SendIdentifyRequestBody;
import com.pinnet.chargerapp.mvp.model.http.requestbody.UserInfoRequestBody;

import java.util.List;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * @author P00558
 * @date 2018/4/12
 * 网络接口方法
 */

public interface HttpsHelper {
    /**
     * 密码登录
     *
     * @param requestBody
     * @return
     */
    Flowable<BaseBean<LoginBean>> fetchLoginInfo(LoginRequestBody requestBody);

    /**
     * 验证码登录
     *
     * @param requestBody
     * @return
     */
    Flowable<BaseBean<LoginBean>> fetchLoginByVerCodeInfo(LoginRequestBody requestBody);

    /**
     * 发送验证码
     *
     * @param requestBody
     * @return
     */
    Flowable<BaseBean> fetchSendIdentifyCodeInfo(SendIdentifyRequestBody requestBody);

    /**
     * 注册
     *
     * @param requestBody
     * @return
     */
    Flowable<BaseBean<LoginBean>> fetchRegisterInfo(RegisterRequestBody requestBody);

    /**
     * 重置密码
     *
     * @param requestBody
     * @return
     */
    Flowable<BaseBean> fetchResetPassword(RegisterRequestBody requestBody);

    /**
     * 重置用户信息
     *
     * @param requestBody
     * @return
     */
    Flowable<BaseBean<PersonalInfoBean>> fetchPersonalInfoSet(UserInfoRequestBody requestBody);

    /**
     * 查询用户信息
     *
     * @param requestBody
     * @return
     */
    Flowable<BaseBean<PersonalInfoBean>> fetchQueryPersonalInfo(UserInfoRequestBody requestBody);

    /**
     * 注销登录
     * @return
     */
    Flowable<BaseBean> fetchLoginOut();

    /**
     * 上传头像
     *
     * @param userId
     * @param file
     * @return
     */
    Flowable<BaseBean> fetchUploadFile(String userId, MultipartBody.Part file);

    /**
     * 下载头像
     *
     * @param userId
     * @return
     */

    Flowable<ResponseBody> fetchDownloadLatestFeature(String userId);

    Flowable<BaseBean<LoginBean>> fetchChargerProgress(ChargerRequestBody requestBody);

    /**
     * 获取电站列表
     *
     * @return
     */
    Flowable<BaseBean<List<ChargerStationBean>>> fetchChargerStationList(ChargerRequestBody requestBody);

    /**
     * 电站详情
     *
     * @return
     */
    Flowable<BaseBean<ChargerStationDetailBean>> fetchChargerStationById(ChargerRequestBody requestBody);

    /**
     * 通过电站Id获取电桩列表
     *
     * @return
     */
    Flowable<BaseBean<List<ChargerStatusBean>>> fetchChargeListByStationId(ChargerRequestBody requestBody);

    /**
     * 查询是否有正在充电的订单
     *
     * @param requestBody
     * @return
     */
    Flowable<BaseBean<StartChargerBean>> fetchGetUnfinishedOrder(ChargerRequestBody requestBody);
    /**
     * 开始充电
     *
     * @param requestBody
     * @return
     */
    Flowable<BaseBean<StartChargerBean>> fetchStartCharging(ChargerRequestBody requestBody);
    /**
     * 停止充电
     *
     * @param requestBody
     * @return
     */
    Flowable<BaseBean> fetchStopCharging(ChargerRequestBody requestBody);
    /**
     * 获取充电桩参数
     *
     * @param requestBody
     * @return
     */
    Flowable<BaseBean<ChargerPileInfoBean>> fetchGetChargePileInfo(ChargerRequestBody requestBody);
    /**
     * 获取充电桩状态
     *
     * @param requestBody
     * @return
     */
    Flowable<BaseBean> fetchCheckChargePileStatus(ChargerRequestBody requestBody);

    /**
     * 下载电站图片
     *
     * @param sId
     * @return
     */
    Flowable<ResponseBody> fetchDownloadStationImage(String sId);

    /**
     * 支付-获取订单信息
     *
     * @param requestBody
     * @return
     */
    Flowable<BaseBean<ChargerPayMentBean>> fetchPayment(ChargerRequestBody requestBody);

    /**
     * 订单详情-通过用户Id
     *
     * @param requestBody
     * @return
     */
    Flowable<BaseBean<OrderDetailBean>> fetchGetOrderInfoByUserId(ChargerRequestBody requestBody);

    /**
     * 订单详情-通过订单Id
     *
     * @param requestBody
     * @return
     */
    Flowable<BaseBean<OrderDetailBean>> fetchGetOrderInfoByOrderId(ChargerRequestBody requestBody);

    /**
     * 检查是否有未完成订单
     *
     * @param requestBody
     * @return
     */
    Flowable<BaseBean> fetchGetCheckAbnormalOrder(ChargerRequestBody requestBody);

    /**
     * 获取充电记录
     *
     * @param requestBody
     * @return
     */
    Flowable<BaseBean<List<ChargerRecordBean>>> fetchGetChargeRecords(ChargerRequestBody requestBody);
}
