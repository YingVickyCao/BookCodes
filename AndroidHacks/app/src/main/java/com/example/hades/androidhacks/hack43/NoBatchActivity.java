/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.example.hades.androidhacks.hack43;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.hades.androidhacks.R;
import com.example.hades.androidhacks.hack43.adapter.NoBatchAdapter;
import com.example.hades.androidhacks.hack43.provider.NoBatchNumbersContentProvider;
import com.example.hades.androidhacks.hack43.service.NoBatchService;

public class NoBatchActivity extends Activity {
    private static final String[] PROJECTION = new String[]{
            NoBatchNumbersContentProvider.COLUMN_ID,
            NoBatchNumbersContentProvider.COLUMN_TEXT};

    private ListView mListView;
    private NoBatchAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hack_43_list);

        mListView = findViewById(R.id.list_listview);
        findViewById(R.id.onUpdateClick).setOnClickListener(v -> onUpdateClick(v));

        // TODO: 23/05/2018
        Cursor cursor = getCursor();
        mAdapter = new NoBatchAdapter(this, cursor);
        mListView.setAdapter(mAdapter);

        //        updateList();
    }

    private Cursor getCursor() {
        return getContentResolver().query(NoBatchNumbersContentProvider.CONTENT_URI, PROJECTION, null, null, NoBatchNumbersContentProvider.DEFAULT_SORT_ORDER);
    }

    private void updateList() {
        startNoBatchService();
    }

    private void startNoBatchService() {
        startService(new Intent(this, NoBatchService.class));
    }

    public void onUpdateClick(View v) {
        updateList();
    }
}
