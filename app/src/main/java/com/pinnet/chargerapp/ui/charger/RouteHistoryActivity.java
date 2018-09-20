package com.pinnet.chargerapp.ui.charger;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pinnet.chargerapp.R;
import com.pinnet.chargerapp.base.BaseActivity;
import com.pinnet.chargerapp.mvp.contract.charger.RoutePlanContract;
import com.pinnet.chargerapp.mvp.model.beans.charger.RouteHistoryBean;
import com.pinnet.chargerapp.mvp.presenter.charger.RoutePlanPresenter;
import com.pinnet.chargerapp.widget.GrayItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author P00558
 * @date 2018/4/28
 */

public class RouteHistoryActivity extends BaseActivity<RoutePlanPresenter> implements RoutePlanContract.View {
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.view_main)
    RecyclerView rlvHistory;
    HistoryAdapter historyAdapter;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.charger_route_history_activity;
    }

    @Override
    protected void onViewCreated(@Nullable Bundle savedInstanceState) {
        super.onViewCreated(savedInstanceState);
        historyAdapter = new HistoryAdapter();
        tvHeaderTitle.setText(R.string.charger_route_plan_route_history);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rlvHistory.setLayoutManager(layoutManager);
        rlvHistory.addItemDecoration(new GrayItemDecoration(this, LinearLayoutManager.VERTICAL,
                getResources().getDrawable(R.drawable.mine_charge_record_recycle_divider)));
        rlvHistory.setAdapter(historyAdapter);
        mPresenter.onQueryRouteHistory();
    }

    @OnClick({R.id.iv_back})
    void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finishAct();
                break;
            default:
                break;
        }
    }

    @Override
    public void onQueryRouteHistoryResult(List<RouteHistoryBean> beanList) {
        historyAdapter.setData(beanList);
    }

    public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
        List<RouteHistoryBean> mBeanList;

        public void setData(List<RouteHistoryBean> beanList) {
            if (mBeanList == null) {
                mBeanList = new ArrayList<>();
            }
            mBeanList.clear();
            mBeanList.addAll(beanList);
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.charger_route_history_recycler_item, null, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            RouteHistoryBean bean = mBeanList.get(position);
            holder.tvEndPointName.setText(bean.getEndPointName());
            holder.tvStartPointName.setText(bean.getStartPointName());
        }

        @Override
        public int getItemCount() {
            return mBeanList == null ? 0 : mBeanList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvStartPointName;
            TextView tvEndPointName;

            public ViewHolder(View itemView) {
                super(itemView);
                tvStartPointName = (TextView) itemView.findViewById(R.id.tv_start_point);
                tvEndPointName = (TextView) itemView.findViewById(R.id.tv_end_point);
            }
        }
    }
}
