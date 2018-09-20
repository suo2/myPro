package com.pinnet.chargerapp.ui.charger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.pinnet.chargerapp.R;
import com.pinnet.chargerapp.app.Constants;
import com.pinnet.chargerapp.base.BaseCommonActivity;
import com.pinnet.chargerapp.mvp.model.beans.charger.RouteHistoryBean;
import com.pinnet.chargerapp.widget.ClearEditText;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author P00558
 * @date 2018/5/9
 * 路径规划-历史路径-选择路径
 */

public class RouteHistorySetAddress extends BaseCommonActivity implements Inputtips.InputtipsListener, AMapLocationListener,TextWatcher {
    @BindView(R.id.cet_search)
    ClearEditText cetSearch;
    @BindView(R.id.view_main)
    RecyclerView addressList;
    AddressAdapter addressAdapter;
    List<Tip> addressTipLists;
    AMapLocationClient aMapLocationClient;
    private int locationTimes = 5;
    private RouteHistoryBean currentRouteBean = new RouteHistoryBean();
    private int mAddressPlanType = 0;
    InputtipsQuery inputquery;
    Inputtips inputTips;

    @Override
    protected int getLayoutId() {
        return R.layout.charger_route_history_set_address_activity;
    }

    @Override
    protected void onInitView() {
        super.onInitView();
        onLocation();
        if (getIntent() != null) {
            mAddressPlanType = getIntent().getIntExtra(Constants.CHARGER_ROUTE_PLAN_TYPE, 0);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        addressList.setLayoutManager(layoutManager);
        addressAdapter = new AddressAdapter();
        addressList.setAdapter(addressAdapter);
        cetSearch.addTextChangedListener(this);
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
    public void onGetInputtips(List<Tip> list, int i) {
        addressTipLists = list;
        addressAdapter.setAdapter(list);
    }

    /**
     * 定位
     */
    private void onLocation() {
        //限制定位次数，避免一直定位
        if (locationTimes == 0) {
            if (aMapLocationClient.isStarted()) {
                aMapLocationClient.stopLocation();
            }
            return;
        }
        locationTimes--;
        if (aMapLocationClient == null) {
            aMapLocationClient = new AMapLocationClient(mContext);
            AMapLocationClientOption clientOption = new AMapLocationClientOption();
            clientOption.setOnceLocation(true);
            // 地址信息
            clientOption.setNeedAddress(true);
            clientOption.setLocationCacheEnable(false);
            //设置定位模式为Hight_Accuracy高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
            clientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            aMapLocationClient.setLocationOption(clientOption);
            aMapLocationClient.setLocationListener(this);
        }
        if (aMapLocationClient.isStarted()) {
            aMapLocationClient.stopLocation();
        }
        aMapLocationClient.startLocation();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                currentRouteBean.setStartPointName(aMapLocation.getPoiName());
                currentRouteBean.setStartPointLat(aMapLocation.getLatitude());
                currentRouteBean.setStartPointLng(aMapLocation.getLongitude());
                aMapLocationClient.stopLocation();
            } else {
                onLocation();
            }
        } else {
            onLocation();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (TextUtils.isEmpty(editable) || cetSearch.getText().equals(editable)) {
            return;
        }
        //第二个参数传入null或者“”代表在全国进行检索，否则按照传入的city进行检索
        inputquery = new InputtipsQuery(editable.toString(), null);
        inputquery.setCityLimit(false);//限制在当前城市
        if (inputTips == null) {
            inputTips = new Inputtips(RouteHistorySetAddress.this, inputquery);
            inputTips.setInputtipsListener(RouteHistorySetAddress.this);
        } else {
            inputTips.setQuery(inputquery);
        }
        inputTips.requestInputtipsAsyn();
    }


    public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {
        List<Tip> list;

        public void setAdapter(List<Tip> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.charger_route_plan_item_address_popup, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            final Tip tip = list.get(position);
            holder.tvAddressName.setText(tip.getName());
            holder.tvAddress.setText(tip.getAddress());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putInt(Constants.CHARGER_ROUTE_PLAN_TYPE, mAddressPlanType);
                    bundle.putParcelable(Constants.CHARGER_ROUTE_PLAN_ADDRESS_TIP, tip);
                    intent.putExtras(bundle);
                    setResult(Constants.CHARGER_ROUTE_RESULT_CODE, intent);
                    finishAct();
                }
            });
        }

        @Override
        public int getItemCount() {
            return list == null ? 0 : list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.tv_address_name)
            TextView tvAddressName;
            @BindView(R.id.tv_address)
            TextView tvAddress;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cetSearch.removeTextChangedListener(this);
        addressTipLists = null;
        aMapLocationClient = null;
        inputTips = null;
        inputquery = null;
        addressAdapter = null;
        addressList = null;
    }
}
