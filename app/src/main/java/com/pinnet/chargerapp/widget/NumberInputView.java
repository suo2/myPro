package com.pinnet.chargerapp.widget;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pinnet.chargerapp.R;
import com.pinnet.chargerapp.utils.ScreenUtils;

/**
 * @author P00558
 * @date 2018/4/27
 * 编号输入框
 */

public class NumberInputView extends ViewGroup implements TextWatcher, View.OnKeyListener, View.OnFocusChangeListener {
    private int defaultTextSize = 10;
    private Context mContext;
    private int mEtNumber = 4;
    private int textSize = defaultTextSize;
    private boolean isInputFinish;

    public NumberInputView(Context context) {
        this(context, null);
    }

    public NumberInputView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumberInputView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initChildView(mEtNumber);
    }

    public void initChildView(int viewCount) {
        removeAllViews();
        if (viewCount >= 0) {
            mEtNumber = viewCount;
            for (int i = 0; i < viewCount; i++) {
                EditText editText = new EditText(mContext);
                initEditText(editText, i); //editText初始化
                addView(editText);
                if (i == 0) { //设置第一个editText获取焦点
                    editText.setFocusable(true);
                }
            }
        }
    }

    private void initEditText(EditText editText, int id) {
        editText.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_333333));
        editText.setBackgroundResource(R.drawable.charger_method_option_input_bg);
        editText.setGravity(Gravity.CENTER);
        editText.setCursorVisible(false);
        editText.setFilters(new InputFilter[]{
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        if (dstart != 0) {
                            return "";
                        }
                        for (int i = start; i < end; i++) {
                            if (!Character.isDigit(source.charAt(i)) && !"_".equals(String.valueOf(source.charAt(i)))) {
                                return "";
                            }
                        }
                        return null;
                    }
                }
        });
        editText.setTextSize(ScreenUtils.sp2px(mContext, defaultTextSize));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ScreenUtils.dip2px(mContext, 20), LinearLayout.LayoutParams.WRAP_CONTENT);
        params.rightMargin = ScreenUtils.dip2px(mContext, 4);
        params.gravity = Gravity.CENTER;
        editText.setLayoutParams(params);
        editText.addTextChangedListener(this);
        editText.setOnKeyListener(this);
        editText.setOnFocusChangeListener(this);
    }

    private void focus() {
        int count = getChildCount();
        EditText editText;
        //利用for循环找出还最前面那个还没被输入字符的EditText，并把焦点移交给它。
        for (int i = 0; i < count; i++) {
            editText = (EditText) getChildAt(i);
            if (editText.getText().length() < 1) {
                //editText.setCursorVisible(true);
                editText.requestFocus();
                return;
            } else {
                //editText.setCursorVisible(false);
            }
        }
        //如果最后一个输入框有字符，则返回结果
        EditText lastEditText = (EditText) getChildAt(mEtNumber - 1);
        if (lastEditText.getText().length() > 0) {
            isInputFinish = true;
            if (mFinishListner != null) {
                mFinishListner.onInputFinish(isInputFinish);
            }
        }
    }

    public String getText() {
        int count = getChildCount();
        EditText editText;
        StringBuffer stringBuffer = new StringBuffer();
        //利用for循环找出还最前面那个还没被输入字符的EditText，并把焦点移交给它。
        for (int i = 0; i < count; i++) {
            editText = (EditText) getChildAt(i);
            stringBuffer.append(editText.getText());
        }
        return stringBuffer.toString();
    }

    private long endTime;

    private void backFocus() {
        isInputFinish = false;
        if (mFinishListner != null) {
            mFinishListner.onInputFinish(isInputFinish);
        }
        //博主手机不好，经常点一次却触发两次`onKey`事件，就设置了一个防止多点击，间隔100毫秒。
        long startTime = System.currentTimeMillis();
        EditText editText;
        //循环检测有字符的`editText`，把其置空，并获取焦点。
        for (int i = mEtNumber - 1; i >= 0; i--) {
            editText = (EditText) getChildAt(i);
            if (editText.getText().length() >= 1 && startTime - endTime > 100) {
                editText.setText("");
                editText.setCursorVisible(true);
                editText.requestFocus();
                endTime = startTime;
                return;
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (editable.length() != 0 && !isInputFinish) {
            focus();
        }
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (i == KeyEvent.KEYCODE_DEL) {
            backFocus();
        }
        return false;
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if (b && !isInputFinish) {
            focus();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        //获取子控件的数量
        int count = getChildCount();
        //设置默认起始值
        int startWidth = 0;
        int startHeight = 0;
        //循环遍历子控件
        for (int j = 0; j < count; j++) {
            //取出每一条子视图
            View v = getChildAt(j);
//            注释部分可实现流式布局,内容满之后换行
            if (startWidth + v.getMeasuredWidth() > getMeasuredWidth()) {
                startHeight += v.getMeasuredHeight() + getPaddingBottom();
                startWidth = 0;
            }
            //给每一个子视图设置定位
            v.layout(startWidth, startHeight, startWidth + v.getMeasuredWidth(), startHeight + v.getMeasuredHeight());
            startWidth += v.getMeasuredWidth() + ((MarginLayoutParams) v.getLayoutParams()).rightMargin;
        }
    }

    private OnInputFinishListener mFinishListner;

    public void setOnInputFinishListener(OnInputFinishListener finishListener) {
        this.mFinishListner = finishListener;
    }

    public interface OnInputFinishListener {
        void onInputFinish(boolean isFinish);
    }
}
