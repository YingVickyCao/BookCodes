/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.example.hades.androidhacks.hack43;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.hades.androidhacks.R;

public class Hack43Activity extends Activity {
    public static final int ONCE_FRESH_DATA_NUM=1000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hack_43_main);
        initView();
    }

    private void initView() {
        findViewById(R.id.onNoBatchClick).setOnClickListener(this::onNoBatchClick);
        findViewById(R.id.onBatchClick).setOnClickListener(this::onBatchClick);
        findViewById(R.id.onSQLContentProviderClick).setOnClickListener(this::onSQLContentProviderClick);
    }

    public void onNoBatchClick(View v) {
        startActivity(new Intent(this, NoBatchActivity.class));
    }

    public void onBatchClick(View v) {
        startActivity(new Intent(this, BatchActivity.class));
    }

    public void onSQLContentProviderClick(View v) {
        startActivity(new Intent(this, SQLContentProviderActivity.class));
    }

}
