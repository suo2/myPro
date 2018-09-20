package com.pinnet.chargerapp.ui.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.pinnet.chargerapp.R;
import com.pinnet.chargerapp.base.BaseFragment;
import com.pinnet.chargerapp.mvp.contract.login.RegisterContract;
import com.pinnet.chargerapp.mvp.presenter.login.RegisterPresenter;
import com.pinnet.chargerapp.ui.common.dialogfragment.CustomDialogFragment;
import com.pinnet.chargerapp.widget.LoginEditText;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author P00558
 * @date 2018/4/10
 * 修改密码
 */

public class ResetPasswordConfirmFragment extends BaseFragment<RegisterPresenter> implements RegisterContract.View {
    private static final String TAG = "ResetPasswordConfirmFragment";
    @BindView(R.id.let_password)
    LoginEditText letPassword;
    @BindView(R.id.let_password_re)
    LoginEditText letPasswordRe;
    private String phoneNumber;
    private String identifyCode;
    private CustomDialogFragment mDialog;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.login_reset_password_confirm_fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getIntentData();
        initDialog();
    }

    @OnClick({R.id.btn_confirm})
    void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                mPresenter.onResetPassWord();
                break;
            default:
                break;
        }
    }

    private void getIntentData() {
        Bundle bundle = getArguments();
        phoneNumber = bundle.getString(ResetPasswordActivity.KEY_PHONE);
        identifyCode = bundle.getString(ResetPasswordActivity.KEY_IDENTIDY);
    }

    private void initDialog() {
        mDialog = CustomDialogFragment.newInstance();
        mDialog.setTitle("修改成功");
        mDialog.setContent("密码修改成功，请点击返回登录");
        mDialog.setOnConfirmClick(new CustomDialogFragment.OnConfirmClick() {
            @Override
            public void onConfirm() {
                getActivity().finish();
            }
        });
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String getIdentifyinyCode() {
        return identifyCode;
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
    }

    @Override
    public void onRegistered() {
        mDialog.show(getFragmentManager(), TAG);
    }


}
