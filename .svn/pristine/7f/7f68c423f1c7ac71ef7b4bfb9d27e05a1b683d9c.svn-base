package com.pinnet.chargerapp.ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pinnet.chargerapp.R;
import com.pinnet.chargerapp.app.Constants;
import com.pinnet.chargerapp.base.BaseActivity;
import com.pinnet.chargerapp.minterface.IRecycleClickListener;
import com.pinnet.chargerapp.mvp.contract.mine.ChargingRecordContract;
import com.pinnet.chargerapp.mvp.model.beans.mine.ChargerRecordBean;
import com.pinnet.chargerapp.mvp.model.beans.mine.ChargerRecordFilterBean;
import com.pinnet.chargerapp.mvp.model.http.requestbody.ChargerRequestBody;
import com.pinnet.chargerapp.mvp.presenter.mine.ChargingRecordPresenter;
import com.pinnet.chargerapp.ui.charger.ChargerOrderDetailActivity;
import com.pinnet.chargerapp.utils.SharePrefUtils;
import com.pinnet.chargerapp.widget.GrayItemDecoration;
import com.pinnet.chargerapp.widget.ILoadMoreScrollListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author P00558
 * @date 2018/4/9
 * 充电记录
 */

public class ChargingRecordActivity extends BaseActivity<ChargingRecordPresenter> implements ChargingRecordContract.View {
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.view_main)
    SwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.rlv_charging_record)
    RecyclerView mRecyclerRecord;
    @BindView(R.id.iv_right_menu)
    ImageView ivRightMenu;
    @BindView(R.id.rlv_filter)
    RecyclerView rlvFilter;
    @BindView(R.id.drawerlayout)
    DrawerLayout drawerLayout;
    @Inject
    ChargeRecordAdapter chargeRecordAdapter;
    private ChargerRecordFilterAdapter mFilterAdapter;
    private List<ChargerRecordFilterBean> mFilterBeanList = new ArrayList<>();
    private int pageNum = 1;
    private int pageSize = 20;
    private List<ChargerRecordBean> mRecordBeanList = new ArrayList<>();
    private boolean isRefreshing;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.mine_charging_record_filter_activity;
    }

    @Override
    protected void onInitView() {
        super.onInitView();
        tvHeaderTitle.setText(R.string.mine_charging_record);
        ivRightMenu.setImageResource(R.drawable.mine_icon_charging_record_filter);
        ivRightMenu.setVisibility(View.VISIBLE);
        initRecyclerView();
        initSwipeRefresh();
        mRecyclerRecord.postDelayed(new Runnable() {
            @Override
            public void run() {
                stateLoading();
                mPresenter.getChargerRecords();
            }
        }, Constants.ANIMATION_DUREATION);


    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerRecord.setLayoutManager(layoutManager);
        mRecyclerRecord.setAdapter(chargeRecordAdapter);
        mRecyclerRecord.addItemDecoration(new GrayItemDecoration(this, LinearLayoutManager.VERTICAL,
                getResources().getDrawable(R.drawable.mine_charge_record_recycle_divider)));
        chargeRecordAdapter.setOnIRecyclerClickListener(new IRecycleClickListener<ChargerRecordBean>() {
            @Override
            public void onItemClick(ChargerRecordBean bean) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.CHARGER_RECORD_BEAN, bean);
                bundle.putInt(Constants.ORDER_DETAIL_TYPE, Constants.ORDER_DETAIL_TYPE_LOOK);
                startActivity(new Intent(mContext, ChargerOrderDetailActivity.class)
                        .putExtras(bundle));
            }
        });
        mRecyclerRecord.addOnScrollListener(new ILoadMoreScrollListener(layoutManager) {
            @Override
            public void onLoadMore() {
                if (!isRefreshing && mRecordBeanList.size() >= pageSize) {
                    isRefreshing = true;
                    pageNum++;
                    showLoading();
                    mPresenter.getChargerRecords();
                }

            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rlvFilter.setLayoutManager(gridLayoutManager);
        mFilterBeanList.add(new ChargerRecordFilterBean("1", "所有记录", true));
        mFilterBeanList.add(new ChargerRecordFilterBean("2", "最近一周"));
        mFilterBeanList.add(new ChargerRecordFilterBean("3", "最近一个月"));
        mFilterBeanList.add(new ChargerRecordFilterBean("4", "最近六个月"));
        mFilterBeanList.add(new ChargerRecordFilterBean("5", "最近一年"));
        mFilterAdapter = new ChargerRecordFilterAdapter(this, mFilterBeanList);
        rlvFilter.setAdapter(mFilterAdapter);

    }

    private void initSwipeRefresh() {
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefreshing = false;
                pageNum = 1;
                mRecordBeanList.clear();
                mPresenter.getChargerRecords();
            }
        });
        mSwipeRefresh.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

    }

    @OnClick({R.id.iv_back, R.id.iv_right_menu, R.id.btn_confirm})
    void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finishAct();
                break;
            case R.id.iv_right_menu:
                onOpenDrawer();
                break;
            case R.id.btn_confirm:
                onOpenDrawer();
                onRefresh();
                break;
            default:
                break;
        }
    }

    /**
     * 打开或关闭 抽屉
     */
    public void onOpenDrawer() {
        if (drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
            drawerLayout.closeDrawer(Gravity.RIGHT);
        } else {
            drawerLayout.openDrawer(Gravity.RIGHT);
        }
    }

    @Override
    public void onGetChargerRecordsResult(List<ChargerRecordBean> beanList) {
        if (mSwipeRefresh.isRefreshing()) {
            mSwipeRefresh.setRefreshing(false);
        }
        isRefreshing = false;
        mRecordBeanList.addAll(beanList);
        chargeRecordAdapter.setData(mRecordBeanList,mContext);
    }

    @Override
    public ChargerRequestBody onGetRequestBody() {
        ChargerRequestBody body = new ChargerRequestBody();
        body.userId = SharePrefUtils.getInstance().getLoginedUserId();
        body.pageNum = String.valueOf(pageNum);
        body.pageSize = String.valueOf(pageSize);
        body.type = getFilter();
        return body;
    }

    @Override
    public void onChangeRefreshState() {
        isRefreshing = false;
    }

    private String getFilter() {
        for (ChargerRecordFilterBean bean : mFilterBeanList) {
            if (bean != null && bean.isChecked()) {
                return bean.getFilterId();
            }
        }
        return "1";
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        isRefreshing = false;
        if (pageNum != 1) {
            showLoading();
        } else {
            stateLoading();
        }
        pageNum = 1;
        mRecordBeanList.clear();
        mPresenter.getChargerRecords();
    }

    @Override
    public void dismissLoading() {
        super.dismissLoading();
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss(isResumed);
        }
    }

    @Override
    public void showLoading() {
        super.showLoading();
        mLoadingDialog.show(getSupportFragmentManager(), "加载中", isResumed);
    }
}