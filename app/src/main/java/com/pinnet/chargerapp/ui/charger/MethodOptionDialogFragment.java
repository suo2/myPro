package com.pinnet.chargerapp.ui.charger;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pinnet.chargerapp.R;
import com.pinnet.chargerapp.mvp.model.beans.charger.ChargerMathodOption;
import com.pinnet.chargerapp.utils.ScreenUtils;
import com.pinnet.chargerapp.utils.SystemUtil;
import com.pinnet.chargerapp.widget.CustomTimerPicker;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author P00558
 * @date 2018/4/24
 * 充电方式-配置参数
 */

public class MethodOptionDialogFragment extends AppCompatDialogFragment implements CompoundButton.OnCheckedChangeListener {
    private Unbinder unbinder;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rg_options)
    RadioGroup rgOptions;
    @BindView(R.id.timeer_picker)
    CustomTimerPicker timePicker;
    @BindView(R.id.et_money_input)
    EditText etInput;
    @BindView(R.id.rl_input)
    RelativeLayout rlInut;
    @BindView(R.id.tv_unit)
    TextView tvUnit;
    List<ChargerMathodOption> radioButtonNameList = new ArrayList<>();
    private Context mContext;
    private int currentClickPosition = 0;
    private int currentMethod;
    private String currentUnit;

    public static MethodOptionDialogFragment newInstance() {
        MethodOptionDialogFragment fragment = new MethodOptionDialogFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.CityPickerStyle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.charger_dialog_method_option, container, false);
        unbinder = ButterKnife.bind(this, view);
        rgOptions.removeAllViews();
        if (currentMethod == ChargerMethodActivity.OPTION_BATTERY) {
            tvTitle.setText("请选择充电电量");
            tvUnit.setText(R.string.charger_method_option_unit_battery);
        } else if (currentMethod == ChargerMethodActivity.OPTION_MONEY) {
            tvUnit.setText(R.string.charger_method_option_unit_money);
            tvTitle.setText("请选择充电金额");
        } else if (currentMethod == ChargerMethodActivity.OPTION_TIME) {
            tvTitle.setText("请选择充电时间");
        }
        addview(rgOptions);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick({R.id.btn_confirm})
    void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                if (mConfirmListener != null) {
                    if (currentClickPosition == radioButtonNameList.size() - 1) {
                        if (currentMethod == ChargerMethodActivity.OPTION_TIME) {
                            radioButtonNameList.get(currentClickPosition).setShowName(
                                    "已选择充电时间：" + String.valueOf(timePicker.getHour() + "H" + timePicker.getMinute() + "m"));
                            radioButtonNameList.get(currentClickPosition).setValue(
                                    String.valueOf(timePicker.getCurrentTimes()));
                        } else {
                            radioButtonNameList.get(currentClickPosition).setValue(etInput.getText().toString());
                            if (currentMethod == ChargerMethodActivity.OPTION_BATTERY) {
                                radioButtonNameList.get(currentClickPosition).setShowName("已选择充电电量：" + etInput.getText().toString() + tvUnit.getText());
                            } else if (currentMethod == ChargerMethodActivity.OPTION_MONEY) {
                                radioButtonNameList.get(currentClickPosition).setShowName("已选择充电金额：" + etInput.getText().toString() + tvUnit.getText());
                            }
                        }

                    }
                    mConfirmListener.onConfirm(currentClickPosition, radioButtonNameList.get(currentClickPosition));
                    dismiss();
                }
                break;
            default:
                break;
        }
    }

    //动态添加视图
    public void addview(RadioGroup radiogroup) {
        radiogroup.removeAllViews();
        int size = radioButtonNameList.size();
        for (int i = 0; i < size; i++) {
            String name = radioButtonNameList.get(i).getTitle();
            RadioButton button = new RadioButton(mContext);
            if (i == 0) {
                button.setChecked(true);
            }
            setRaidBtnAttribute(button, name, i);
            radiogroup.addView(button);
            if (i < size - 1) {
                View divider = new View(mContext);
                divider.setBackgroundColor(ContextCompat.getColor(mContext, R.color.login_divider));
                LinearLayout.LayoutParams dividerLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, SystemUtil.dip2px(mContext, 1f));
                divider.setLayoutParams(dividerLp);
                radiogroup.addView(divider);
            }

        }
    }


    private void setRaidBtnAttribute(final RadioButton codeBtn, String btnContent, final int id) {
        if (null == codeBtn) {
            return;
        }
        codeBtn.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(mContext, R.drawable.charger_pay_checkbox_bg), null, null, null);
        codeBtn.setButtonDrawable(android.R.color.transparent);//隐藏单选圆形按钮
        codeBtn.setId(id);
        codeBtn.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_333333));
        codeBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        codeBtn.setCompoundDrawablePadding(ScreenUtils.dip2px(mContext, mContext.getResources().getDimension(R.dimen.main_padding_2x)));
        codeBtn.setText(btnContent);
        codeBtn.setGravity(Gravity.CENTER_VERTICAL);
        codeBtn.setPadding(0, (int)mContext.getResources().getDimension(R.dimen.main_padding_1x), 0,
                (int)mContext.getResources().getDimension(R.dimen.main_padding_1x));
        codeBtn.setOnCheckedChangeListener(this);
        LinearLayout.LayoutParams rlp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        codeBtn.setLayoutParams(rlp);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        Window window = dialog.getWindow();
        if (window != null) {
            window.getDecorView().setPadding(0, 0, 0, 0);
            window.setBackgroundDrawableResource(android.R.color.transparent);
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        }
        return dialog;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void addRadioButtonName(List<ChargerMathodOption> nameList, int currentMethod) {
        radioButtonNameList.clear();
        radioButtonNameList.addAll(nameList);
        ChargerMathodOption option = new ChargerMathodOption("其他", "", "");
        radioButtonNameList.add(option);
        this.currentMethod = currentMethod;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (!isChecked) {
            return;
        }
        currentClickPosition = buttonView.getId();
        if (currentClickPosition == radioButtonNameList.size() - 1) {
            if (currentMethod == ChargerMethodActivity.OPTION_TIME) {
                timePicker.setVisibility(View.VISIBLE);
            } else {
                rlInut.setVisibility(View.VISIBLE);
            }

        } else {
            if (currentMethod == ChargerMethodActivity.OPTION_TIME) {
                timePicker.setVisibility(View.GONE);
            } else {
                rlInut.setVisibility(View.GONE);
            }
            hideInputMethod(mContext,buttonView);
        }
    }

    public static interface OnOptionsConfirmListener {
        void onConfirm(int position, ChargerMathodOption option);
    }

    public OnOptionsConfirmListener mConfirmListener;

    public void setOnOptionsConfirmListener(OnOptionsConfirmListener confirmListener) {
        this.mConfirmListener = confirmListener;
    }

    public static Boolean hideInputMethod(Context context, View v) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            return imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
        return false;
    }
}
