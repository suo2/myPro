package com.pinnet.chargerapp.ui.mine;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pinnet.chargerapp.R;
import com.pinnet.chargerapp.mvp.model.beans.mine.ChargerRecordFilterBean;

import java.util.List;

/**
 * @author P00558
 * @date 2018/5/3
 */

public class ChargerRecordFilterAdapter extends RecyclerView.Adapter<ChargerRecordFilterAdapter.ViewHolder> {
    private List<ChargerRecordFilterBean> filterBeen;
    private Context mContext;

    public ChargerRecordFilterAdapter(Context context, List<ChargerRecordFilterBean> screenlingItemBeenList) {
        super();
        this.mContext = context;
        this.filterBeen = screenlingItemBeenList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.mine_charging_record_filter_recycler_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ChargerRecordFilterBean bean = filterBeen.get(position);
        final boolean isSelected = bean.isChecked();
        holder.tvItemName.setText(bean.getFilterName());
        if (bean.isChecked()) {
            holder.tvItemName.setBackgroundResource(R.drawable.drawer_screening_item_bg);
            holder.tvItemName.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_ff9933));
        } else {
            holder.tvItemName.setBackgroundResource(R.color.white);
            holder.tvItemName.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_666666));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (ChargerRecordFilterBean itemBean : filterBeen) {
                    itemBean.setChecked(false);
                }
                bean.setChecked(!isSelected);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return filterBeen == null ? 0 : filterBeen.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemName;

        public ViewHolder(View itemView) {
            super(itemView);
            tvItemName = (TextView) itemView.findViewById(R.id.tv_item_name);
        }
    }
}
