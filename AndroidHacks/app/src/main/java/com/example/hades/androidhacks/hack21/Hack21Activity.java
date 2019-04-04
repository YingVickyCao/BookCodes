/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.example.hades.androidhacks.hack21;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;

import com.example.hades.androidhacks.R;

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