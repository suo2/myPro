package com.pinnet.chargerapp.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pinnet.chargerapp.R;
import com.pinnet.chargerapp.utils.ScreenUtils;

/**
 * @author P00558
 * @date 2018/7/12
 */

public class SearchFlowTagView extends LinearLayout {
    private Context mContext;
    private TextView tvContent;
    private ImageView ivDelete;

    public SearchFlowTagView(Context context) {
        this(context, null);
    }

    public SearchFlowTagView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchFlowTagView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init() {
        setOrientation(HORIZONTAL);
        LayoutParams layoutParams=new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity= Gravity.CENTER;
        setLayoutParams(layoutParams);
        setPadding(ScreenUtils.dip2px(mContext, 16), ScreenUtils.dip2px(mContext, 3), ScreenUtils.dip2px(mContext, 16), ScreenUtils.dip2px(mContext, 3));
        setBackgroundResource(R.drawable.charger_widget_searchflowtag_bg);

        tvContent = new TextView(mContext);
        tvContent.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_333333));
        ivDelete = new ImageView(mContext);
        ivDelete.setImageResource(R.drawable.login_icon_close);
        ivDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener!=null){
                    clickListener.onDelete();
                }
            }
        });

        addView(tvContent);
        LayoutParams deleteParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        deleteParams.leftMargin = 10;
        addView(ivDelete, deleteParams);
    }

    public void setText(String text) {
        if (tvContent != null) {
            tvContent.setText(text);
        }
    }

    public String getText() {
        if (tvContent != null) {
            return tvContent.getText().toString();
        }
        return "";
    }
    private DrawableRightClickListener clickListener;
    public void setDrawableRightClickListener(DrawableRightClickListener clickListener){
        this.clickListener=clickListener;
    }
    public static interface DrawableRightClickListener{
        void onDelete();
    }
}
