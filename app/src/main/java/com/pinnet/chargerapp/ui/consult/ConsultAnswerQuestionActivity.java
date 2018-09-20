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
 * 咨询 回答
 */

public class ConsultAnswerQuestionActivity extends BaseActivity<ConsultHomePresenter> implements ConsultHomeContract.View {
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.tv_right_menu)
    TextView tvRightMenu;

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.consult_answer_question_activity;
    }

    @Override
    protected void onInitView() {
        super.onInitView();
        tvHeaderTitle.setText(R.string.consult_tip_answer);
        tvRightMenu.setVisibility(View.VISIBLE);
        tvRightMenu.setText(R.string.consult_tip_submit);
    }

    @OnClick({R.id.iv_back, R.id.tv_right_menu})
    void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finishAct();
                break;
            case R.id.tv_right_menu:
                break;
            default:
                break;
        }
    }
}
