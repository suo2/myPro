package com.pinnet.chargerapp.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import com.pinnet.chargerapp.R;

/**
 * @author P00558
 * @date 2018/4/27
 */

public class CustomTimerPicker extends LinearLayout {
    private NumberPicker mHourPicker;
    private NumberPicker mMinutePicker;

    public CustomTimerPicker(Context context) {
        this(context, null);
    }

    public CustomTimerPicker(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTimerPicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.custom_widget_timer_picker, this);
        mHourPicker = (NumberPicker) findViewById(R.id.np_hour);
        mMinutePicker = (NumberPicker) findViewById(R.id.np_minute);
        mHourPicker.setMinValue(0);
        mHourPicker.setMaxValue(23);
        mMinutePicker.setMaxValue(59);
        mMinutePicker.setMinValue(0);
    }

    public int getHour() {
        return mHourPicker.getValue();
    }

    public int getMinute() {
        return mMinutePicker.getValue();
    }

    /**
     * 返回当前时间，单位秒
     *
     * @return
     */
    public long getCurrentTimes() {
        return mHourPicker.getValue() * 60 * 60 + mMinutePicker.getValue() * 60;
    }
}
