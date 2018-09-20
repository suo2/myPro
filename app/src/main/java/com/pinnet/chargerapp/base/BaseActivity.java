package com.pinnet.chargerapp.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pinnet.chargerapp.app.ChargerApp;
import com.pinnet.chargerapp.dagger.component.ActivityComponent;
import com.pinnet.chargerapp.dagger.component.DaggerActivityComponent;
import com.pinnet.chargerapp.dagger.module.ActivityModule;
import com.pinnet.chargerapp.ui.common.dialogfragment.CustomDialogFragment;
import com.pinnet.chargerapp.ui.login.LoginActivity;
import com.pinnet.chargerapp.utils.LogUtils;
import com.pinnet.chargerapp.utils.SharePrefUtils;
import com.pinnet.chargerapp.utils.ToastUtils;
import com.pinnet.chargerapp.R;

import javax.inject.Inject;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 * @author P00558
 * @date 2018/4/2
 * 有MVP的基类
 */

public abstract class BaseActivity<T extends IBasePresenter> extends BaseCommonActivity implements IBaseView {
    private static final int STATE_MAIN = 0x00;
    private static final int STATE_LOADING = 0x01;
    private static final int STATE_ERROR = 0x02;
    private static final int STATE_EMPTY = 0x03;
    private int currentState = STATE_MAIN;
    private boolean isErrorViewAdded;
    private boolean isEmptyViewAdded;
    private boolean isLoadingViewAdded;
    private ViewGroup mainView;
    private ViewGroup parentView;
    private View viewError;
    private View viewLoading;
    private View viewEmpty;
    GifImageView ivLoading;
    @Inject
    protected T mPresenter;

    protected ActivityComponent getActivityComponent() {
        return DaggerActivityComponent.builder()
                .chargerAppComponent(ChargerApp.getAppComponent())
                .activityModule(getActivityModule())
                .build();
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    @Override
    protected void onViewCreated(@Nullable Bundle savedInstanceState) {
        super.onViewCreated(savedInstanceState);
        initInject();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        mainView = (ViewGroup) findViewById(R.id.view_main);
        if (mainView == null) {
            throw new IllegalStateException(
                    "The subclass of BaseActivity must contain a View named 'view_main'.");
        }
        if (!(mainView.getParent() instanceof ViewGroup)) {
            throw new IllegalStateException(
                    "view_main's ParentView should be a ViewGroup.");
        }
        parentView = (ViewGroup) mainView.getParent();
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        super.onDestroy();
    }

    protected abstract void initInject();

    @Override
    public void stateError() {
        if (currentState == STATE_ERROR) {
            return;
        }
        if (!isErrorViewAdded) {
            isErrorViewAdded = true;
            View.inflate(mContext, R.layout.main_error, parentView);
            viewError = parentView.findViewById(R.id.ll_error);
            TextView tvRefresh = (TextView) viewError.findViewById(R.id.tv_refresh);
            tvRefresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onRefresh();
                }
            });
            if (viewError == null) {
                throw new IllegalStateException(
                        "A View should be named 'view_error' in ErrorLayoutResource.");
            }
        }
        hideCurrentView();
        currentState = STATE_ERROR;
        viewError.setVisibility(View.VISIBLE);
    }

    @Override
    public void stateLoading() {
        if (currentState == STATE_LOADING) {
            return;
        }
        if (!isLoadingViewAdded) {
            isLoadingViewAdded = true;
            View.inflate(mContext, R.layout.main_loading, parentView);
            viewLoading = parentView.findViewById(R.id.ll_loading);
            ivLoading = (GifImageView) viewLoading.findViewById(R.id.iv_loading);
            if (viewLoading == null) {
                throw new IllegalStateException(
                        "A View should be named 'view_loading' in ErrorLayoutResource.");
            }
            try {
                // 如果加载的是gif动图，第一步需要先将gif动图资源转化为GifDrawable
                // 将gif图资源转化为GifDrawable
                GifDrawable gifDrawable = new GifDrawable(getResources(), R.drawable.default_page_loading);
                // gif1加载一个动态图gif
                ivLoading.setImageDrawable(gifDrawable);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        hideCurrentView();
        currentState = STATE_LOADING;
        viewLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void stateMain() {
        if (currentState == STATE_MAIN) {
            return;
        }
        hideCurrentView();
        currentState = STATE_MAIN;
        mainView.setVisibility(View.VISIBLE);
    }

    @Override
    public void stateEmpty() {
        if (currentState == STATE_EMPTY) {
            return;
        }
        if (!isEmptyViewAdded) {
            isEmptyViewAdded = true;
            View.inflate(mContext, R.layout.main_empty, parentView);
            viewEmpty = parentView.findViewById(R.id.ll_empty);
            TextView tvRefresh = (TextView) viewEmpty.findViewById(R.id.tv_refresh);
            tvRefresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onRefresh();
                }
            });
            if (viewEmpty == null) {
                throw new IllegalStateException(
                        "A View should be named 'view_empty' in ErrorLayoutResource.");
            }
        }
        hideCurrentView();
        currentState = STATE_EMPTY;
        viewEmpty.setVisibility(View.VISIBLE);
    }

    @Override
    public void showErrorMsg(String msg) {
        ToastUtils.getInstance().showMessage(msg);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void dismissLoading() {
        mLoadingDialog.dismiss(isResumed);
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void onThirdLogined(int errorCode) {
        onConnectionConflict(errorCode);
    }

    private void onConnectionConflict(int errorCode) {
        SharePrefUtils.getInstance().onLoginOut();
        final Activity taskTop = ChargerApp.getInstance().getTaskTop();
        if (taskTop == null) {
            return;
        }
        final CustomDialogFragment dialogFragment = new CustomDialogFragment();
        if (306 == errorCode) {
            dialogFragment.setContent("您的登录已过期，请重新登录");
        } else if (307 == errorCode) {
            dialogFragment.setContent("您的账号已在其他终端登录,请重新登录");
        }
        dialogFragment.setTitle("登录异常");
        dialogFragment.setOnConfirmClick(new CustomDialogFragment.OnConfirmClick() {
            @Override
            public void onConfirm() {
                dialogFragment.dismiss();
                Intent intent = new Intent(taskTop, LoginActivity.class);
                taskTop.startActivity(intent);
            }
        });

        dialogFragment.show(getSupportFragmentManager(), taskTop.getClass().getSimpleName(),isResumed);
    }

    private void hideCurrentView() {
        switch (currentState) {
            case STATE_MAIN:
                mainView.setVisibility(View.GONE);
                break;
            case STATE_LOADING:
                viewLoading.clearAnimation();
                ivLoading.clearAnimation();
                viewLoading.setVisibility(View.GONE);
                break;
            case STATE_ERROR:
                if (viewError != null) {
                    viewError.setVisibility(View.GONE);
                }
                break;
            case STATE_EMPTY:
                viewEmpty.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }
}
