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
import com.example.hades.androidhacks.hack43.adapter.BatchAdapter;
import com.example.hades.androidhacks.hack43.provider.BatchNumbersContentProvider;
import com.example.hades.androidhacks.hack43.service.BatchService;

public class BatchActivity extends Activity {
    private static final String[] PROJECTION = new String[]{
            BatchNumbersContentProvider.COLUMN_ID,
            BatchNumbersContentProvider.COLUMN_TEXT};

    private ListView mListView;
    private BatchAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hack_43_list);
//        updateList();

        mListView = findViewById(R.id.list_listview);
        Cursor cursor = getCursor();
        mAdapter = new BatchAdapter(this, cursor);
        mListView.setAdapter(mAdapter);
    }

    private Cursor getCursor() {
        return getContentResolver().query(BatchNumbersContentProvider.CONTENT_URI, PROJECTION, null, null, BatchNumbersContentProvider.DEFAULT_SORT_ORDER);
    }

    private void updateList() {
        startService(new Intent(this, BatchService.class));
    }

    public void onUpdateClick(View v) {
        updateList();
    }
}
