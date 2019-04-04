/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.example.hades.androidhacks.hack43.service;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;

import com.example.hades.androidhacks.hack43.provider.NoBatchNumbersContentProvider;

import static com.example.hades.androidhacks.hack43.Hack43Activity.ONCE_FRESH_DATA_NUM;

public class NoBatchService extends IntentService {
    public NoBatchService() {
        super(NoBatchService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        deleteOldNum();
        insertNewNum();
    }

    private void deleteOldNum() {
        ContentResolver contentResolver = getContentResolver();
        contentResolver.delete(NoBatchNumbersContentProvider.CONTENT_URI, null, null);
    }

    private void insertNewNum() {
        ContentResolver contentResolver = getContentResolver();
        //在for循环中，创建ContentView，并通过ContentResolver插入数字
        for (int i = 1; i <= ONCE_FRESH_DATA_NUM; i++) {
            ContentValues cv = new ContentValues();
            cv.put(NoBatchNumbersContentProvider.COLUMN_TEXT, "" + i);
            contentResolver.insert(NoBatchNumbersContentProvider.CONTENT_URI, cv);
        }
    }
}
