/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.example.hades.androidhacks.hack39;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

import java.util.List;

class ActivityHelper {
    /**
     * 判断手机是否安装某个应用
     *
     * @param context
     * @param appPackageName 应用包名
     * @return true：安装，false：未安装  判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
     */
    public static boolean isApplicationInstalled(Context context, String appPackageName) {
        PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> info = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (info != null) {
            for (int i = 0; i < info.size(); i++) {
                String pn = info.get(i).packageName;
                if (appPackageName.equals(pn)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isApplicationInstalled2(Context ctx, final String packageName) {
        PackageManager pm = ctx.getPackageManager();
        try {
            pm.getApplicationInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            // 如果发生NameNotFoundException 异常，则确定应用程序不可用
            return false;
        }
    }

    /**
     * @param context
     * @param packageName id为包名. "com.skype.android.verizon" => "market://details?id=com.skype.android.verizon"
     * @return
     */
    //创建一个AlertDialog,让用户决定是否需要从Market上下载
    static AlertDialog showDownloadDialog(final Context context, final String packageName) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(context);
        downloadDialog.setTitle("app is not available");
        downloadDialog.setMessage("Do you want to download it from the market?");
        downloadDialog.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        downloadApp(context, packageName);
                    }

                });

        downloadDialog.setNegativeButton("No",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }

                });

        return downloadDialog.show();
    }

    private static void downloadApp(final Context context, final String packageName) {
        //要启动Market,可以使用uri并指定Intent的action为ACTION_VIEW
        Uri uri = Uri.parse("market://details?id=" + packageName);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException anfe) {
            //一些Android设备上可能没有Market应用程序，这里使用try－catch块确保应用不会崩溃
            Toast.makeText(context, "Market not installed", Toast.LENGTH_SHORT).show();
        }
    }
}