/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.example.hades.androidhacks.hack43.service;

import android.app.IntentService;
import android.content.ContentProviderOperation;
import android.content.ContentProviderOperation.Builder;
import android.content.ContentValues;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.os.RemoteException;
import android.util.Log;

import com.example.hades.androidhacks.hack43.provider.MySQLContentProvider;

import java.util.ArrayList;

import static com.example.hades.androidhacks.hack43.Hack43Activity.ONCE_FRESH_DATA_NUM;


public class SQLContentProviderService extends IntentService {
    private static final String TAG = SQLContentProviderService.class.getCanonicalName();

    public SQLContentProviderService() {
        super(SQLContentProviderService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // 思路是创建一个ContentProviderOperation的列表，然后一起处理列表项。
        try {
            //以操作列表为参数调用applyBatch()方法
            getContentResolver().applyBatch(MySQLContentProvider.AUTHORITY, getContentProviderOperationList());
        } catch (RemoteException e) {
            Log.e(TAG, "Couldn't apply batch: " + e.getMessage());
        } catch (OperationApplicationException e) {
            Log.e(TAG, "Couldn't apply batch: " + e.getMessage());
        }
    }

    //创建ContentProviderOperations的列表
    private ArrayList<ContentProviderOperation> getContentProviderOperationList() {
        ArrayList<ContentProviderOperation> operations = new ArrayList<>();

        //使用ContentProviderOperations的构建器创建删除操作，并将该操作添加到操作列表中
        operations.add(createNewDeleteBuilder().build());

        for (int i = 1; i <= ONCE_FRESH_DATA_NUM; i++) {
            operations.add(createNewInsertBuilder(i).build());
        }

        return operations;
    }

    //创建ContentProviderOperations的列表
    private Builder createNewInsertBuilder(int index) {
        ContentValues cv = new ContentValues();
        cv.put(MySQLContentProvider.COLUMN_TEXT, "" + index);

        Builder builder = ContentProviderOperation.newInsert(MySQLContentProvider.CONTENT_URI);
        //为每个数字创建要给插入操作
        builder.withValues(cv);
        return builder;
    }

    private Builder createNewDeleteBuilder() {
        return ContentProviderOperation.newDelete(MySQLContentProvider.CONTENT_URI);
    }

}
