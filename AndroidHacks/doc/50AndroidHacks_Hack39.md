# Hack 39 从Market中获取依赖功能
# 问题：检查用户是否安装了某个 app，如果没有安装，则提示用户从Market 下载该app？
# 解决方案：使用PackageManager遍历包信息，查看是否已安装 app。如果没有，则send intent（market://details?id=" + packageName）来使用手机上的Market app 下载该 app。

- 实例中需要完成的功能：      
1. 判断是否安装Layar的方法     
2. 编写代码以打开Market下载Layar 
3. 打开一个特定图层的Intent调用


`ActivityHelper.java`
```
class ActivityHelper {
    /**
     * 判断手机是否安装某个应用
     *
     * @param context
     * @param appPackageName 应用包名
     * @return true：安装，false：未安装
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
```

`Hack39Activity.java`
```
 public void openAppClick(View v) {
        if (!ActivityHelper.isApplicationInstalled(this, APP_NAME)) {
            ActivityHelper.showDownloadDialog(this, APP_NAME);
        } else {
            useInstalledApp();
        }
    }

    private void useInstalledApp() {
        // 如果安装了 app，则打开某一个东西
//        Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_VIEW);
//        Uri uri = Uri.parse("layar://teather/?action=refresh");
//        intent.setData(uri);
//        startActivity(intent);

        Intent i = new Intent();
        ComponentName cn = new ComponentName(APP_NAME, APP_MAIN_NAME);
        i.setComponent(cn);
        startActivityForResult(i, RESULT_OK);
    }
}
```

## References:
- [走在路上程序员 - Hack 39 从Market中获取依赖功能](https://blog.csdn.net/hzc_01/article/details/50106027)
- [https://blog.csdn.net/cstarbl/article/details/23741361](https://blog.csdn.net/cstarbl/article/details/23741361)
- [android如何判断手机上是否安装了浏览器](https://blog.csdn.net/liupengcheng201/article/details/77867540)
- [Android 判断手机是否安装某个应用](https://blog.csdn.net/u010880009/article/details/53883396)