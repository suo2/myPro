package com.pinnet.chargerapp.ui.main;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.transition.Fade;
import android.transition.Visibility;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;

import com.pinnet.chargerapp.R;
import com.pinnet.chargerapp.base.BaseCommonActivity;
import com.pinnet.chargerapp.utils.ScreenUtils;
import com.pinnet.chargerapp.utils.SystemUtil;

import butterknife.BindView;


/**
 * @author P00558
 * @date 2018/5/14
 */

public class SplashActivity extends BaseCommonActivity {
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.iv_name)
    ImageView ivName;
    @BindView(R.id.iv_anim_logo)
    ImageView ivAnimLogo;
    @BindView(R.id.tv_tip)
    TextView tvTip;
    private boolean animatorStart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.TranslucentTheme);
        getWindow().setBackgroundDrawableResource(R.drawable.main_splash_bg);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onBeforeSetContentView() {
        super.onBeforeSetContentView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Fade fadeIn = new Fade();
            fadeIn.setDuration(1000);
            fadeIn.setMode(Visibility.MODE_IN);
            getWindow().setEnterTransition(fadeIn);
            Fade fadeOut = new Fade();
            fadeOut.setDuration(1000);
            fadeOut.setMode(Visibility.MODE_OUT);
            getWindow().setExitTransition(fadeOut);
        }
        //解决返回桌面启动 进入加载页面问题
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_splash_activity;
    }

    @Override
    protected void onInitView() {
        super.onInitView();

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!animatorStart && hasFocus) {
            animatorStart = true;
            ObjectAnimator ivLogoAlpha = ObjectAnimator.ofFloat(ivLogo, "alpha", 0.0f, 1.0f);
            ObjectAnimator ivLogoScaleX = ObjectAnimator.ofFloat(ivLogo, "scaleX", 0.0f, 1.0f, 1.5f, 1.0f);
            ObjectAnimator ivLogoScaleY = ObjectAnimator.ofFloat(ivLogo, "scaleY", 0.0f, 1.0f, 1.5f, 1.0f);
            ObjectAnimator ivNameAlpha = ObjectAnimator.ofFloat(ivName, "alpha", 0.0f, 1.0f);
            ObjectAnimator tvTipAlpha = ObjectAnimator.ofFloat(tvTip, "alpha", 0.0f, 1.0f);

            ObjectAnimator ivAnimLogoAlpha = ObjectAnimator.ofFloat(ivAnimLogo, "alpha", 0.0f, 1.0f, 0.0f);
            ObjectAnimator ivAnimLogoTranlsationY = new ObjectAnimator().ofFloat(ivAnimLogo, "translationY", ScreenUtils.getScreenHeight(this), ivLogo.getY());
            AnimatorSet ivAnimLogoSet = new AnimatorSet();
            ivAnimLogoSet.playTogether(ivAnimLogoTranlsationY, ivAnimLogoAlpha);
            ivAnimLogoSet.setDuration(2000);
            ivAnimLogoSet.start();
            final AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(ivLogoAlpha, ivLogoScaleX, ivLogoScaleY, ivNameAlpha, tvTipAlpha);
            animatorSet.setDuration(1500).setStartDelay(1800);
            animatorSet.start();
            animatorSet.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    Intent intent = new Intent(mContext, MainActivity.class);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this).toBundle());
                    } else {
                        startActivity(intent);
                        overridePendingTransition(R.anim.splash_anim_fade_in, R.anim.splash_anim_fade_out);
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        if (KeyEvent.KEYCODE_BACK == keyCode) {
            // 使返回键失效
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }
}
