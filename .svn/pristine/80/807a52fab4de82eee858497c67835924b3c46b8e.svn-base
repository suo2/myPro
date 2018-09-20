package com.pinnet.chargerapp.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import com.pinnet.chargerapp.app.ChargerApp;
import com.pinnet.chargerapp.app.Constants;
import com.pinnet.chargerapp.base.BaseActivity;
import com.pinnet.chargerapp.mvp.contract.mine.SettingContract;
import com.pinnet.chargerapp.mvp.presenter.mine.SettingPresenter;
import com.pinnet.chargerapp.R;
import com.pinnet.chargerapp.ui.common.dialogfragment.CustomDialogFragment;
import com.pinnet.chargerapp.ui.main.MainActivity;

import java.io.File;
import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author P00558
 * @date 2018/4/9
 * 我的消息
 */

public class SettingActivity extends BaseActivity<SettingPresenter> implements SettingContract.View {
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.tv_cache)
    TextView tvCache;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    private CustomDialogFragment clearCacheDialog;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.mine_setting_activity;
    }

    @Override
    protected void onInitView() {
        super.onInitView();
        tvHeaderTitle.setText("设置");
        tvCache.setText(mPresenter.getCacheSize(this));
        try {
            tvVersion.setText("V" + ChargerApp.getInstance().getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.iv_back, R.id.btn_login_out, R.id.tv_cache, R.id.rl_feedback, R.id.rl_aboute_us})
    void onViewClicked(View view) {
        Intent mIntent;
        switch (view.getId()) {

            case R.id.iv_back:
                finishAct();
                break;
            case R.id.btn_login_out:
                mPresenter.onLoginOut();
                startAct(new Intent(this, MainActivity.class).putExtra(Constants.MAIN_LOGIN_OUT, true));
                finishAct();
                break;
            case R.id.tv_cache:
                if (clearCacheDialog == null) {
                    clearCacheDialog = CustomDialogFragment.newInstance();
                    clearCacheDialog.setTitle("清空缓存");
                    clearCacheDialog.setContent("您确定要清空缓存？");
                    clearCacheDialog.showCancle();
                    clearCacheDialog.setOnConfirmClick(new CustomDialogFragment.OnConfirmClick() {
                        @Override
                        public void onConfirm() {
                            mPresenter.clearAllCache(mContext);
                            tvCache.setText(mPresenter.getCacheSize(mContext));
                        }
                    });
                }
                clearCacheDialog.show(getSupportFragmentManager(), "clearCacheDialog", isResumed);
                break;
            case R.id.rl_feedback:
                mIntent = new Intent(this, FeedBackActivity.class);
                startAct(mIntent);
                break;
            case R.id.rl_aboute_us:
                mIntent = new Intent(this, AboutUsActivity.class);
                startAct(mIntent);
                break;
            default:
                break;
        }
    }
}
