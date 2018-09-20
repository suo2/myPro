package com.pinnet.chargerapp.ui.common.dialogfragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.pinnet.chargerapp.R;
import com.pinnet.chargerapp.base.BaseDialogFragment;

/**
 * @author P00558
 * @date 2018/4/17
 * 自定义的DialogFrangment
 */

public class CustomDialogFragment extends BaseDialogFragment implements View.OnClickListener {
    private TextView tvTitle;
    private TextView tvContent;
    private Button tvConfirm;
    private Button tvCancle;
    private OnConfirmClick onConfirmClick;
    private OnCancelClick onCancelClick;
    private OnDismissListener onDismissListener;
    private String mDialogTitle = "";
    private String mDialogContent = "";
    private String mConfirmName = "确定";
    private int cancleVisiable = View.GONE;

    public static CustomDialogFragment newInstance() {
        CustomDialogFragment fragment = new CustomDialogFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.CityPickerStyle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.custom_dialog_toast, container, false);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        tvConfirm = (Button) view.findViewById(R.id.btn_confirm);
        tvContent = (TextView) view.findViewById(R.id.tv_content);
        tvCancle = (Button) view.findViewById(R.id.tv_cancle);
        tvCancle.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);
        tvConfirm.setText(mConfirmName);
        if (TextUtils.isEmpty(mDialogTitle)){
            tvTitle.setVisibility(View.GONE);
        }else{
            tvTitle.setText(mDialogTitle);
        }

        tvContent.setText(mDialogContent);
        tvCancle.setVisibility(cancleVisiable);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                if (onConfirmClick != null) {
                    onConfirmClick.onConfirm();
                }
                dismiss();
                break;
            case R.id.tv_cancle:
                if (onCancelClick != null) {
                    onCancelClick.onCancel();
                }
                dismiss();
                break;
            default:
                break;
        }

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        Window window = dialog.getWindow();
        if (window != null) {
            window.getDecorView().setPadding(0, 0, 0, 0);
            window.setBackgroundDrawableResource(android.R.color.transparent);
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        }
        return dialog;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onDismissListener != null) {
            onDismissListener.onDismiss();
        }
    }

    public void showCancle() {
        cancleVisiable = View.VISIBLE;
    }

    public void setConfirmName(String name) {
        mConfirmName = name;
    }

    public void setConfirmName(int resId) {
        mConfirmName = getContext().getString(resId);
    }

    public void setTitle(String title) {
        mDialogTitle = title;
    }

    public void setTitle(int resId) {
        mDialogTitle = getContext().getString(resId);
    }

    public void setContent(String content) {
        mDialogContent = content;
    }

    public void setContent(int resId) {
        mDialogContent = getContext().getString(resId);
    }

    public void setOnConfirmClick(OnConfirmClick onConfirmClick) {
        this.onConfirmClick = onConfirmClick;
    }

    public void setOnCancelClick(OnCancelClick onCancelClick) {
        this.onCancelClick = onCancelClick;
    }

    public void setOnDismissListener(OnDismissListener dismissListener) {
        this.onDismissListener = dismissListener;
    }

    public interface OnConfirmClick {
        void onConfirm();
    }

    public interface OnCancelClick {
        void onCancel();
    }

    public interface OnDismissListener {
        void onDismiss();
    }

}
