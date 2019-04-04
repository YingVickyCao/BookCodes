/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.example.hades.androidhacks.hack36;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.hades.androidhacks.R;
import com.example.hades.androidhacks.hack36.util.LaunchEmailUtil;

public class Hack36Activity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hack_36);
    }

    public void sendFeedback(View v) {
        LaunchEmailUtil.launchEmailToIntent(this);
    }
}
