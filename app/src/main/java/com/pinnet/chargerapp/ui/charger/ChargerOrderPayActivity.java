package com.pinnet.chargerapp.ui.charger;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.pinnet.chargerapp.R;
import com.pinnet.chargerapp.app.Constants;
import com.pinnet.chargerapp.base.BaseActivity;
import com.pinnet.chargerapp.mvp.contract.charger.OrderPayContract;
import com.pinnet.chargerapp.mvp.model.beans.charger.ChargerPayMentBean;
import com.pinnet.chargerapp.mvp.model.beans.charger.OrderDetailBean;
import com.pinnet.chargerapp.mvp.presenter.charger.OrderPayPresenter;
import com.pinnet.chargerapp.ui.charger.pay.PayResult;
import com.pinnet.chargerapp.wxapi.WXPayEntryActivity;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * @author P00558
 * @date 2018/4/16
 * 订单支付
 */

public class ChargerOrderPayActivity extends BaseActivity<OrderPayPresenter> implements OrderPayContract.View {
    private static final String TAG = "ChargerOrderPayActivity";
    private int payCode = Constants.PAY_WEIXIN_CODE;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.cb_alipay_pay)
    CheckBox cbAlipay;
    @BindView(R.id.cb_weixin_pay)
    CheckBox cbWeixinPay;
    @BindView(R.id.tv_initiate_money)
    TextView tvInitiateMonty;
    @BindView(R.id.tv_pay_money)
    TextView tvPayMoney;
    OrderDetailBean orderDetailBean;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.charger_order_pay_activity;
    }

    @Override
    protected void onInitView() {
        super.onInitView();
        tvHeaderTitle.setText(R.string.charger_pay_order_title);
        getIntentData();
    }

    private void getIntentData() {
        if (getIntent().getExtras() != null) {
            orderDetailBean = (OrderDetailBean) getIntent().getSerializableExtra(Constants.ORDER_DETAIL_BEAN);
            tvInitiateMonty.setText(orderDetailBean.getTotalAmount() + getString(R.string.main_unit_rmb));
            tvPayMoney.setText(orderDetailBean.getTotalAmount() + getString(R.string.main_unit_rmb));
        }
    }

    @OnClick({R.id.iv_back, R.id.rl_alipay_pay, R.id.rl_weixin_pay, R.id.tv_confirm_pay})
    void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finishAct();
                break;
            case R.id.rl_alipay_pay:
                cbAlipay.setChecked(true);
                break;
            case R.id.rl_weixin_pay:
                cbWeixinPay.setChecked(true);
                break;
            case R.id.tv_confirm_pay:
                mPresenter.onPayMent();
                break;
            default:
                break;
        }
    }

    @OnCheckedChanged({R.id.cb_weixin_pay, R.id.cb_alipay_pay})
    public void onCheckedChangeListener(CompoundButton view, boolean isChecked) {
        switch (view.getId()) {
            case R.id.cb_alipay_pay:
                if (isChecked) {
                    payCode = Constants.PAY_ALI_CODE;
                    cbWeixinPay.setChecked(false);
                }
                break;
            case R.id.cb_weixin_pay:
                if (isChecked) {
                    payCode = Constants.PAY_WEIXIN_CODE;
                    cbAlipay.setChecked(false);
                }
                break;
            default:
                break;
        }

    }

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(ChargerOrderPayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(ChargerOrderPayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    /**
     * 支付宝支付业务
     */
    public void onAliPay(final ChargerPayMentBean bean) {
//        Runnable payRunnable = new Runnable() {
//
//            @Override
//            public void run() {
//                PayTask alipay = new PayTask(ChargerOrderPayActivity.this);
//                Map<String, String> result = alipay.payV2(bean.getAppAskStr(), true);
//                Log.i("msp", result.toString());
//
//                Message msg = new Message();
//                msg.what = SDK_PAY_FLAG;
//                msg.obj = result;
//                mHandler.sendMessage(msg);
//            }
//        };
//
//        Thread payThread = new Thread(payRunnable);
//        payThread.start();
    }

    /**
     * 微信支付
     *
     * @param bean
     */
    private void onWeixinPay(ChargerPayMentBean bean) {
        Constants.APP_ID = bean.getAppid();
        IWXAPI msgApi = WXAPIFactory.createWXAPI(mContext, bean.getAppid(), false);
        // 将该app注册到微信
        msgApi.registerApp(bean.getAppid());
        PayReq request = new PayReq();
        request.appId = bean.getAppid();
        request.partnerId = bean.getPartnerid();
        request.prepayId = bean.getPrepayid();
        request.packageValue = "Sign=WXPay";
        request.nonceStr = bean.getNoncestr();
        request.timeStamp = bean.getTimestamp();
        request.sign = bean.getSign();
        msgApi.sendReq(request);
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public String getPayType() {
        if (payCode == Constants.PAY_WEIXIN_CODE) {
            return String.valueOf(2);
        } else {
            return String.valueOf(1);
        }
    }

    @Override
    public void onPaymentResult(final ChargerPayMentBean bean) {
        if (bean != null && bean.isPaid()) {
            startAct(new Intent(this, WXPayEntryActivity.class).putExtra("payState", true));
            return;
        }
        if (payCode == Constants.PAY_WEIXIN_CODE) {
            onWeixinPay(bean);
        } else {
            onAliPay(bean);
        }
    }

    @Override
    public void showLoading() {
        super.showLoading();
        mLoadingDialog.show(getSupportFragmentManager(), isResumed);
    }

    @Override
    public void dismissLoading() {
        super.dismissLoading();
        mLoadingDialog.dismiss(isResumed);
    }

}
