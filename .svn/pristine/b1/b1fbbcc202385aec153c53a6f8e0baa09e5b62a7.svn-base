package com.pinnet.chargerapp.ui.charger;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.pinnet.chargerapp.R;
import com.pinnet.chargerapp.app.ChargerApp;
import com.pinnet.chargerapp.app.Constants;
import com.pinnet.chargerapp.base.BaseFragment;
import com.pinnet.chargerapp.mvp.contract.charger.ChargerHomeContract;
import com.pinnet.chargerapp.mvp.model.beans.charger.ChargerStationBean;
import com.pinnet.chargerapp.mvp.model.beans.charger.StartChargerBean;
import com.pinnet.chargerapp.mvp.model.http.requestbody.ChargerRequestBody;
import com.pinnet.chargerapp.mvp.presenter.charger.ChargerHomePresenter;
import com.pinnet.chargerapp.service.PushService;
import com.pinnet.chargerapp.third.citypicker.CityPickerPopupWindow;
import com.pinnet.chargerapp.third.citypicker.adapter.OnPickListener;
import com.pinnet.chargerapp.third.citypicker.model.City;
import com.pinnet.chargerapp.third.citypicker.model.LocateState;
import com.pinnet.chargerapp.third.citypicker.model.LocatedCity;
import com.pinnet.chargerapp.ui.charger.amap.Cluster;
import com.pinnet.chargerapp.ui.charger.amap.ClusterClickListener;
import com.pinnet.chargerapp.ui.charger.amap.ClusterItem;
import com.pinnet.chargerapp.ui.charger.amap.ClusterOverlay;
import com.pinnet.chargerapp.ui.common.dialogfragment.CustomDialogFragment;
import com.pinnet.chargerapp.ui.common.popupwindow.AMapStationWindow;
import com.pinnet.chargerapp.ui.main.MainActivity;
import com.pinnet.chargerapp.utils.SystemUtil;
import com.pinnet.chargerapp.utils.ToastUtils;
import com.pinnet.chargerapp.utils.Utils;
import com.pinnet.chargerapp.widget.SearchFlowTagLayout;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

import static com.umeng.socialize.utils.DeviceConfig.context;

/**
 * @author P00558
 * @date 2018/4/3
 * 电点首页
 */

public class ChargerHomeFragment extends BaseFragment<ChargerHomePresenter> implements ChargerHomeContract.View, ClusterClickListener, AMapLocationListener {
    private static final String TAG = "ChargerHomeFragment";
    @BindView(R.id.view_amap)
    TextureMapView mMapView;
    /**
     * 交通状况
     */
    @BindView(R.id.iv_home_traffic)
    ImageView ivHomeTraffic;
    @BindView(R.id.main_toolbar)
    View mainToolbar;
    @BindView(R.id.tv_address_choose)
    TextView tvAddressChoose;
    @BindView(R.id.searchflowtag)
    SearchFlowTagLayout searchFlowTagLayout;
    AMap mAmap;
    /**
     * 图标说明 dialog
     */
    LogoExplainDialogFragment mLogoExplainDialog;
    /**
     * 定位客户端
     */
    AMapLocationClient locationClientSingle;
    /**
     * 电站详情 弹窗
     */
    AMapStationWindow mStationPopupWindow;
    /**
     * 是否显示交通状况
     */
    private boolean mAmapTrafficEnabled = false;
    /**
     * 聚合点大小
     */
    private int clusterRadius = 100;
    /**
     * 聚合点图层
     */
    private ClusterOverlay mClusterOverlay;
    /**
     * 导航选择弹窗
     */
    private ChargerNavSelectDialogFragment navSelectDialogFragment;
    /**
     * 导航经纬度
     */
    private double latitude = 0;
    private double longtitude = 0;
    /**
     * 当前定位地点
     */
    private LatLng mLocationLatLng = new LatLng(104.06, 30.67);
    private AMapLocation mAMapLocation;
    private CityPickerPopupWindow mCityPickerPopupWindow;
    private Map<String, String> mFilterMap = new HashMap<>();
    private String mQueryKeyWord;

    RxPermissions rxPermissions;
    /**
     * 是否是第一次进入定位
     */
    private boolean isStartLocation = true;

    public static ChargerHomeFragment newInstance() {
        ChargerHomeFragment fragment = new ChargerHomeFragment();
        return fragment;
    }


    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.charger_include_homepage_container;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        mMapView.onCreate(savedInstanceState);
        rxPermissions = new RxPermissions(mActivity);
        rxPermissions.requestEach(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(@NonNull Permission permission) throws Exception {
                        if (permission.granted) {
                            // 用户已经同意该权限
                            initAmap();
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                            stateEmpty();
                        } else {
                            // 用户拒绝了该权限，并且选中『不再询问』
                            stateEmpty();
                        }
                    }
                });
        rxPermissions.request(Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            onLocation();
                        }
                    }
                });
    }

    /**
     * 初始化控件
     */
    private void initView() {
        searchFlowTagLayout.setOnRemoveItemListenter(new SearchFlowTagLayout.OnRemoveItemListenter() {
            @Override
            public void onRemove() {
                mQueryKeyWord = "";
            }
        });
        mStationPopupWindow = new AMapStationWindow(mActivity);
        mStationPopupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
        mStationPopupWindow.setOnIPopupWindowCilckLisenter(new AMapStationWindow.IPopupWindowCilckLisenter() {
            @Override
            public void onPopupWindowClick(ChargerStationBean chargerStationBean) {
                Intent intent = new Intent(mContext, ChargerStationDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.CHARGER_USER_LOCATION, mLocationLatLng);
                bundle.putString(Constants.CHAGER_SID, chargerStationBean.getStationInfo().getStationCode());
                bundle.putDouble(Constants.AMAP_LONGTITUDE, chargerStationBean.getStationInfo().getLongitude());
                bundle.putDouble(Constants.AMAP_LATITUDE, chargerStationBean.getStationInfo().getLatitude());
                intent.putExtras(bundle);
                startAct(intent);
            }

            @Override
            public void onNavigation(ChargerStationBean chargerStationBean) {
                latitude = chargerStationBean.getStationInfo().getLatitude();
                longtitude = chargerStationBean.getStationInfo().getLongitude();
                onNavgation();
            }
        });
        if (mCityPickerPopupWindow == null) {
            mCityPickerPopupWindow = new CityPickerPopupWindow(mContext);
            mCityPickerPopupWindow.setOnPickListener(new OnPickListener() {
                @Override
                public void onPick(int position, City data) {
                    if (data != null) {
                        tvAddressChoose.setText(data.getName());
                        getLatlonByCityName(data);
                    }
                }

                @Override
                public void onLocate() {

                }
            });
        }
    }

    @OnClick({R.id.iv_home_help, R.id.iv_home_location, R.id.iv_home_traffic, R.id.searchflowtag,
            R.id.iv_home_navigation, R.id.iv_drawer_switch, R.id.tv_address_choose, R.id.iv_mode_switch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_home_help:
                showLogoExplainDialog();
                break;
            case R.id.iv_home_navigation:
                startActForResult(new Intent(mContext, RoutePlanActivity.class), Constants.CHARGER_HOME_REQUEST_CODE);
                break;
            case R.id.iv_home_traffic:
                if (mAmapTrafficEnabled) {
                    mAmapTrafficEnabled = false;
                    mAmap.setTrafficEnabled(false);
                    ivHomeTraffic.setImageResource(R.drawable.home_icon_traffic);
                } else {
                    mAmapTrafficEnabled = true;
                    mAmap.setTrafficEnabled(true);
                    ivHomeTraffic.setImageResource(R.drawable.home_icon_traffic_open);
                }
                break;
            case R.id.iv_home_location:
                rxPermissions.request(Manifest.permission.ACCESS_FINE_LOCATION)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(@NonNull Boolean aBoolean) throws Exception {
                                if (aBoolean) {
                                    onLocation();
                                }
                            }
                        });
                break;
            case R.id.iv_drawer_switch:
                ((MainActivity) mActivity).onOpenDrawer();
                break;
            case R.id.tv_address_choose:
                mCityPickerPopupWindow.showAsDropDown(mainToolbar);
                break;
            case R.id.iv_mode_switch:
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.CHARGER_USER_LOCATION, mAMapLocation);
                startAct(new Intent(mContext, ChargerStationListActivity.class).putExtras(bundle));
                break;
            case R.id.searchflowtag:
                Intent intent = new Intent(mContext, ChargerStationSearchActivity.class);
                startActForResult(intent, Constants.CHARGER_STATION_LIST_REQUEST_CODE);
                break;
            default:
                break;
        }
    }

    /**
     * 初始化地图
     */
    private void initAmap() {
        if (mAmap == null) {

            mAmap = mMapView.getMap();
            UiSettings settings = mAmap.getUiSettings();
            // 去掉比例尺
            settings.setScaleControlsEnabled(false);
            // 设置缩放按钮不可见
            settings.setZoomControlsEnabled(false);
            // 设置禁止旋转
            settings.setRotateGesturesEnabled(false);
            // 禁止倾斜
            settings.setTiltGesturesEnabled(false);
            MyLocationStyle myLocationStyle = new MyLocationStyle();
            myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
            myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色

            myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
            mAmap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
            // 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
            mAmap.setMyLocationEnabled(true);

            mAmapTrafficEnabled = true;
            mAmap.setTrafficEnabled(true);
            ivHomeTraffic.setImageResource(R.drawable.home_icon_traffic_open);
        }
    }

    /**
     * 展示标识说明
     */
    private void showLogoExplainDialog() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag(LogoExplainDialogFragment.class.getSimpleName());
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        if (mLogoExplainDialog == null) {
            mLogoExplainDialog = LogoExplainDialogFragment.newInstance();
        }
        mLogoExplainDialog.show(ft, LogoExplainDialogFragment.class.getSimpleName());
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
        mPresenter.onRequestStationList();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroyView() {
        mMapView.onDestroy();

        super.onDestroyView();
        if (locationClientSingle != null) {
            locationClientSingle.stopLocation();
            locationClientSingle.onDestroy();
        }
        if (mClusterOverlay != null) {
            mClusterOverlay.onDestroy();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    /**
     * 定位
     */
    private void onLocation() {
        if (locationClientSingle == null) {
            locationClientSingle = new AMapLocationClient(mContext);
            AMapLocationClientOption clientOption = new AMapLocationClientOption();
            clientOption.setOnceLocation(true);
            // 地址信息
            clientOption.setNeedAddress(true);
            clientOption.setLocationCacheEnable(false);
            //设置定位模式为Hight_Accuracy高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
            clientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            locationClientSingle.setLocationOption(clientOption);
            locationClientSingle.setLocationListener(this);
        }
        if (locationClientSingle.isStarted()) {
            locationClientSingle.stopLocation();
        }
        locationClientSingle.startLocation();
    }

    /**
     * 导航
     */
    private void onNavgation() {
        if (Utils.isPkgInstalled(Constants.AMAP_PACKGE_NAME, mContext) || Utils.isPkgInstalled(Constants.BAIDUMAP_PACKGE_NAME, mContext)) {
            if (navSelectDialogFragment == null) {
                navSelectDialogFragment = ChargerNavSelectDialogFragment.newInstance();
            }
            navSelectDialogFragment.setEndLatLng(latitude, longtitude);
            navSelectDialogFragment.show(getFragmentManager(), TAG);
        } else {
            Intent intent = new Intent(mContext, RouteNaviActivity.class);
            intent.putExtra(Constants.AMAP_LATITUDE, latitude);
            intent.putExtra(Constants.AMAP_LONGTITUDE, longtitude);
            startAct(intent);
        }
    }

    /**
     * 根据城市名称获取定位
     *
     * @param data 城市
     */
    private void getLatlonByCityName(City data) {

        GeocodeSearch geocodeSearch = new GeocodeSearch(mContext);
        geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

                if (i == 1000) {
                    if (geocodeResult != null && geocodeResult.getGeocodeAddressList() != null &&
                            geocodeResult.getGeocodeAddressList().size() > 0) {

                        GeocodeAddress geocodeAddress = geocodeResult.getGeocodeAddressList().get(0);
                        double latitude = geocodeAddress.getLatLonPoint().getLatitude();//纬度
                        double longititude = geocodeAddress.getLatLonPoint().getLongitude();//经度
                        String adcode = geocodeAddress.getAdcode();//区域编码
                        mLocationLatLng = new LatLng(latitude, longititude);
                        mAmap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLocationLatLng, 11));
                    }
                }
            }
        });
        GeocodeQuery geocodeQuery = new GeocodeQuery(data.getName(), data.getCode());
        geocodeSearch.getFromLocationNameAsyn(geocodeQuery);


    }

    /**
     * 请求电站列表
     *
     * @param mFilterMap
     */
    public void onRequestStationList(Map<String, String> mFilterMap) {
        this.mFilterMap = mFilterMap;
        mPresenter.onRequestStationList();
    }

    @Override
    public void onClick(Marker marker, List<ChargerStationBean> clusterItems) {
        if (clusterItems != null && clusterItems.size() == 1) {
            mStationPopupWindow.show(ivHomeTraffic, clusterItems.get(0));
        } else {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (ClusterItem clusterItem : clusterItems) {
                builder.include(clusterItem.getPosition());
            }
            LatLngBounds latLngBounds = builder.build();
            mAmap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 80)
            );
        }
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                mLocationLatLng = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                mAMapLocation = aMapLocation;
                mAmap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLocationLatLng, 17));
                locationClientSingle.stopLocation();
                mCityPickerPopupWindow.locationChanged(new LocatedCity(aMapLocation.getCity(), aMapLocation.getProvince(),
                        aMapLocation.getCityCode()), LocateState.SUCCESS);
                if (isStartLocation) {
                    mPresenter.onRequestStationList();
                }

            } else {
                ToastUtils.getInstance().showMessage("定位失败，请重试");
            }
        } else {
            ToastUtils.getInstance().showMessage("定位失败，请重试");
        }
    }

    @Override
    public void onRequestStationListResult(List<ChargerStationBean> stationBeanList) {
        if (mClusterOverlay == null) {
            mClusterOverlay = new ClusterOverlay(mAmap, stationBeanList,
                    SystemUtil.dip2px(ChargerApp.getInstance(), clusterRadius),
                    ChargerApp.getInstance());
            mClusterOverlay.setOnClusterClickListener(ChargerHomeFragment.this);
        } else {
            if (isStartLocation){
                mClusterOverlay.resetDataNoCamera(stationBeanList);
                isStartLocation=false;
            }else{
                mClusterOverlay.resetData(stationBeanList);
            }

        }

    }

    @Override
    public void onRequestUnfinishedOrderResult(final StartChargerBean startChargerBean) {
        CustomDialogFragment dialogFragment = CustomDialogFragment.newInstance();
        dialogFragment.setContent("您正在充电，是否查看充电进度？");
        dialogFragment.setTitle("充电中...");
        dialogFragment.setConfirmName("查看");
        dialogFragment.showCancle();
        dialogFragment.setOnConfirmClick(new CustomDialogFragment.OnConfirmClick() {
            @Override
            public void onConfirm() {
                if (SystemUtil.isPushServiceWorked()) {
                    mContext.stopService(new Intent(mContext, PushService.class));
                }
                final Intent intent = new Intent(mContext, PushService.class);
                intent.putExtra("topic", startChargerBean.getTopic());
                intent.putExtra("address", startChargerBean.getAddress());
                intent.putExtra("userName", startChargerBean.getUsername());
                intent.putExtra("password", startChargerBean.getPassword());
                rxPermissions.request(Manifest.permission.READ_PHONE_STATE).subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean aBoolean) throws Exception {
                        if (aBoolean && !SystemUtil.isPushServiceWorked()) {
                            mContext.startService(intent);
                        }
                    }
                });

                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.CHARGER_CHARGING_BEAN, startChargerBean.getRealTimeData());
                if (startChargerBean.getType() == 1) {
                    startAct(new Intent(mContext, ChargerChargingAutoFillActivity.class).putExtras(bundle));
                } else {
                    startAct(new Intent(mContext, ChargerChargingActivity.class).putExtras(bundle));
                }
            }
        });
        dialogFragment.show(getFragmentManager(), TAG, isResumed());
    }

    @Override
    public ChargerRequestBody getRequestBody() {
        ChargerRequestBody chargerRequestBody = new ChargerRequestBody();
        if (mLocationLatLng != null) {
            chargerRequestBody.latitude = String.valueOf(mLocationLatLng.latitude);
            chargerRequestBody.longitude = String.valueOf(mLocationLatLng.longitude);
        }
        chargerRequestBody.distance = TextUtils.isEmpty(mFilterMap.get("distance")) ? "" : mFilterMap.get("distance");
        chargerRequestBody.devType = TextUtils.isEmpty(mFilterMap.get("devType")) ? "" : mFilterMap.get("devType");
        chargerRequestBody.interfaceType = TextUtils.isEmpty(mFilterMap.get("interfaceType")) ? "" : mFilterMap.get("interfaceType");
        chargerRequestBody.keyword = TextUtils.isEmpty(mQueryKeyWord) ? "" : mQueryKeyWord;
        return chargerRequestBody;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == Constants.CHARGER_STATION_LIST_REQUEST_CODE && resultCode == Constants.CHARGER_STATION_SEARCH_RESULT_CODE) {
                mQueryKeyWord = data.getStringExtra(Constants.CHARGER_STATION_SEARCH_RESULT);
                searchFlowTagLayout.addItem(mQueryKeyWord);
                showLoading();
                mPresenter.onRequestStationList(false);
            }
        }
    }

}
