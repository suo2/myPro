package com.pinnet.chargerapp.ui.charger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.model.LatLng;
import com.pinnet.chargerapp.R;
import com.pinnet.chargerapp.app.Constants;
import com.pinnet.chargerapp.base.BaseActivity;
import com.pinnet.chargerapp.mvp.contract.charger.ChargerHomeContract;
import com.pinnet.chargerapp.mvp.model.beans.charger.ChargerStationBean;
import com.pinnet.chargerapp.mvp.model.beans.charger.StartChargerBean;
import com.pinnet.chargerapp.mvp.model.beans.main.DrawerScreenlingBean;
import com.pinnet.chargerapp.mvp.model.http.requestbody.ChargerRequestBody;
import com.pinnet.chargerapp.mvp.presenter.charger.ChargerHomePresenter;
import com.pinnet.chargerapp.third.citypicker.CityPickerPopupWindow;
import com.pinnet.chargerapp.third.citypicker.adapter.OnPickListener;
import com.pinnet.chargerapp.third.citypicker.model.City;
import com.pinnet.chargerapp.third.citypicker.model.LocateState;
import com.pinnet.chargerapp.third.citypicker.model.LocatedCity;
import com.pinnet.chargerapp.ui.charger.adapter.StationListAdapter;
import com.pinnet.chargerapp.ui.main.DrawerScreeningAdapter;
import com.pinnet.chargerapp.utils.ScreenUtils;
import com.pinnet.chargerapp.utils.Utils;
import com.pinnet.chargerapp.widget.GrayItemDecoration;
import com.pinnet.chargerapp.widget.SearchFlowTagLayout;
import com.pinnet.chargerapp.widget.SearchFlowTagView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author P00558
 * @date 2018/5/28
 */

public class ChargerStationListActivity extends BaseActivity<ChargerHomePresenter> implements ChargerHomeContract.View {
    private static final String TAG = "ChargerStationListActivity";
    @BindView(R.id.rlv_station_list)
    RecyclerView rlvViewMain;
    @BindView(R.id.iv_mode_switch)
    ImageView ivModeSwitch;
    @BindView(R.id.iv_drawer_switch)
    ImageView ivDrawerSwitch;
    @BindView(R.id.drawerlayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.rlv_screening)
    RecyclerView rlvScreening;
    @BindView(R.id.ll_navigation)
    LinearLayout llNavigation;
    @BindView(R.id.tv_address_choose)
    TextView tvAddressChoose;
    @BindView(R.id.main_toolbar)
    View mainToolbar;
    @BindView(R.id.searchflowtag)
    SearchFlowTagLayout searchFlowTagLayout;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    DrawerScreeningAdapter screeningAdaper;
    private StationListAdapter mStationListAdapter;
    private List<DrawerScreenlingBean> drawerScreenlingBeanList;
    private String[] distanceData = new String[]{"<1km", "<2km", "<5km", ">5km"};
    private String[] devTypeData = new String[]{"直流快充", "直流慢充", "交流快充", "交流慢充"};
    private String[] chargerTypeData = new String[]{"国标", "欧标", "美标", "日标"};
    /**
     * 当前定位地点
     */
    private LatLng mLocationLatLng;
    private AMapLocation mAMapLocation;
    private CityPickerPopupWindow mCityPickerPopupWindow;
    /**
     * 导航选择弹窗
     */
    private ChargerNavSelectDialogFragment navSelectDialogFragment;
    private Map<String, String> mFilterMap = new HashMap<>();
    private String mQueryKeyWord;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.charger_station_list_activity;
    }

    @Override
    protected void onInitView() {
        super.onInitView();
        getInentData();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rlvViewMain.setLayoutManager(layoutManager);
        rlvViewMain.addItemDecoration(new GrayItemDecoration(this, LinearLayoutManager.VERTICAL,
                getResources().getDrawable(R.drawable.mine_charge_recycle_divider)));
        mStationListAdapter = new StationListAdapter(this);
        mStationListAdapter.setOnItemClickListener(new StationListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ChargerStationBean stationBean) {
                Intent intent = new Intent(mContext, ChargerStationDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.CHARGER_USER_LOCATION, mLocationLatLng);
                bundle.putString(Constants.CHAGER_SID, stationBean.getStationInfo().getStationCode());
                bundle.putDouble(Constants.AMAP_LONGTITUDE, stationBean.getStationInfo().getLongitude());
                bundle.putDouble(Constants.AMAP_LATITUDE, stationBean.getStationInfo().getLatitude());
                intent.putExtras(bundle);
                startAct(intent);
            }

            @Override
            public void onNavigation(ChargerStationBean chargerStationBean) {
                onNavgationSelect(chargerStationBean.getStationInfo().getLatitude(), chargerStationBean.getStationInfo().getLongitude());
            }
        });
        rlvViewMain.setAdapter(mStationListAdapter);
        ivModeSwitch.setImageResource(R.drawable.charger_icon_amap);
        initDrawerScreeningData();
        if (mCityPickerPopupWindow == null) {
            mCityPickerPopupWindow = new CityPickerPopupWindow(mContext);
            if (mAMapLocation != null) {
                mCityPickerPopupWindow.locationChanged(new LocatedCity(mAMapLocation.getCity(), mAMapLocation.getProvince(),
                        mAMapLocation.getCityCode()), LocateState.SUCCESS);
            }
            mCityPickerPopupWindow.setOnPickListener(new OnPickListener() {
                @Override
                public void onPick(int position, City data) {
                    if (data != null) {
                        tvAddressChoose.setText(data.getName());
                    }
                }

                @Override
                public void onLocate() {

                }
            });
        }
        searchFlowTagLayout.setOnRemoveItemListenter(new SearchFlowTagLayout.OnRemoveItemListenter() {
            @Override
            public void onRemove() {
                mQueryKeyWord="";
                mPresenter.onRequestStationList(true);
            }
        });
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        // 设置下拉进度的主题颜色
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.onRequestStationList();
            }
        });
        stateLoading();
        mPresenter.onRequestStationList(true);
    }


    @Override
    public void showLoading() {
        super.showLoading();
        mLoadingDialog.show(getSupportFragmentManager(), isResumed);
    }

    private void getInentData() {
        if (getIntent().getExtras() != null) {
            mAMapLocation = (AMapLocation) getIntent().getParcelableExtra(Constants.CHARGER_USER_LOCATION);
            if (mAMapLocation != null) {
                mLocationLatLng = new LatLng(mAMapLocation.getLatitude(), mAMapLocation.getLongitude());
            } else {
                mLocationLatLng = new LatLng(104.06, 30.67);
            }
        }
    }

    /**
     * 导航
     */
    private void onNavgationSelect(double latitude, double longtitude) {
        if (Utils.isPkgInstalled(Constants.AMAP_PACKGE_NAME, mContext) || Utils.isPkgInstalled(Constants.BAIDUMAP_PACKGE_NAME, mContext)) {
            if (navSelectDialogFragment == null) {
                navSelectDialogFragment = ChargerNavSelectDialogFragment.newInstance();
            }
            navSelectDialogFragment.setEndLatLng(latitude, longtitude);
            navSelectDialogFragment.show(getSupportFragmentManager(), TAG);
        } else {
            Intent intent = new Intent(mContext, RouteNaviActivity.class);
            intent.putExtra(Constants.AMAP_LATITUDE, latitude);
            intent.putExtra(Constants.AMAP_LONGTITUDE, longtitude);
            startAct(intent);
        }
    }

    /**
     * 初始化侧滑页面数据
     */
    private void initDrawerScreeningData() {

        drawerScreenlingBeanList = new ArrayList<>();
        DrawerScreenlingBean distanceBean = new DrawerScreenlingBean();
        distanceBean.setItemName("距离");
        distanceBean.setItemId("distance");
        List<DrawerScreenlingBean.ScreenlingItemBean> distanceItemBeanList = new ArrayList<>();

        distanceItemBeanList.add(new DrawerScreenlingBean.ScreenlingItemBean("1", distanceData[0]));
        distanceItemBeanList.add(new DrawerScreenlingBean.ScreenlingItemBean("2", distanceData[1]));
        distanceItemBeanList.add(new DrawerScreenlingBean.ScreenlingItemBean("5", distanceData[2]));
        distanceItemBeanList.add(new DrawerScreenlingBean.ScreenlingItemBean("6", distanceData[3]));
        distanceBean.setItemList(distanceItemBeanList);
        drawerScreenlingBeanList.add(distanceBean);

        DrawerScreenlingBean chargerTypeBean = new DrawerScreenlingBean();
        chargerTypeBean.setItemName("枪口类型");
        chargerTypeBean.setItemId("interfaceType");
        List<DrawerScreenlingBean.ScreenlingItemBean> chargerTypeItemBeanList = new ArrayList<>();
        chargerTypeItemBeanList.add(new DrawerScreenlingBean.ScreenlingItemBean("1", chargerTypeData[0]));
        chargerTypeItemBeanList.add(new DrawerScreenlingBean.ScreenlingItemBean("2", chargerTypeData[1]));
        chargerTypeItemBeanList.add(new DrawerScreenlingBean.ScreenlingItemBean("3", chargerTypeData[2]));
        chargerTypeItemBeanList.add(new DrawerScreenlingBean.ScreenlingItemBean("4", chargerTypeData[3]));
        chargerTypeBean.setItemList(chargerTypeItemBeanList);
        drawerScreenlingBeanList.add(chargerTypeBean);

        DrawerScreenlingBean devTypeBean = new DrawerScreenlingBean();
        devTypeBean.setItemName("设备类型");
        devTypeBean.setItemId("devType");
        List<DrawerScreenlingBean.ScreenlingItemBean> opreatorBeanList = new ArrayList<>();
        opreatorBeanList.add(new DrawerScreenlingBean.ScreenlingItemBean("1", devTypeData[0]));
        opreatorBeanList.add(new DrawerScreenlingBean.ScreenlingItemBean("2", devTypeData[1]));
        opreatorBeanList.add(new DrawerScreenlingBean.ScreenlingItemBean("3", devTypeData[2]));
        opreatorBeanList.add(new DrawerScreenlingBean.ScreenlingItemBean("4", devTypeData[3]));
        devTypeBean.setItemList(opreatorBeanList);
        drawerScreenlingBeanList.add(devTypeBean);

        ViewGroup.LayoutParams para = llNavigation.getLayoutParams();//获取drawerlayout的布局
        para.width = ScreenUtils.getScreenWidth(this) / 4 * 3;//修改宽度
        llNavigation.setLayoutParams(para); //设置修改后的布局。
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rlvScreening.setLayoutManager(layoutManager);
        screeningAdaper = new DrawerScreeningAdapter(this, drawerScreenlingBeanList);
        rlvScreening.setAdapter(screeningAdaper);
    }

    /**
     * 打开或关闭抽屉
     */
    public void onOpenDrawer() {
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            drawerLayout.openDrawer(Gravity.LEFT);
        }
    }

    @OnClick({R.id.iv_mode_switch, R.id.iv_drawer_switch, R.id.btn_reset, R.id.btn_confirm, R.id.tv_address_choose, R.id.searchflowtag})
    void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_mode_switch:
                finishAct();
                break;
            case R.id.iv_drawer_switch:
                onOpenDrawer();
                break;
            case R.id.btn_reset:
                screeningAdaper.reset();
                break;
            case R.id.btn_confirm:
                mFilterMap = screeningAdaper.getSeleteItem();
                stateLoading();
                mPresenter.onRequestStationList(true);
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;
            case R.id.tv_address_choose:
                mCityPickerPopupWindow.showAsDropDown(mainToolbar);
                break;
            case R.id.searchflowtag:
                Intent intent = new Intent(this, ChargerStationSearchActivity.class);
                startActForResult(intent, Constants.CHARGER_STATION_LIST_REQUEST_CODE);
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestStationListResult(List<ChargerStationBean> stationBeanList) {
        if (mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(false);
        }
        stateMain();
        mStationListAdapter.setData(stationBeanList);
    }

    @Override
    public void onRequestUnfinishedOrderResult(StartChargerBean startChargerBean) {

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
    public void onRefresh() {
        super.onRefresh();
        stateLoading();
        mPresenter.onRequestStationList(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == Constants.CHARGER_STATION_LIST_REQUEST_CODE && resultCode == Constants.CHARGER_STATION_SEARCH_RESULT_CODE) {
                mQueryKeyWord = data.getStringExtra(Constants.CHARGER_STATION_SEARCH_RESULT);
                searchFlowTagLayout.addItem(mQueryKeyWord);
                showLoading();
                mPresenter.onRequestStationList(true);
            }
        }

    }
}
