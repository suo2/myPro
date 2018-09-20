package com.pinnet.chargerapp.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pinnet.chargerapp.R;


/**
 * @author P00558
 * @date 2018/4/4
 * 充电模式选择 自定义View
 */

public class ChargerMethodView extends RelativeLayout {
    /**
     * 图标
     */
    private ImageView ivIcon;
    /**
     * 模式名称
     */
    private TextView tvName;
    /**
     * 模式描述语句
     */
    private TextView tvDescribe;
    /**
     * 文本颜色
     */
    private int textBasicColor;
    private int textSelectColor;
    /**
     * 图标资源
     */
    private int basicIcon;
    private int selectIcon;
    /**
     * 背景颜色
     */
    private int mainBasicBackground;
    private int mainSelectBackground;
    private String showText = "";
    private boolean isSelect;


    public ChargerMethodView(Context context) {
        this(context, null);
    }

    public ChargerMethodView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChargerMethodView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ChargerMethodView);
        textBasicColor = typedArray.getResourceId(R.styleable.ChargerMethodView_basicTextColor, R.color.color_ff4d30);
        textSelectColor = typedArray.getResourceId(R.styleable.ChargerMethodView_selectTextColor, R.color.white);
        basicIcon = typedArray.getResourceId(R.styleable.ChargerMethodView_basicIcon, R.drawable.charger_icon_auto_fill_red);
        selectIcon = typedArray.getResourceId(R.styleable.ChargerMethodView_selectIcon, R.drawable.charger_icon_auto_fill_white);
        mainSelectBackground = typedArray.getResourceId(R.styleable.ChargerMethodView_mainSelectBackgroud, R.drawable.charger_widget_charge_method_red_bg);
        mainBasicBackground = typedArray.getResourceId(R.styleable.ChargerMethodView_mainBasicBackgroud, R.drawable.charger_widget_charge_method_white_bg);
        showText = typedArray.getString(R.styleable.ChargerMethodView_text);
        isSelect = typedArray.getBoolean(R.styleable.ChargerMethodView_select, false);
        typedArray.recycle();
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.custom_widget_charger_method, this);
        ivIcon = (ImageView) findViewById(R.id.iv_icon);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvDescribe = (TextView) findViewById(R.id.tv_describe);
        tvName.setText(showText);
        setSelect(isSelect);
    }

    public boolean isSelected() {
        return isSelect;
    }

    /**
     * 设置是否选中
     *
     * @param select
     */
    public void setSelect(boolean select) {
        if (select) {
            tvDescribe.setVisibility(VISIBLE);
            setSelectStatus();
        } else {
            tvDescribe.setVisibility(GONE);
            setBasicStatus();
        }
        this.isSelect = select;
    }

    /**
     * 设置选中后的状态
     */
    private void setSelectStatus() {
        setBackgroundResource(mainSelectBackground);
        tvName.setTextColor(getResources().getColor(textSelectColor));
        ivIcon.setImageResource(selectIcon);
    }

    /**
     * 设置未选中的状态
     */
    private void setBasicStatus() {
        setBackgroundResource(mainBasicBackground);
        tvName.setTextColor(getResources().getColor(textBasicColor));
        ivIcon.setImageResource(basicIcon);
    }

    /**
     * 设置描述语句
     *
     * @param describe
     */
    public void setDescribe(String describe) {
        tvDescribe.setText(describe);
        tvDescribe.setVisibility(View.VISIBLE);
    }
}
