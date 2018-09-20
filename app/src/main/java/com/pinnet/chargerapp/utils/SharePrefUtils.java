package com.pinnet.chargerapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.pinnet.chargerapp.app.ChargerApp;
import com.pinnet.chargerapp.app.Constants;
import com.pinnet.chargerapp.mvp.model.sharepref.SharePrefUtilHelper;

import javax.inject.Inject;

/**
 * @author P00558
 * @date 2018/4/12
 */

public class SharePrefUtils implements SharePrefUtilHelper {
    private static final String SHAREDPREFERENCES_NAME = "ChargerApp_SharePref";

    private final SharedPreferences mSPrefs;
    private static SharePrefUtils instance;

    public static SharePrefUtils getInstance() {
        if (instance == null) {
            instance = new SharePrefUtils();
        }
        return instance;
    }

    @Inject
    public SharePrefUtils() {
        mSPrefs = ChargerApp.getInstance().getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        instance = this;
    }

    @Override
    public String getLoginedUserName() {
        return mSPrefs.getString(Constants.LOGINED_USERNAME, "");
    }

    @Override
    public void setLoginedUserName(String userName) {
        mSPrefs.edit().putString(Constants.LOGINED_USERNAME, userName).apply();
    }

    @Override
    public boolean getUserIsLogined() {
        return !TextUtils.isEmpty(getLoginedUserId()) && !TextUtils.isEmpty(getLoginedTokenId());
    }

    @Override
    public void setLoginedUserId(String userId) {
        mSPrefs.edit().putString(Constants.LOGINED_USERID, userId).apply();
    }

    @Override
    public String getLoginedUserId() {
        return mSPrefs.getString(Constants.LOGINED_USERID, "");
    }

    @Override
    public void setLoginedTokenId(String tokenId) {
        mSPrefs.edit().putString(Constants.LOGINED_TOKEN_ID, tokenId).apply();
    }

    @Override
    public String getLoginedTokenId() {
        return mSPrefs.getString(Constants.LOGINED_TOKEN_ID, "");
    }

    @Override
    public void onLoginOut() {
        setLoginedTokenId(null);
        setLoginedUserId(null);
        setLoginedUserName(null);
    }

    @Override
    public void setLoginedAccount(String phoneNumber) {
        mSPrefs.edit().putString(Constants.LOGINED_ACCOUNT, phoneNumber).apply();
    }

    @Override
    public String getLoginedAccount() {
        return mSPrefs.getString(Constants.LOGINED_ACCOUNT, "");
    }

    /**
     *
     * @param status 1、停止充电;2、服务断连
     */
    public void setChargingStatus(int status) {
        mSPrefs.edit().putInt("ChargingStatus", status).apply();
    }

    public int getChargingStatus() {
        return mSPrefs.getInt("ChargingStatus", -1);
    }
}
