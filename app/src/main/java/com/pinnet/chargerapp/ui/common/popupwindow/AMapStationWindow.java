package com.pinnet.chargerapp.ui.common.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pinnet.chargerapp.R;
import com.pinnet.chargerapp.app.Constants;
import com.pinnet.chargerapp.mvp.model.beans.charger.ChargerStationBean;
import com.pinnet.chargerapp.mvp.model.beans.charger.StationInfoBean;
import com.pinnet.chargerapp.mvp.model.http.api.ChargerApi;
import com.pinnet.chargerapp.utils.DataUtils;
import com.pinnet.chargerapp.utils.SystemUtil;
import com.pinnet.chargerapp.utils.Utils;
import com.pinnet.chargerapp.widget.HttpLoggingInterceptor;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author P00558
 * @date 2018/5/3
 * 地图 电站弹窗
 */

public class AMapStationWindow extends PopupWindow {
    private View popupView;
    private TextView tvStationName;
    private ChargerStationBean chargerStationBean;
    private TextView tvOpreatorFlag;
    private TextView tvStationAddress;
    private TextView tvDistance;
    private TextView tvChargerFast;
    private TextView tvChargerSlow;
    private TextView tvTotalCount;
    private TextView tvFreeCount;
    private ImageView ivNavigation;
    private ImageView ivStationImage;
    private Activity activity;
    private CompositeDisposable mCompositeDisposable;
    private ChargerApi chargerApi;

    public AMapStationWindow(Activity context) {
        super(context);
        activity = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        popupView = inflater.inflate(R.layout.charger_station_list_recycler_item, null);
        popupView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickLisenter != null) {
                    mClickLisenter.onPopupWindowClick(chargerStationBean);
                }
            }
        });
        ivStationImage = (ImageView) popupView.findViewById(R.id.iv_station_image);
        tvStationName = (TextView) popupView.findViewById(R.id.tv_station_name);
        tvOpreatorFlag = (TextView) popupView.findViewById(R.id.tv_opreator_flag);
        tvStationAddress = (TextView) popupView.findViewById(R.id.tv_station_address);
        tvDistance = (TextView) popupView.findViewById(R.id.tv_distance);
        ivNavigation = (ImageView) popupView.findViewById(R.id.iv_navigation);
        ivNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickLisenter != null) {
                    mClickLisenter.onNavigation(chargerStationBean);
                }
            }
        });
        tvChargerFast = (TextView) popupView.findViewById(R.id.tv_charger_fast);
        tvChargerSlow = (TextView) popupView.findViewById(R.id.tv_charger_slow);
        tvTotalCount = (TextView) popupView.findViewById(R.id.tv_charger_total_count);
        tvFreeCount = (TextView) popupView.findViewById(R.id.tv_charger_free_count);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.HTTP_HTTP)
                .client(getOkhttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        chargerApi = retrofit.create(ChargerApi.class);
        this.setContentView(popupView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setBackgroundDrawable(new BitmapDrawable());
    }

    public void show(View view) {
        if (view != null && !isShowing()) {
            showAtLocation(view, Gravity.BOTTOM, 0, 0);
        }
    }

    public void show(View view, ChargerStationBean bean) {
        this.chargerStationBean = bean;
        if (bean != null) {
            StationInfoBean stationInfoBean = bean.getStationInfo();


            tvStationName.setText(bean.getStationName());
            if (bean.isFast()) {
                tvChargerFast.setVisibility(View.VISIBLE);
            } else {
                tvChargerFast.setVisibility(View.GONE);
            }
            if (bean.isSlow()) {
                tvChargerSlow.setVisibility(View.VISIBLE);
            } else {
                tvChargerSlow.setVisibility(View.GONE);
            }
            tvTotalCount.setText(bean.getTotalCount() + "个充电桩");
            tvFreeCount.setText(bean.getFreeCount() + "个空闲");
            if ("0".equals(stationInfoBean.getOperationType())) {
                tvOpreatorFlag.setText("运营商");
            } else if ("1".equals(stationInfoBean.getOperationType())) {
                tvOpreatorFlag.setText("第三方");
            } else {
                tvOpreatorFlag.setText(R.string.main_invate_string);
            }
            tvStationAddress.setText(stationInfoBean.getStationAddr());
            String[] distance = DataUtils.getInstance().handleDistance(stationInfoBean.getDistance());
            tvDistance.setText(distance[0] + distance[1]);
           downLoadImage(bean);

        }

        if (view != null && !isShowing()) {
            showAtLocation(view, Gravity.BOTTOM, 0, 0);
        }
    }

    private IPopupWindowCilckLisenter mClickLisenter;

    public void setOnIPopupWindowCilckLisenter(IPopupWindowCilckLisenter iPopupWindowCilckLisenter) {
        this.mClickLisenter = iPopupWindowCilckLisenter;
    }

    public interface IPopupWindowCilckLisenter {
        void onPopupWindowClick(ChargerStationBean chargerStationBean);

        void onNavigation(ChargerStationBean chargerStationBean);
    }

    /**
     * 下载图片
     *
     * @param bean
     */
    private void downLoadImage(ChargerStationBean bean) {
        final String fileName = bean.getStationInfo().getStationCode() + ".jpg";
        File file = new File(Constants.PATH_CACHE_PICTURE, fileName);
        if (file.exists()) {
            Glide.with(activity)
                    .load(file)
                    .asBitmap()
                    .placeholder(R.drawable.charger_icon_station_default)
                    .error(R.drawable.charger_icon_station_default)
                    .into(ivStationImage);
            return;
        }
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(chargerApi.downloadStationImage(bean.getStationInfo().getStationCode())
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
                        Glide.with(activity)
                                .load(Constants.PATH_CACHE_PICTURE + File.separator + fileName)
                                .asBitmap()
                                .placeholder(R.drawable.charger_icon_station_default)
                                .error(R.drawable.charger_icon_station_default)
                                .into(ivStationImage);
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

    private OkHttpClient getOkhttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(loggingInterceptor);
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!SystemUtil.isNetworkConnected()) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (SystemUtil.isNetworkConnected()) {
                    int maxAge = 0;
                    // 有网络时, 不缓存, 最大保存时长为0
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .header(Constants.HEADER_CONTENT_TYPE, Constants.AUTH_CONTENT_TYPE)
                            .header(Constants.HEADER_ACCEPT, Constants.AUTH_ACCEPT)
                            //.removeHeader("Pragma")
                            .build();
                } else {
                    // 无网络时，设置超时为4周
                    int maxStale = 60 * 60 * 24 * 28;
                    response.newBuilder()
                            .header(Constants.HEADER_CONTENT_TYPE, Constants.AUTH_CONTENT_TYPE)
                            .header(Constants.HEADER_ACCEPT, Constants.AUTH_ACCEPT)
                            .build();
                }

                return response;
            }
        };
        builder.addInterceptor(cacheInterceptor);
        //设置超时
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(false);
        return builder.build();
    }
}
