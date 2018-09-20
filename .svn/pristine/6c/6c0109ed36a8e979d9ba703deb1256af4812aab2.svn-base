package com.pinnet.chargerapp.ui.charger;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.pinnet.chargerapp.R;
import com.pinnet.chargerapp.app.Constants;
import com.pinnet.chargerapp.utils.Utils;


/**
 * @author P00558
 * @date 2018/4/9
 * 充电桩导航选择
 */

public class ChargerNavSelectDialogFragment extends AppCompatDialogFragment implements View.OnClickListener {
    public static ChargerNavSelectDialogFragment newInstance() {
        ChargerNavSelectDialogFragment fragment = new ChargerNavSelectDialogFragment();
        return fragment;
    }

    private Context mContext;
    Button btnAmapNav;
    Button btnAPPNav;
    Button btnCancle;
    Button btnBaiduMapNav;
    private double latitude;
    private double longitude;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.CityPickerStyle);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.charger_dialog_nav_select, container, false);
        btnCancle = (Button) view.findViewById(R.id.btn_cancle);
        btnAmapNav = (Button) view.findViewById(R.id.btn_amap_nav);
        btnAPPNav = (Button) view.findViewById(R.id.btn_app_nav);
        btnBaiduMapNav = (Button) view.findViewById(R.id.btn_baidumap_nav);
        if (Utils.isPkgInstalled(Constants.AMAP_PACKGE_NAME, mContext)) {
            btnAmapNav.setVisibility(View.VISIBLE);
        }
        if (Utils.isPkgInstalled(Constants.BAIDUMAP_PACKGE_NAME, mContext)) {
            btnBaiduMapNav.setVisibility(View.VISIBLE);
        }
        btnAPPNav.setOnClickListener(this);
        btnAmapNav.setOnClickListener(this);
        btnBaiduMapNav.setOnClickListener(this);
        btnCancle.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancle:
                dismiss();
                break;
            case R.id.btn_amap_nav:
                Utils.toAmapNavigation(mContext, latitude, longitude);
                dismiss();
                break;
            case R.id.btn_baidumap_nav:
                Utils.toBaiduMapNavigation(mContext, latitude, longitude);
                dismiss();
                break;
            case R.id.btn_app_nav:
                Intent intent = new Intent(mContext, RouteNaviActivity.class);
                intent.putExtra(Constants.AMAP_LATITUDE, latitude);
                intent.putExtra(Constants.AMAP_LONGTITUDE, longitude);
                mContext.startActivity(intent);
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
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
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
    }

    public void setEndLatLng(double lat, double lng) {
        longitude = lng;
        latitude = lat;
    }
}
