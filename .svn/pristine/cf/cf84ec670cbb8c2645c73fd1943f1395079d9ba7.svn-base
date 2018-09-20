package com.pinnet.chargerapp.ui.mine;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Config;
import android.view.View;
import android.widget.TextView;

import com.pinnet.chargerapp.R;
import com.pinnet.chargerapp.base.BaseCommonActivity;
import com.pinnet.chargerapp.utils.LogUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * @author P00558
 * @date 2018/4/11
 */

public class ContactUsActivity extends BaseCommonActivity {
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    RxPermissions rxPermissions;

    @Override
    protected int getLayoutId() {
        return R.layout.mine_contact_us_activity;
    }

    @Override
    protected void onInitView() {
        super.onInitView();
        tvHeaderTitle.setText(R.string.mine_contact_us);
        rxPermissions = new RxPermissions(this);

    }

    @OnClick({R.id.iv_back, R.id.rl_call_phone})
    void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finishAct();
                break;
            case R.id.rl_call_phone:
                rxPermissions.request(Manifest.permission.CALL_PHONE)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(@NonNull Boolean aBoolean) throws Exception {
                                if (aBoolean) {
                                    Intent intent = new Intent(Intent.ACTION_CALL);
                                    Uri data = Uri.parse("tel:" + "86-0571-28332811");
                                    intent.setData(data);
                                    if (getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
                                        try {
                                            startAct(intent);
                                        } catch (ActivityNotFoundException e) {
                                            if (Config.LOGD) {
                                                LogUtils.getInstance().d(e.toString());
                                            }
                                        }
                                    }
                                }
                            }
                        });
                break;
            default:
                break;
        }
    }

}
