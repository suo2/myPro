package com.pinnet.chargerapp.ui.charger.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pinnet.chargerapp.R;
import com.pinnet.chargerapp.mvp.model.beans.charger.ChargerRateBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author P00558
 * @date 2018/7/3
 */

public class ChargerPriceRateAdapter extends RecyclerView.Adapter<ChargerPriceRateAdapter.RateHolder> {
    private List<ChargerRateBean> mRateBeanList;
    private Context mContext;

    public ChargerPriceRateAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<ChargerRateBean> rateBeanList) {
        if (mRateBeanList == null) {
            mRateBeanList = new ArrayList<>();
        }
        mRateBeanList.clear();
        mRateBeanList.addAll(rateBeanList);
        notifyDataSetChanged();
    }

    @Override
    public RateHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RateHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.charger_price_rate_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RateHolder holder, int position) {
        ChargerRateBean rateBean = mRateBeanList.get(position);
        holder.tvTimeZone.setText(position+1+"、"+rateBean.getStartTime() + "～～" + rateBean.getEndTime());
        holder.tvPrice.setText(String.valueOf(rateBean.getPrice()) + mContext.getResources().getString(R.string.main_unit_charge_cost));
        holder.tvServicePrice.setText(String.valueOf(rateBean.getService()) + mContext.getResources().getString(R.string.main_unit_charge_cost));
    }

    @Override
    public int getItemCount() {
        return mRateBeanList == null ? 0 : mRateBeanList.size();
    }

    public class RateHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_time_zone)
        TextView tvTimeZone;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_service_price)
        TextView tvServicePrice;

        public RateHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
