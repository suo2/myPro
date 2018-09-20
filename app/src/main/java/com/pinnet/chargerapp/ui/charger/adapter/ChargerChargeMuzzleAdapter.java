package com.pinnet.chargerapp.ui.charger.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pinnet.chargerapp.R;
import com.pinnet.chargerapp.minterface.IRecycleClickListener;
import com.pinnet.chargerapp.mvp.model.beans.charger.ChargerMuzzleBean;
import com.pinnet.chargerapp.mvp.model.beans.charger.ChargerRateBean;
import com.pinnet.chargerapp.mvp.model.beans.charger.ChargerStatusBean;
import com.pinnet.chargerapp.ui.common.popupwindow.ChargerRatePopupWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author P00558
 * @date 2018/4/20
 */

public class ChargerChargeMuzzleAdapter extends RecyclerView.Adapter<ChargerChargeMuzzleAdapter.ViewHolder> {
    private List<ChargerMuzzleBean> mDataLists;
    private List<ChargerRateBean> mChargerRatelist;
    private IRecycleClickListener<ChargerMuzzleBean> mIRecycleClickListenerListener;
    private ChargerRatePopupWindow mPopupWindow;
    private double price;
    private double servicePrice;
    private int type;
    private Context mContext;

    public ChargerChargeMuzzleAdapter(Context context) {
        this.mContext = context;
        mPopupWindow = new ChargerRatePopupWindow(context);
    }

    public void setData(ChargerStatusBean chargerStatusBean) {
        if (mDataLists == null) {
            mDataLists = new ArrayList<>();
        }
        this.mChargerRatelist = chargerStatusBean.getRateList();
        this.price = chargerStatusBean.getPrice();
        this.servicePrice = chargerStatusBean.getService();
        this.type = chargerStatusBean.getType();
        mDataLists.clear();
        mDataLists.addAll(chargerStatusBean.getList());
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.charger_charge_muzzle_recycle_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ChargerMuzzleBean bean = mDataLists.get(position);
        holder.tvNumber.setText(bean.getGunNumber());
        holder.tvPrice.setText(String.valueOf(price) + mContext.getResources().getString(R.string.main_unit_charge_cost));
        holder.tvServicePrice.setText(String.valueOf(servicePrice) + mContext.getResources().getString(R.string.main_unit_charge_cost));
        holder.tvInterface.setText("国标2015");
        switch (type) {
            case 1:
                holder.tvType.setText(R.string.charger_station_detail_fast_charge);
                break;
            case 2:
                holder.tvType.setText(R.string.charger_station_detail_slow_charge);
                break;
            default:
                holder.tvType.setText(R.string.main_invate_string);
                break;
        }
        if (1==bean.getManagerStatus()){
            holder.tvTypeMark.setVisibility(View.VISIBLE);
        }else{
            holder.tvTypeMark.setVisibility(View.GONE);
        }
        switch (bean.getChargeStatus()) {
            case 1:
                holder.tvTypeMark.setText("空闲中");
                holder.tvTypeMark.setBackgroundResource(R.drawable.charger_muzzle_status_free_bg);
                holder.tvTypeMark.setTextColor(ContextCompat.getColor(mContext, R.color.color_ff9934));
                break;
            case 2:
            case 3:
            case 4:
            case 5:
                holder.tvTypeMark.setText("充电中");
                holder.tvTypeMark.setBackgroundResource(R.drawable.charger_muzzle_status_charging_bg);
                holder.tvTypeMark.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_44daaa));
                break;
            case 6:
            case 7:
                holder.tvTypeMark.setText("预约中");
                holder.tvTypeMark.setBackgroundResource(R.drawable.charger_muzzle_status_fix_bg);
                holder.tvTypeMark.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_ff5253));
                break;
            default:
                holder.tvTypeMark.setText(R.string.main_invate_string);
                holder.tvTypeMark.setBackgroundResource(R.drawable.charger_muzzle_status_free_bg);
                holder.tvTypeMark.setTextColor(ContextCompat.getColor(mContext, R.color.color_ff9934));
                break;
        }
        if (mChargerRatelist == null || mChargerRatelist.size() == 0) {
            holder.ivTips.setVisibility(View.GONE);
        } else {
            holder.ivTips.setVisibility(View.VISIBLE);
        }
        holder.ivTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.show(v, Gravity.BOTTOM, -100, 20, mChargerRatelist);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataLists == null ? 0 : mDataLists.size();
    }

    public void setOnRecyclerClickListener(IRecycleClickListener<ChargerMuzzleBean> mRecycleClickListener) {
        this.mIRecycleClickListenerListener = mRecycleClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_number)
        TextView tvNumber;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_service_price)
        TextView tvServicePrice;
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.tv_type_mark)
        TextView tvTypeMark;
        @BindView(R.id.tv_interface)
        TextView tvInterface;
        @BindView(R.id.iv_tips)
        ImageView ivTips;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
