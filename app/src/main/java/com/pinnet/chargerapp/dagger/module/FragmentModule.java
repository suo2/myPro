package com.pinnet.chargerapp.dagger.module;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.pinnet.chargerapp.dagger.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * @author P00558
 * @date 2018/4/2
 */
@Module
public class FragmentModule {
    private Fragment mFragment;

    public FragmentModule(Fragment fragment) {
        this.mFragment = fragment;
    }

    @FragmentScope
    @Provides
    public Activity providerActivity() {
        return mFragment.getActivity();
    }
}
