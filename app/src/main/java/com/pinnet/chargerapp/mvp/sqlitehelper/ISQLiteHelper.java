package com.pinnet.chargerapp.mvp.sqlitehelper;

import android.database.sqlite.SQLiteOpenHelper;

import com.pinnet.chargerapp.mvp.model.beans.charger.RouteHistoryBean;

import java.util.List;

import io.reactivex.Flowable;

/**
 * @author P00558
 * @date 2018/4/28
 */

public interface ISQLiteHelper {
    Flowable<Boolean> insertRouteHistory(RouteHistoryBean bean);

    Flowable<List<RouteHistoryBean>> getUserRouteHistory(String userId);
}
