package com.pinnet.chargerapp.ui.main;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pinnet.chargerapp.R;
import com.pinnet.chargerapp.mvp.model.beans.main.DrawerScreenlingBean;

import java.util.List;

/**
 * @author P00558
 * @date 2018/5/3
 */

public class DrawerScreeningItemAdapter extends RecyclerView.Adapter<DrawerScreeningItemAdapter.ViewHolder> {
    private List<DrawerScreenlingBean.ScreenlingItemBean> screenlingItemBeenList;
    private Context mContext;

    public DrawerScreeningItemAdapter(Context context, List<DrawerScreenlingBean.ScreenlingItemBean> screenlingItemBeenList) {
        super();
        this.mContext = context;
        this.screenlingItemBeenList = screenlingItemBeenList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.main_drawer_screening_childe_recycler_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final DrawerScreenlingBean.ScreenlingItemBean bean = screenlingItemBeenList.get(position);
        final boolean isSelected = bean.isSelected();
        holder.tvItemName.setText(bean.getItemName());
        if (bean.isSelected()) {
            holder.tvItemName.setBackgroundResource(R.drawable.drawer_screening_item_bg);
            holder.tvItemName.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_ff9933));
        } else {
            holder.tvItemName.setBackgroundResource(R.color.white);
            holder.tvItemName.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_666666));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (DrawerScreenlingBean.ScreenlingItemBean itemBean : screenlingItemBeenList) {
                    itemBean.setSelected(false);
                }
                bean.setSelected(!isSelected);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return screenlingItemBeenList == null ? 0 : screenlingItemBeenList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemName;

        public ViewHolder(View itemView) {
            super(itemView);
            tvItemName = (TextView) itemView.findViewById(R.id.tv_item_name);
        }
    }
}
