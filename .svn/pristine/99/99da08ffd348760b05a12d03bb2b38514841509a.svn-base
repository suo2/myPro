package com.pinnet.chargerapp.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pinnet.chargerapp.R;
import com.pinnet.chargerapp.app.ChargerApp;
import com.pinnet.chargerapp.app.Constants;
import com.pinnet.chargerapp.base.BaseCommonActivity;
import com.pinnet.chargerapp.ui.charger.ChargerOrderDetailActivity;
import com.pinnet.chargerapp.ui.charger.ChargerOrderPayActivity;
import com.pinnet.chargerapp.ui.main.MainActivity;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import butterknife.BindView;


public class WXPayEntryActivity extends BaseCommonActivity implements IWXAPIEventHandler, View.OnClickListener {

    private static final String TAG = ".WXPayEntryActivity";

    private IWXAPI api;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;
    @BindView(R.id.tv_pay_tips)
    TextView tvPayTips;
    @BindView(R.id.ll_pay_result)
    LinearLayout llPayResult;
    @BindView(R.id.iv_pay_result)
    ImageView ivPayResult;
    private boolean paySuccess;

    @Override
    protected int getLayoutId() {
        return R.layout.pay_result;
    }

    @Override
    protected void onViewCreated(@Nullable Bundle savedInstanceState) {
        super.onViewCreated(savedInstanceState);
        tvConfirm.setOnClickListener(this);
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        api.handleIntent(getIntent(), this);
        if (!paySuccess) {
            paySuccess = getIntent().getBooleanExtra("payState", false);
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {

        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            switch (resp.errCode) {
                case 0:
                    paySuccess = true;
                    ivPayResult.setImageResource(R.drawable.charger_icon_pay_success);
                    tvPayTips.setText(R.string.charger_pay_tips_success);
                    break;
                case -1:
                    paySuccess = false;
                    ivPayResult.setImageResource(R.drawable.charger_icon_pay_fail);
                    tvPayTips.setText(R.string.charger_pay_tips_fail);
                    break;
                case -2:
                    paySuccess = false;
                    ivPayResult.setImageResource(R.drawable.charger_icon_pay_fail);
                    tvPayTips.setText(R.string.charger_pay_tips_cancel);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_confirm:
                onBack();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            onBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void onBack() {
        if (paySuccess) {
            startAct(new Intent(this, MainActivity.class));
            finish();
        } else {
            finish();
        }
    }
}