package com.pinnet.chargerapp.ui.charger;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.pinnet.chargerapp.R;
import com.pinnet.chargerapp.app.Constants;
import com.pinnet.chargerapp.base.BaseCommonActivity;
import com.pinnet.chargerapp.ui.charger.amap.DriveRouteDetailActivity;
import com.pinnet.chargerapp.ui.charger.amap.DrivingRouteOverlay;
import com.pinnet.chargerapp.utils.ToastUtils;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * @author P00558
 * @date 2018/5/9
 * 路径规划展示页面
 */

public class RoutePlanAMapActivity extends BaseCommonActivity implements RouteSearch.OnRouteSearchListener {
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.view_amap)
    TextureMapView mMapView;
    @BindView(R.id.tv_right_menu)
    TextView tvRightMenu;
    AMap mAmap;
    private RouteSearch mRouteSearch;
    public static DriveRouteResult mDriveRouteResult;
    public static DrivePath drivePath;
    private final int ROUTE_TYPE_DRIVE = 2;
    private LatLonPoint mStartPoint;//起点，116.335891,39.942295
    private LatLonPoint mEndPoint;//终点，116.481288,39.995576
    private RxPermissions rxPermissions;

    @Override
    protected int getLayoutId() {
        return R.layout.charger_route_plan_amap_activity;
    }

    @Override
    protected void onViewCreated(@Nullable Bundle savedInstanceState) {
        super.onViewCreated(savedInstanceState);
        mMapView.onCreate(savedInstanceState);
        rxPermissions = new RxPermissions(this);
        rxPermissions.requestEach(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(@NonNull Permission permission) throws Exception {
                        if (permission.granted) {
                            // 用户已经同意该权限
                            initAmap();
                        }
                    }
                });
        getIntentData();
        searchRouteResult(ROUTE_TYPE_DRIVE, RouteSearch.DRIVING_SINGLE_DEFAULT);
    }

    private void getIntentData() {
        if (getIntent() != null) {
            mStartPoint = getIntent().getParcelableExtra(Constants.CHARGER_ROUTE_START_POINT);
            mEndPoint = getIntent().getParcelableExtra(Constants.CHARGER_ROUTE_END_POINT);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onInitView() {
        super.onInitView();
        tvHeaderTitle.setText(R.string.charger_route_plan_title);
        tvRightMenu.setText("路径详情");
        tvRightMenu.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.iv_back, R.id.tv_right_menu})
    void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finishAct();
                break;
            case R.id.tv_right_menu:
                if (drivePath == null || mDriveRouteResult == null) {
                    ToastUtils.getInstance().showMessage("路径规划中，请等待...");
                    return;
                }
                Intent intent = new Intent(mContext,
                        DriveRouteDetailActivity.class);
//                intent.putExtra("drive_path", drivePath);
//                intent.putExtra("drive_result", mDriveRouteResult);
                startAct(intent);
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

        }
        mRouteSearch = new RouteSearch(mContext);
        mRouteSearch.setRouteSearchListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        mDriveRouteResult = null;
        drivePath = null;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    /**
     * 开始搜索路径规划方案
     */
    public void searchRouteResult(int routeType, int mode) {
        if (mStartPoint == null) {
            return;
        }
        if (mEndPoint == null) {
        }
        final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
                mStartPoint, mEndPoint);
        if (routeType == ROUTE_TYPE_DRIVE) {// 驾车路径规划
            RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo, mode, null,
                    null, "");// 第一个参数表示路径规划的起点和终点，第二个参数表示驾车模式，第三个参数表示途经点，第四个参数表示避让区域，第五个参数表示避让道路
            mRouteSearch.calculateDriveRouteAsyn(query);// 异步路径规划驾车模式查询
        }
    }

    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {
        mAmap.clear();// 清理地图上的所有覆盖物
        if (i == 1000) {
            if (driveRouteResult != null && driveRouteResult.getPaths() != null) {
                if (driveRouteResult.getPaths().size() > 0) {
                    mDriveRouteResult = driveRouteResult;
                    drivePath = mDriveRouteResult.getPaths()
                            .get(0);
                    DrivingRouteOverlay drivingRouteOverlay = new DrivingRouteOverlay(
                            mContext, mAmap, drivePath,
                            mDriveRouteResult.getStartPos(),
                            mDriveRouteResult.getTargetPos(), null);
                    LatLonPoint startPoint = mDriveRouteResult.getStartPos();
                    drivingRouteOverlay.setNodeIconVisibility(false);//设置节点marker是否显示
                    drivingRouteOverlay.setIsColorfulline(true);//是否用颜色展示交通拥堵情况，默认true
                    drivingRouteOverlay.removeFromMap();
                    drivingRouteOverlay.addToMap();
                    drivingRouteOverlay.zoomToSpan();
                    mAmap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(startPoint.getLatitude(), startPoint.getLongitude()), 17));
                } else if (driveRouteResult != null && driveRouteResult.getPaths() == null) {
                    //   ToastUtil.show(mContext, R.string.no_result);
                }

            } else {
                // ToastUtil.show(mContext, R.string.no_result);
            }
        } else {
            //  ToastUtil.showerror(this.getApplicationContext(), errorCode);
        }
    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {

    }

    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

    }

}
