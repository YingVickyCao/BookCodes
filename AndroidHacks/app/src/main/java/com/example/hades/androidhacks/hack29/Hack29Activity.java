/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.example.hades.androidhacks.hack29;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;

import com.example.hades.androidhacks.R;
import com.example.hades.androidhacks.hack29.adapter.ColorAdapter;

public class Hack29Activity extends FragmentActivity {
    private static final String TAG = Hack29Activity.class.getSimpleName();

    private ViewPager mViewPager;
    private ColorAdapter mAdapter;
    private OnPageChangeListener mOnPageChangeListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");

        //1.设置默认屏幕方向
//        enforcePortrait();
        setContentView(R.layout.activity_hack_29);

        mViewPager = findViewById(R.id.pager);
        mAdapter = new ColorAdapter(getSupportFragmentManager());

        //2.引用viewpager
        mViewPager.setAdapter(mAdapter);

        mOnPageChangeListener = new OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (mAdapter.usesLandscape(position)) {
                    allowOrientationChanges();
                } else {
                    enforcePortrait();
                }

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        mViewPager.addOnPageChangeListener(mOnPageChangeListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mViewPager.removeOnPageChangeListener(mOnPageChangeListener);
    }

    public void allowOrientationChanges() {
        Log.d(TAG, "allowOrientationChanges: ");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
    }

    public void enforcePortrait() {
        Log.d(TAG, "enforcePortrait: ");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
}
