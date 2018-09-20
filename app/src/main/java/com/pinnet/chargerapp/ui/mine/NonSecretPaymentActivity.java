package com.pinnet.chargerapp.ui.mine;

import android.view.View;
import android.widget.TextView;

import com.pinnet.chargerapp.base.BaseCommonActivity;
import com.pinnet.chargerapp.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author P00558
 * @date 2018/4/11
 * 免密支付
 */

public class NonSecretPaymentActivity extends BaseCommonActivity {
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.mine_wallet_non_secret_payment_activity;
    }

    @Override
    protected void onInitView() {
        super.onInitView();
        tvHeaderTitle.setText("免密支付");
    }

    @OnClick({R.id.iv_back})
    void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finishAct();
                break;
            default:
                break;
        }
    }
}
