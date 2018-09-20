package com.pinnet.chargerapp.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.pinnet.chargerapp.app.Constants;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

import okhttp3.ResponseBody;

/**
 * @author P00558
 * @date 2018/4/23
 */

public class Utils {
    private static final int MIN_DELAY_TIME = 1000;  // 两次点击间隔不能少于1000ms
    private static long lastClickTime;

    /**
     * 是否是快速点击
     *
     * @return
     */
    public static boolean isFastClick() {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - lastClickTime) >= MIN_DELAY_TIME) {
            flag = false;
            lastClickTime = currentClickTime;
        }
        return flag;
    }

    public static int range(int min, int max, int value) {
        return (value > max ? max : (value < min ? min : value));
    }

    /**
     * 跳转高德地图
     *
     * @param context
     * @param lat
     * @param lng
     */
    public static void toAmapNavigation(Context context, double lat, double lng) {
        //1.判断用户手机是否安装高德地图APP
        boolean isInstalled = isPkgInstalled(Constants.AMAP_PACKGE_NAME, context);
        //2.首选使用高德地图APP完成导航
        if (isInstalled) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("androidamap://navi?");
            try {
                //填写应用名称
                stringBuilder.append("sourceApplication=" + URLEncoder.encode("测试", "utf-8"));
                //导航目的地
                stringBuilder.append("&poiname=" + URLEncoder.encode("测试", "utf-8"));
                //目的地经纬度
                stringBuilder.append("&lat=" + lat);
                stringBuilder.append("&lon=" + lng);
                stringBuilder.append("&dev=1&style=2");
            } catch (Exception e) {
                e.printStackTrace();
            }
            //调用高德地图APP
            Intent intent = new Intent();
            intent.setPackage(Constants.AMAP_PACKGE_NAME);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setAction(Intent.ACTION_VIEW);
            //传递组装的数据
            intent.setData(Uri.parse(stringBuilder.toString()));
            context.startActivity(intent);
        }
    }

    /**
     * 跳转百度地图
     *
     * @param context
     * @param lat
     * @param lng
     */
    public static void toBaiduMapNavigation(@NonNull Context context, @NonNull double lat, @NonNull double lng) {
        boolean isInstalled = isPkgInstalled(Constants.BAIDUMAP_PACKGE_NAME, context);
        if (isInstalled) {
            Intent intent = new Intent("android.intent.action.VIEW",
                    android.net.Uri.parse("baidumap://map/navi?location=" + lat + "," + lng));
            context.startActivity(intent);
        }

    }

    /**
     * 判断包是否存在
     *
     * @param packagename
     * @param context
     * @return
     */
    public static boolean isPkgInstalled(String packagename, Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(packagename, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    /**
     * 保存下载的图片流写入SD卡文件
     *
     * @param imageName xxx.jpg
     * @param body      image stream
     */
    public synchronized static void writeResponseBodyToDisk(String path, String imageName, ResponseBody body) {
        if (body == null) {
            ToastUtils.getInstance().showMessage("图片为空");
            return;
        }
        try {
            InputStream is = body.byteStream();
            File fileDr = new File(path);
            if (!fileDr.exists()) {
                fileDr.mkdirs();
            }
            File file = new File(path, imageName);
            if (file.exists()) {
                file.delete();
                file = new File(path, imageName);
            }
            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
            fos.flush();
            fos.close();
            bis.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
