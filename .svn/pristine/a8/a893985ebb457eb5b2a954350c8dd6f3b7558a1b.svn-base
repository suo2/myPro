package com.pinnet.chargerapp.ui.main;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.transition.Fade;
import android.transition.Visibility;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.pinnet.chargerapp.R;
import com.pinnet.chargerapp.app.ChargerApp;
import com.pinnet.chargerapp.app.Constants;
import com.pinnet.chargerapp.base.BaseActivity;
import com.pinnet.chargerapp.base.BaseCommonActivity;
import com.pinnet.chargerapp.base.BaseCommonFragment;
import com.pinnet.chargerapp.mvp.contract.main.MainContract;
import com.pinnet.chargerapp.mvp.model.beans.charger.StartChargerBean;
import com.pinnet.chargerapp.mvp.model.beans.main.DrawerScreenlingBean;
import com.pinnet.chargerapp.mvp.presenter.main.MainPresenter;
import com.pinnet.chargerapp.service.PushService;
import com.pinnet.chargerapp.ui.business.BusinessHomeFragment;
import com.pinnet.chargerapp.ui.charger.ChargerChargingActivity;
import com.pinnet.chargerapp.ui.charger.ChargerChargingAutoFillActivity;
import com.pinnet.chargerapp.ui.charger.ChargerHomeFragment;
import com.pinnet.chargerapp.ui.charger.ChargerMethodActivity;
import com.pinnet.chargerapp.ui.charger.ChargerOrderDetailActivity;
import com.pinnet.chargerapp.ui.charger.CustomZxingCaptureActivity;
import com.pinnet.chargerapp.ui.consult.ConsultHomeFragment;
import com.pinnet.chargerapp.ui.common.dialogfragment.CustomDialogFragment;
import com.pinnet.chargerapp.ui.login.LoginActivity;
import com.pinnet.chargerapp.ui.mine.MineHomeFragment;
import com.pinnet.chargerapp.utils.ScreenUtils;
import com.pinnet.chargerapp.utils.SharePrefUtils;
import com.pinnet.chargerapp.utils.SystemUtil;
import com.pinnet.chargerapp.utils.ToastUtils;
import com.pinnet.chargerapp.widget.RadarView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * @author P00558
 * @date 2018/4/2
 * 首页
 */

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {
    private static final String TAG = "MainActivity";

    @BindView(R.id.rg_tablayout)
    RadioGroup rgTablayout;
    @BindView(R.id.rb_scan)
    RadioButton rbScan;
    @BindView(R.id.ll_navigation)
    LinearLayout llNavigation;
    @BindView(R.id.rlv_screening)
    RecyclerView rlvScreening;
    @BindView(R.id.drawerlayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.radar_view)
    RadarView mRadarView;
    DrawerScreeningAdapter screeningAdaper;
    IntentIntegrator mIntentIntegrator;
    private CustomDialogFragment mBackDialog;
    /**
     * 是否有未支付订单
     */
    private CustomDialogFragment mImcompleteOrderDialog;
    private List<DrawerScreenlingBean> drawerScreenlingBeanList;
    private ChargerHomeFragment mChargerHomeFragment;
    private ConsultHomeFragment mConsultHomeFragment;
    private BusinessHomeFragment mBusinessHomeFragment;
    private MineHomeFragment mMineHomeFragment;
    private FragmentManager mFragmentManager;
    private BaseCommonFragment currentFragment;
    private BaseCommonFragment preFragment;
    private int preRadioButtonId;
    private int currentRadioButtonId;
    private RxPermissions rxPermissions;
    private String[] distanceData = new String[]{"<1km", "<2km", "<5km", ">5km"};
    private String[] devTypeData = new String[]{"直流快充", "直流慢充", "交流快充", "交流慢充"};
    private String[] chargerTypeData = new String[]{"国标", "欧标", "美标", "日标"};

    private Intent scanIntent;
    /**
     * 是否是后台刷新，判断是否显示雷达动画
     */
    private boolean firstCheckOrder = false;
    /**
     * 判断是否是雷达动画点击
     */
    private boolean mRadarViewClick = false;
    private StartChargerBean mStartChargerBean;

    @Override
    protected int getLayoutId() {
        return R.layout.main_activity;
    }

    @Override
    protected void onViewCreated(@Nullable Bundle savedInstanceState) {
        super.onViewCreated(savedInstanceState);
        mFragmentManager = getSupportFragmentManager();
        initFragment();
        setDefaultFragment();
        initDrawerScreeningData();
        initDialog();
        rxPermissions = new RxPermissions(this);
        mIntentIntegrator = new IntentIntegrator(this);
        mIntentIntegrator.setCaptureActivity(CustomZxingCaptureActivity.class);
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void onFindView() {
        super.onFindView();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getBooleanExtra(Constants.MAIN_LOGIN_OUT, false)) {
            if (currentFragment != mChargerHomeFragment) {
                ((RadioButton) rgTablayout.findViewById(R.id.rb_charger)).setChecked(true);
            }
        }
    }

    @Override
    protected void onInitView() {
        super.onInitView();
        initTabLayout();
        ViewGroup.LayoutParams para = llNavigation.getLayoutParams();//获取drawerlayout的布局
        para.width = ScreenUtils.getScreenWidth(this) / 4 * 3;//修改宽度
        llNavigation.setLayoutParams(para); //设置修改后的布局。
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rlvScreening.setLayoutManager(layoutManager);
        screeningAdaper = new DrawerScreeningAdapter(this, drawerScreenlingBeanList);
        rlvScreening.setAdapter(screeningAdaper);
    }

    @OnClick({R.id.btn_reset, R.id.btn_confirm, R.id.radar_view})
    void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_reset:
                screeningAdaper.reset();
                break;
            case R.id.btn_confirm:
                mChargerHomeFragment.onRequestStationList(screeningAdaper.getSeleteItem());
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;
            case R.id.radar_view:
                mRadarViewClick = true;
                mPresenter.onRequestUnfinishedOrder();
                break;
            default:
                break;
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
    }

    /**
     * 初始化Fragment
     */
    private void initFragment() {
        if (mChargerHomeFragment == null) {
            if (mFragmentManager.findFragmentByTag(ChargerHomeFragment.class.getSimpleName()) != null) {
                mChargerHomeFragment = (ChargerHomeFragment) mFragmentManager.findFragmentByTag(ChargerHomeFragment.class.getSimpleName());
            } else {
                mChargerHomeFragment = ChargerHomeFragment.newInstance();
            }
        }
        if (mConsultHomeFragment == null) {
            if (mFragmentManager.findFragmentByTag(ConsultHomeFragment.class.getSimpleName()) != null) {
                mConsultHomeFragment = (ConsultHomeFragment) mFragmentManager.findFragmentByTag(ConsultHomeFragment.class.getSimpleName());
            } else {
                mConsultHomeFragment = ConsultHomeFragment.newInstance();
            }
        }
        if (mBusinessHomeFragment == null) {
            if (mFragmentManager.findFragmentByTag(BusinessHomeFragment.class.getSimpleName()) != null) {
                mBusinessHomeFragment = (BusinessHomeFragment) mFragmentManager.findFragmentByTag(BusinessHomeFragment.class.getSimpleName());
            } else {
                mBusinessHomeFragment = new BusinessHomeFragment();
            }
        }
        if (mMineHomeFragment == null) {
            if (mFragmentManager.findFragmentByTag(MineHomeFragment.class.getSimpleName()) != null) {
                mMineHomeFragment = (MineHomeFragment) mFragmentManager.findFragmentByTag(MineHomeFragment.class.getSimpleName());
            } else {
                mMineHomeFragment = new MineHomeFragment();
            }
        }
        currentFragment = mChargerHomeFragment;
        currentRadioButtonId = R.id.rb_charger;
    }

    /**
     * 设置默认fragment
     */
    private void setDefaultFragment() {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if (!mChargerHomeFragment.isAdded()) {
            transaction.add(R.id.fl_content, mChargerHomeFragment, ChargerHomeFragment.class.getSimpleName()).show(mChargerHomeFragment);
        } else {
            transaction.show(mChargerHomeFragment);
        }
        if (!mConsultHomeFragment.isAdded()) {
            transaction.add(R.id.fl_content, mConsultHomeFragment, ConsultHomeFragment.class.getSimpleName());
            transaction.hide(mConsultHomeFragment);
        }
        if (!mBusinessHomeFragment.isAdded()) {
            transaction.add(R.id.fl_content, mBusinessHomeFragment, BusinessHomeFragment.class.getSimpleName());
            transaction.hide(mBusinessHomeFragment);
        }
        if (!mMineHomeFragment.isAdded()) {
            transaction.add(R.id.fl_content, mMineHomeFragment, MineHomeFragment.class.getSimpleName());
            transaction.hide(mMineHomeFragment);
        }
        transaction.commit();
    }

    /**
     * 初始化TabLayout
     */
    private void initTabLayout() {
        rgTablayout.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                preFragment = currentFragment;
                preRadioButtonId = currentRadioButtonId;
                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.rb_charger:
                        currentFragment = mChargerHomeFragment;
                        currentRadioButtonId = R.id.rb_charger;
                        showHideFragment(mChargerHomeFragment, preFragment, mFragmentManager);
                        break;
                    case R.id.rb_consult:
                        currentRadioButtonId = R.id.rb_consult;
                        currentFragment = mConsultHomeFragment;
                        showHideFragment(mConsultHomeFragment, preFragment, mFragmentManager);
                        break;
                    case R.id.rb_scan:
                        currentRadioButtonId = R.id.rb_scan;
                        if (SharePrefUtils.getInstance().getUserIsLogined()) {
                            mPresenter.onQueryImcompleteOrder();
                        } else {
                            startAct(new Intent(MainActivity.this, LoginActivity.class));
                        }
                        showPreFragment();
                        break;
                    case R.id.rb_business:
                        currentFragment = mBusinessHomeFragment;
                        currentRadioButtonId = R.id.rb_business;
                        showHideFragment(mBusinessHomeFragment, preFragment, mFragmentManager);
                        break;
                    case R.id.rb_mine:
                        if (SharePrefUtils.getInstance().getUserIsLogined()) {
                            currentFragment = mMineHomeFragment;
                            currentRadioButtonId = R.id.rb_mine;
                            showHideFragment(mMineHomeFragment, preFragment, mFragmentManager);
                        } else {
                            startAct(new Intent(MainActivity.this, LoginActivity.class));
                            showPreFragment();
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * 显示上次展示的页面
     */
    public void showPreFragment() {
        ((RadioButton) rgTablayout.findViewById(preRadioButtonId)).setChecked(true);
    }

    /**
     * 控制抽屉的展开或关闭
     */
    public void onOpenDrawer() {
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            drawerLayout.openDrawer(Gravity.LEFT);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (SharePrefUtils.getInstance().getUserIsLogined()) {
            firstCheckOrder = true;
            mPresenter.onRequestUnfinishedOrder();
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
        mImcompleteOrderDialog.setOnCancelClick(new CustomDialogFragment.OnCancelClick() {
            @Override
            public void onCancel() {
                dismissLoading();
            }
        });
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBack();
            return false;
        }
        return super.onKeyUp(keyCode, event);
    }

    /**
     * 返回dialog
     */
    private void onBack() {
        if (currentFragment != mChargerHomeFragment) {
            ((RadioButton) rgTablayout.findViewById(R.id.rb_charger)).setChecked(true);
        } else {
            if (mBackDialog == null) {
                mBackDialog = CustomDialogFragment.newInstance();
                mBackDialog.setTitle(getString(R.string.main_tip_exit_title));
                mBackDialog.setContent(getString(R.string.main_tip_exit_content));
                mBackDialog.setConfirmName(getString(R.string.main_tip_exit));
                mBackDialog.showCancle();
                mBackDialog.setOnConfirmClick(new CustomDialogFragment.OnConfirmClick() {
                    @Override
                    public void onConfirm() {
                        mBackDialog.dismiss();
                        ChargerApp.getInstance().exitApp();
                    }
                });
            }
            if (!mBackDialog.isAdded()) {
                mBackDialog.show(getSupportFragmentManager(), TAG, isResumed);
            }
        }

    }

    @Override
    public void showLoading() {
        super.showLoading();
        mLoadingDialog.show(getSupportFragmentManager(), isResumed);
    }

    @Override
    public void onQueryImcompleteOrderResult(boolean hasImcompleteOrder) {
        if (hasImcompleteOrder) {
            if (mImcompleteOrderDialog != null) {
                mImcompleteOrderDialog.show(getSupportFragmentManager(), "imcompleteOrder", isResumed);
            }
        } else {
            showLoading();
            mPresenter.onRequestUnfinishedOrder();
        }
    }

    @Override
    public void onRequestUnfinishedOrderResult(final StartChargerBean startChargerBean, boolean isSuccess) {
        if (mRadarViewClick) {//雷达按钮点击，解单：58797
            if (startChargerBean != null) {
                if (SystemUtil.isPushServiceWorked()) {
                    mContext.stopService(new Intent(mContext, PushService.class));
                }
                final Intent intent = new Intent(mContext, PushService.class);
                intent.putExtra("topic", mStartChargerBean.getTopic());
                intent.putExtra("address", mStartChargerBean.getAddress());
                intent.putExtra("userName", mStartChargerBean.getUsername());
                intent.putExtra("password", mStartChargerBean.getPassword());
                rxPermissions.request(Manifest.permission.READ_PHONE_STATE).subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean aBoolean) throws Exception {
                        if (aBoolean && !SystemUtil.isPushServiceWorked()) {
                            mContext.startService(intent);
                        }
                    }
                });

                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.CHARGER_CHARGING_BEAN, mStartChargerBean.getRealTimeData());
                if (mStartChargerBean.getType() == 1) {
                    startAct(new Intent(mContext, ChargerChargingAutoFillActivity.class).putExtras(bundle));
                } else {
                    startAct(new Intent(mContext, ChargerChargingActivity.class).putExtras(bundle));
                }
            } else {
                showScanBtn(true);
            }
            mRadarViewClick = false;
            return;
        }
        if (firstCheckOrder) {
            if (startChargerBean != null) {
                mStartChargerBean = startChargerBean;
                showScanBtn(false);
            } else {
                showScanBtn(true);
            }
            firstCheckOrder = false;
            return;
        }
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

    /**
     * 是否显示扫描按钮
     *
     * @param show
     */
    private void showScanBtn(boolean show) {
        if (show) {
            mRadarView.stopRippleAnimation();
            mRadarView.setVisibility(View.GONE);
            rbScan.setVisibility(View.VISIBLE);
        } else {
            mRadarView.startRippleAnimation();
            mRadarView.setVisibility(View.VISIBLE);
            rbScan.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onCheckChargePileStatusResult(boolean enable) {
        if (enable) {
            startActivity(scanIntent);
        }
    }
}
