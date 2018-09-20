package com.pinnet.chargerapp.utils;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.pinnet.chargerapp.app.ChargerApp;
import com.pinnet.chargerapp.R;

/**
 * @author P00558
 * @date 2018/4/17
 */

public class ToastUtils {
    private volatile static Toast mToast;
    private volatile static ToastUtils instance;

    /**
     * 双重锁定，使用同一个Toast实例
     */
    public static ToastUtils getInstance() {
        if (instance == null) {
            synchronized (ToastUtils.class) {
                if (instance == null) {
                    instance = new ToastUtils();
                }
            }
        }
        return instance;
    }

    public ToastUtils() {
        super();
        if (mToast == null) {
            if (mToast == null) {
                mToast = new Toast(ChargerApp.getInstance());
            }
        }
    }

    public void showMessage(int resId) {
        showMessage(ChargerApp.getInstance().getString(resId));
    }

    public void showMessage(String message) {
        View customView = LayoutInflater.from(ChargerApp.getInstance()).inflate(R.layout.custom_widget_toast, null);
        TextView tv = (TextView) customView;
        tv.setText(message);
        mToast.setView(customView);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.setGravity(Gravity.BOTTOM, 0, 100);
        mToast.show();
    }
}
