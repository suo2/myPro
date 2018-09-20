package com.pinnet.chargerapp.ui.charger;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.transition.Fade;
import android.transition.Visibility;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pinnet.chargerapp.app.Constants;
import com.pinnet.chargerapp.base.BaseActivity;
import com.pinnet.chargerapp.base.BaseCommonActivity;
import com.pinnet.chargerapp.R;
import com.pinnet.chargerapp.mvp.contract.charger.OrderDetailContract;
import com.pinnet.chargerapp.mvp.model.beans.charger.OrderDetailBean;
import com.pinnet.chargerapp.mvp.model.beans.mine.ChargerRecordBean;
import com.pinnet.chargerapp.mvp.presenter.charger.OrderDetialPresenter;
import com.pinnet.chargerapp.utils.DataUtils;
import com.pinnet.chargerapp.utils.TimeUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author P00558
 * @date 2018/4/16
 * 充电订单详情
 */

public class ChargerOrderDetailActivity extends BaseActivity<OrderDetialPresenter> implements OrderDetailContract.View {
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    /**
     * 充电时间
     */
    @BindView(R.id.tv_charger_date)
    TextView tvChargerDate;
    @BindView(R.id.tv_station_name)
    TextView tvStationName;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_service_price)
    TextView tvServicePrice;
    @BindView(R.id.tv_operators)
    TextView tvOpreators;
    @BindView(R.id.tv_charger_use_time)
    TextView tvChargeUsedTime;
    @BindView(R.id.tv_total_amount)
    TextView tvTotalAmount;
    @BindView(R.id.tv_charger_power)
    TextView tvChargerPower;
    @BindView(R.id.btn_confirm_pay)
    Button btnConfirmPay;
    @BindView(R.id.btn_continue)
    Button btnContinue;
    /**
     * 进入订单详情页的方式
     */
    private int orderDetailType = Constants.ORDER_DETAIL_TYPE_PAY;
    private ChargerRecordBean chargerRecordBean;
    private OrderDetailBean orderDetailBean;
    /**
     * 避免结束充电后，立即查看订单导致没数据问题，做个延时
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mPresenter.onRequestOrderDetail();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.charger_order_detail_activity;
    }

    @Override
    protected void onInitView() {
        super.onInitView();
        getIntentData();
        tvHeaderTitle.setText(R.string.charger_order_detail);
        if (orderDetailType == Constants.ORDER_DETAIL_TYPE_LOOK) {
            if (chargerRecordBean != null && chargerRecordBean.getOrderStatus() == 2) {
                btnConfirmPay.setVisibility(View.VISIBLE);
            } else {
                btnConfirmPay.setVisibility(View.GONE);
            }
            mPresenter.onRequestOrderDetail();
        } else if (orderDetailType == Constants.ORDER_DETAIL_TYPE_PAY) {
            stateLoading();
            tvHeaderTitle.postDelayed(runnable, 5 * 1000);
        } else if (orderDetailType == Constants.ORDER_DETAIL_TYPE_UNPAY) {
            btnConfirmPay.setVisibility(View.VISIBLE);
            mPresenter.onRequestOrderDetail();
        }


    }

    @Override
    protected void onBeforeSetContentView() {
        super.onBeforeSetContentView();
    }

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        orderDetailType = intent.getIntExtra(Constants.ORDER_DETAIL_TYPE, Constants.ORDER_DETAIL_TYPE_PAY);
        if (orderDetailType == Constants.ORDER_DETAIL_TYPE_LOOK) {
            chargerRecordBean = (ChargerRecordBean) intent.getSerializableExtra(Constants.CHARGER_RECORD_BEAN);
        }
    }

    @OnClick({R.id.iv_back, R.id.btn_confirm_pay, R.id.btn_continue})
    void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finishAct();
                break;
            case R.id.btn_continue:
                finishAct();
                break;
            case R.id.btn_confirm_pay:
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.ORDER_DETAIL_BEAN, orderDetailBean);
                startAct(new Intent(this, ChargerOrderPayActivity.class).putExtras(bundle));
                break;
            default:
                break;
        }
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.onRequestOrderDetail();
    }

    @Override
    public void onRequestOrderDetailResult(OrderDetailBean detailBean) {
        orderDetailBean = detailBean;
        tvStationName.setText(detailBean.getName());
        tvPrice.setText(String.valueOf(detailBean.getPrice()) +getString(R.string.main_unit_yuan_rmb));
        tvServicePrice.setText(String.valueOf(detailBean.getServiceFee()) + getString(R.string.main_unit_yuan_rmb));
        tvTotalAmount.setText(String.valueOf(detailBean.getTotalAmount()) + getString(R.string.main_unit_yuan_rmb));
        tvChargerPower.setText(detailBean.getElectric() + getString(R.string.main_unit_kwh));
        tvChargerDate.setText(TimeUtils.getInstance().getDateYYYYMMDD3(detailBean.getChargeTime()));
        tvChargeUsedTime.setText(detailBean.getUseTime());
        tvOpreators.setText(detailBean.getStationOwner());
    }

    @Override
    public int getOrderDetailType() {
        return orderDetailType;
    }

    @Override
    public String getOrderId() {
        return chargerRecordBean == null ? "" : chargerRecordBean.getOrderId();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tvHeaderTitle.removeCallbacks(runnable);
    }
}
