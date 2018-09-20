package com.pinnet.chargerapp.base;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.alipay.sdk.app.EnvUtils;
import com.pinnet.chargerapp.app.ChargerApp;
import com.pinnet.chargerapp.ui.common.dialogfragment.CustomLoadingDialogFragment;
import com.pinnet.chargerapp.utils.ScreenUtils;
import com.squareup.leakcanary.RefWatcher;

import java.util.List;

import butterknife.ButterKnife;

/**
 * @author P00558
 * @date 2018/4/2
 * 无MVP基类
 */

public abstract class BaseCommonActivity extends AppCompatActivity {
    protected Context mContext;
    protected CustomLoadingDialogFragment mLoadingDialog;
    protected boolean isResumed;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        mLoadingDialog = CustomLoadingDialogFragment.newInstance(mContext, getSupportFragmentManager());
        onBeforeSetContentView();
        setContentView(getLayoutId());
        ChargerApp.getInstance().addActivity(this);
        ScreenUtils.assistActivity(this);
        mContext = this;
        ButterKnife.bind(this);
        setImmersionBar();
        onViewCreated(savedInstanceState);
        onFindView();
        onInitView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ChargerApp.getInstance().removeActivity(this);
        RefWatcher refWatcher = ChargerApp.getRefWatcher(this);//1
        refWatcher.watch(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isResumed = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isResumed = false;
    }

    protected void onBeforeSetContentView() {

    }

    /**
     * 设置透明状态栏
     */
    private void setImmersionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
                Window window = getWindow();
                View decorView = window.getDecorView();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            } else {
                Window window = getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                attributes.flags |= flagTranslucentStatus;
                window.setAttributes(attributes);
            }
        }
    }

    /**
     * Activity加载
     */
    protected void onViewCreated(@Nullable Bundle savedInstanceState) {
    }

    /**
     * 实例化控件
     */
    protected void onFindView() {

    }


    protected void onInitView() {

    }

    protected abstract int getLayoutId();

    /**
     * 显示隐藏Fragment
     *
     * @param showFragment     待显示Fragment
     * @param hideFragment     待隐藏Fragment
     * @param mFragmentManager
     */
    @SuppressLint("RestrictedApi")
    protected void showHideFragment(@NonNull Fragment showFragment, Fragment hideFragment, @NonNull FragmentManager mFragmentManager) {
        if (showFragment == hideFragment) {
            return;
        }
        FragmentTransaction mTransaction = mFragmentManager.beginTransaction();
        mTransaction.show(showFragment);
        if (hideFragment == null) {
            List<Fragment> fragmentList = mFragmentManager.getFragments();
            if (fragmentList != null) {
                for (Fragment fragment : fragmentList) {
                    if (fragment != null && fragment != showFragment) {
                        mTransaction.hide(fragment);
                    }
                }
            }
        } else {
            mTransaction.hide(hideFragment);
        }
        mTransaction.commit();
    }

    /**
     * 跳转Activity 带动画
     *
     * @param intent
     */
    protected void startAct(Intent intent) {
        mLoadingDialog.dismiss(isResumed);
        startActivity(intent);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
//        } else {
//            startActivity(intent);
//        }
    }

    /**
     * 跳转Activity 带动画
     *
     * @param intent
     */
    protected void startActForResult(Intent intent, int requestCode) {
        mLoadingDialog.dismiss(isResumed);
        startActivityForResult(intent, requestCode);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            startActivityForResult(intent, requestCode, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
//        } else {
//            startActivityForResult(intent, requestCode);
//        }
    }

    /**
     * 关闭Activity 带动画
     */
    protected void finishAct() {
        finish();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            finishAfterTransition();
//        } else {
//            finish();
//        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                if (hideInputMethod(this, v)) {
                    return true; //隐藏键盘时，其他控件不响应点击事件==》注释则不拦截点击事件
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public static Boolean hideInputMethod(Context context, View v) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            return imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
        return false;
    }

    public static boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            v.getLocationInWindow(leftTop);
            int left = leftTop[0], top = leftTop[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
}
