# Hack 36 在用户反馈中收集信息

## 问题：用户如何告诉开发者遇到了什么问题？     
## 解决方案：关注用户反馈（feedback）是确保应用程序成功有效的方式之一。用户反馈可以突出用户最喜欢应用程序的哪些部分，用户反馈还可能要求开发一些新特性以改进应用程序。 

Sample：展示如何在反馈邮件中附加用户设备信息。     
要实现上述功能，需要创建两个类：一个用于收集用户信息，另一个用于提供发送反馈邮件的Intent。    

`EnvironmentInfoUtil.java`
```

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
```


`LaunchEmailUtil.java`     
```
/**
 * 用于提供反馈邮件的 intent
 */
public class LaunchEmailUtil {
    public static void launchEmailToIntent(Context context) {
        Intent msg = new Intent(Intent.ACTION_SEND);

        StringBuilder body = new StringBuilder("\n\n----------\n");
        body.append(EnvironmentInfoUtil.getApplicationInfo(context));
        msg.putExtra(Intent.EXTRA_EMAIL, new String[]{"feed@back.com"});
        msg.putExtra(Intent.EXTRA_SUBJECT, "[50AH] Feedback");
        msg.putExtra(Intent.EXTRA_TEXT, body.toString());

        msg.setType("message/rfc822");
        context.startActivity(Intent.createChooser(msg, "Send feedback"));
    }
}
```


为了防备用户安装不止一个可以发送邮件的应用程序，创建一个应用程序选择器并为该选择器指定自定义标题。   

在准备发送私人信息之前，一定让用户知晓。

## References:
- [Build](https://developer.android.google.cn/reference/android/os/Build.html)
- [TelephonyManager](https://developer.android.google.cn/reference/android/telephony/TelephonyManager.html)