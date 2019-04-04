# Hack 45 在旧版本上使用新API
- 第一个API是Android v9中为SharedPreferences.Editor类新添加的apply()方法。 
- 第二个API是在Android API Level 8中引入的，用于在manifest文件中声明是否允许讲应用程序安装在SD卡上。

## 45.1 使用apply()替代commit()
### commit() VS apply() 
- 从Android v9版本开始，SharedPreferences.Editor提供了apply()方法用于替代commit()方法。 
- 官网文档的解释：
```
commit()方法会同步地将偏好值(Preference)直接写入持久化存储设备，  
apply()方法会立即把修改内容提交到SharedPreferences内容缓存中，然后开始异步地将修改提交到存储设备上，在这个过程中，开发者不会察觉到任何错误问题。  
所以，如果不需要用到操作的返回值，开发者就应该用apply()方法代替commit()方法。   
```
commit():同步，主线程操作,需要用到操作的返回值 
apply()：异步，在子线程操作，不需要用到操作的返回值   

### 如何使用apply()替代commit()
提交数据时，使用SharedPreferencesCompat.EditorCompat.getInstance().apply(editor) 代替 editor.apply().  

```
Editor editor = getSharedPreferences(SF_FILE_NAME, 0).edit();
editor.putInt(SF_KEY, increaseOne());
//  editor.apply();
SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
```
### SharedPreferencesCompat源码解析

New SharedPreferencesCompat源码解析:
```
public void apply(@NonNull SharedPreferences.Editor editor) {
    try {
        editor.apply();
    } catch (AbstractMethodError unused) {
        // The app injected its own pre-Gingerbread
        // SharedPreferences.Editor implementation without
        // an apply method.
        editor.commit();
    }
}
```
先调用了editor.apply()方法，如果发生异常的话，则再调用editor.commit()的方法。简单说就是优先使用apply()方法。

Old SharedPreferencesCompat源码解析:
```
public class SharedPreferencesCompat{
    private static final Method sApplyMethod = findApplyMethod();
    private static Method findApplyMethod(){
        //检查apply()方法是否可用
        try{
            Class cls = SharedPreferences.Editor.class;
            return cls.getMethod("apply");
        }catch(NoSuchMethodException unused){}
        return null;
    }
    public static void apply(SharedPreferences.Editor editor){
        if(sApplyMethod != null){
            try{
                //真正调用Editor的apply()方法
                sApplyMethod.invoke(editor);
                return;
                }catch (InvocationTargetException unused) {}catch (IllegalAccessException unused) {}
        }
        editor.commit();//否则调用commit()
    }

}
```
SharedPreferencesCompat类通过java反射机制检查SharedPreferences.Editor类的apply()方法是否可用。
如果可用就将其存放到一个静态变量中。当调用我们创建的apply()方法时，便通过传入的Editor参数真正触发该参数的apply()方法。如果调用失败就调用commit()方法。

## 45.2 将应用程序安装到SD卡中
从Android v8开始，可以向AndroidManifest中添加一个名为android:installLocation的属性。   
该属性开发文档的解释：   
`"这是一个可选特性，你可以通过在Manifest文件中声明android:installLocation属性来使用这项特性。如果你没有声明，应用程序就会安装到内部存储器上，并且你也不能把它移到外部存储器中。"`

要使用上述属性，我们需要在AndroidManifest.xml文件中修改下面几个代码：  
```
//设置android:installLocation属性值为preferExternal
<manifest ...
    package="com.manning.androidhacks.hack045"
    android:versionCode="1"
    android:versionName="1.0"
    android:installLocation="preferExternal">
    //设置minSdkVersion属性值为8
    <uses-sdk android:minSdkVersion="8"/>
```

将android:installLocation属性值设为preferExternal，有SD卡的时候，应用程序就会安装到SD卡上。要使用这个特性，需要将minSdkVersion属性值设置为8。在代码中如果指定上述内容，用户就无法在API 8下级别的Android上安装该应用。（这个现在已经没什么意义了），我们现在开发已经不会兼容8以下的版本了。    

使用 API Level 8的 JAR 包编译，并且使用了新的 API，但允许应用程序安装在 API Level4以及以上的设备中：   
`<uses-sdk android:minSdkVersion="8" android:targetSdkVersion="8"/>`

- 强行在高版本API Level上编译，会导致类和方法不能向后兼容，如：想在当前运行版本上调用一个该版本中没有提供的方法，会出现java.lang.VerifyError异常。  
- 开发过程中，使用最旧版本的设备已避免不兼容的隐患。  
- 当发现有较新的 API 无法在这种设备上使用时，就创建这样的兼容类，big 选择如何处理这种 新API。
- targetSdkVersion是即可以使用 android 新特性，又不会放弃使用旧版本系统的老用户的好方法。  

## References:
- https://www.jianshu.com/p/56b37a12f8ed
- https://blog.csdn.net/hzc_01/article/details/50106041
- https://developer.android.google.cn/training/search/backward-compat
- https://developer.android.google.cn/reference/android/content/SharedPreferences.Editor#apply