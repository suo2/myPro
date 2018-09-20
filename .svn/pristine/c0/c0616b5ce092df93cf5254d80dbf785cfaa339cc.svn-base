package com.pinnet.chargerapp.ui.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.pinnet.chargerapp.base.BaseActivity;
import com.pinnet.chargerapp.mvp.contract.mine.PersonalInfoModifyContract;
import com.pinnet.chargerapp.mvp.model.beans.mine.PersonalInfoBean;
import com.pinnet.chargerapp.mvp.model.http.requestbody.UserInfoRequestBody;
import com.pinnet.chargerapp.mvp.presenter.mine.PersonalInfoModifyPresenter;
import com.pinnet.chargerapp.R;
import com.pinnet.chargerapp.widget.EmojiFilter;
import com.pinnet.chargerapp.widget.NameLengthFilter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author P00558
 * @date 2018/4/17
 * 个人信息修改界面
 */

public class PersonalInfoModifyActivity extends BaseActivity<PersonalInfoModifyPresenter> implements PersonalInfoModifyContract.View, TextWatcher {
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.tv_right_menu)
    TextView tvRightMenu;
    @BindView(R.id.et_input)
    EditText etInput;
    @BindView(R.id.iv_clear)
    ImageView ivClear;
    String modifyContent;
    int modifyType;
    private PersonalInfoBean personalInfoBean;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.mine_personalinfo_modify_activity;
    }

    @Override
    protected void onViewCreated(@Nullable Bundle savedInstanceState) {
        super.onViewCreated(savedInstanceState);
        if (getIntent().getExtras() != null) {
            modifyType = getIntent().getIntExtra(PersonalInfoActivity.MODIFY_TYPE, -1);
            modifyContent = getIntent().getStringExtra(PersonalInfoActivity.MODIFY_CONTENT);
            personalInfoBean = (PersonalInfoBean) getIntent().getSerializableExtra(PersonalInfoActivity.MODIFY_BEAN);
        }
    }


    @Override
    protected void onInitView() {
        super.onInitView();
        etInput.addTextChangedListener(this);
        etInput.setText(modifyContent);
        tvRightMenu.setText(R.string.mine_personalinfo_complete);
        tvRightMenu.setVisibility(View.VISIBLE);
        switch (modifyType) {
            case PersonalInfoActivity.MODIFY_NICKNAME:
                tvHeaderTitle.setText(R.string.mine_personalinfo_alter_nickname);
                etInput.setFilters(new InputFilter[]{new EmojiFilter(),new NameLengthFilter(20)});
                break;
            case PersonalInfoActivity.MODIFY_ADDRESS:
                tvHeaderTitle.setText(R.string.mine_personalinfo_alter_address);
                etInput.setFilters(new InputFilter[]{new EmojiFilter(),new NameLengthFilter(100)});
                break;
            default:
                break;
        }

    }

    @OnClick({R.id.iv_back, R.id.iv_clear, R.id.tv_right_menu})
    void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finishAct();
                break;
            case R.id.iv_clear:
                etInput.setText("");
                break;
            case R.id.tv_right_menu:
                showLoading();
                mPresenter.onModify();
                break;
            default:
                break;
        }
    }

    @Override
    public String getInputString() {
        return etInput.getText().toString();
    }

    @Override
    public void onModifyResult(PersonalInfoBean bean) {
        finishAct();
    }

    @Override
    public UserInfoRequestBody getUserInfoRequestBody() {
        UserInfoRequestBody requestBody = new UserInfoRequestBody();
        if (personalInfoBean != null) {
            requestBody.userId = personalInfoBean.getUserid();
            switch (modifyType) {
                case PersonalInfoActivity.MODIFY_NICKNAME:
                    requestBody.nickName = getInputString();
                    requestBody.address = TextUtils.isEmpty(personalInfoBean.getUserAddr()) ? "" : personalInfoBean.getUserAddr();
                    break;
                case PersonalInfoActivity.MODIFY_ADDRESS:
                    requestBody.nickName = TextUtils.isEmpty(personalInfoBean.getNickName()) ? "" : personalInfoBean.getNickName();
                    requestBody.address = getInputString();
                    break;
                default:
                    break;
            }
        }
        return requestBody;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (TextUtils.isEmpty(editable)) {
            ivClear.setVisibility(View.GONE);
        } else {
            ivClear.setVisibility(View.VISIBLE);
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
        mLoadingDialog.show(getSupportFragmentManager(), getString(R.string.main_loading_tip_please_waiting), isResumed);
    }
}
