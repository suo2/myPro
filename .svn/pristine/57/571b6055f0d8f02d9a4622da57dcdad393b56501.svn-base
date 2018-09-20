package com.pinnet.chargerapp.ui.mine;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.pinnet.chargerapp.base.BaseActivity;
import com.pinnet.chargerapp.mvp.contract.mine.WalletContract;
import com.pinnet.chargerapp.mvp.model.beans.BaseBean;
import com.pinnet.chargerapp.mvp.presenter.mine.WalletPresenter;
import com.pinnet.chargerapp.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author P00558
 * @date 2018/4/11
 * 我的钱包
 */

public class WalletActivity extends BaseActivity<WalletPresenter> implements WalletContract.View {
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.tv_balance)
    TextView tvBalance;
    @BindView(R.id.tv_income)
    TextView tvIncome;
    @BindView(R.id.tv_integrate)
    TextView tvIntegrate;
    @BindView(R.id.tv_coupon)
    TextView tvCoupon;

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.mine_wallet_activity;
    }

    @Override
    protected void onInitView() {
        super.onInitView();
        tvHeaderTitle.setText(R.string.mine_my_wallet);
    }

    @OnClick({R.id.iv_back, R.id.rl_non_secret_payment})
    void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finishAct();
                break;
            case R.id.rl_non_secret_payment:
                startAct(new Intent(this, NonSecretPaymentActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestResult(BaseBean baseBean) {

    }
}
