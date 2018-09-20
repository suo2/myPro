package com.pinnet.chargerapp.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pinnet.chargerapp.R;
import com.pinnet.chargerapp.utils.ScreenUtils;

/**
 * @author P00558
 * @date 2018/5/11
 * 带字数限制提示的输入框
 */

public class LimitEditText extends RelativeLayout implements TextWatcher {
    private EditText etInput;
    private TextView limitText;
    private String hintText = "请写下您的问题并用问号结尾（4到50个字）";
    private int maxWordsNum = 50;
    private int currentNumber;
    private boolean limitEnable;

    public LimitEditText(Context context) {
        this(context, null);
    }

    public LimitEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LimitEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.LimitEditText, 0, 0);
        int n = a.getIndexCount();

        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.LimitEditText_maxWordsNum:
                    maxWordsNum = a.getInt(attr, maxWordsNum);
                    break;
                case R.styleable.LimitEditText_hint:
                    hintText = a.getString(attr);
                    break;
                case R.styleable.LimitEditText_limitEnable:
                    limitEnable = a.getBoolean(attr, true);
                    break;
                default:
                    break;
            }
        }
        a.recycle();

        etInput = new EditText(getContext());
        etInput.setBackground(new BitmapDrawable());
        etInput.setHint(hintText);
        etInput.setId(R.id.et_input);
        etInput.setHintTextColor(ContextCompat.getColor(getContext(), R.color.text_color_999999));
        etInput.setTextColor(ContextCompat.getColor(getContext(), R.color.text_color_333333));
        etInput.setGravity(Gravity.TOP);
        etInput.setTextSize(ScreenUtils.sp2px(getContext(), 12));
        etInput.setMinHeight(ScreenUtils.dip2px(getContext(), 150));
        if (limitEnable) {
            etInput.setFilters(new InputFilter[]{new MaxTextLengthFilter(maxWordsNum)});
            etInput.addTextChangedListener(this);

            limitText = new TextView(getContext());
            limitText.setTextSize(ScreenUtils.sp2px(getContext(), 12));
            limitText.setTextColor(ContextCompat.getColor(getContext(), R.color.text_color_999999));
            RelativeLayout.LayoutParams limitParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            limitParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            limitParams.addRule(RelativeLayout.BELOW, R.id.et_input);
            limitParams.topMargin = ScreenUtils.dip2px(getContext(), 5);
            limitText.setLayoutParams(limitParams);
            addView(limitText, limitParams);
        }
        RelativeLayout.LayoutParams etParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        etInput.setLayoutParams(etParams);
        addView(etInput, etParams);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //编辑框内容变化之后会调用该方法，s为编辑框内容变化后的内容
        currentNumber = s.length();
        if (currentNumber > maxWordsNum) {
            etInput.setText(s.toString().substring(0, currentNumber - 1));
        } else {
            limitText.setText(currentNumber + "/" + maxWordsNum);
        }

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public static class MaxTextLengthFilter implements InputFilter {

        private int mMaxLength;

        public MaxTextLengthFilter(int max) {
            mMaxLength = max;
        }

        /**
         * @param source 输入的文字
         * @param start  输入-0，删除-0
         * @param end    输入-文字的长度，删除-0
         * @param dest   原先显示的内容
         * @param dstart 输入-原光标位置，删除-光标删除结束位置
         * @param dend   输入-原光标位置，删除-光标删除开始位置
         * @return
         */
        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {
            int inputLength = (dest.length() - (dend - dstart));
            int keep = mMaxLength - inputLength;
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
