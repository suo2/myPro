package com.pinnet.chargerapp.dagger.module;

import android.app.Activity;

import com.pinnet.chargerapp.dagger.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * @author P00558
 * @date 2018/4/2
 */
@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity activity) {
        this.mActivity = activity;
    }

    @ActivityScope
    @Provides
    public Activity providerActivity() {
        return mActivity;
    }
}
