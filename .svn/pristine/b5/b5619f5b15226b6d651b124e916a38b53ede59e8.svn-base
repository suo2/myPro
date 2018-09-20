package com.pinnet.chargerapp.ui.common.popupwindow;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.pinnet.chargerapp.R;
import com.pinnet.chargerapp.mvp.model.beans.charger.ChargerRateBean;
import com.pinnet.chargerapp.ui.charger.adapter.ChargerPriceRateAdapter;

import java.util.List;

/**
 * @author P00558
 * @date 2018/7/3
 */

public class ChargerRatePopupWindow extends PopupWindow {
    private RecyclerView mRlvRate;
    private ChargerPriceRateAdapter priceRateAdapter;

    public ChargerRatePopupWindow(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mContentView = inflater.inflate(R.layout.charger_price_rate_popupwindow, null);
        mRlvRate = (RecyclerView) mContentView.findViewById(R.id.rlv_rate);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRlvRate.setLayoutManager(layoutManager);
        priceRateAdapter = new ChargerPriceRateAdapter(context);
        mRlvRate.setAdapter(priceRateAdapter);
        this.setContentView(mContentView);
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setBackgroundDrawable(new BitmapDrawable());
        this.setOutsideTouchable(true);
        this.setFocusable(true);
    }

    public void show(View parent, int gravity, int x, int y, List<ChargerRateBean> rateBeanList) {
        if (priceRateAdapter != null) {
            priceRateAdapter.setData(rateBeanList);
        }
        showAsDropDown(parent,x,y);
    }
}
