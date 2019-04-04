# Hack 21 与Activity生命周期绑定的BroadcastReceiver

## 问题：通过BroadcastReceiver监听操作系统发出的不同通知，但开发者不能在BroadcastReceiver中访问Activity。 如何在Activity中获取BroadcastReceiver的信息？  

## 解决方案：将BroadcastReceiver做为Activity的内部类使用

假设需要根据网络链接状态更新UI，想在Activity中获取BroadcastReceiver的信息。这个时候就需要将BroadcastReceiver作为Activity的内部类使用以获取广播对应的Intent。   

**将BroadcastReceiver做为Activity的内部类使用**，可以实现两个重要功能：    
1. 在BroadcastReceiver内部访问Activity的方法   
2. 根据Activity的状态开启或关闭BroadcastReceiver   

示例：根据Service发送的广播更新ui。   

```
public class Hack21Activity extends Activity {
    private ProgressDialog mProgressDialog;
    private TextView mTextView;

    private BroadcastReceiver mReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hack_21);

        mTextView = findViewById(R.id.text_view);

        mProgressDialog = ProgressDialog.show(this, "Loading", "Please wait");
        mProgressDialog.show();

        mReceiver = new MyServiceReceiver();

        startService(new Intent(this, MyService.class));
    }

/**
 * 创建BroadcastReceiver对象，然后创建IntentFilter,通过该对象定义BroadcastReceiver对象可以监听哪种类型的Intent
 */
    private IntentFilter getIntentFilter() {
        return new IntentFilter(MyService.ACTION);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mReceiver, getIntentFilter());
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, MyService.class));
    }

    private void update(String msg) {
        mProgressDialog.dismiss();
        mTextView.setText(msg);
    }

    // TODO: 23/03/2018 refactor MyServiceReceiver
    class MyServiceReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            update(intent.getExtras().getString(MyService.MSG_KEY));
        }
    }
}
```

- 创建了一个BroadcastReceiver，只在Activity显示的时候才去更新UI。
- 在适当的时候取消注册BroadcastReceiver是避免不必要的UI更新操作的好方法。 
- 因为只在Activity内部使用BroadcastReceiver对象，因此需要在onResume()方法中注册，在onPause()方法中取消注册。

## References:
- [Intent](https://developer.android.google.cn/reference/android/content/Intent.html)
- [BroadcastReceiver](https://developer.android.google.cn/reference/android/content/BroadcastReceiver.html)