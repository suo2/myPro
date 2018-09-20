package com.pinnet.chargerapp.ui.common.dialogfragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pinnet.chargerapp.R;
import com.pinnet.chargerapp.base.BaseDialogFragment;
import com.pinnet.chargerapp.mvp.model.beans.charger.ChargerPileInfoBean;
import com.pinnet.chargerapp.ui.charger.adapter.ChargerPriceRateAdapter;

/**
 * @author P00558
 * @date 2018/6/27
 * 充电桩参数说明
 */

public class ChargerArgumentsDialogFragment extends BaseDialogFragment {
    private Context mContext;
    private TextView tvSerialNumber;
    private TextView tvChargerStatus;
    private TextView tvChargerType;
    /**
     * 额定功率
     */
    private TextView tvPowerRating;
    /**
     * 额定电压
     */
    private TextView tvRatedVoltage;
    private ChargerPriceRateAdapter mPriceRateAdapter;
    private RecyclerView rlvPriceRate;
    private ChargerPileInfoBean chargerPileInfoBean;
    /**
     * 枪口个数
     */
    private TextView tvChargerMuzzle;
    private LinearLayout llPriceLayout;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            dialog.getWindow().setLayout((int) (dm.widthPixels * 0.75), ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View view = LayoutInflater.from(mContext).inflate(R.layout.charger_method_arguments_popupwindow, null);
        tvChargerStatus = (TextView) view.findViewById(R.id.tv_charger_status);
        tvChargerType = (TextView) view.findViewById(R.id.tv_charger_type);
        tvPowerRating = (TextView) view.findViewById(R.id.tv_power_rating);
        tvRatedVoltage = (TextView) view.findViewById(R.id.tv_rated_oltage);
        tvChargerMuzzle = (TextView) view.findViewById(R.id.tv_charger_muzzle);
        tvSerialNumber = (TextView) view.findViewById(R.id.tv_serialnumber);
        rlvPriceRate = (RecyclerView) view.findViewById(R.id.rlv_rate);
        llPriceLayout = (LinearLayout) view.findViewById(R.id.ll_price);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rlvPriceRate.setLayoutManager(layoutManager);
        mPriceRateAdapter = new ChargerPriceRateAdapter(mContext);
        rlvPriceRate.setAdapter(mPriceRateAdapter);
        ChargerPileInfoBean.PileParameter pileParameter = chargerPileInfoBean.getPileParameter();
        if (pileParameter != null) {
            tvSerialNumber.setText(pileParameter.getSerialNumber());
            switch (pileParameter.getDeviceType()) {
                case 1:
                    tvChargerType.setText("直流快充");
                    break;
                case 2:
                    tvChargerType.setText("直流慢充");
                    break;
                case 3:
                    tvChargerType.setText("交流快充");
                    break;
                case 4:
                    tvChargerType.setText("交流慢充");
                    break;
                case 5:
                    tvChargerType.setText("交直流混合");
                    break;
                default:
                    tvChargerType.setText("");
                    break;
            }
            if (pileParameter.getIsExpire() > 0) {
                tvChargerStatus.setText("空闲");
            } else {
                tvChargerStatus.setText("非空闲");
            }
            tvChargerMuzzle.setText(pileParameter.getGunsNum() + "个");
            tvRatedVoltage.setText(pileParameter.getRatedVoltage() + "v");
            tvPowerRating.setText(pileParameter.getPowerRating() + "w");
        }
        mPriceRateAdapter.setData(chargerPileInfoBean.getFeeRate());
        if (chargerPileInfoBean.getFeeRate() == null || chargerPileInfoBean.getFeeRate().size() == 0) {
            llPriceLayout.setVisibility(View.GONE);
        }
        builder.setView(view);
        Dialog dialog = builder.create();
        return dialog;
    }

    public void showData(FragmentManager manager, String tag, ChargerPileInfoBean chargerPileInfoBean) {
        this.chargerPileInfoBean = chargerPileInfoBean;
        show(manager, tag);
    }

}
