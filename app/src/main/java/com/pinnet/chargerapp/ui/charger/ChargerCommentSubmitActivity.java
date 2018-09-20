package com.pinnet.chargerapp.ui.charger;

import android.view.View;
import android.widget.TextView;

import com.pinnet.chargerapp.R;
import com.pinnet.chargerapp.base.BaseCommonActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author P00558
 * @date 2018/5/10
 * 提交评论
 */

public class ChargerCommentSubmitActivity extends BaseCommonActivity {
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.charger_comment_submit_activity;
    }

    @Override
    protected void onInitView() {
        super.onInitView();
        tvHeaderTitle.setText(R.string.charger_comment_submit);
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
