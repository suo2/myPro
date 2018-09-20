package com.pinnet.chargerapp.ui.consult;

import android.view.View;
import android.widget.TextView;

import com.pinnet.chargerapp.R;
import com.pinnet.chargerapp.base.BaseActivity;
import com.pinnet.chargerapp.mvp.contract.consult.ConsultHomeContract;
import com.pinnet.chargerapp.mvp.presenter.consult.ConsultHomePresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author P00558
 * @date 2018/5/11
 * 问题详情
 */

public class ConsultDetailActivity extends BaseActivity<ConsultHomePresenter> implements ConsultHomeContract.View {
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.consult_detial_activity;
    }

    @Override
    protected void onInitView() {
        super.onInitView();
        tvHeaderTitle.setText(R.string.consult_question_detail);
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
