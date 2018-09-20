package com.pinnet.chargerapp.ui.charger;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.pinnet.chargerapp.R;
import com.pinnet.chargerapp.app.ChargerApp;
import com.pinnet.chargerapp.app.Constants;
import com.pinnet.chargerapp.base.BaseCommonActivity;
import com.pinnet.chargerapp.utils.SystemUtil;
import com.pinnet.chargerapp.widget.NumberInputView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author P00558
 * @date 2018/4/27
 */

public class ChargerInputNumberActivity extends BaseCommonActivity {
    @BindView(R.id.number_input_view)
    NumberInputView numberInputView;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;

    @Override
    protected int getLayoutId() {
        return R.layout.charger_input_number_activity;
    }

    @Override
    protected void onViewCreated(@Nullable Bundle savedInstanceState) {
        super.onViewCreated(savedInstanceState);
        numberInputView.initChildView(22);
        numberInputView.setOnInputFinishListener(new NumberInputView.OnInputFinishListener() {
            @Override
            public void onInputFinish(boolean isFinish) {
                if (isFinish) {
                    btnConfirm.setVisibility(View.VISIBLE);
                    SystemUtil.hideKeyboard(btnConfirm);
                } else {
                    btnConfirm.setVisibility(View.GONE);
                }
            }
        });
    }

    @OnClick({R.id.btn_confirm})
    void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                decodeScanResult(numberInputView.getText());
                break;
            default:
                break;
        }
    }

    private void decodeScanResult(String content) {
        if (!TextUtils.isEmpty(content)) {
            Intent intent = new Intent(this, ChargerMethodActivity.class);
            //如果电桩没有配置枪口编号，枪口编号默认传1
            if (content.contains("_")) {
                String[] strs = content.split("_");
                intent.putExtra(Constants.CHARGER_CHARGE_NUMBER, strs[0]);
                intent.putExtra(Constants.CHARGER_MUZZLE_NUMBER, strs[1]);
            } else {
                intent.putExtra(Constants.CHARGER_CHARGE_NUMBER, content);
                intent.putExtra(Constants.CHARGER_MUZZLE_NUMBER, "1");
            }
            startAct(intent);
            ChargerApp.getInstance().removeActivity(CustomZxingCaptureActivity.class);
            ChargerApp.getInstance().removeActivity(this);
        }
    }
}
