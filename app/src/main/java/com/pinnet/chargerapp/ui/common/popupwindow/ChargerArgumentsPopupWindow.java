package com.pinnet.chargerapp.ui.common.popupwindow;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.pinnet.chargerapp.R;

/**
 * @author P00558
 * @date 2018/6/27
 */

public class ChargerArgumentsPopupWindow extends PopupWindow {
    private View mContentView;
    private Context mContext;

    public ChargerArgumentsPopupWindow(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContentView = inflater.inflate(R.layout.charger_method_arguments_popupwindow, null);
        this.setContentView(mContentView);
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setBackgroundDrawable(new BitmapDrawable());
        this.setOutsideTouchable(true);
        this.setFocusable(true);
    }

}
