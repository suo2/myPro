/*
 * Copyright (C) TD Tech<br>
 * All Rights Reserved.<br>
 * 
 */
package com.pinnet.chargerapp.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;


import com.pinnet.chargerapp.app.Constants;

import java.io.File;
import java.io.InputStream;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Create Date: 2014-12-30<br>
 * Create Author: cWX239887<br>
 * Description :UncaughtException处理类,当程序发生Uncaught异常的时候,有该类来接管程序,并记录发送错误报告,需要在Application中注册，为了要在程序启动器就监控整个程序
 */
public class CrashHandler implements UncaughtExceptionHandler {
    public static final String TAG = "CrashHandler";
    /**
     * 崩溃日志保存路径
     */
    public static final String CRASH_PATH;
    /**
     * 系统默认的UncaughtException处理类
     */
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    /**
     * 程序的Context对象
     */
    private Context mContext;

    static {
        CRASH_PATH = Constants.PATH_LOGS + File.separator
                + "crash" + File.separator;
    }

    /**
     * 保证只有一个CrashHandler实例
     */
    private CrashHandler() {
    }

    private static class InstanceHolder {
        /**
         * CrashHandler实例
         */
        private static final CrashHandler INSTANCE = new CrashHandler();
    }

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static CrashHandler getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public void init(Context context) {
        mContext = context;
        // 获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {

        handleException(ex);

        if (mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(thread, ex);
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        Log.e("error", ex.toString());
        // 收集设备参数信息
        Map<String, String> map = collectAppInfo(mContext);
        // 保存日志文件
        saveCatchInfo2File(map, ex);
        return true;
    }

    /**
     * 收集App参数信息
     *
     * @param ctx
     */
    public Map<String, String> collectAppInfo(Context ctx) {
        Map<String, String> mInfos = new HashMap<String, String>();
        InputStream inputStream = null;
        try {
            inputStream = mContext.getAssets().open(Constants.KEY_PLATFORM_CONFIG);
            Properties properties = new Properties();
            properties.load(inputStream);
            String wApp_version = properties.getProperty("ChargerApp_version");
            String wApp_mode = properties.getProperty("ChargerApp_mode");
            mInfos.put("ChargerApp_version", wApp_version);
            mInfos.put("ChargerApp_mode", wApp_mode);
        } catch (Exception e) {
            Log.e(TAG, "Exception", e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                Log.e(TAG, "Exception", e);
            }
        }

        return mInfos;
    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    private String saveCatchInfo2File(Map<String, String> mInfos, Throwable ex) {
        StringBuffer sb = new StringBuffer();
        sb.append('\n');
        for (Map.Entry<String, String> entry : mInfos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }
        LogUtils.getInstance().e(TAG+sb,ex);
        return LogUtils.LOG_FILE_NAME;
    }

}
