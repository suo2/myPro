package com.pinnet.chargerapp.ui.charger;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.pinnet.chargerapp.app.Constants;
import com.pinnet.chargerapp.base.BaseActivity;
import com.pinnet.chargerapp.mvp.contract.charger.ChargerMethodContract;
import com.pinnet.chargerapp.mvp.model.beans.charger.ChargerMathodOption;
import com.pinnet.chargerapp.mvp.model.beans.charger.ChargerPileInfoBean;
import com.pinnet.chargerapp.mvp.model.beans.charger.StartChargerBean;
import com.pinnet.chargerapp.mvp.model.http.requestbody.ChargerRequestBody;
import com.pinnet.chargerapp.mvp.presenter.charger.ChargerMethodPresenter;
import com.pinnet.chargerapp.service.PushService;
import com.pinnet.chargerapp.ui.common.dialogfragment.ChargerArgumentsDialogFragment;
import com.pinnet.chargerapp.ui.common.dialogfragment.CustomDialogFragment;
import com.pinnet.chargerapp.ui.common.popupwindow.ChargerArgumentsPopupWindow;
import com.pinnet.chargerapp.utils.SharePrefUtils;
import com.pinnet.chargerapp.utils.SystemUtil;
import com.pinnet.chargerapp.utils.ToastUtils;
import com.pinnet.chargerapp.widget.ChargerMethodView;
import com.pinnet.chargerapp.R;
import com.pinnet.chargerapp.wxapi.WXPayEntryActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * @author P00558
 * @date 2018/4/13
 * 充电模式
 */

public class ChargerMethodActivity extends BaseActivity<ChargerMethodPresenter> implements ChargerMethodContract.View {
    private static final String TAG = "ChargerMethodActivity";
    public static final int OPTION_MONEY = 2;
    public static final int OPTION_TIME = 4;
    public static final int OPTION_AUTO_FILL = 1;
    public static final int OPTION_BATTERY = 3;
    private int currentOptions = OPTION_AUTO_FILL;
    String currentValue = "";
    @BindView(R.id.cmv_auto_fill)
    ChargerMethodView cmvAutoFill;
    @BindView(R.id.cmv_battery)
    ChargerMethodView cmvBattery;
    @BindView(R.id.cmv_money)
    ChargerMethodView cmvMoney;
    @BindView(R.id.cmv_time)
    ChargerMethodView cmvTime;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.btn_charge)
    Button btnCharger;
    @BindView(R.id.iv_right_menu)
    ImageView ivRightMenu;
    private boolean haveIncompleteOrder;
    private CustomDialogFragment mDialog;
    private MethodOptionDialogFragmentNew mOptionsDialog;
    private ChargerArgumentsDialogFragment mArgumentsDialog;
    private List<ChargerMathodOption> batteryOptions = new ArrayList<>();
    private List<ChargerMathodOption> timeOptions = new ArrayList<>();
    private List<ChargerMathodOption> moneyOptions = new ArrayList<>();
    /**
     * 电桩编号
     */
    private String charegeNumber = "zzfcdz";
    /**
     * 枪口编号
     */
    private String muzzleNumber = "1";
    private RxPermissions rxPermissions;
    /**
     * 电桩参数
     */
    private ChargerPileInfoBean mChargerPileInfoBean;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.charger_charge_method_activity;
    }

    @Override
    protected void onInitView() {
        super.onInitView();
        getIntentData();
        rxPermissions = new RxPermissions(this);
        tvHeaderTitle.setText(R.string.charger_charge_method_title);
        ivRightMenu.setImageResource(R.drawable.charger_icon_method_question);
        mPresenter.onGetChargePileInfo(charegeNumber);
        initOptions();
        initDialog();

    }

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
            charegeNumber = intent.getStringExtra(Constants.CHARGER_CHARGE_NUMBER);
            muzzleNumber = intent.getStringExtra(Constants.CHARGER_MUZZLE_NUMBER);
        }
    }

    private void initOptions() {

        batteryOptions.add(new ChargerMathodOption("25kWh", "已选择充电电量：25kWh", "25", true));
        batteryOptions.add(new ChargerMathodOption("50kWh", "已选择充电电量：50kWh", "50"));
        batteryOptions.add(new ChargerMathodOption("75kWh", "已选择充电电量：75kWh", "75"));
        batteryOptions.add(new ChargerMathodOption("100kWh", "已选择充电电量：100kWh", "100"));
        timeOptions.add(new ChargerMathodOption("0.5h", "已选择充电时间：0.5h", (int) (60 * 60 * 0.5) + "", true));
        timeOptions.add(new ChargerMathodOption("1h", "已选择充电时间：1h", 60 * 60 * 1 + ""));
        timeOptions.add(new ChargerMathodOption("1.5h", "已选择充电时间：1.5h", (int) (60 * 60 * 1.5) + ""));
        timeOptions.add(new ChargerMathodOption("2h", "已选择充电时间：2h", 60 * 60 * 2 + ""));
        moneyOptions.add(new ChargerMathodOption("40￥", "已选择充电金额：40￥", "40", true));
        moneyOptions.add(new ChargerMathodOption("80￥", "已选择充电金额：80￥", "80"));
        moneyOptions.add(new ChargerMathodOption("120￥", "已选择充电金额：120￥", "120"));
        moneyOptions.add(new ChargerMathodOption("160￥", "已选择充电金额：160￥", "160"));
    }

    private void initDialog() {

        mOptionsDialog = MethodOptionDialogFragmentNew.newInstance();
        mOptionsDialog.setIOptionConfirmListenter(new MethodOptionDialogFragmentNew.IOptionConfirmListenter() {
            @Override
            public void onOptionConfirm(ChargerMathodOption option) {
                switch (currentOptions) {
                    case OPTION_TIME:
                        cmvTime.setDescribe(option.getShowName());
                        currentValue = option.getValue();
                        break;
                    case OPTION_BATTERY:
                        cmvBattery.setDescribe(option.getShowName());
                        currentValue = option.getValue();
                        break;
                    case OPTION_MONEY:
                        cmvMoney.setDescribe(option.getShowName());
                        currentValue = option.getValue();
                        break;
                    default:
                        break;
                }
            }
        });

        mDialog = CustomDialogFragment.newInstance();
        mDialog.setTitle(getString(R.string.charger_pay_order));
        mDialog.setContent(getString(R.string.charger_pay_not_pay_tip));
        mDialog.setConfirmName(getString(R.string.charge_pay_go_pay));
        mDialog.showCancle();
        mDialog.setOnConfirmClick(new CustomDialogFragment.OnConfirmClick() {
            @Override
            public void onConfirm() {
                Intent intent = new Intent(ChargerMethodActivity.this, ChargerOrderDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    @OnClick({R.id.iv_back, R.id.cmv_money, R.id.cmv_time, R.id.cmv_auto_fill, R.id.cmv_battery, R.id.btn_charge, R.id.iv_right_menu})
    void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finishAct();
                break;
            case R.id.cmv_auto_fill:
                currentValue = "";
                currentOptions = OPTION_AUTO_FILL;
                cmvTime.setSelect(false);
                cmvAutoFill.setSelect(true);
                cmvBattery.setSelect(false);
                cmvMoney.setSelect(false);
                break;
            case R.id.cmv_battery:
                currentValue = "";
                currentOptions = OPTION_BATTERY;
                cmvBattery.setDescribe("");
                cmvTime.setSelect(false);
                cmvAutoFill.setSelect(false);
                cmvBattery.setSelect(true);
                cmvMoney.setSelect(false);
                mOptionsDialog.setOptionDatas(batteryOptions, OPTION_BATTERY);
                mOptionsDialog.show(getSupportFragmentManager(), TAG);
                break;
            case R.id.cmv_money:
                currentValue = "";
                currentOptions = OPTION_MONEY;
                cmvTime.setSelect(false);
                cmvAutoFill.setSelect(false);
                cmvBattery.setSelect(false);
                cmvMoney.setSelect(true);
                cmvMoney.setDescribe("");
                mOptionsDialog.setOptionDatas(moneyOptions, OPTION_MONEY);
                mOptionsDialog.show(getSupportFragmentManager(), TAG);
                break;
            case R.id.cmv_time:
                currentValue = "";
                currentOptions = OPTION_TIME;
                cmvTime.setDescribe("");
                cmvTime.setSelect(true);
                cmvAutoFill.setSelect(false);
                cmvBattery.setSelect(false);
                cmvMoney.setSelect(false);
                mOptionsDialog.setOptionDatas(timeOptions, OPTION_TIME);
                mOptionsDialog.show(getSupportFragmentManager(), TAG);
                break;
            case R.id.btn_charge:
                if (currentOptions != OPTION_AUTO_FILL && TextUtils.isEmpty(currentValue)) {
                    CustomDialogFragment dialogFragment = CustomDialogFragment.newInstance();
                    dialogFragment.setContent("请选择正确的充电金额/充电电量/充电时间");
                    dialogFragment.show(getSupportFragmentManager(), "optionError", isResumed);
                    return;
                }
                btnCharger.setEnabled(false);
                mPresenter.onRequestUnfinishedOrder();
                break;
            case R.id.iv_right_menu:
                if (mArgumentsDialog == null) {
                    mArgumentsDialog = new ChargerArgumentsDialogFragment();
                }
                if (mChargerPileInfoBean != null) {
                    mArgumentsDialog.showData(getSupportFragmentManager(), "ArgumentDialog", mChargerPileInfoBean);
                }

                break;
            default:
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void showLoading() {
        super.showLoading();
        mLoadingDialog.show(getSupportFragmentManager(), isResumed);
    }

    @Override
    public void onQueryImcompleteOrderResult() {
        haveIncompleteOrder = true;
    }

    @Override
    public void onStartChargingResult(StartChargerBean startChargerBean) {
        btnCharger.setEnabled(true);
        if (startChargerBean == null) {
            return;
        }
        switch (startChargerBean.getCause()) {
            case 0:
                if (isPushServiceWorked()) {
                    stopService(new Intent(this, PushService.class));
                }
                final Intent intent = new Intent(this, PushService.class);
                intent.putExtra("topic", startChargerBean.getTopic());
                intent.putExtra("address", startChargerBean.getAddress());
                intent.putExtra("userName", startChargerBean.getUsername());
                intent.putExtra("password", startChargerBean.getPassword());
                rxPermissions.request(Manifest.permission.READ_PHONE_STATE).subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean aBoolean) throws Exception {
                        if (aBoolean && !isPushServiceWorked()) {
                            startService(intent);
                        }
                    }
                });
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.CHARGER_CHARGING_BEAN, startChargerBean.getRealTimeData());
                bundle.putString(Constants.CHARGER_CHARGE_NUMBER, charegeNumber);
                bundle.putString(Constants.CHARGER_MUZZLE_NUMBER, muzzleNumber);
                if (currentOptions == OPTION_AUTO_FILL) {
                    startAct(new Intent(this, ChargerChargingAutoFillActivity.class).putExtras(bundle));
                } else {
                    startAct(new Intent(this, ChargerChargingActivity.class).putExtras(bundle));
                }
                finishAct();
                break;
            case 1:
                ToastUtils.getInstance().showMessage("充电机检测错误");
                break;
            case 2:
                ToastUtils.getInstance().showMessage("需求错误");
                break;
            case 3:
                ToastUtils.getInstance().showMessage("车子未准备好");
                break;
            case 4:
                ToastUtils.getInstance().showMessage("可用余额不足");
                break;
            default:
                break;
        }

    }

    public boolean isPushServiceWorked() {
        ActivityManager myManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ArrayList<ActivityManager.RunningServiceInfo> runningService = (ArrayList<ActivityManager.RunningServiceInfo>) myManager.getRunningServices(30);
        for (int i = 0; i < runningService.size(); i++) {
            if ("com.pinnet.chargerapp.service.PushService".equals(runningService.get(i).service.getClassName().toString())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ChargerRequestBody getChargerRequestBody() {
        ChargerRequestBody chargerRequestBody = new ChargerRequestBody();
        chargerRequestBody.userId = SharePrefUtils.getInstance().getLoginedUserId();
        chargerRequestBody.chargePileNum = charegeNumber;
        chargerRequestBody.gunNum = muzzleNumber;
        chargerRequestBody.chargeType = String.valueOf(currentOptions);
        chargerRequestBody.value = currentValue;
        return chargerRequestBody;
    }

    @Override
    public void onRequestUnfinishedOrderResult(final StartChargerBean startChargerBean) {
        if (startChargerBean != null) {
            btnCharger.setEnabled(true);
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
                    finishAct();
                }
            });
            dialogFragment.show(getSupportFragmentManager(), "StartCharging", isResumed);
        } else {
            mPresenter.onStartCharging();
        }
    }

    @Override
    public void onGetChargePileInfoResult(ChargerPileInfoBean chargerPileInfoBean) {
        mChargerPileInfoBean = chargerPileInfoBean;
        if (mChargerPileInfoBean != null) {
            ivRightMenu.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mContext = null;
        batteryOptions = null;
        moneyOptions = null;
        timeOptions = null;
    }
}
