package com.pinnet.chargerapp.ui.charger.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pinnet.chargerapp.R;
import com.pinnet.chargerapp.app.ChargerApp;
import com.pinnet.chargerapp.minterface.IRecycleClickListener;
import com.pinnet.chargerapp.mvp.model.beans.charger.ChargerStatusBean;
import com.pinnet.chargerapp.mvp.model.beans.charger.StationChargerStatusBean;
import com.pinnet.chargerapp.ui.common.popupwindow.ChargerRatePopupWindow;
import com.pinnet.chargerapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author P00558
 * @date 2018/4/20
 * 电桩状态
 */

public class ChargerChargeAdapter extends RecyclerView.Adapter<ChargerChargeAdapter.ViewHolder> {
    private List<ChargerStatusBean> mDataLists;
    private IRecycleClickListener<ChargerStatusBean> mIRecycleClickListenerListener;
private Context mContext;
    public ChargerChargeAdapter(Context context) {
        super();
this.mContext=context;
    }

    public void setData(List<ChargerStatusBean> beans) {
        if (mDataLists == null) {
            mDataLists = new ArrayList<>();
        }
        mDataLists.clear();
        mDataLists.addAll(beans);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.charger_charge_recycle_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ChargerStatusBean bean = mDataLists.get(position);
        holder.tvChargerName.setText(bean.getSerialNumber());
        if (bean.isChecked()) {
            holder.itemView.setBackgroundResource(R.drawable.charger_charger_list_item_select_bg);
            holder.tvChargerName.setTextColor(ContextCompat.getColor(ChargerApp.getInstance(), R.color.text_color_ffa415));
        } else {
            holder.itemView.setBackgroundResource(R.color.text_color_ffffff);
            holder.tvChargerName.setTextColor(ContextCompat.getColor(ChargerApp.getInstance(), R.color.text_color_333333));
        }
        switch (bean.getManagerStatus()) {
            case 1:
                holder.tvManagerStatus.setText("正常");
                holder.tvManagerStatus.setBackgroundResource(R.drawable.charger_muzzle_status_free_bg);
                holder.tvManagerStatus.setTextColor(ContextCompat.getColor(mContext, R.color.color_ff9934));
                break;
            case 2:
                holder.tvManagerStatus.setText("故障");
                holder.tvManagerStatus.setBackgroundResource(R.drawable.charger_muzzle_status_fix_bg);
                holder.tvManagerStatus.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_ff5253));
                break;
            case 3:
                holder.tvManagerStatus.setText("离网");
                holder.tvManagerStatus.setBackgroundResource(R.drawable.charger_muzzle_status_charging_bg);
                holder.tvManagerStatus.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_44daaa));
                break;
            case 4:
                holder.tvManagerStatus.setText("数据上传");
                holder.tvManagerStatus.setBackgroundResource(R.drawable.charger_muzzle_status_charging_bg);
                holder.tvManagerStatus.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_44daaa));
                break;
            case 5:
                holder.tvManagerStatus.setText("维护");
                holder.tvManagerStatus.setBackgroundResource(R.drawable.charger_muzzle_status_charging_bg);
                holder.tvManagerStatus.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_44daaa));
                break;
            default:
                break;
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemViewClick(position);
                if (mIRecycleClickListenerListener != null) {
                    mIRecycleClickListenerListener.onItemClick(bean);
                }
                notifyDataSetChanged();
            }
        });
    }

    private void itemViewClick(int position) {
        for (ChargerStatusBean bean : mDataLists) {
            bean.setChecked(false);
        }
        mDataLists.get(position).setChecked(true);
    }

    public void setOnRecyclerClickListener(IRecycleClickListener<ChargerStatusBean> mRecycleClickListener) {
        this.mIRecycleClickListenerListener = mRecycleClickListener;
    }

    @Override
    public int getItemCount() {
        return mDataLists == null ? 0 : mDataLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_charge_name)
        TextView tvChargerName;
        @BindView(R.id.tv_manager_status)
        TextView tvManagerStatus;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
