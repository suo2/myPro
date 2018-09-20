package com.pinnet.chargerapp.mvp.model.http.api;

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
import com.pinnet.chargerapp.mvp.model.http.requestbody.ChargerRequestBody;

import java.util.List;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

/**
 * @author P00558
 * @date 2018/4/21
 */

public interface ChargerApi {
    /**
     * 充电进度
     *
     * @param requestBody
     * @return
     */
    @POST("micronetApp/loginByPasswd")
    Flowable<BaseBean<LoginBean>> chargerProgress(@Body ChargerRequestBody requestBody);

    /**
     * 电站列表
     *
     * @return
     */
    @POST("charge/getChargeStationList")
    Flowable<BaseBean<List<ChargerStationBean>>> getChargerStationList(@Body ChargerRequestBody requestBody);

    /**
     * 电站详情
     *
     * @return
     */
    @POST("charge/getChargeStationById")
    Flowable<BaseBean<ChargerStationDetailBean>> getChargeStationById(@Body ChargerRequestBody requestBody);

    /**
     * 电桩列表
     *
     * @return
     */
    @POST("charge/getChargeListByStationId")
    Flowable<BaseBean<List<ChargerStatusBean>>> getChargeListByStationId(@Body ChargerRequestBody requestBody);

    /**
     * 获取充电桩参数
     *
     * @return
     */
    @POST("charge/getChargePileInfo")
    Flowable<BaseBean<ChargerPileInfoBean>> getChargePileInfo(@Body ChargerRequestBody requestBody);

    /**
     * 获取充电桩状态
     *
     * @return
     */
    @POST("charge/checkChargePileStatus")
    Flowable<BaseBean> checkChargePileStatus(@Body ChargerRequestBody requestBody);


    /**
     * 开始充电
     *
     * @return
     */
    @POST("charge/startCharging")
    Flowable<BaseBean<StartChargerBean>> startCharging(@Body ChargerRequestBody requestBody);

    /**
     * 停止充电
     *
     * @return
     */
    @POST("charge/stopCharging")
    Flowable<BaseBean> stopCharging(@Body ChargerRequestBody requestBody);

    /**
     * 查询是否有正在充电的订单
     *
     * @return
     */
    @POST("charge/getUnfinishedOrder")
    Flowable<BaseBean<StartChargerBean>> getUnfinishedOrder(@Body ChargerRequestBody requestBody);


    /**
     * 创建支付订单
     *
     * @return
     */
    @POST("charge/payment")
    Flowable<BaseBean<ChargerPayMentBean>> payment(@Body ChargerRequestBody requestBody);

    /**
     * 获取订单信息
     *
     * @return
     */
    @POST("charge/getOrderInfoByUserId")
    Flowable<BaseBean<OrderDetailBean>> getOrderInfoByUserId(@Body ChargerRequestBody requestBody);

    /**
     * 获取订单信息
     *
     * @return
     */
    @POST("charge/getOrderInfoByOrderId")
    Flowable<BaseBean<OrderDetailBean>> getOrderInfoByOrderId(@Body ChargerRequestBody requestBody);

    /**
     * 获取是否有未支付订单
     *
     * @return
     */
    @POST("charge/checkAbnormalOrder")
    Flowable<BaseBean> getCheckAbnormalOrder(@Body ChargerRequestBody requestBody);

    /**
     * 下载电站图片
     *
     * @param sId
     * @return
     */
    @Streaming
    @GET("charge/getStationImage")
    Flowable<ResponseBody> downloadStationImage(@Header("sId") String sId);

}
