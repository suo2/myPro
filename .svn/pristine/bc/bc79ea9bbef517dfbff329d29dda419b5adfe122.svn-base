package com.pinnet.chargerapp.utils;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * @author P00558
 * @date 2018/4/23
 * 对数据的初步判断处理
 */

public class DataUtils {
    private volatile static DataUtils instance;

    public static DataUtils getInstance() {
        if (instance == null) {
            synchronized (DataUtils.class) {
                if (instance == null) {
                    instance = new DataUtils();
                }
            }
        }
        return instance;
    }

    // 分割并保留2位小数
    public static final String FORMAT_COMMA_WITH_TWO = "###,##0.00";
    // 分割并保留3位小数
    public static final String FORMAT_COMMA_WITH_THREE = "###,##0.000";
    // 分割并保留5位小数
    public static final String FORMAT_COMMA_WITH_FIVE = "###,##0.00000";
    //分割并保留4位小数
    public static final String FORMAT_COMMA_WITH_FOUR = "###,##0.0000";
    // 分割并保留1位小数
    public static final String FORMAT_COMMA_WITH_ONE = "###,##0.0";
    // 分割并保留0位小数
    public static final String FORMAT_COMMA_WITH_ZERO = "###,###";

    // 不分割并保留2位小数
    public static final String FORMAT_WITH_TWO = "###.00";
    // 不分割并保留1位小数
    public static final String FORMAT_WITH_ONE = "###.0";
    // 不分割并保留0位小数
    public static final String FORMAT_WITH_ZERO = "###";

    /**
     * 判断是否是Double无穷小 或无穷大
     *
     * @param doubleValue
     * @return
     */
    public boolean isDoubleMinValue(Double doubleValue) {
        return Double.doubleToLongBits(doubleValue) == Double.doubleToLongBits(Double.MIN_VALUE)
                || doubleValue.isInfinite();
    }

    /**
     * 判断是否是Integer无穷小
     *
     * @param intData
     * @return
     */
    public boolean isIntegerMinValue(Integer intData) {
        return intData == Integer.MIN_VALUE
                || intData == Integer.MAX_VALUE;
    }

    /**
     * 判断是否是Long无穷小或无穷大
     *
     * @param longData
     * @return
     */
    public boolean isLongMinValue(Long longData) {
        return longData == Long.MIN_VALUE
                || longData == Long.MAX_VALUE;
    }

    /**
     * 判断是否是Float无穷小
     *
     * @param floatData
     * @return
     */
    public boolean isFloatMinValue(Float floatData) {
        return floatData == Float.MIN_VALUE
                || floatData == Float.MAX_VALUE;
    }

    /**
     * 输出数字的格式,如:1,234,567.89
     *
     * @param value  BigDecimal 要格式化的数字
     * @param format String 格式 "###,###.00"
     * @returnString
     */
    public String numberFormatBase(BigDecimal value, String format) {

        if (value == null) {
            return "0";
        }

        int characterIndex = -1;
        characterIndex = format.indexOf(".");
        int scale = 0;
        if (characterIndex > 0) {
            // 不保留小数
            if (format.length() - characterIndex - 1 == 0) {
                scale = 0;
            }
            // 保留一位小数·
            else if (format.length() - characterIndex - 1 == 1) {
                scale = 1;
            }
            // 保留三位小数
            else if (format.length() - characterIndex - 1 == 3) {
                scale = 3;
            }
            //保留四位小数
            else if (format.length() - characterIndex - 1 == 4) {
                scale = 4;
            }
            // 保留五位小数
            else if (format.length() - characterIndex - 1 == 5) {
                scale = 5;
            }
            // 保留两位小数
            else {
                scale = 2;
            }
        }
        String round = round(value.doubleValue(), scale);
        // 不分割，去分隔符
        if (format.indexOf(",") < 0) {
            round = round.replaceAll(",", "");
        }
        return round;
    }

    /**
     * 保留小数四舍五入
     *
     * @param value
     * @param scale 保留的小数位数
     * @return
     */
    public String round(double value, int scale) {
        final NumberFormat format = NumberFormat.getNumberInstance(Locale.ENGLISH);
        format.setMaximumFractionDigits(scale);
        format.setMinimumFractionDigits(scale);
        format.setRoundingMode(RoundingMode.HALF_UP);
        return format.format(value);
    }

    /**
     * 处理距离单位问题
     *
     * @param value 原始数据 单位m
     * @return
     */
    public String[] handleDistance(double value) {

        String[] strings = new String[2];
        if (isDoubleMinValue(value)) {
            strings[0] = "N/A";
            strings[1] = "m";
            return strings;
        }
        if (value > 1000) {
            strings[0] = round(value / 1000, 2);
            strings[1] = "km";
        } else {
            strings[0] = round(value, 2);
            strings[1] = "m";
        }
        return strings;
    }
}
