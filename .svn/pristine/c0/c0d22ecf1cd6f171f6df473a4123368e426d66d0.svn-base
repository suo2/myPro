package com.pinnet.chargerapp.ui.login;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pinnet.chargerapp.base.BaseCommonActivity;
import com.pinnet.chargerapp.base.BaseCommonFragment;
import com.pinnet.chargerapp.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author P00558
 * @date 2018/4/8
 * 忘记密码
 */

public class ResetPasswordActivity extends BaseCommonActivity {
    public static final String KEY_PHONE = "phone_number";
    public static final String KEY_IDENTIDY = "identify_code";
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    BaseCommonFragment mSendCodeFragment;
    BaseCommonFragment mConfirmFragment;
    private FragmentManager mFragmentManager;

    @Override
    protected int getLayoutId() {
        return R.layout.login_forget_passwort_activity;
    }

    @Override
    protected void onInitView() {
        super.onInitView();
        tvHeaderTitle.setText(getResources().getString(R.string.login_find_password));
        ivBack.setVisibility(View.VISIBLE);
        mFragmentManager = getSupportFragmentManager();
        initFragment();
        setDefaultFragment();
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

    private void initFragment() {
        mSendCodeFragment = new ResetPasswordSendCodeFragment();
        mConfirmFragment = new ResetPasswordConfirmFragment();
    }

    /**
     * 设置默认fragment
     */
    private void setDefaultFragment() {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if (!mSendCodeFragment.isAdded()) {
            transaction.add(R.id.view_main, mSendCodeFragment, ResetPasswordSendCodeFragment.class.getSimpleName()).show(mSendCodeFragment);
        } else {
            transaction.show(mSendCodeFragment);
        }
        transaction.commit();
    }

    public void replaceConfirmFragment(String phone, String identifyCode) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_PHONE, phone);
        bundle.putString(KEY_IDENTIDY, identifyCode);
        mConfirmFragment.setArguments(bundle);
        transaction.replace(R.id.view_main, mConfirmFragment, ResetPasswordConfirmFragment.class.getSimpleName());
        transaction.commit();
    }
}
