package com.pinnet.chargerapp.utils;

import android.os.Environment;
import android.support.compat.BuildConfig;
import android.util.Log;

import com.pinnet.chargerapp.app.Constants;
import com.pinnet.chargerapp.utils.ringbuffer.StringRingBuffer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.OkHttpClient;

/**
 * @author P00558
 * @date 2018/4/23
 * 日志辅助工具
 */

public class LogUtils {
    private volatile static LogUtils instance;
    static String className;//类名
    static String methodName;//方法名
    static int lineNumber;//行数
    /**
     * 日志等级
     */
    public static final String VERBOSE = "VERBOSE";
    public static final String DEBUG = "DEBUG";
    public static final String INFO = "INFO";
    public static final String WARN = "WARN";
    public static final String ERROR = "ERROR";
    public static final String ASSERT = "ASSERT";

    /**
     * 缓冲区大小 （1000千条日志）
     */
    public static final int BUFFER_SIZE = 10;
    /**
     * App日志文件最大长度
     */
    public static final int LOG_FILE_MAX_SIZE = 2 * 1024 * 1024;
    /**
     * App日志文件名称
     */
    public static final String LOG_FILE_NAME = "ChargerAppLog.txt";
    /**
     * App日志文件路径
     */
    public static final String LOG_PATH;
    /**
     * App日志压缩文件路径
     */
    public static final String LOG_PATH_ZIP;
    /**
     * 日志文件日期格式
     */
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * 日志分割用的符号
     */
    private static final String LOG_SEPARATE = "\t|\t";
    /**
     * 数组缓冲区
     */
    private static final StringRingBuffer CIRCULAR_BUFFER = new StringRingBuffer(BUFFER_SIZE);

    static {
        LOG_PATH = Constants.PATH_LOGS + File.separator
                + "Log";
        LOG_PATH_ZIP = Constants.PATH_LOGS + File.separator
                + "LogZip";
    }

    public static LogUtils getInstance() {
        if (instance == null) {
            synchronized (LogUtils.class) {
                if (instance == null) {
                    instance = new LogUtils();
                }
            }

        }
        return instance;
    }

    public boolean isDebuggable() {
        return true;
    }

    private String createLog(String log) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(methodName);
        buffer.append("(").append(className).append(":").append(lineNumber).append(")");
        buffer.append(log);
        return buffer.toString();
    }

    private void getMethodNames(StackTraceElement[] sElements) {
        className = sElements[1].getFileName();
        methodName = sElements[1].getMethodName();
        lineNumber = sElements[1].getLineNumber();
    }


    public void e(String message) {
        if (!isDebuggable()) {
            return;
        }

        getMethodNames(new Throwable().getStackTrace());
        Log.e(className, createLog(message));
        writeBuffer(ERROR, className, message);
    }

    public void e(String message, Throwable throwable) {
        if (!isDebuggable()) {
            return;
        }

        getMethodNames(new Throwable().getStackTrace());
        Log.e(className, createLog(message + "\n" + getStackTraceString(throwable)));
        writeBuffer(ERROR, className, message + "\n" + getStackTraceString(throwable));
    }

    public void i(String message) {
        if (!isDebuggable()) {
            return;
        }

        getMethodNames(new Throwable().getStackTrace());
        Log.i(className, createLog(message));
        writeBuffer(INFO, className, message);
    }

    public void d(String message) {
        if (!isDebuggable()) {
            return;
        }

        getMethodNames(new Throwable().getStackTrace());
        Log.d(className, createLog(message));
        writeBuffer(DEBUG, className, message);
    }

    public void v(String message) {
        if (!isDebuggable()) {
            return;
        }

        getMethodNames(new Throwable().getStackTrace());
        Log.v(className, createLog(message));
        writeBuffer(VERBOSE, className, message);
    }

    public void w(String message) {
        if (!isDebuggable()) {
            return;
        }

        getMethodNames(new Throwable().getStackTrace());
        Log.w(className, createLog(message));
        writeBuffer(WARN, className, message);
    }

    public void w(String message, Throwable throwable) {
        if (!isDebuggable()) {
            return;
        }

        getMethodNames(new Throwable().getStackTrace());
        Log.w(className, createLog(message));
        writeBuffer(WARN, className, message + "\n" + getStackTraceString(throwable));
    }

    public void wtf(String message) {
        if (!isDebuggable()) {
            return;
        }

        getMethodNames(new Throwable().getStackTrace());
        Log.wtf(className, createLog(message));
    }

    /**
     * 写入到缓冲区，不会主动写入到文件
     *
     * @param level   消息等级
     * @param tag     消息标签
     * @param message 消息内容
     */
    private static synchronized void writeBuffer(String level, String tag, String message) {
        StringBuffer buffer = new StringBuffer();
        long time = System.currentTimeMillis();
        long pid = android.os.Process.myPid();
        long tid = android.os.Process.myTid();
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        buffer.append(level);
        buffer.append(LOG_SEPARATE);
        buffer.append(formatter.format(time));
        buffer.append(LOG_SEPARATE);
        buffer.append(pid);
        buffer.append(LOG_SEPARATE);
        buffer.append(tid);
        buffer.append(LOG_SEPARATE);
        buffer.append(String.format(Locale.ENGLISH, "%-30s", tag));
        buffer.append(LOG_SEPARATE);
        buffer.append(message);
        buffer.append("\n");
        CIRCULAR_BUFFER.add(buffer.toString());
        flushBuffer();
//        if (ERROR.equals(level)) {
//            flushBuffer();
//        } else {
//            if (CIRCULAR_BUFFER.getValidElementSize() >= BUFFER_SIZE) {
//                flushBuffer();
//            }
//        }

    }

    public static void writeOkhttpLog(String message) {
        writeBuffer(INFO, OkHttpClient.class.getName(), message);
    }

    public static String getStackTraceString(Throwable tr) {
        if (tr == null) {
            return "";
        }

        // This is to reduce the amount of log spew that apps do in the non-error
        // condition of the network being unavailable.
        Throwable t = tr;
        while (t != null) {
            if (t instanceof UnknownHostException) {
                return "";
            }
            t = t.getCause();
        }

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        tr.printStackTrace(pw);
        pw.flush();
        return sw.toString();
    }

    /**
     * 生成日志片段头信息
     */
    private static String generateSectionHeader() {
        /** 格式化输出日期 */
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        StringBuffer buffer = new StringBuffer();
        buffer.append("\n=======================");
        buffer.append(formatter.format(System.currentTimeMillis()));
        buffer.append(" \t日志数量：" + CIRCULAR_BUFFER.getValidElementSize());
        buffer.append("=======================\n");
        buffer.append(String.format(Locale.ENGLISH, "%-5s", "Level"));
        buffer.append(LOG_SEPARATE);
        buffer.append(String.format(Locale.ENGLISH, "%-13s", "timestamp"));
        buffer.append(LOG_SEPARATE);
        buffer.append(String.format(Locale.ENGLISH, "%-5s", "PID"));
        buffer.append(LOG_SEPARATE);
        buffer.append(String.format(Locale.ENGLISH, "%-5s", "TID"));
        buffer.append(LOG_SEPARATE);
        buffer.append(String.format(Locale.ENGLISH, "%-30s", "TAG"));
        buffer.append(LOG_SEPARATE);
        buffer.append("TEXT");
        buffer.append("\n");
        return buffer.toString();
    }

    /**
     * 处理历史日志文件
     * <ul>
     * <li>原则:只保留一个当前日志文件和一个历史日志文件
     * <li>处理逻辑:检查当前日志文件是否写满，如果写满则重命名为历史日志文件
     * </ul>
     */
    private static boolean processLogFiles() {
        File dir = new File(LOG_PATH);
        File logFile = new File(LOG_PATH + File.separator + LOG_FILE_NAME);
        long logFileLength = logFile.length();
        if (logFileLength < LOG_FILE_MAX_SIZE) {
            return true;
        }
        String fileNames[] = dir.list();
        for (String name : fileNames) {
            if (name == null) {
                continue;
            }
            if (!name.equals(LOG_FILE_NAME)) {
                // 删除其他历史日志文件
                boolean isDel = new File(dir, name).delete();
                if (isDel) {
                    Log.i("Log", "delete success " + name);
                } else {
                    Log.i("Log", "delete failed " + name);
                }
            }
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss_", Locale.getDefault());
        String time = dateFormat.format(new Date());
        // 重命名当前的日志文件（打上时间标签）
        File newFile = new File(LOG_PATH, time + LOG_FILE_NAME);
        return logFile.renameTo(newFile);
    }

    /**
     * 将缓存数据写入到文件
     *
     * @return 如果写入成功则返回true，否则返回false
     */
    private static boolean writeTofile() {
        boolean success = true;
        if (!processLogFiles()) {
            return false;
        }
        String head = generateSectionHeader();
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        File logFile = new File(LOG_PATH + File.separator + LOG_FILE_NAME);
        try {
            fileWriter = new FileWriter(logFile, true);
            bufferedWriter = new BufferedWriter(fileWriter);
            String[] logs = CIRCULAR_BUFFER.getBuffer();
            // 写入日志信息头

            //  bufferedWriter.write(head);
            for (String msg : logs) {
                bufferedWriter.write(msg);
            }
            // 回写磁盘
            bufferedWriter.flush();
        } catch (IOException e) {
            android.util.Log.e("Log", "write log file to disk fail.");
            success = false;
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
                if (fileWriter != null) {
                    fileWriter.close();
                }
            } catch (Exception e1) {
                android.util.Log.e("Log", "close log file error.");
                success = false;
            }
        }
        return success;
    }

    /**
     * 将缓存里面的数据输入到
     */
    public static synchronized void flushBuffer() {
        if (CIRCULAR_BUFFER.getValidElementSize() <= 0) {
            return;
        }

        File dir = new File(LOG_PATH);
        boolean created = true;
        if (!dir.exists()) {
            created = dir.mkdirs();
        }
        if (!dir.exists() || !created) {
            return;
        }
        if (writeTofile()) {
            CIRCULAR_BUFFER.reset();
        }
    }
}
