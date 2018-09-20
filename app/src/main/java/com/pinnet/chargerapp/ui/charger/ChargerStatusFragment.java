package com.pinnet.chargerapp.ui.charger;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pinnet.chargerapp.R;
import com.pinnet.chargerapp.app.Constants;
import com.pinnet.chargerapp.base.BaseFragment;
import com.pinnet.chargerapp.minterface.IRecycleClickListener;
import com.pinnet.chargerapp.mvp.contract.charger.ChargeStatusContract;
import com.pinnet.chargerapp.mvp.model.beans.charger.ChargerStatusBean;
import com.pinnet.chargerapp.mvp.model.beans.charger.StationChargerStatusBean;
import com.pinnet.chargerapp.mvp.presenter.charger.ChargeStatusPresenter;
import com.pinnet.chargerapp.ui.charger.adapter.ChargerChargeAdapter;
import com.pinnet.chargerapp.ui.charger.adapter.ChargerChargeMuzzleAdapter;
import com.pinnet.chargerapp.widget.GrayItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author P00558
 * @date 2018/4/10
 * 电桩状态
 */

public class ChargerStatusFragment extends BaseFragment<ChargeStatusPresenter> implements ChargeStatusContract.View {
    @BindView(R.id.rlv_charger_list)
    RecyclerView rlvChargerList;
    @BindView(R.id.rlv_charger_muzzle_list)
    RecyclerView rlvChargerMuzzleList;
    @BindView(R.id.tv_charger_describe)
    TextView tvChargerDescribe;
    @BindView(R.id.ll_charger_empty)
    LinearLayout llChargerEmpty;
    @BindView(R.id.ll_muzzle_empty)
    LinearLayout llMuzzleEmpty;
    private ChargerChargeAdapter mChargeAdapter;
    private ChargerChargeMuzzleAdapter mChargeMuzzleAdapter;
    private String sId;

    public static ChargerStatusFragment newInstance(Bundle args) {
        ChargerStatusFragment fragment = new ChargerStatusFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.charger_station_status_fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView();
        getArgumentsData();
    }

    private void getArgumentsData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            sId = bundle.getString(Constants.CHAGER_SID);
        }
    }

    private void initRecyclerView() {
        LinearLayoutManager chargerManager = new LinearLayoutManager(mContext);
        chargerManager.setOrientation(LinearLayoutManager.VERTICAL);
        mChargeAdapter = new ChargerChargeAdapter(mContext);
        mChargeAdapter.setOnRecyclerClickListener(new IRecycleClickListener<ChargerStatusBean>() {
            @Override
            public void onItemClick(ChargerStatusBean chargerStatusBean) {
                updateMuzzleData(chargerStatusBean);
            }
        });
        rlvChargerList.setLayoutManager(chargerManager);
        rlvChargerList.addItemDecoration(new GrayItemDecoration(mContext, LinearLayoutManager.VERTICAL,
                getResources().getDrawable(R.drawable.mine_charge_record_recycle_divider)));
        rlvChargerList.setAdapter(mChargeAdapter);

        LinearLayoutManager chargerMuzzleManager = new LinearLayoutManager(mContext);
        chargerManager.setOrientation(LinearLayoutManager.VERTICAL);
        mChargeMuzzleAdapter = new ChargerChargeMuzzleAdapter(mContext);
        rlvChargerMuzzleList.setLayoutManager(chargerMuzzleManager);
        rlvChargerMuzzleList.setAdapter(mChargeMuzzleAdapter);

    }

    private void updateMuzzleData(ChargerStatusBean chargerStatusBean) {
        tvChargerDescribe.setText(Html.fromHtml(String.format(getString(R.string.charger_status_descibe),
                chargerStatusBean.getSerialNumber(), String.valueOf(chargerStatusBean.getCount()), String.valueOf(chargerStatusBean.getExpire()))));
        if (chargerStatusBean.getList() == null || chargerStatusBean.getList().size() == 0) {
            llMuzzleEmpty.setVisibility(View.VISIBLE);
            rlvChargerMuzzleList.setVisibility(View.GONE);
        } else {
            llMuzzleEmpty.setVisibility(View.GONE);
            rlvChargerMuzzleList.setVisibility(View.VISIBLE);
            mChargeMuzzleAdapter.setData(chargerStatusBean);
        }
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        prepareFetchData(true);
    }

    @Override
    public void requestData() {
        super.requestData();
        mPresenter.onRequestChargeList(sId);
    }

    @Override
    public void onRequestChargeListResult(List<ChargerStatusBean> statusBeanList) {
        if (statusBeanList == null || statusBeanList.size() == 0) {
            llChargerEmpty.setVisibility(View.VISIBLE);
            rlvChargerList.setVisibility(View.GONE);
        } else {
            llChargerEmpty.setVisibility(View.GONE);
            rlvChargerList.setVisibility(View.VISIBLE);
            statusBeanList.get(0).setChecked(true);
            updateMuzzleData(statusBeanList.get(0));
            mChargeAdapter.setData(statusBeanList);
        }
    }

    @Override
    public void onRequestMuzzleStatusResult() {
    }
}
