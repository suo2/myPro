package com.pinnet.chargerapp.ui.login;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pinnet.chargerapp.base.BaseActivity;
import com.pinnet.chargerapp.mvp.contract.login.LoginContract;
import com.pinnet.chargerapp.mvp.presenter.login.LoginPresenter;
import com.pinnet.chargerapp.utils.SharePrefUtils;
import com.pinnet.chargerapp.widget.LoginEditText;
import com.pinnet.chargerapp.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author P00558
 * @date 2018/4/8
 * 登录页面
 */

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {
    private static final String TAG = "LoginActivity";
    @BindView(R.id.tv_forget_password)
    TextView tvForgetPassword;
    @BindView(R.id.tv_register_user)
    TextView tvRegisterUser;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.let_username)
    LoginEditText letUserName;
    @BindView(R.id.let_password)
    LoginEditText letPassword;
    @BindView(R.id.ll_card_front)
    LinearLayout mFlCardFront;
    @BindView(R.id.ll_card_back)
    LinearLayout mFlCardBack;
    @BindView(R.id.tv_switch_login_method)
    TextView tvLoginMethod;
    @BindView(R.id.tv_send_identifying_code)
    TextView tvSendIdentifyingCode;
    @BindView(R.id.let_username_back)
    LoginEditText letUserNameBack;
    @BindView(R.id.let_identify_code)
    LoginEditText letIdentifyCode;
    AnimatorSet mRightOutSet;
    AnimatorSet mLeftInSet;
    private boolean mIsShowBack;

    @Override
    protected int getLayoutId() {
        return R.layout.login_home_activity;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void onViewCreated(@Nullable Bundle savedInstanceState) {
        super.onViewCreated(savedInstanceState);
        letUserName.setInputType(InputType.TYPE_CLASS_PHONE);
        letUserNameBack.setInputType(InputType.TYPE_CLASS_PHONE);
        letIdentifyCode.setInputType(InputType.TYPE_CLASS_NUMBER);
        letUserName.setText(SharePrefUtils.getInstance().getLoginedAccount());
        setAnimators();
        setCameraDistance();
    }

    @Override
    public String getUserName() {
        return mIsShowBack ? letUserNameBack.getTextValus() : letUserName.getTextValus();
    }

    @Override
    public String getPassWord() {
        return letPassword.getTextValus();
    }

    @Override
    public String getIdentifyinyCode() {
        return letIdentifyCode.getTextValus();
    }

    @Override
    public void onLogined() {
        finishAct();
    }

    @Override
    public void updateIdentifyingCodeView(boolean enable, long times) {
        if (enable) {
            tvSendIdentifyingCode.setEnabled(true);
            tvSendIdentifyingCode.setText(getString(R.string.login_send_identifying_code));
            tvSendIdentifyingCode.setTextColor(getResources().getColor(R.color.text_color_ff9933));
        } else {
            tvSendIdentifyingCode.setEnabled(false);
            tvSendIdentifyingCode.setText(String.format(getResources().getString(R.string.login_resend_identifying_code), String.valueOf(times)));
            tvSendIdentifyingCode.setTextColor(getResources().getColor(R.color.text_color_cccccc));
        }
    }

    @OnClick({R.id.tv_register_user, R.id.tv_forget_password, R.id.btn_login, R.id.tv_switch_login_method, R.id.tv_send_identifying_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_register_user:
                startAct(new Intent(this, RegisterActivity.class));
                break;
            case R.id.tv_forget_password:
                startAct(new Intent(this, ResetPasswordActivity.class));
                break;
            case R.id.btn_login:
                if (mIsShowBack) {
                    mPresenter.onLoginByVerCode();
                } else {
                    mPresenter.onLogin();
                }
                break;
            case R.id.tv_switch_login_method:
                flipCard();
                break;
            case R.id.tv_send_identifying_code:
                mPresenter.onSendIdentifyCode();
                break;
            default:
                break;
        }
    }

    @Override
    public void dismissLoading() {
        super.dismissLoading();
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss(isResumed);
        }
    }

    @Override
    public void showLoading() {
        super.showLoading();
        mLoadingDialog.show(getSupportFragmentManager(), getString(R.string.main_loading_tip_login), isResumed);
    }

    /**
     * 改变视角距离, 贴近屏幕
     */
    private void setCameraDistance() {
        int distance = 16000;
        float scale = getResources().getDisplayMetrics().density * distance;
        mFlCardFront.setCameraDistance(scale);
        mFlCardBack.setCameraDistance(scale);
    }

    /**
     * 设置动画
     */
    private void setAnimators() {
        mRightOutSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.card_anim_out);
        mLeftInSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.card_anim_in);

        // 设置点击事件
        mRightOutSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                tvLoginMethod.setClickable(false);
            }
        });
        mLeftInSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (mIsShowBack) {
                    mFlCardFront.setVisibility(View.GONE);
                } else {
                    mFlCardBack.setVisibility(View.GONE);
                }
                tvLoginMethod.setClickable(true);
                if (mIsShowBack) {
                    tvLoginMethod.setText(R.string.login_by_password);
                } else {
                    tvLoginMethod.setText(R.string.login_by_identifying);
                }

            }

        });
    }

    /**
     * 翻转卡片
     */
    private void flipCard() {
        // 正面朝上
        if (!mIsShowBack) {
            mFlCardBack.setVisibility(View.VISIBLE);
            mRightOutSet.setTarget(mFlCardFront);
            mLeftInSet.setTarget(mFlCardBack);
            mRightOutSet.start();
            mLeftInSet.start();
            mIsShowBack = true;
        } else { // 背面朝上
            mFlCardFront.setVisibility(View.VISIBLE);
            mRightOutSet.setTarget(mFlCardBack);
            mLeftInSet.setTarget(mFlCardFront);
            mRightOutSet.start();
            mLeftInSet.start();
            mIsShowBack = false;
        }
    }
}
