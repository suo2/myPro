package com.pinnet.chargerapp.ui.login;

import android.text.InputType;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pinnet.chargerapp.base.BaseActivity;
import com.pinnet.chargerapp.mvp.contract.login.RegisterContract;
import com.pinnet.chargerapp.mvp.presenter.login.RegisterPresenter;
import com.pinnet.chargerapp.ui.common.dialogfragment.CustomDialogFragment;
import com.pinnet.chargerapp.widget.LoginEditText;
import com.pinnet.chargerapp.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author P00558
 * @date 2018/4/8
 * 注册页面
 */

public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterContract.View {
    private static final String TAG = RegisterActivity.class.getSimpleName();
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_send_identifying_code)
    TextView tvSendIdentifyingCode;
    @BindView(R.id.let_username)
    LoginEditText letUserName;
    @BindView(R.id.let_identify_code)
    LoginEditText letIdentifyCode;
    @BindView(R.id.let_password)
    LoginEditText letPassword;
    @BindView(R.id.let_password_re)
    LoginEditText letPasswordRe;
    private CustomDialogFragment customDialogFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.login_register_activity;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void onInitView() {
        super.onInitView();
        tvHeaderTitle.setText(getResources().getString(R.string.login_register_title));
        letUserName.setInputType(InputType.TYPE_CLASS_PHONE);
        letIdentifyCode.setInputType(InputType.TYPE_CLASS_NUMBER);
        ivBack.setVisibility(View.VISIBLE);
        customDialogFragment = CustomDialogFragment.newInstance();
        customDialogFragment.setTitle("注册成功");
        customDialogFragment.setContent("恭喜您注册成功，点击确定开始登录");
        customDialogFragment.setOnConfirmClick(new CustomDialogFragment.OnConfirmClick() {
            @Override
            public void onConfirm() {
                finishAct();
            }
        });

    }

    @OnClick({R.id.tv_send_identifying_code, R.id.iv_back, R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_send_identifying_code:
                mPresenter.onSendIdentifyCode();
                break;
            case R.id.iv_back:
                finishAct();
                break;
            case R.id.btn_register:
                mPresenter.onRegister();
                break;
            default:
                break;
        }
    }

    @Override
    public void showLoading() {
        super.showLoading();
        mLoadingDialog.show(getSupportFragmentManager(), getString(R.string.main_loading_tip_register), isResumed);
    }

    @Override
    public String getPhoneNumber() {
        return letUserName.getTextValus();
    }

    @Override
    public String getIdentifyinyCode() {
        return letIdentifyCode.getTextValus();
    }

    @Override
    public String getPassword() {
        return letPassword.getTextValus();
    }

    @Override
    public String getPasswordAgain() {
        return letPasswordRe.getTextValus();
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

    @Override
    public void onRegistered() {
        customDialogFragment.show(getSupportFragmentManager(), TAG, isResumed);
    }

    @Override
    public void dismissLoading() {
        super.dismissLoading();
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss(isResumed);
        }
    }
}
