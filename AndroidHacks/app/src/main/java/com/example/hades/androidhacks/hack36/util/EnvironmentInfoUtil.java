/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.example.hades.androidhacks.hack36.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.telephony.TelephonyManager;

/**
 * 用于收集用户信息
 */
public class EnvironmentInfoUtil {

    //便于获取所有可用信息的方法
    public static String getApplicationInfo(Context context) {
        return String.format("%s\n%s\n%s\n%s\n%s\n%s\n",
                getCountry(context), getBrandInfo(), getModelInfo(),
                getDeviceInfo(), getVersionInfo(context), getLocale(context));
    }


    //使用TelephonyManager确定用户所在国家
    public static String getCountry(Context context) {
        TelephonyManager mTelephonyMgr = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return String.format("Country: %s", mTelephonyMgr.getNetworkCountryIso());
    }

    //从Build类中获取信息
    public static String getModelInfo() {
        return String.format("Model: %s", Build.MODEL);
    }

    public static String getBrandInfo() {
        return String.format("Brand: %s", Build.BRAND);
    }

    public static String getDeviceInfo() {
        return String.format("Device: %s", Build.DEVICE);
    }

    //使用Context获取用户本地化信息
    public static String getLocale(Context context) {
        return String.format("Locale: %s", context.getResources()
                .getConfiguration().locale.getDisplayName());
    }

    public static String getVersionInfo(Context context) {
        String version = null;

        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            version = info.versionName + " (release " + info.versionCode
                    + ")";
        } catch (NameNotFoundException e) {
            version = "not_found";
        }

        return String.format("Version: %s", version);
    }
}
