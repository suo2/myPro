package com.pinnet.chargerapp.mvp.sqlitehelper;

import com.pinnet.chargerapp.app.ChargerApp;
import com.pinnet.chargerapp.mvp.model.beans.charger.RouteHistoryBean;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * @author P00558
 * @date 2018/4/28
 */

public class ISQLiteHelperImpl implements ISQLiteHelper {
    private SQLiteHelper mSQLiteHelper;

    @Inject
    ISQLiteHelperImpl() {
        mSQLiteHelper = SQLiteHelper.getInstance(ChargerApp.getInstance());
    }

    @Override
    public Flowable<Boolean> insertRouteHistory(RouteHistoryBean bean) {
        return mSQLiteHelper.insertRouteHistory(mSQLiteHelper.getWritableDatabase(),bean);
    }

    @Override
    public Flowable<List<RouteHistoryBean>> getUserRouteHistory(String userId) {
        return mSQLiteHelper.getUserRouteHistory(mSQLiteHelper.getReadableDatabase(),userId);
    }
}
