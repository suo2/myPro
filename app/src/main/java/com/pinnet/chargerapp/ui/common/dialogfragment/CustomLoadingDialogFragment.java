package com.pinnet.chargerapp.ui.common.dialogfragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.pinnet.chargerapp.R;
import com.pinnet.chargerapp.base.BaseDialogFragment;

/**
 * @author P00558
 * @date 2018/4/17
 * 自定义的Loading 加载
 */

public class CustomLoadingDialogFragment extends BaseDialogFragment implements View.OnClickListener {
    private static final String TAG = "CustomLoadingDialogFragment";

    public static CustomLoadingDialogFragment newInstance(Context mContext, FragmentManager fm) {
        Fragment fragment = fm.findFragmentByTag(TAG);
        if (fragment == null) {
            CustomLoadingDialogFragment dialogFragment = new CustomLoadingDialogFragment();
            return dialogFragment;
        } else {
            return (CustomLoadingDialogFragment) fragment;
        }
    }

    private String loadingTip;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.CityPickerStyle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.custom_dialog_loading, container, false);
        ImageView ivLoading = (ImageView) view.findViewById(R.id.iv_loading);
        AnimationDrawable animationDrawable = (AnimationDrawable) ivLoading.getDrawable();
        animationDrawable.start();
        TextView tvLoadingTip = (TextView) view.findViewById(R.id.tv_loading_tip);
        if (!TextUtils.isEmpty(loadingTip)) {
            tvLoadingTip.setText(loadingTip);
        }
        return view;
    }

    @Override
    public void onClick(View view) {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        if (window != null) {
            window.getDecorView().setPadding(0, 0, 0, 0);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.getAttributes().dimAmount = 0f;
            window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        }
        return dialog;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void show(FragmentManager fragmentManager, boolean isResumed) {
        super.show(fragmentManager, TAG, isResumed);
    }

    public void show(FragmentManager fragmentManager, String loadingTip, boolean isResumed) {
        this.loadingTip = loadingTip;
        super.show(fragmentManager, TAG, isResumed);
    }

}
