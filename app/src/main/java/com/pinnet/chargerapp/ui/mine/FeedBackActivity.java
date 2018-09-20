package com.pinnet.chargerapp.ui.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.pinnet.chargerapp.R;
import com.pinnet.chargerapp.base.BaseCommonActivity;
import com.pinnet.chargerapp.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author P00558
 * @date 2018/5/2
 * 意见反馈
 */

public class FeedBackActivity extends BaseCommonActivity {
    @BindView(R.id.tv_header_title)
    TextView tvHeadTitle;
    @BindView(R.id.et_feedback)
    EditText etFeedBack;

    @Override
    protected int getLayoutId() {
        return R.layout.mine_feedback_activity;
    }

    @Override
    protected void onViewCreated(@Nullable Bundle savedInstanceState) {
        super.onViewCreated(savedInstanceState);
        tvHeadTitle.setText(R.string.mine_setting_feedback);
    }

    @OnClick({R.id.iv_back, R.id.btn_submit})
    void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finishAct();
                break;
            case R.id.btn_submit:
                ToastUtils.getInstance().showMessage("意见反馈成功");
                finishAct();
                break;
        }
    }
}
