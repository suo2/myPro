package com.pinnet.chargerapp.app;

import android.app.Activity;
import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.pinnet.chargerapp.dagger.component.ChargerAppComponent;
import com.pinnet.chargerapp.dagger.component.DaggerChargerAppComponent;
import com.pinnet.chargerapp.dagger.module.ChargerAppModule;
import com.pinnet.chargerapp.dagger.module.HttpModul;
import com.pinnet.chargerapp.utils.CrashHandler;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareConfig;

import java.util.Stack;

/**
 * @author P00558
 * @date 2018/4/2
 * Application
 */

public class ChargerApp extends MultiDexApplication {
    public static final String TAG = ChargerApp.class.getSimpleName();
    private static ChargerApp instance;
    public volatile static ChargerAppComponent appComponent;
    private RefWatcher refWatcher;
    /**
     * activity管理栈
     */
    private Stack<Activity> allActivities;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        refWatcher = setupLeakCanary();
        CrashHandler.getInstance().init(this);
        UMConfigure.init(this, "5b0b600ef29d9843ca0000f9"
                , "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
        PlatformConfig.setWeixin("wxce3e5ad15c03e1e6", "5021cf868b2344b6d0fa07ce3ca37dac");//微信APPID和AppSecret

    }

    private RefWatcher setupLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return RefWatcher.DISABLED;
        }
        return LeakCanary.install(this);
    }

    public static RefWatcher getRefWatcher(Context context) {
        ChargerApp leakApplication = (ChargerApp) context.getApplicationContext();
        return leakApplication.refWatcher;
    }

    /**
     * 添加Activity 至栈内
     *
     * @param act
     */
    public void addActivity(Activity act) {
        if (allActivities == null) {
            allActivities = new Stack<>();
        }
        allActivities.add(act);
    }

    /**
     * 从栈内移除Activity
     *
     * @param act
     */
    public void removeActivity(Activity act) {
        if (allActivities != null) {
            allActivities.remove(act);
            act.finish();
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void removeActivity(Class<?> cls) {
        synchronized (allActivities) {
            for (Activity activity : allActivities) {
                if (activity.getClass().equals(cls)) {
                    removeActivity(activity);
                }
            }
        }
    }
    public Activity getTaskTop() {
        return allActivities.get(allActivities.size() - 1);
    }
    /**
     * 退出APP
     */
    public void exitApp() {
        if (allActivities != null) {
            synchronized (allActivities) {
                for (Activity act : allActivities) {
                    if (act != null) {
                        act.finish();
                    }
                }
                allActivities.clear();
            }
        }
    }

    public static synchronized ChargerApp getInstance() {
        return instance;
    }

    public static ChargerAppComponent getAppComponent() {
        if (appComponent == null) {
            appComponent = DaggerChargerAppComponent.builder()
                    .chargerAppModule(new ChargerAppModule(instance))
                    .httpModul(new HttpModul())
                    .build();
        }
        return appComponent;
    }
}
