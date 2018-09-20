package com.pinnet.chargerapp.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.AbsoluteSizeSpan;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pinnet.chargerapp.R;
import com.pinnet.chargerapp.utils.ScreenUtils;
import com.pinnet.chargerapp.utils.SystemUtil;


/**
 * @author P00558
 * @date 2018/4/4
 * 登录-输入框
 */

public class LoginEditText extends RelativeLayout implements View.OnClickListener, View.OnFocusChangeListener, TextWatcher, TextView.OnEditorActionListener {
    /**
     * 左边的Logo
     */
    private ImageView ivLeftIcon;
    /**
     * 清除图片
     */
    private ImageView ivClearIcon;
    /**
     * 右侧图标
     */
    private ImageView ivRightIcon;
    private EditText etContent;
    /**
     * 默认图片id
     */
    private int normalIconId;
    /**
     * 获取焦点时的ID
     */
    private int focusIconId;
    /**
     * 是否显示右侧图标
     */
    private boolean showRightIconEnabled;
    /**
     * 文本显示密文还是明文
     */
    private boolean showTextEnabled;
    /**
     * 提示文本
     */
    private String hideText;
    /**
     * 是否显示左侧图标
     */
    private boolean showLeftIcon;

    public LoginEditText(Context context) {
        this(context, null);
    }

    public LoginEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoginEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoginEditText);
        normalIconId = typedArray.getResourceId(R.styleable.LoginEditText_normalIcon, R.drawable.login_icon_account_nomal);
        focusIconId = typedArray.getResourceId(R.styleable.LoginEditText_focusIcon, R.drawable.login_icon_account_nomal);
        showRightIconEnabled = typedArray.getBoolean(R.styleable.LoginEditText_rightIconEnabled, false);
        showTextEnabled = typedArray.getBoolean(R.styleable.LoginEditText_showTextEnabled, true);
        showLeftIcon = typedArray.getBoolean(R.styleable.LoginEditText_showLeftIcon, true);
        hideText = typedArray.getString(R.styleable.LoginEditText_hideText);
        typedArray.recycle();
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.custom_widget_login_edittext, this);
        ivLeftIcon = (ImageView) findViewById(R.id.iv_left_icon);
        ivClearIcon = (ImageView) findViewById(R.id.iv_clear);
        ivRightIcon = (ImageView) findViewById(R.id.iv_right_icon);
        etContent = (EditText) findViewById(R.id.et_content);
        etContent.setHint(hideText);
        SpannableString s = new SpannableString(hideText);
        AbsoluteSizeSpan textSize = new AbsoluteSizeSpan(12, true);
        s.setSpan(textSize, 0, s.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // 设置hint
        etContent.setHint(s); // 一定要进行转换,否则属性会消失

        if (!showLeftIcon) {
            ivLeftIcon.setVisibility(View.GONE);
        }
        ivLeftIcon.setImageResource(normalIconId);
        if (!showTextEnabled) {
            etContent.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        initListener();
    }

    private void initListener() {
        ivClearIcon.setOnClickListener(this);
        ivRightIcon.setOnClickListener(this);
        etContent.addTextChangedListener(this);
        etContent.setOnFocusChangeListener(this);
        etContent.setOnEditorActionListener(this);
    }

    public String getTextValus() {
        if (etContent == null) {
            return "";
        }
        return etContent.getText().toString();
    }

    public void setText(String value) {
        if (etContent != null && !TextUtils.isEmpty(value)) {
            etContent.setText(value);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_clear:
                onClearText();
                break;
            case R.id.iv_right_icon:
                onShowContent();
                break;
            default:
                break;
        }
    }

    /**
     * 清除输入内容
     */
    private void onClearText() {
        if (etContent != null) {
            etContent.setText("");
        }
    }

    private void onShowContent() {
        if (etContent != null) {
            if (showTextEnabled) {
                //设置文本隐藏
                etContent.setTransformationMethod(PasswordTransformationMethod.getInstance());
                ivRightIcon.setImageResource(R.drawable.login_icon_eye_close);
            } else {
                //设置文本显示
                etContent.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                ivRightIcon.setImageResource(R.drawable.login_icon_eye_open);
            }
            showTextEnabled = !showTextEnabled;
        }
    }

    public void setInputType(int inputType) {
        if (etContent != null) {
        etContent.setInputType(inputType);
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
        if (!TextUtils.isEmpty(editable)) {
            if (showRightIconEnabled) {
                ivRightIcon.setVisibility(View.VISIBLE);
            }
        } else {
            if (showRightIconEnabled) {
                ivRightIcon.setVisibility(View.GONE);
            }
        }
    }


    @Override
    public void onFocusChange(View view, boolean b) {
        if (b) {
            ivClearIcon.setVisibility(View.VISIBLE);
            ivLeftIcon.setImageResource(focusIconId);
        } else {
            ivClearIcon.setVisibility(View.INVISIBLE);
            ivLeftIcon.setImageResource(normalIconId);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(measureWidth(widthMeasureSpec),
                measureHeight(heightMeasureSpec));

    }

    private int measureHeight(int measureSpec) {
        int result = 0;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = ScreenUtils.dip2px(getContext(), 56);
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size);
            }
        }
        return result;

    }

    private int measureWidth(int measureSpec) {
        int result = 0;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = ScreenUtils.dip2px(getContext(), 56);
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size);
            }
        }
        return result;
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        return false;
    }


}
