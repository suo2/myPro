package com.pinnet.chargerapp.dagger.component;

import com.pinnet.chargerapp.app.ChargerApp;
import com.pinnet.chargerapp.dagger.module.ChargerAppModule;
import com.pinnet.chargerapp.dagger.module.HttpModul;
import com.pinnet.chargerapp.mvp.model.http.RetrofitHelper;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author P00558
 * @date 2018/4/8
 */
@Singleton
@Component(modules = {ChargerAppModule.class, HttpModul.class})
public interface ChargerAppComponent {
    ChargerApp getContext();

    RetrofitHelper getRetrofitHelper();
}
