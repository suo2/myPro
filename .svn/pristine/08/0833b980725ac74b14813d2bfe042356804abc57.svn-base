package com.pinnet.chargerapp.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * @author P00558
 * @date 2018/7/12
 */

public class SearchFlowTagLayout extends LinearLayout {
    private Context mContext;

    public SearchFlowTagLayout(Context context) {
        super(context);
        init(context);
    }

    public SearchFlowTagLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SearchFlowTagLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        setOrientation(HORIZONTAL);
    }

    public void addItem(String text) {
        if (TextUtils.isEmpty(text)) {
            showHideText();
            return;
        }
        getChildAt(0).setVisibility(View.GONE);
        removeViews(1, getChildCount() - 1);
        SearchFlowTagView tagView = new SearchFlowTagView(mContext);
        tagView.setDrawableRightClickListener(new SearchFlowTagView.DrawableRightClickListener() {
            @Override
            public void onDelete() {
                showHideText();
            }
        });
        tagView.setText(text);
        addView(tagView);
    }

    public void removeItem() {

    }

    public void showHideText() {
        getChildAt(0).setVisibility(View.VISIBLE);
        removeViews(1, getChildCount() - 1);
        if (removeItemListenter!=null){
            removeItemListenter.onRemove();
        }
    }

    private OnRemoveItemListenter removeItemListenter;

    public void setOnRemoveItemListenter(OnRemoveItemListenter listenter) {
        this.removeItemListenter = listenter;
    }

    public static interface OnRemoveItemListenter {
        void onRemove();
    }
}
