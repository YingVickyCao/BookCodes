/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.example.hades.androidhacks.hack7;

import android.app.Activity;
import android.os.Bundle;
import android.view.Display;

import com.example.hades.androidhacks.hack7.view.DrawView;


public class Hack7Activity extends Activity {

    private DrawView mDrawView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 获取屏幕的宽和高
        Display display = getWindowManager().getDefaultDisplay();

        // DrawView 占据所有可用空间
        mDrawView = new DrawView(this);
        mDrawView.height = display.getHeight();
        mDrawView.width = display.getWidth();

        setContentView(mDrawView);
    }
}