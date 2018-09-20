package com.pinnet.chargerapp.ui.charger.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pinnet.chargerapp.R;
import com.pinnet.chargerapp.minterface.IRecycleClickListener;
import com.pinnet.chargerapp.mvp.model.beans.charger.ChargerStationBean;
import com.pinnet.chargerapp.mvp.model.beans.charger.StationInfoBean;
import com.pinnet.chargerapp.utils.DataUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author P00558
 * @date 2018/5/28
 */

public class StationListAdapter extends RecyclerView.Adapter<StationListAdapter.ViewHolder> implements View.OnClickListener {
    private Context mContext;
    private List<ChargerStationBean> mStationBeanList;
    private OnItemClickListener mListener;

    public StationListAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<ChargerStationBean> stationBeanList) {
        if (mStationBeanList == null) {
            mStationBeanList = new ArrayList<>();
        }
        mStationBeanList.clear();
        mStationBeanList.addAll(stationBeanList);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.charger_station_list_recycler_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ChargerStationBean stationBean = mStationBeanList.get(position);
        StationInfoBean stationInfoBean = stationBean.getStationInfo();
        holder.tvStationName.setText(stationBean.getStationName());
        if (stationBean.isFast()) {
            holder.tvChargerFast.setVisibility(View.VISIBLE);
        } else {
            holder.tvChargerFast.setVisibility(View.GONE);
        }
        if (stationBean.isSlow()) {
            holder.tvChargerSlow.setVisibility(View.VISIBLE);
        } else {
            holder.tvChargerSlow.setVisibility(View.GONE);
        }
        holder.tvTotalCount.setText(stationBean.getTotalCount() + "个充电桩");
        holder.tvFreeCount.setText(stationBean.getFreeCount() + "个空闲");
        if ("0".equals(stationInfoBean.getOperationType())) {
            holder.tvOpreatorFlag.setText("运营商");
        } else if ("1".equals(stationInfoBean.getOperationType())) {
            holder.tvOpreatorFlag.setText("第三方");
        } else {
            holder.tvOpreatorFlag.setText(R.string.main_invate_string);
        }
        holder.tvStationAddress.setText(stationInfoBean.getStationAddr());
        String[] distance = DataUtils.getInstance().handleDistance(stationInfoBean.getDistance());
        holder.tvDistance.setText(distance[0] + distance[1]);
        holder.itemView.setOnClickListener(this);
        holder.ivNavigation.setOnClickListener(this);
        holder.ivNavigation.setTag(stationBean);
        holder.itemView.setTag(stationBean);
    }

    @Override
    public int getItemCount() {
        return mStationBeanList == null ? 0 : mStationBeanList.size();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_view:
                if (mListener != null) {
                    mListener.onItemClick((ChargerStationBean) v.getTag());
                }
                break;
            case R.id.iv_navigation:
                if (mListener != null) {
                    mListener.onNavigation((ChargerStationBean) v.getTag());
                }
                break;
            default:
                break;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(ChargerStationBean stationBean);

        void onNavigation(ChargerStationBean chargerStationBean);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_station_image)
        ImageView ivStationImage;
        @BindView(R.id.tv_station_name)
        TextView tvStationName;
        @BindView(R.id.tv_opreator_flag)
        TextView tvOpreatorFlag;
        @BindView(R.id.tv_station_address)
        TextView tvStationAddress;
        @BindView(R.id.tv_distance)
        TextView tvDistance;
        @BindView(R.id.iv_navigation)
        ImageView ivNavigation;
        @BindView(R.id.tv_charger_fast)
        TextView tvChargerFast;
        @BindView(R.id.tv_charger_slow)
        TextView tvChargerSlow;
        @BindView(R.id.tv_charger_total_count)
        TextView tvTotalCount;
        @BindView(R.id.tv_charger_free_count)
        TextView tvFreeCount;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
