/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.example.hades.androidhacks.hack45;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.content.SharedPreferencesCompat;
import android.util.Log;
import android.widget.TextView;

import com.example.hades.androidhacks.R;

import java.util.Locale;

public class Hack45Activity extends Activity {
    private static final String TAG = Hack45Activity.class.getSimpleName();

    private static final String SF_FILE_NAME = "sf_name";
    private static final String SF_KEY = "increaseOne";
    private static final String COUNTER = "Counter is : %d";
    private TextView numTv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_hack_45);

        numTv = findViewById(R.id.num);

        findViewById(R.id.countAndCacheData).setOnClickListener(v -> countAndCacheData());
        findViewById(R.id.showCachedData).setOnClickListener(v -> showCachedData());
    }

    private void countAndCacheData() {
        Editor editor = getSharedPreferences(SF_FILE_NAME, 0).edit();
        editor.putInt(SF_KEY, increaseOne());
//        editor.apply();
        SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
    }

    private int increaseOne() {
        return getCachedData() + 1;
    }

    private int getCachedData() {
        SharedPreferences prefs = getSharedPreferences(SF_FILE_NAME, 0);
        int num = prefs.getInt(SF_KEY, 1);
        Log.d(TAG, "getCachedData: num=" + num);
        return num;
    }

    private void showCachedData() {
        int num = getCachedData();
        numTv.setText(String.format(Locale.US, COUNTER, num));
    }

}
