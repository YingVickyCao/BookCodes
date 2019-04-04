/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.example.hades.androidhacks.hack21;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;

import java.util.Date;

/**
 * MyService 如果没有在 AndroidManifest 中注册：MyService不起作用，但也不抛出异常
 */
public class MyService extends IntentService {

    public static final String ACTION = "com.manning.androidhacks.hack021.SERVICE_MSG";
    public static final String MSG_KEY = "MSG_KEY";

    public MyService() {
        super("MyService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SystemClock.sleep(5000L);
        sendBroadcast();
    }

    private void sendBroadcast() {
        Intent broadcast = new Intent(ACTION);
        broadcast.putExtra(MSG_KEY, new Date().toGMTString());
        sendBroadcast(broadcast);
    }
}
