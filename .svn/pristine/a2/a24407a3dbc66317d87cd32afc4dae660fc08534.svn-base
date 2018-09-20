package com.pinnet.chargerapp.service;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;


import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.UUID;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by p00322 on 2017/2/17.
 */
public class GetAndroidId {
    static String uniqueId = "";

    public static String getMyUUID(final Context context) {
        uniqueId = "";
        final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        final String tmDevice, tmSerial, tmPhone, androidId;

        tmDevice = "" + tm.getDeviceId();

        tmSerial = "" + tm.getSimSerialNumber();

        androidId = "" + Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());

        uniqueId = deviceUuid.toString();

        Log.d("debug", "uuid=" + uniqueId);
        return uniqueId;


    }
}
