package com.pinnet.chargerapp.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author P00558
 * @date 2018/4/23
 */

public class TimeUtils {
    private volatile static TimeUtils instance;

    private TimeUtils() {

    }

    public static TimeUtils getInstance() {
        if (instance == null) {
            synchronized (TimeUtils.class) {
                if (instance == null) {
                    instance = new TimeUtils();
                }
            }
        }
        return instance;
    }

    public final long ONEDAYMIL = 1000 * 60 * 60 * 24;
    private static final String CONSTANTS_STRING_EMPTY = "";

    private static final String DATE_FORMAT_YYYYMMDDHHMM = "yyyy-MM-dd HH:mm";
    private static final String DATE_FORMAT_YYYYMMDD = "yyyy-MM-dd";
    private static final String DATE_FORMAT_YYYYMMDD1 = "yyyyMMdd";
    private static final String DATE_FORMAT_YYYYMMDD2 = "yyyy年MM月dd日";
    private static final String DATE_FORMAT_YYYYMMDD3 = "yyyy.MM.dd";
    private static final String DATE_FORMAT_YYYYMMDDHHMMSS = "yyyy年MM月dd日  HH:mm:ss";
    private static final String DATE_FORMAT_YYYYMMDDHHMMSS2 = "yyyy-MM-dd HH:mm:ss";

    private static final String DATE_FORMAT_HHMMSS = "HH:mm:ss";
    private static final String DATE_FORMAT_HHMM = "HH:mm";
    private static final String DATE_FORMAT_YYYYMM = "yyyy-MM";
    private static final String URL_HTTP = "http://";

    private static final String TIME_ZONE = "GMT+08:00";
    private static final String TIME_ZONE_ZERO = "GMT+00:00";
    private static final String HOUR = "h";
    private static final String MINUTE = "min";

    /**
     * get special time by special rules
     *
     * @param time
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public String getSpecTime(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd hh:mm");
        return sdf.format(new Date(time));
    }

    /**
     * 将时间转换为 yyyy-MM-dd HH:mm
     *
     * @param time
     * @return
     */
    public String getDateYYYYMMDDHHMM(long time) {
        if (time < 0) {
            return CONSTANTS_STRING_EMPTY;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_YYYYMMDDHHMM, Locale.getDefault());
        Date date = new Date(time);
        return formatter.format(date);
    }

    /**
     * 将时间转换为 yyyy.mm.dd
     *
     * @param time
     * @return
     */
    public String getDateYYYYMMDD3(long time) {
        if (time < 0) {
            return CONSTANTS_STRING_EMPTY;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_YYYYMMDD3, Locale.getDefault());
        Date date = new Date(time);
        return formatter.format(date);
    }

    /**
     * 将时间转换为 yyyy-MM-dd HH:mm:ss
     *
     * @param time
     * @return
     */
    public String getDateYYYYMMDDHHMMSS2(long time) {
        if (time < 0) {
            return CONSTANTS_STRING_EMPTY;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_YYYYMMDDHHMMSS2, Locale.getDefault());
        Date date = new Date(time);
        return formatter.format(date);
    }

    /**
     * 将时间转换为HH:mm:ss
     *
     * @param time
     * @return
     */
    public String getDateHHMMSS(long time) {
        if (time < 0) {
            return CONSTANTS_STRING_EMPTY;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_HHMMSS, Locale.getDefault());
        Date date = new Date(time);
        return formatter.format(date);
    }

    /**
     * 将时间转换为HH:mm:ss，时区为GMT+00:00
     *
     * @param time
     * @return
     */
    public String getDateHHMMSSTimeZoneZero(long time) {
        if (time < 0) {
            return CONSTANTS_STRING_EMPTY;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_HHMMSS);
        formatter.setTimeZone(TimeZone.getTimeZone(TIME_ZONE_ZERO));
        Date date = new Date(time);
        return formatter.format(date);
    }

    /**
     * 将时间转换为HH:mm，时区为GMT+00:00
     *
     * @param time
     * @return
     */
    public String getDateHHMMTimeZoneZero(long time) {
        if (time < 0) {
            return CONSTANTS_STRING_EMPTY;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_HHMM);
        formatter.setTimeZone(TimeZone.getTimeZone(TIME_ZONE_ZERO));
        Date date = new Date(time);
        return formatter.format(date);
    }
}
