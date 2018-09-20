package com.pinnet.chargerapp.ui.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pinnet.chargerapp.base.BaseCommonFragment;
import com.pinnet.chargerapp.R;
import com.pinnet.chargerapp.base.BaseFragment;
import com.pinnet.chargerapp.mvp.contract.login.RegisterContract;
import com.pinnet.chargerapp.mvp.presenter.login.RegisterPresenter;
import com.pinnet.chargerapp.utils.ToastUtils;
import com.pinnet.chargerapp.widget.LoginEditText;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author P00558
 * @date 2018/4/10
 */

public class ResetPasswordSendCodeFragment extends BaseFragment<RegisterPresenter> implements RegisterContract.View {
    @BindView(R.id.btn_next_step)
    Button btnNextStep;
    @BindView(R.id.let_input_phone)
    LoginEditText letPhone;
    @BindView(R.id.let_identify_code)
    LoginEditText letIdentifyCode;
    @BindView(R.id.tv_send_identifying_code)
    TextView tvSendIdentifyingCode;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.login_reset_password_send_code_fragment;
    }

    @OnClick({R.id.btn_next_step, R.id.tv_send_identifying_code})
    void onViewClickde(View view) {
        switch (view.getId()) {
            case R.id.btn_next_step:
                onNextStep();
                break;
            case R.id.tv_send_identifying_code:
                mPresenter.onSendIdentifyCode();
                break;
            default:
                break;
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        letPhone.setInputType(InputType.TYPE_CLASS_PHONE);
        letIdentifyCode.setInputType(InputType.TYPE_CLASS_NUMBER);
    }

    /**
     * 下一步
     */
    private void onNextStep() {
        if (TextUtils.isEmpty(getPhoneNumber()) || TextUtils.isEmpty(getIdentifyinyCode())) {
            ToastUtils.getInstance().showMessage("手机号或验证码不能为空");
            return;
        }
        if (mContext instanceof ResetPasswordActivity) {
            ((ResetPasswordActivity) mContext).replaceConfirmFragment(getPhoneNumber(), getIdentifyinyCode());
        }
    }

    @Override
    public String getPhoneNumber() {
        return letPhone.getTextValus();
    }

    @Override
    public String getIdentifyinyCode() {
        return letIdentifyCode.getTextValus();
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getPasswordAgain() {
        return null;
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

    }


}
