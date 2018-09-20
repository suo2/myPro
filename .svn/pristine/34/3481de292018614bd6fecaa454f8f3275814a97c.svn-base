package com.pinnet.chargerapp.ui.charger;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.pinnet.chargerapp.app.Constants;
import com.pinnet.chargerapp.base.BaseCommonActivity;
import com.pinnet.chargerapp.R;
import com.pinnet.chargerapp.utils.SystemUtil;
import com.pinnet.chargerapp.utils.ToastUtils;
import com.pinnet.chargerapp.widget.ClearEditText;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author P00558
 * @date 2018/4/12
 * 扫描
 */

public class CustomZxingCaptureActivity extends BaseCommonActivity {

    /**
     * 条形码扫描管理器
     */
    private CaptureManager mCaptureManager;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.tv_right_menu)
    TextView tvRightMenu;
    @BindView(R.id.tv_flashlight)
    CheckBox cbFlashLight;
    @BindView(R.id.tv_input_number)
    TextView tvInputNumber;
    @BindView(R.id.ll_input_number)
    LinearLayout llInputNumber;
    @BindView(R.id.cet_input_number)
    ClearEditText cetInputNumber;

    /**
     * 条形码扫描视图
     */
    @BindView(R.id.zxing_barcode_scanner)
    DecoratedBarcodeView mBarcodeView;
    private boolean flashListIsOpen;
    private ZxingHelpDialogFragment mHelpDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.charger_zxing_activity;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onViewCreated(@Nullable Bundle savedInstanceState) {
        super.onViewCreated(savedInstanceState);
        mCaptureManager = new CaptureManager(this, mBarcodeView);
        mCaptureManager.initializeFromIntent(getIntent(), savedInstanceState);
        mCaptureManager.decode();
    }

    @Override
    protected void onInitView() {
        super.onInitView();
        tvHeaderTitle.setText("扫描二维码");
        tvRightMenu.setText("帮助");
        tvRightMenu.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.iv_back, R.id.tv_right_menu, R.id.tv_flashlight, R.id.tv_input_number, R.id.btn_confirm})
    void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finishAct();
                break;
            case R.id.tv_right_menu:
                showHelpDialog();
                break;
            case R.id.tv_input_number:
                if (llInputNumber.getVisibility() == View.GONE) {
                    llInputNumber.setVisibility(View.VISIBLE);
                    tvInputNumber.setText("切换扫描充电");
                    SystemUtil.showKeyboard(view);
                } else {
                    llInputNumber.setVisibility(View.GONE);
                    tvInputNumber.setText(R.string.charger_scan_input_charger_number);
                    SystemUtil.hideKeyboard(view);
                }
                break;
            case R.id.tv_flashlight:
                switchFlashLight();
                break;
            case R.id.btn_confirm:
                setResult(Constants.CHARGER_SCAN_RESULT_CODE, new Intent().putExtra(Constants.CHARGER_SCAN_RESULT, cetInputNumber.getText().toString()));
                finishAct();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCaptureManager.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCaptureManager.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCaptureManager.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mCaptureManager.onSaveInstanceState(outState);
    }

    /**
     * 权限处理
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        mCaptureManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 按键处理
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mBarcodeView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    // Get the results:
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                ToastUtils.getInstance().showMessage("Cancelled");
            } else {
                ToastUtils.getInstance().showMessage("Scanned");
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * 显示帮助dialog
     */
    private void showHelpDialog() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag(ZxingHelpDialogFragment.class.getSimpleName());
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        if (mHelpDialog == null) {
            mHelpDialog = ZxingHelpDialogFragment.newInstance();
        }
        mHelpDialog.show(ft, ZxingHelpDialogFragment.class.getSimpleName());
    }

    private void switchFlashLight() {
        if (!flashListIsOpen) {
            cbFlashLight.setText(R.string.charger_zxing_open_flashlight);
            mBarcodeView.setTorchOn();
        } else {
            mBarcodeView.setTorchOff();
            cbFlashLight.setText(R.string.charger_zxing_close_flashlight);
        }
        flashListIsOpen = !flashListIsOpen;
    }
}
