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
import com.example.hades.androidhacks.hack43.adapter.SQLContentProviderAdapter;
import com.example.hades.androidhacks.hack43.provider.MySQLContentProvider;
import com.example.hades.androidhacks.hack43.service.SQLContentProviderService;

public class SQLContentProviderActivity extends Activity {
    private static final String[] PROJECTION = new String[]{MySQLContentProvider.COLUMN_ID, MySQLContentProvider.COLUMN_TEXT};

    private ListView mListView;
    private SQLContentProviderAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hack_43_list);
        updateList();

        mListView = findViewById(R.id.list_listview);
        Cursor cursor = getCursor();
        mAdapter = new SQLContentProviderAdapter(this, cursor);
        mListView.setAdapter(mAdapter);
    }

    private Cursor getCursor() {
        return getContentResolver().query(MySQLContentProvider.CONTENT_URI, PROJECTION, null, null, MySQLContentProvider.DEFAULT_SORT_ORDER);
    }

    private void updateList() {
        startService(new Intent(this, SQLContentProviderService.class));
    }

    public void onUpdateClick(View v) {
        updateList();
    }
}
