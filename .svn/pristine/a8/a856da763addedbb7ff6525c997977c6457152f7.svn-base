package com.pinnet.chargerapp.ui.mine;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.pinnet.chargerapp.R;
import com.pinnet.chargerapp.minterface.IRecycleClickListener;
import com.pinnet.chargerapp.mvp.model.beans.mine.ChargerRecordBean;
import com.pinnet.chargerapp.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author P00558
 * @date 2018/4/11
 */

public class ChargeRecordAdapter extends RecyclerView.Adapter<ChargeRecordAdapter.ViewHolder> {
    private IRecycleClickListener mClickListener;
    private List<ChargerRecordBean> mBeanList;
    private Context mContext;
    public void setData(List<ChargerRecordBean> beanList,Context context) {
        if (mBeanList == null) {
            mBeanList = new ArrayList<>();
        }
        mBeanList.clear();
        mBeanList.addAll(beanList);
        notifyDataSetChanged();
        mContext=context;
    }

    @Inject
    public ChargeRecordAdapter() {
        super();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mine_charge_record_recycle_item, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ChargerRecordBean bean = mBeanList.get(position);
        holder.tvChargeName.setText(bean.getChargeName());
        holder.tvChargeTime.setText(TimeUtils.getInstance().getDateYYYYMMDDHHMMSS2(bean.getChargeTime()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mClickListener != null) {
                    mClickListener.onItemClick(bean);
                }
            }
        });
        switch (bean.getOrderStatus()) {
            case 1:
                holder.tvOrderStatus.setText("已支付");
                holder.tvOrderStatus.setBackgroundResource(R.drawable.charger_muzzle_status_charging_bg);
                holder.tvOrderStatus.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_44daaa));
                break;
            case 2:
                holder.tvOrderStatus.setText("未支付");
                holder.tvOrderStatus.setBackgroundResource(R.drawable.charger_muzzle_status_fix_bg);
                holder.tvOrderStatus.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_ff5253));
                break;
            default:
                holder.tvOrderStatus.setText("异常");
                holder.tvOrderStatus.setBackgroundResource(R.drawable.charger_muzzle_status_free_bg);
                holder.tvOrderStatus.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_ffa415));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mBeanList == null ? 0 : mBeanList.size();
    }

    public void setOnIRecyclerClickListener(IRecycleClickListener<ChargerRecordBean> listener) {
        this.mClickListener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_charge_name)
        TextView tvChargeName;
        @BindView(R.id.tv_charge_time)
        TextView tvChargeTime;
        @BindView(R.id.tv_order_status)
        TextView tvOrderStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
