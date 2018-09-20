package com.pinnet.chargerapp.ui.main;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pinnet.chargerapp.R;
import com.pinnet.chargerapp.mvp.model.beans.main.DrawerScreenlingBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author P00558
 * @date 2018/5/3
 */

public class DrawerScreeningAdapter extends RecyclerView.Adapter<DrawerScreeningAdapter.ViewHolder> {
    private Context mContext;
    private List<DrawerScreenlingBean> beanList;

    public DrawerScreeningAdapter(Context context, List<DrawerScreenlingBean> beanList) {
        this.mContext = context;
        this.beanList = beanList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.main_drawer_screening_recycler_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DrawerScreenlingBean bean = beanList.get(position);
        holder.tvItemName.setText(bean.getItemName());
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 2);
        holder.rlvItem.setLayoutManager(layoutManager);
        DrawerScreeningItemAdapter itemAdapter = new DrawerScreeningItemAdapter(mContext, bean.getItemList());
        holder.rlvItem.setAdapter(itemAdapter);
    }

    @Override
    public int getItemCount() {
        return beanList == null ? 0 : beanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemName;
        RecyclerView rlvItem;

        public ViewHolder(View itemView) {
            super(itemView);
            tvItemName = (TextView) itemView.findViewById(R.id.tv_item_name);
            rlvItem = (RecyclerView) itemView.findViewById(R.id.rlv_screening_item);
        }
    }

    public Map<String, String> getSeleteItem() {
        Map<String, String> selectMap = new HashMap<>();
        for (int i = 0; i < beanList.size(); i++) {
            DrawerScreenlingBean bean = beanList.get(i);
            if (bean != null && bean.getItemList() != null) {
                String itemId = bean.getItemId();
                for (DrawerScreenlingBean.ScreenlingItemBean itemBean : bean.getItemList()) {
                    if (itemBean.isSelected()) {
                        selectMap.put(itemId, itemBean.getItemId());
                        break;
                    }
                }
            }

        }
        return selectMap;
    }

    public void reset() {
        for (DrawerScreenlingBean bean : beanList) {
            for (DrawerScreenlingBean.ScreenlingItemBean itemBean : bean.getItemList()) {
                itemBean.setSelected(false);
            }
        }
        notifyDataSetChanged();
    }
}
