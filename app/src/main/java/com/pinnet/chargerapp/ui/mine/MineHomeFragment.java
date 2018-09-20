package com.pinnet.chargerapp.ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pinnet.chargerapp.base.BaseCommonFragment;
import com.pinnet.chargerapp.R;
import com.pinnet.chargerapp.base.BaseFragment;
import com.pinnet.chargerapp.mvp.contract.mine.MineHomeContract;
import com.pinnet.chargerapp.mvp.model.beans.mine.PersonalInfoBean;
import com.pinnet.chargerapp.mvp.presenter.mine.MineHomePresenter;
import com.pinnet.chargerapp.widget.CircleImageView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author P00558
 * @date 2018/4/9
 */

public class MineHomeFragment extends BaseFragment<MineHomePresenter> implements MineHomeContract.View {
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.iv_user_header)
    CircleImageView ivUserHeader;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.mine_home_fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick({R.id.rl_charging_record, R.id.rl_contact_us,
            R.id.rl_my_wallet, R.id.rl_setting, R.id.rl_my_message, R.id.rl_user_personal_info})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.rl_my_wallet:
                intent.setClass(mContext, WalletActivity.class);
                break;
            case R.id.rl_setting:
                intent.setClass(mContext, SettingActivity.class);
                break;
            case R.id.rl_my_message:
                intent.setClass(mContext, MyMessageActivity.class);
                break;
            case R.id.rl_charging_record:
                intent.setClass(mContext, ChargingRecordActivity.class);
                break;
            case R.id.rl_user_personal_info:
                intent.setClass(mContext, PersonalInfoActivity.class);
                break;
            case R.id.rl_contact_us:
                intent.setClass(mContext, ContactUsActivity.class);
                break;
            default:
                break;
        }
        startAct(intent);
    }

    @Override
    public void updatePersonalInfo(PersonalInfoBean bean) {
        if (TextUtils.isEmpty(bean.getNickName())) {
            tvUserName.setText("e品充初级会员");
        } else {
            tvUserName.setText(bean.getNickName());
        }
        mPresenter.onDownloadFile(bean.getUserAvatarUpdateTIme());
    }

    @Override
    public void onLoadUserHeader(String path) {
        Glide.with(this).load(path).asBitmap().placeholder(R.drawable.mine_icon_defaule_header).error(R.drawable.mine_icon_defaule_header).into(ivUserHeader);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            mPresenter.onQueryPersonalInfo();
        }
    }
}
