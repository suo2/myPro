package com.pinnet.chargerapp.ui.charger;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.amap.api.maps.model.LatLng;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.pinnet.chargerapp.app.Constants;
import com.pinnet.chargerapp.base.BaseActivity;
import com.pinnet.chargerapp.base.BaseCommonActivity;
import com.pinnet.chargerapp.base.BaseCommonFragment;
import com.pinnet.chargerapp.R;
import com.pinnet.chargerapp.mvp.contract.main.MainContract;
import com.pinnet.chargerapp.mvp.model.beans.charger.StartChargerBean;
import com.pinnet.chargerapp.mvp.presenter.main.MainPresenter;
import com.pinnet.chargerapp.service.PushService;
import com.pinnet.chargerapp.ui.common.dialogfragment.CustomDialogFragment;
import com.pinnet.chargerapp.ui.login.LoginActivity;
import com.pinnet.chargerapp.utils.SharePrefUtils;
import com.pinnet.chargerapp.utils.SystemUtil;
import com.pinnet.chargerapp.utils.ToastUtils;
import com.pinnet.chargerapp.utils.Utils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * @author P00558
 * @date 2018/4/10
 * 电站详情
 */

public class ChargerStationDetailActivity extends BaseActivity<MainPresenter> implements MainContract.View, UMShareListener {
    private static final String TAG = ChargerStationDetailActivity.class.getSimpleName();
    @BindView(R.id.view_main)
    ViewPager vpMain;
    @BindView(R.id.tab_switch)
    TabLayout tabSwitch;
    @BindView(R.id.ll_tablayout)
    LinearLayout llTablayout;
    BaseCommonFragment mStationDetailFragment;
    BaseCommonFragment mChargeStatusFragment;
    BaseCommonFragment mChargerCommentFragment;
    private List<BaseCommonFragment> mFragmentLists;
    private List<String> mTabTitleLists = new ArrayList<>();
    private DetailPagerAdapter mPagerAdapter;
    private ChargerNavSelectDialogFragment navSelectDialogFragment;
    private double latitude = 0;
    private double longtitude = 0;
    private CustomDialogFragment mImcompleteOrderDialog;
    private RxPermissions rxPermissions;
    /**
     * 电站
     */
    private String sId;
    private LatLng mLocationLatLng;
    IntentIntegrator mIntentIntegrator;
    private Intent scanIntent;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.charger_station_detail_activity;
    }

    @Override
    protected void onInitView() {
        super.onInitView();
        mIntentIntegrator = new IntentIntegrator(this);
        mIntentIntegrator.setCaptureActivity(CustomZxingCaptureActivity.class);
        rxPermissions = new RxPermissions(this);
        getIntentData();
        initDialog();
        initFragment();
        initViewPager();
        initTabLayout();
    }

    @OnClick({R.id.iv_back, R.id.ll_navigation, R.id.ll_charged, R.id.iv_share})
    void onViewClicked(View view) {
        if (Utils.isFastClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.iv_back:
                finishAct();
                break;
            case R.id.ll_navigation:
                onNavgation();
                break;
            case R.id.ll_charged:
                if (SharePrefUtils.getInstance().getUserIsLogined()) {
                    mPresenter.onQueryImcompleteOrder();
                } else {
                    startAct(new Intent(this, LoginActivity.class));
                }
                break;
            case R.id.iv_share:
                onShare();
                break;
            default:
                break;
        }
    }

    /**
     * 初始化dialog
     */
    private void initDialog() {
        mImcompleteOrderDialog = CustomDialogFragment.newInstance();
        mImcompleteOrderDialog.setTitle(getString(R.string.charger_pay_order));
        mImcompleteOrderDialog.setContent(getString(R.string.charger_pay_not_pay_tip));
        mImcompleteOrderDialog.setConfirmName(getString(R.string.charge_pay_go_pay));
        mImcompleteOrderDialog.showCancle();
        mImcompleteOrderDialog.setOnConfirmClick(new CustomDialogFragment.OnConfirmClick() {
            @Override
            public void onConfirm() {
                Intent intent = new Intent(mContext, ChargerOrderDetailActivity.class).putExtra(Constants.ORDER_DETAIL_TYPE, Constants.ORDER_DETAIL_TYPE_UNPAY);
                startActivity(intent);
            }
        });
    }

    private void getIntentData() {
        sId = getIntent().getStringExtra(Constants.CHAGER_SID);
        latitude = getIntent().getDoubleExtra(Constants.AMAP_LATITUDE, 0);
        longtitude = getIntent().getDoubleExtra(Constants.AMAP_LONGTITUDE, 0);
        mLocationLatLng = (LatLng) getIntent().getParcelableExtra(Constants.CHARGER_USER_LOCATION);

    }

    /**
     * 初始化fragment
     */
    private void initFragment() {
        Bundle args = new Bundle();
        args.putString(Constants.CHAGER_SID, sId);
        args.putParcelable(Constants.CHARGER_USER_LOCATION, mLocationLatLng);
        mStationDetailFragment = ChargerStationDetailFragment.newInstance(args);
        mChargeStatusFragment = ChargerStatusFragment.newInstance(args);
        mChargerCommentFragment = ChargerCommentFragment.newInstance(args);
        if (mFragmentLists == null) {
            mFragmentLists = new ArrayList<>();
        }
        mFragmentLists.add(mStationDetailFragment);
        mFragmentLists.add(mChargeStatusFragment);
        //mFragmentLists.add(mChargerCommentFragment);
        mTabTitleLists.add("详情");
        mTabTitleLists.add("状态");
        //  mTabTitleLists.add("评论");
    }

    /**
     * 初始viewpager
     */
    private void initViewPager() {
        if (mPagerAdapter == null) {
            mPagerAdapter = new DetailPagerAdapter(getSupportFragmentManager());
        }
        vpMain.setAdapter(mPagerAdapter);
        vpMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                if (position == mTabTitleLists.size() - 1) {
//                    llTablayout.setVisibility(View.GONE);
//                } else {
//                    llTablayout.setVisibility(View.VISIBLE);
//                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 初始化tablayout
     */
    private void initTabLayout() {
        tabSwitch.addTab(tabSwitch.newTab().setText(mTabTitleLists.get(0)));
        tabSwitch.addTab(tabSwitch.newTab().setText(mTabTitleLists.get(1)));
        tabSwitch.setupWithViewPager(vpMain);
    }

    public void switchTabLayout(int position) {
        if (position < tabSwitch.getTabCount()) {
            tabSwitch.getTabAt(position).select();
        }
    }

    /**
     * 导航
     */
    private void onNavgation() {
        if (Utils.isPkgInstalled(Constants.AMAP_PACKGE_NAME, this) || Utils.isPkgInstalled(Constants.BAIDUMAP_PACKGE_NAME, this)) {
            if (navSelectDialogFragment == null) {
                navSelectDialogFragment = ChargerNavSelectDialogFragment.newInstance();
            }
            navSelectDialogFragment.setEndLatLng(latitude, longtitude);
            navSelectDialogFragment.show(getSupportFragmentManager(), TAG);
        } else {
            Intent intent = new Intent(this, RouteNaviActivity.class);
            intent.putExtra(Constants.AMAP_LATITUDE, latitude);
            intent.putExtra(Constants.AMAP_LONGTITUDE, longtitude);
            startAct(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.CHARGER_SCAN_RESULT_CODE) {
            if (data != null) {
                decodeScanResult(data.getStringExtra(Constants.CHARGER_SCAN_RESULT));
            }
        } else if (result != null) {
            if (result.getContents() == null && result.getFormatName() != null) {
                ToastUtils.getInstance().showMessage(getString(R.string.charger_zxing_scan_fail));
            } else {
                decodeScanResult(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * 解析扫描结果
     *
     * @param content
     */
    private void decodeScanResult(String content) {
        //桩编号应为 16位，枪口号为1位，所以总长度应为16-18位
        if (!TextUtils.isEmpty(content)) {
            String chargePileNum;
            if (content.length() < 16 || content.length() > 18) {
                ToastUtils.getInstance().showMessage("请扫描正确的二维码");
                return;
            }
            scanIntent = new Intent(this, ChargerMethodActivity.class);
            //如果电桩没有配置枪口编号，枪口编号默认传1
            if (content.contains(Constants.MAIN_CHARGER_SPLIT)) {
                String[] strs = content.split(Constants.MAIN_CHARGER_SPLIT);
                if (strs.length >= 2) {
                    scanIntent.putExtra(Constants.CHARGER_CHARGE_NUMBER, strs[0]);
                    scanIntent.putExtra(Constants.CHARGER_MUZZLE_NUMBER, strs[1]);
                } else {
                    scanIntent.putExtra(Constants.CHARGER_CHARGE_NUMBER, content);
                    scanIntent.putExtra(Constants.CHARGER_MUZZLE_NUMBER, "1");
                }

            } else {
                scanIntent.putExtra(Constants.CHARGER_CHARGE_NUMBER, content);
                scanIntent.putExtra(Constants.CHARGER_MUZZLE_NUMBER, "1");
            }
            mPresenter.onCheckChargePileStatus(scanIntent.getStringExtra(Constants.CHARGER_CHARGE_NUMBER));
        }
    }

    private void onShare() {
        final UMImage image = new UMImage(this, R.mipmap.app_icon);//分享图标
        final UMWeb web = new UMWeb("http://a.app.qq.com/o/simple.jsp?pkgname=com.pinnet.chargerapp#opened"); //切记切记 这里分享的链接必须是http开头
        web.setTitle("e品充");//标题
        web.setThumb(image);  //缩略图
        web.setDescription("为广大车友提供一键找桩充电功能");//描述
        new ShareAction(this)
                .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                .setShareboardclickCallback(new ShareBoardlistener() {
                    @Override
                    public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {

                        if (share_media == SHARE_MEDIA.WEIXIN) {
                            new ShareAction(ChargerStationDetailActivity.this).setPlatform(SHARE_MEDIA.WEIXIN)
                                    .withMedia(web)
                                    .setCallback(ChargerStationDetailActivity.this)
                                    .share();
                        } else if (share_media == SHARE_MEDIA.WEIXIN_CIRCLE) {
                            new ShareAction(ChargerStationDetailActivity.this).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                                    .withMedia(web)
                                    .setCallback(ChargerStationDetailActivity.this)
                                    .share();
                        }
                    }
                }).open();
    }

    @Override
    public void onStart(SHARE_MEDIA share_media) {

    }

    @Override
    public void onResult(SHARE_MEDIA share_media) {

    }

    @Override
    public void onError(SHARE_MEDIA share_media, Throwable throwable) {

    }

    @Override
    public void onCancel(SHARE_MEDIA share_media) {

    }

    @Override
    public void onQueryImcompleteOrderResult(boolean hasImcompleteOrder) {
        if (hasImcompleteOrder) {
            if (mImcompleteOrderDialog != null) {
                mImcompleteOrderDialog.show(getSupportFragmentManager(), "imcompleteOrder", isResumed);
            }
        } else {
            mPresenter.onRequestUnfinishedOrder();
        }
    }

    @Override
    public void onRequestUnfinishedOrderResult(final StartChargerBean startChargerBean, boolean isSuccess) {
        if (startChargerBean != null) {
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
            dialogFragment.show(getSupportFragmentManager(), "StartCharging", isResumed);
        } else {
            if (isSuccess) {
                mIntentIntegrator.initiateScan();
            }
        }
    }

    @Override
    public void onCheckChargePileStatusResult(boolean enable) {
        if (enable) {
            startActivity(scanIntent);
        }
    }


    private class DetailPagerAdapter extends FragmentPagerAdapter {
        public DetailPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentLists.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentLists.size();
        }

        /**
         * 如果不是自定义标签布局，需要重写该方法
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return mTabTitleLists.get(position);
        }
    }
}
