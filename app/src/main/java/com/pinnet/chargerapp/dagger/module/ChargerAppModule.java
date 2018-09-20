package com.pinnet.chargerapp.dagger.module;

import com.pinnet.chargerapp.app.ChargerApp;
import com.pinnet.chargerapp.mvp.model.DataManager;
import com.pinnet.chargerapp.mvp.model.http.HttpsHelper;
import com.pinnet.chargerapp.mvp.model.http.RetrofitHelper;
import com.pinnet.chargerapp.mvp.model.sharepref.SharePrefUtilHelper;
import com.pinnet.chargerapp.mvp.sqlitehelper.ISQLiteHelperImpl;
import com.pinnet.chargerapp.utils.SharePrefUtils;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author P00558
 * @date 2018/4/8
 */
@Module
public class ChargerAppModule {
    ChargerApp application;

    public ChargerAppModule(ChargerApp application) {
        this.application = application;
    }

    @Provides
    @Singleton
    ChargerApp provideApplicationContext() {
        return application;
    }

    @Provides
    @Singleton
    HttpsHelper provideHttpsHelper(RetrofitHelper retrofitHelper) {
        return retrofitHelper;
    }

    @Provides
    @Singleton
    SharePrefUtilHelper provideSharePrefUtilHelper(SharePrefUtils sharePrefUtils) {
        return sharePrefUtils;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(RetrofitHelper retrofitHelper, SharePrefUtils sharePrefUtilHelper, ISQLiteHelperImpl isqLiteHelper) {
        return new DataManager(retrofitHelper, sharePrefUtilHelper,isqLiteHelper);
    }
}
