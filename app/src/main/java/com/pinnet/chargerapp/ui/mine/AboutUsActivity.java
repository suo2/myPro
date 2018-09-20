package com.pinnet.chargerapp.ui.mine;

import android.view.View;
import android.widget.TextView;

import com.pinnet.chargerapp.R;
import com.pinnet.chargerapp.base.BaseCommonActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author P00558
 * @date 2018/5/25
 * 关于我们
 */

public class AboutUsActivity extends BaseCommonActivity {
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.mine_about_us_activity;
    }

    @Override
    protected void onInitView() {
        super.onInitView();
        tvHeaderTitle.setText(R.string.mine_setting_about_us);
    }

    @OnClick({R.id.iv_back})
    void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finishAct();
                break;
        }
    }

}
