package com.pinnet.chargerapp.mvp.model.http.api;

import com.pinnet.chargerapp.mvp.model.beans.BaseBean;
import com.pinnet.chargerapp.mvp.model.beans.charger.OrderDetailBean;
import com.pinnet.chargerapp.mvp.model.beans.mine.ChargerRecordBean;
import com.pinnet.chargerapp.mvp.model.beans.mine.PersonalInfoBean;
import com.pinnet.chargerapp.mvp.model.http.requestbody.ChargerRequestBody;
import com.pinnet.chargerapp.mvp.model.http.requestbody.UserInfoRequestBody;

import java.util.List;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * @author P00558
 * @date 2018/4/14
 * 我的 接口
 */

public interface MineApis {
    /**
     * 查询个人信息
     *
     * @param requestBody
     * @return
     */
    @POST("micronetApp/queryPersonalInfo")
    Flowable<BaseBean<PersonalInfoBean>> queryPersonalInfo(@Body UserInfoRequestBody requestBody);

    /**
     * 修改个人信息
     *
     * @param requestBody
     * @return
     */
    @POST("micronetApp/personalInfoSet")
    Flowable<BaseBean<PersonalInfoBean>> personalInfoSet(@Body UserInfoRequestBody requestBody);

    /**
     * 上传图片
     *
     * @param file
     * @return
     */
    @Multipart
    @POST("micronetApp/uploadImage")
    Flowable<BaseBean> uploadFile(@Query("userId") String userId, @Part MultipartBody.Part file);

    /**
     * 下载头像
     *
     * @param userId
     * @return
     */
    @Streaming
    @GET("micronetApp/getImage")
    Flowable<ResponseBody> downloadLatestFeature(@Query("userId") String userId);

    /**
     * 获取用户订单记录
     *
     * @param requestBody
     * @return
     */
    @POST("charge/getChargeRecords")
    Flowable<BaseBean<List<ChargerRecordBean>>> getChargeRecords(@Body ChargerRequestBody requestBody);

    /**
     * 注销登录
     *
     * @return
     */
    @POST("micronetApp/loginOut")
    Flowable<BaseBean> loginOut();


}
