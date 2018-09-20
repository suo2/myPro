package com.pinnet.chargerapp.ui.charger;

import android.content.Intent;
import android.location.LocationListener;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListPopupWindow;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.pinnet.chargerapp.app.Constants;
import com.pinnet.chargerapp.base.BaseActivity;
import com.pinnet.chargerapp.mvp.contract.charger.RoutePlanContract;
import com.pinnet.chargerapp.mvp.model.beans.charger.RouteHistoryBean;
import com.pinnet.chargerapp.mvp.presenter.charger.RoutePlanPresenter;
import com.pinnet.chargerapp.R;
import com.pinnet.chargerapp.utils.ScreenUtils;
import com.pinnet.chargerapp.utils.SharePrefUtils;
import com.pinnet.chargerapp.utils.ToastUtils;
import com.pinnet.chargerapp.utils.rxbus.RxBus;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * @author P00558
 * @date 2018/4/10
 * 路径规划
 */

public class RoutePlanActivity extends BaseActivity<RoutePlanPresenter> implements RoutePlanContract.View, AMapLocationListener {
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.tv_right_menu)
    TextView tvRightMenu;
    @BindView(R.id.et_input_end_point)
    TextView etInputEndPoint;
    @BindView(R.id.tv_start_point)
    TextView tvStartPoint;
    @BindView(R.id.tv_home_point)
    TextView tvHomePoint;
    @BindView(R.id.tv_company_point)
    TextView tvCompanyPoint;
    private LatLonPoint mStartLatLonPoint;
    private LatLonPoint mEndLatLonPoint;
    AMapLocationClient aMapLocationClient;
    private int locationTimes = 5;
    private RouteHistoryBean currentRouteBean = new RouteHistoryBean();

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.charger_route_plan_activity;
    }

    @Override
    protected void onInitView() {
        super.onInitView();
        onLocation();
        currentRouteBean.setUserId(SharePrefUtils.getInstance().getLoginedUserId());
        tvHeaderTitle.setText(R.string.charger_route_plan_title);
        tvRightMenu.setVisibility(View.VISIBLE);
        tvRightMenu.setText(R.string.charger_route_plan_route_history);
    }

    @OnClick({R.id.iv_back, R.id.tv_right_menu, R.id.rl_start_point, R.id.rl_end_point, R.id.rl_home_point, R.id.rl_company_point
            , R.id.btn_start})
    void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finishAct();
                break;
            case R.id.tv_right_menu:
                startAct(new Intent(this, RouteHistoryActivity.class));
                break;
            case R.id.rl_start_point:
                startActForResult(new Intent(this, RouteHistorySetAddress.class).
                        putExtra(Constants.CHARGER_ROUTE_PLAN_TYPE, Constants.CHARGER_ROUTE_PLAN_START_POINT), Constants.CHARGER_ROUTE_REQUEST_CODE);
                break;
            case R.id.rl_end_point:
                startActForResult(new Intent(this, RouteHistorySetAddress.class).
                        putExtra(Constants.CHARGER_ROUTE_PLAN_TYPE, Constants.CHARGER_ROUTE_PLAN_END_POINT), Constants.CHARGER_ROUTE_REQUEST_CODE);
                break;
            case R.id.rl_home_point:
                startActForResult(new Intent(this, RouteHistorySetAddress.class).
                        putExtra(Constants.CHARGER_ROUTE_PLAN_TYPE, Constants.CHARGER_ROUTE_PLAN_HOME_POINT), Constants.CHARGER_ROUTE_REQUEST_CODE);
                break;
            case R.id.rl_company_point:
                startActForResult(new Intent(this, RouteHistorySetAddress.class).
                        putExtra(Constants.CHARGER_ROUTE_PLAN_TYPE, Constants.CHARGER_ROUTE_PLAN_COMPANY_POINT), Constants.CHARGER_ROUTE_REQUEST_CODE);
                break;
            case R.id.btn_start:
                if (mStartLatLonPoint == null || mEndLatLonPoint == null) {
                    ToastUtils.getInstance().showMessage("请设置起始地址与目的地");
                    break;
                }
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.CHARGER_ROUTE_START_POINT, mStartLatLonPoint);
                bundle.putParcelable(Constants.CHARGER_ROUTE_END_POINT, mEndLatLonPoint);
                startAct(new Intent(this, RoutePlanAMapActivity.class).putExtras(bundle));
                mPresenter.addRouteHistory(currentRouteBean);
                break;
            default:
                break;
        }
    }

    /**
     * 定位
     */
    private void onLocation() {
        //限制定位次数，避免一直定位
        if (locationTimes == 0) {
            if (aMapLocationClient.isStarted()) {
                aMapLocationClient.stopLocation();
            }
            return;
        }
        locationTimes--;
        if (aMapLocationClient == null) {
            aMapLocationClient = new AMapLocationClient(mContext);
            AMapLocationClientOption clientOption = new AMapLocationClientOption();
            clientOption.setOnceLocation(true);
            // 地址信息
            clientOption.setNeedAddress(true);
            clientOption.setLocationCacheEnable(false);
            //设置定位模式为Hight_Accuracy高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
            clientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            aMapLocationClient.setLocationOption(clientOption);
            aMapLocationClient.setLocationListener(this);
        }
        if (aMapLocationClient.isStarted()) {
            aMapLocationClient.stopLocation();
        }
        aMapLocationClient.startLocation();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                currentRouteBean.setStartPointName(TextUtils.isEmpty(aMapLocation.getPoiName()) ? "我的位置" : aMapLocation.getPoiName());
                currentRouteBean.setStartPointLat(aMapLocation.getLatitude());
                currentRouteBean.setStartPointLng(aMapLocation.getLongitude());
                if (mStartLatLonPoint == null) {
                    mStartLatLonPoint = new LatLonPoint(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                } else {
                    mStartLatLonPoint.setLatitude(aMapLocation.getLatitude());
                    mStartLatLonPoint.setLongitude(aMapLocation.getLongitude());
                }
                aMapLocationClient.stopLocation();
            } else {
                onLocation();
            }
        } else {
            onLocation();
        }
    }

    @Override
    public void onQueryRouteHistoryResult(List<RouteHistoryBean> beanList) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.CHARGER_ROUTE_REQUEST_CODE && resultCode == Constants.CHARGER_ROUTE_RESULT_CODE) {
            if (data != null) {
                int planType = data.getIntExtra(Constants.CHARGER_ROUTE_PLAN_TYPE, 0);
                Tip tip = data.getParcelableExtra(Constants.CHARGER_ROUTE_PLAN_ADDRESS_TIP);
                if (tip == null||tip.getPoint()==null) {
                    ToastUtils.getInstance().showMessage("请选择一个有效的地址");
                    return;
                }
                switch (planType) {
                    case Constants.CHARGER_ROUTE_PLAN_START_POINT:
                        currentRouteBean.setStartPointName(tip.getAddress());
                        currentRouteBean.setStartPointLng(tip.getPoint().getLongitude());
                        currentRouteBean.setStartPointLat(tip.getPoint().getLatitude());

                        tvStartPoint.setText(tip.getName());
                        mStartLatLonPoint = tip.getPoint();
                        break;
                    case Constants.CHARGER_ROUTE_PLAN_END_POINT:
                        currentRouteBean.setEndPointName(tip.getAddress());
                        currentRouteBean.setEndPointLng(tip.getPoint().getLongitude());
                        currentRouteBean.setEndPointLat(tip.getPoint().getLatitude());
                        etInputEndPoint.setText(tip.getName());
                        mEndLatLonPoint = tip.getPoint();
                        break;
                    case Constants.CHARGER_ROUTE_PLAN_HOME_POINT:
                        currentRouteBean.setEndPointName(tip.getAddress());
                        currentRouteBean.setEndPointLng(tip.getPoint().getLongitude());
                        currentRouteBean.setEndPointLat(tip.getPoint().getLatitude());
                        tvHomePoint.setText(tip.getName());
                        mEndLatLonPoint = tip.getPoint();
                        break;
                    case Constants.CHARGER_ROUTE_PLAN_COMPANY_POINT:
                        currentRouteBean.setEndPointName(tip.getAddress());
                        currentRouteBean.setEndPointLng(tip.getPoint().getLongitude());
                        currentRouteBean.setEndPointLat(tip.getPoint().getLatitude());
                        tvCompanyPoint.setText(tip.getName());
                        mEndLatLonPoint = tip.getPoint();
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
