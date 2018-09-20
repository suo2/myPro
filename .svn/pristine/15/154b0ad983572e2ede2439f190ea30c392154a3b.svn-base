package com.pinnet.chargerapp.ui.charger;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.pinnet.chargerapp.R;
import com.pinnet.chargerapp.mvp.model.beans.charger.ChargerMathodOption;
import com.pinnet.chargerapp.ui.common.dialogfragment.TimePickerDialogFragment;
import com.pinnet.chargerapp.utils.SystemUtil;
import com.pinnet.chargerapp.utils.ToastUtils;
import com.pinnet.chargerapp.widget.DividerGridItemDecoration;
import com.pinnet.chargerapp.widget.KeyboardPatch;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.pinnet.chargerapp.ui.charger.ChargerMethodActivity.OPTION_BATTERY;
import static com.pinnet.chargerapp.ui.charger.ChargerMethodActivity.OPTION_MONEY;
import static com.pinnet.chargerapp.ui.charger.ChargerMethodActivity.OPTION_TIME;

/**
 * @author P00558
 * @date 2018/6/15
 * 充电条件选择界面
 */

public class MethodOptionDialogFragmentNew extends AppCompatDialogFragment {
    private Unbinder unbinder;
    @BindView(R.id.tv_option_title)
    TextView tvTitle;
    @BindView(R.id.tv_option_select_content)
    TextView tvSelectContent;
    @BindView(R.id.rlv_options)
    RecyclerView rlvOptions;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    private Context mContext;
    private Activity mActivity;
    private OptionsAdapter mOptionsAdapter;
    private KeyboardPatch mKeyBoardPatch;
    private List<ChargerMathodOption> optionsList = new ArrayList<>();
    private int currentType;
    private NumberInputFileter mNumberInputFileter = new NumberInputFileter();
    GridLayoutManager layoutManager;

    public static MethodOptionDialogFragmentNew newInstance() {
        MethodOptionDialogFragmentNew fragment = new MethodOptionDialogFragmentNew();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        this.mActivity = (Activity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(true);
        setStyle(STYLE_NO_TITLE, R.style.CityPickerStyle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.charger_dialog_method_option_new, container, false);
        unbinder = ButterKnife.bind(this, view);
        optionsList.add(new ChargerMathodOption(getString(R.string.charger_method_option_other), "", ""));
        mOptionsAdapter = new OptionsAdapter();
        layoutManager = new GridLayoutManager(mContext, 3);
        rlvOptions.setLayoutManager(layoutManager);
        rlvOptions.addItemDecoration(new DividerGridItemDecoration(mContext, R.drawable.charger_method_recycler_devider));
        rlvOptions.setAdapter(mOptionsAdapter);
        mKeyBoardPatch = new KeyboardPatch(mActivity, mActivity.getWindow().getDecorView());
        mKeyBoardPatch.enable();
        ChargerMathodOption methodOption = optionsList.get(0);
        for (ChargerMathodOption option : optionsList) {
            if (option.isChecked()) {
                methodOption = option;
            }
        }
        if (currentType == OPTION_MONEY) {
            tvTitle.setText(String.format(getString(R.string.charger_method_option_title), getString(R.string.charger_method_option_money)));
            tvSelectContent.setText(String.format(getString(R.string.charger_method_option_content),
                    getString(R.string.charger_method_option_money), methodOption.getTitle()));
        } else if (currentType == OPTION_BATTERY) {
            tvTitle.setText(String.format(getString(R.string.charger_method_option_title), getString(R.string.charger_method_option_power)));
            tvSelectContent.setText(String.format(getString(R.string.charger_method_option_content),
                    getString(R.string.charger_method_option_power), methodOption.getTitle()));
        } else if (currentType == OPTION_TIME) {
            tvTitle.setText(String.format(getString(R.string.charger_method_option_title), getString(R.string.charger_method_option_time)));
            tvSelectContent.setText(String.format(getString(R.string.charger_method_option_content),
                    getString(R.string.charger_method_option_time), methodOption.getTitle()));
        }
        return view;
    }

    public void setOptionDatas(List<ChargerMathodOption> optionsList, int type) {
        this.optionsList.clear();
        if (optionsList != null) {
            int length = optionsList.size();
            for (int i = 0; i < length; i++) {
                this.optionsList.add((ChargerMathodOption) optionsList.get(i).clone());
            }
        }

        currentType = type;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.gravity = Gravity.BOTTOM; //底部
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            //window.setDimAmount(0.6f);
            window.setAttributes(lp);
        }
        return dialog;
    }

    @OnClick({R.id.btn_confirm, R.id.btn_cancel})
    void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                if ("确定修改".equals(btnConfirm.getText().toString())) {
                    ChargerMathodOption options = optionsList.get(optionsList.size() - 1);
                    OptionHolder holder = (OptionHolder) rlvOptions.getChildViewHolder(layoutManager.getChildAt(layoutManager.getItemCount() - 1));
                    String inputValue = holder.etOptions.getText().toString();
                    if (TextUtils.isEmpty(inputValue) || 0 == Integer.parseInt(inputValue)) {
                        inputValue = "0";
                    }
                    options.setValue(inputValue);
                    if (currentType == OPTION_MONEY) {
                        options.setTitle(inputValue + getString(R.string.main_unit_rmb));
                        tvSelectContent.setText(String.format(getString(R.string.charger_method_option_content),
                                getString(R.string.charger_method_option_money), options.getTitle()));
                    } else if (currentType == OPTION_BATTERY) {
                        options.setTitle(inputValue + getString(R.string.main_unit_kwh));
                        tvSelectContent.setText(String.format(getString(R.string.charger_method_option_content),
                                getString(R.string.charger_method_option_power), options.getTitle()));
                    }
                    options.setShowName(tvSelectContent.getText().toString());
                    mOptionsAdapter.changeChecked();
                    options.setChecked(true);
                    mOptionsAdapter.notifyDataSetChanged();
                } else {
                    if (mConfirmListener != null) {
                        ChargerMathodOption option = getCheckOptions();
                        if (option != null) {
                            if (TextUtils.isEmpty(option.getValue()) || 0 == Integer.parseInt(option.getValue())) {
                                ToastUtils.getInstance().showMessage(getString(R.string.charger_method_option_useless_value));
                                return;
                            }
                            mConfirmListener.onOptionConfirm(option);
                        }
                    }
                    dismiss();
                }
                SystemUtil.hideKeyboard(view);
                break;
            case R.id.btn_cancel:
                dismiss();
                break;
            default:
                break;
        }
    }

    public class OptionsAdapter extends RecyclerView.Adapter<OptionHolder> {

        @Override
        public OptionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new OptionHolder(LayoutInflater.from(mContext).inflate(R.layout.charger_dialog_method_option_item, null, false));
        }

        @Override
        public void onBindViewHolder(final OptionHolder holder, final int position) {
            final ChargerMathodOption option = optionsList.get(position);
            if (option.isChecked()) {
                holder.etOptions.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_ffffff));
                holder.etOptions.setBackgroundResource(R.drawable.charger_method_option_item_select_bg);
            } else {
                holder.etOptions.setBackgroundResource(R.drawable.charger_method_option_item_unselect_bg);
                holder.etOptions.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_ff9933));
            }
            holder.etOptions.setText(option.getTitle());
            holder.etOptions.setCursorVisible(false);
            holder.etOptions.setFocusable(false);
            holder.etOptions.setFocusableInTouchMode(false);
            holder.etOptions.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    SystemUtil.hideKeyboard(holder.etOptions);
                    btnConfirm.performClick();
                    return false;
                }
            });
            holder.etOptions.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        holder.etOptions.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_ff9933));
                        btnConfirm.setText(R.string.charger_method_btn_confirm_reset);
                    } else {
                        btnConfirm.setText(R.string.charger_method_btn_confirm_select);
                    }
                }
            });
            holder.etOptions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position == getItemCount() - 1) {
                        if (currentType != OPTION_TIME) {
                            holder.etOptions.setText("");
                            holder.etOptions.setFocusable(true);
                            holder.etOptions.setCursorVisible(true);
                            holder.etOptions.setFocusableInTouchMode(true);
                            holder.etOptions.requestFocus();
                            holder.etOptions.setBackgroundResource(R.drawable.charger_method_option_item_input_bg);
                            holder.etOptions.setFilters(new InputFilter[]{mNumberInputFileter});
                            SystemUtil.showKeyboard(holder.etOptions);
                        } else {
                            new TimePickerDialogFragment().show(getChildFragmentManager(), "TikePicker", new TimePicker.OnTimeChangedListener() {
                                @Override
                                public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                                    ChargerMathodOption option = optionsList.get(optionsList.size() - 1);
                                    if (hourOfDay != 0) {
                                        holder.etOptions.setText(hourOfDay + getString(R.string.charger_method_unit_hour) + minute + getString(R.string.charger_method_unit_min));
                                        option.setTitle(hourOfDay + getString(R.string.charger_method_unit_hour) + minute + getString(R.string.charger_method_unit_min));
                                    } else {
                                        holder.etOptions.setText(minute + getString(R.string.charger_method_unit_min));
                                        option.setTitle(minute + getString(R.string.charger_method_unit_min));
                                    }
                                    tvSelectContent.setText(String.format(getString(R.string.charger_method_option_content),
                                            getString(R.string.charger_method_option_time), option.getTitle()));
                                    option.setValue(String.valueOf(hourOfDay * 60 * 60 + minute * 60));
                                    option.setChecked(true);
                                    option.setShowName(tvSelectContent.getText().toString());
                                    notifyDataSetChanged();
                                }
                            });
                            changeChecked();
                            option.setChecked(!option.isChecked());
                            notifyDataSetChanged();
                        }

                    } else {
                        changeChecked();
                        option.setChecked(!option.isChecked());
                        notifyDataSetChanged();
                        if (currentType == OPTION_MONEY) {
                            tvSelectContent.setText(String.format(getString(R.string.charger_method_option_content),
                                    getString(R.string.charger_method_option_money), option.getTitle()));
                        } else if (currentType == OPTION_BATTERY) {
                            tvSelectContent.setText(String.format(getString(R.string.charger_method_option_content),
                                    getString(R.string.charger_method_option_power), option.getTitle()));
                        } else if (currentType == OPTION_TIME) {
                            tvSelectContent.setText(String.format(getString(R.string.charger_method_option_content),
                                    getString(R.string.charger_method_option_time), option.getTitle()));
                        }

                    }

                }
            });
        }

        @Override
        public int getItemCount() {
            return optionsList == null ? 0 : optionsList.size();
        }

        /**
         * 更改选中状态
         */
        private void changeChecked() {
            for (ChargerMathodOption option : optionsList) {
                if (option != null) {
                    option.setChecked(false);
                }
            }
        }
    }

    /**
     * 获取选中的参数
     *
     * @return
     */
    private ChargerMathodOption getCheckOptions() {
        for (ChargerMathodOption option : optionsList) {
            if (option != null && option.isChecked()) {
                return option;
            }
        }
        return null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mKeyBoardPatch.enable();
        unbinder.unbind();
    }

    public class OptionHolder extends RecyclerView.ViewHolder {
        EditText etOptions;

        public OptionHolder(View itemView) {
            super(itemView);
            etOptions = (EditText) itemView.findViewById(R.id.tv_option_item);
        }
    }

    private IOptionConfirmListenter mConfirmListener;

    public void setIOptionConfirmListenter(IOptionConfirmListenter iOptionConfirmListenter) {
        this.mConfirmListener = iOptionConfirmListenter;
    }

    public static interface IOptionConfirmListenter {
        void onOptionConfirm(ChargerMathodOption option);
    }

    /**
     * 限制输入内容
     */
    public static class NumberInputFileter implements InputFilter {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            int inputLength = (dest.length() - (dend - dstart));
            int keep = 3 - inputLength;
            if (inputLength==0&&"0".equals(source.toString()) ) {
                return "";
            }
            if (keep <= 0) {
                return "";
            } else if (keep >= end - start) {
                return null;
            } else {
                return source.subSequence(start, start + keep);
            }
        }
    }
}
