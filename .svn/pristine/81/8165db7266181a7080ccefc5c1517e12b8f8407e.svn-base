package com.pinnet.chargerapp.ui.charger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pinnet.chargerapp.R;
import com.pinnet.chargerapp.app.Constants;
import com.pinnet.chargerapp.base.BaseActivity;
import com.pinnet.chargerapp.mvp.contract.charger.ChargerChargingContract;
import com.pinnet.chargerapp.mvp.model.beans.charger.ChargingBean;
import com.pinnet.chargerapp.mvp.model.http.requestbody.ChargerRequestBody;
import com.pinnet.chargerapp.mvp.presenter.charger.ChargerChargingPresenter;
import com.pinnet.chargerapp.ui.common.dialogfragment.CustomDialogFragment;
import com.pinnet.chargerapp.utils.DataUtils;
import com.pinnet.chargerapp.utils.SharePrefUtils;
import com.pinnet.chargerapp.utils.TimeUtils;
import com.pinnet.chargerapp.widget.BatteryView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author P00558
 * @date 2018/4/25
 * 充电中-自动充满
 */

public class ChargerChargingAutoFillActivity extends BaseActivity<ChargerChargingPresenter> implements ChargerChargingContract.View {
    private static final String TAG = "ChargerChargingAutoFillActivity";
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.battery_view)
    BatteryView mBatteryView;
    @BindView(R.id.tv_total_time)
    TextView tvTotalTime;
    /**
     * 金额
     */
    @BindView(R.id.tv_amount)
    TextView tvAmount;
    /**
     * 电量
     */
    @BindView(R.id.tv_electric_quantity)
    TextView tvElectricQuantity;
    /**
     * 电压
     */
    @BindView(R.id.tv_voltage)
    TextView tvVoltage;
    /**
     * 电流
     */
    @BindView(R.id.tv_ma)
    TextView tvMa;
    @BindView(R.id.btn_stop_charge)
    Button btnStopCharge;
    private String chargePileNum;
    private String gunNum;
    private ChargingBean mChargingBean;
    /**
     * 是否是手动调用结束充电，规避调用结束充电
     */
    private boolean isStopCharger;
    private PushMsgReciver pushMsgReciver;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.charger_charging_auto_fill_activity;
    }

    @Override
    protected void onViewCreated(@Nullable Bundle savedInstanceState) {
        super.onViewCreated(savedInstanceState);
        SharePrefUtils.getInstance().setChargingStatus(-1);
        mBatteryView.setZOrderOnTop(true);
        mBatteryView.getHolder().setFormat(PixelFormat.TRANSLUCENT);//设置画布  背景透明
        getIntentData();
        tvHeaderTitle.setText(R.string.charger_charging_charge);
        pushMsgReciver = new PushMsgReciver();
    }

    @OnClick({R.id.btn_stop_charge, R.id.iv_back})
    void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_stop_charge:
                mLoadingDialog.show(getSupportFragmentManager(), isResumed);
                isStopCharger = true;
                mPresenter.stopCharger();
                btnStopCharge.setEnabled(false);
                break;
            case R.id.iv_back:
                finishAct();
                break;
            default:
                break;
        }
    }

    /**
     * 获取intent 数据
     */
    private void getIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
            mChargingBean = (ChargingBean) intent.getSerializableExtra(Constants.CHARGER_CHARGING_BEAN);
            chargePileNum = intent.getStringExtra(Constants.CHARGER_CHARGE_NUMBER);
            gunNum = intent.getStringExtra(Constants.CHARGER_MUZZLE_NUMBER);
        }
        if (mChargingBean != null) {
            if (TextUtils.isEmpty(chargePileNum)) {
                chargePileNum = mChargingBean.getSerialNumber();
                gunNum = mChargingBean.getGunNumber();
            }
            resetUiData();
        }
    }

    /**
     * 重置UI数据
     */
    private void resetUiData() {
        tvAmount.setText(DataUtils.getInstance().round(mChargingBean.getChargeMoney(), 2));
        tvElectricQuantity.setText(DataUtils.getInstance().round(mChargingBean.getChargedPower(), 2));
        tvVoltage.setText(DataUtils.getInstance().round(mChargingBean.getChargeDirectVoltage(), 2));
        tvMa.setText(DataUtils.getInstance().round(mChargingBean.getChargeDirectCurrent(), 2));
        if (TextUtils.isEmpty(mChargingBean.getFormatChargedTime())) {
            tvTotalTime.setText("0时0分");
        } else {
            tvTotalTime.setText(mChargingBean.getFormatChargedTime());
        }

    }

    @Override
    public void onRequestProgressResult() {

    }

    @Override
    public void onUpdateCotunDownView(long time) {

    }

    @Override
    public void onStopChargerResult(boolean isSuccess) {
        dismissLoading();
        btnStopCharge.setEnabled(true);
        if (isSuccess) {
            startActivity(new Intent(this, ChargerOrderDetailActivity.class));
            finishAct();
        }

    }

    @Override
    public ChargerRequestBody getStopChargerRequestBody() {
        ChargerRequestBody requestBody = new ChargerRequestBody();
        requestBody.userId = SharePrefUtils.getInstance().getLoginedUserId();
        requestBody.chargePileNum = chargePileNum;
        requestBody.gunNum = gunNum;
        return requestBody;
    }

    @Override
    protected void onStop() {
        super.onStop();
        mBatteryView.stopThread();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharePrefUtils.getInstance().setChargingStatus(-1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (1 == SharePrefUtils.getInstance().getChargingStatus()) {
            startActivity(new Intent(mContext, ChargerOrderDetailActivity.class));
            finishAct();
        } else if (2 == SharePrefUtils.getInstance().getChargingStatus()) {
            CustomDialogFragment customDialogFragment = CustomDialogFragment.newInstance();
            customDialogFragment.setTitle("网络异常");
            customDialogFragment.setContent("网络异常，请检查您的网络状态。。。");
            customDialogFragment.show(getSupportFragmentManager(), "IntentFail", false);
            SharePrefUtils.getInstance().setChargingStatus(-1);//避免重复出现提示
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.PUSH_RECIVER_ACTION);
        registerReceiver(pushMsgReciver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(pushMsgReciver);
    }

    public class PushMsgReciver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getExtras() != null) {
                ChargingBean chargingBean = (ChargingBean) intent.getSerializableExtra(Constants.CHARGER_CHARGING_BEAN);
                if (chargingBean != null) {
                    //服务器停止充电，通过pushservice通知app
                    if (chargingBean.isStopCharger()) {
                        if (!isStopCharger) {
                            startActivity(new Intent(mContext, ChargerOrderDetailActivity.class));
                            finishAct();
                        }
                    } else if (chargingBean.isDisconnection()) {
                        CustomDialogFragment dialogFragment = CustomDialogFragment.newInstance();
                        dialogFragment.setTitle("充电异常");
                        dialogFragment.setContent("充电桩异常，充电已中止；订单将在充电桩恢复正常后生成，请届时支付。");
                        dialogFragment.setOnDismissListener(new CustomDialogFragment.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                finishAct();
                            }
                        });
                        dialogFragment.show(getSupportFragmentManager(), "disconnection", isResumed);
                    } else if (chargingBean.isSocketException()) {
                        CustomDialogFragment customDialogFragment = CustomDialogFragment.newInstance();
                        customDialogFragment.setTitle("网络异常");
                        customDialogFragment.setContent("网络异常，请检查您的网络状态。。。");
                        customDialogFragment.show(getSupportFragmentManager(), "IntentFail", isResumed);
                    } else {
                        mChargingBean = chargingBean;
                        resetUiData();
                    }
                }
            }
        }
    }
}
