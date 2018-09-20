package com.pinnet.chargerapp.mvp.sqlitehelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.pinnet.chargerapp.mvp.model.beans.charger.RouteHistoryBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;

/**
 * @author P00558
 * @date 2018/4/28
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "charger_app.db";//数据库名字
    private static final int DATABASE_VERSION = 1;//数据库版本号
    public static final String TABLE_NAME_ROUTE_HISTORY = "TABLE_RouteHistory";
    public volatile static SQLiteHelper instance;

    public static SQLiteHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (SQLiteHelper.class) {
                if (instance == null) {
                    instance = new SQLiteHelper(context);
                }
            }
        }
        return instance;
    }

    public SQLiteHelper(Context context) {
        this(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME_ROUTE_HISTORY + " (userId text , "
                + "startPointName text,startPointLat double,startPointLng double,"
                + " endPointName text,endPointLat double, endPointLng double,"
                + "insertTime double,addressType int)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_NAME_ROUTE_HISTORY);
    }


    public Flowable<Boolean> insertRouteHistory(final SQLiteDatabase database, final RouteHistoryBean bean) {
        return Flowable.create(new FlowableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(FlowableEmitter<Boolean> e) throws Exception {
                ContentValues values = new ContentValues();
                values.put("userId", bean.getUserId());
                values.put("startPointName", bean.getStartPointName());
                values.put("startPointLat", bean.getStartPointLat());
                values.put("startPointLng", bean.getStartPointLng());
                values.put("endPointName", bean.getEndPointName());
                values.put("endPointLat", bean.getEndPointLat());
                values.put("endPointLng", bean.getEndPointLng());
                values.put("insertTime", System.currentTimeMillis());
                values.put("addressType", 0);
                if (bean.isHome()) {
                    values.put("addressType", 1);
                }
                if (bean.isCompany()) {
                    values.put("addressType", 2);
                }
                if (database.insert(SQLiteHelper.TABLE_NAME_ROUTE_HISTORY, null, values) != -1) {
                    e.onNext(true);
                } else {
                    e.onNext(false);
                }
                e.onComplete();
                database.close();
            }
        }, BackpressureStrategy.BUFFER);
    }

    public Flowable<List<RouteHistoryBean>> getUserRouteHistory(final SQLiteDatabase database, final String userId) {
        return Flowable.create(new FlowableOnSubscribe<List<RouteHistoryBean>>() {
            @Override
            public void subscribe(FlowableEmitter<List<RouteHistoryBean>> e) throws Exception {
                String table = SQLiteHelper.TABLE_NAME_ROUTE_HISTORY;
                List<RouteHistoryBean> beanList = new ArrayList<RouteHistoryBean>();

                Cursor cursor = database.query(table, null, "userId = ?", new String[]{userId}, null, null, " insertTime desc");
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        RouteHistoryBean bean = new RouteHistoryBean();
                        bean.setUserId(cursor.getString(cursor.getColumnIndex("userId")));
                        bean.setStartPointName(cursor.getString(cursor.getColumnIndex("startPointName")));
                        bean.setStartPointLat(cursor.getDouble(cursor.getColumnIndex("startPointLat")));
                        bean.setStartPointLng(cursor.getDouble(cursor.getColumnIndex("startPointLng")));
                        bean.setEndPointName(cursor.getString(cursor.getColumnIndex("endPointName")));
                        bean.setEndPointLat(cursor.getDouble(cursor.getColumnIndex("endPointLat")));
                        bean.setEndPointLng(cursor.getDouble(cursor.getColumnIndex("endPointLng")));
                        bean.setInsertTime(cursor.getDouble(cursor.getColumnIndex("insertTime")));
                        int addressType = cursor.getInt(cursor.getColumnIndex("addressType"));
                        if (addressType == 1) {
                            bean.setHome(true);
                        }
                        if (addressType == 2) {
                            bean.setCompany(true);
                        }
                        beanList.add(bean);
                    }
                }
                cursor.close();
                database.close();
                e.onNext(beanList);
                e.onComplete();
            }
        }, BackpressureStrategy.BUFFER);
    }

    public Flowable<RouteHistoryBean> getUserHomeAddress(final SQLiteDatabase database, final String userId) {
        return Flowable.create(new FlowableOnSubscribe<RouteHistoryBean>() {
            @Override
            public void subscribe(FlowableEmitter<RouteHistoryBean> e) throws Exception {
                String table = SQLiteHelper.TABLE_NAME_ROUTE_HISTORY;
                RouteHistoryBean bean = null;
                Cursor cursor = database.query(table, null, "userId = ? and addressType=?", new String[]{userId, "1"}, null, null, " insertTime desc");
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        bean = new RouteHistoryBean();
                        bean.setUserId(cursor.getString(cursor.getColumnIndex("userId")));
                        bean.setStartPointName(cursor.getString(cursor.getColumnIndex("startPointName")));
                        bean.setStartPointLat(cursor.getDouble(cursor.getColumnIndex("startPointLat")));
                        bean.setStartPointLng(cursor.getDouble(cursor.getColumnIndex("startPointLng")));
                        bean.setEndPointName(cursor.getString(cursor.getColumnIndex("endPointName")));
                        bean.setEndPointLat(cursor.getDouble(cursor.getColumnIndex("endPointLat")));
                        bean.setEndPointLng(cursor.getDouble(cursor.getColumnIndex("endPointLng")));
                        bean.setInsertTime(cursor.getDouble(cursor.getColumnIndex("insertTime")));
                        int addressType = cursor.getInt(cursor.getColumnIndex("addressType"));
                        if (addressType == 1) {
                            bean.setHome(true);
                        }
                        if (addressType == 2) {
                            bean.setCompany(true);
                        }
                        break;
                    }
                }
                cursor.close();
                database.close();
                e.onNext(bean);
                e.onComplete();
            }
        }, BackpressureStrategy.BUFFER);
    }

    public Flowable<RouteHistoryBean> getUserCompanyAddress(final SQLiteDatabase database, final String userId) {
        return Flowable.create(new FlowableOnSubscribe<RouteHistoryBean>() {
            @Override
            public void subscribe(FlowableEmitter<RouteHistoryBean> e) throws Exception {
                String table = SQLiteHelper.TABLE_NAME_ROUTE_HISTORY;
                RouteHistoryBean bean = null;
                Cursor cursor = database.query(table, null, "userId = ? and addressType=?", new String[]{userId, "2"}, null, null, " insertTime desc");
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        bean = new RouteHistoryBean();
                        bean.setUserId(cursor.getString(cursor.getColumnIndex("userId")));
                        bean.setStartPointName(cursor.getString(cursor.getColumnIndex("startPointName")));
                        bean.setStartPointLat(cursor.getDouble(cursor.getColumnIndex("startPointLat")));
                        bean.setStartPointLng(cursor.getDouble(cursor.getColumnIndex("startPointLng")));
                        bean.setEndPointName(cursor.getString(cursor.getColumnIndex("endPointName")));
                        bean.setEndPointLat(cursor.getDouble(cursor.getColumnIndex("endPointLat")));
                        bean.setEndPointLng(cursor.getDouble(cursor.getColumnIndex("endPointLng")));
                        bean.setInsertTime(cursor.getDouble(cursor.getColumnIndex("insertTime")));
                        int addressType = cursor.getInt(cursor.getColumnIndex("addressType"));
                        if (addressType == 1) {
                            bean.setHome(true);
                        }
                        if (addressType == 2) {
                            bean.setCompany(true);
                        }
                        break;
                    }
                }
                cursor.close();
                database.close();
                e.onNext(bean);
                e.onComplete();
            }
        }, BackpressureStrategy.BUFFER);
    }

    /**
     * 删除单条记录
     *
     * @param database
     * @param whear
     * @param whearArgs
     * @return
     */
    public Flowable<Boolean> deleteSingleHistory(final SQLiteDatabase database, final String whear, final String[] whearArgs) {
        return Flowable.create(new FlowableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(FlowableEmitter<Boolean> e) throws Exception {
                database.beginTransaction();
                if (database.delete(SQLiteHelper.TABLE_NAME_ROUTE_HISTORY, whear, whearArgs) != -1) {
                    e.onNext(true);
                } else {
                    e.onNext(false);
                }
                e.onComplete();
                database.close();
            }
        }, BackpressureStrategy.BUFFER);
    }

    /**
     * 删除家的地址
     *
     * @param database
     * @return
     */
    public Flowable<Boolean> deleteHomeAddress(final SQLiteDatabase database) {
        return deleteSingleHistory(database, "addressType", new String[]{"1"});
    }

    /**
     * 删除公司的地址
     *
     * @param database
     * @return
     */
    public Flowable<Boolean> deleteCompanyAddress(final SQLiteDatabase database) {
        return deleteSingleHistory(database, "addressType", new String[]{"2"});
    }
}
