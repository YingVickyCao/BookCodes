/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.example.hades.androidhacks.hack43.provider;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteTransactionListener;
import android.net.Uri;

import java.util.ArrayList;

public abstract class SQLiteContentProvider extends ContentProvider
        implements SQLiteTransactionListener {

    private SQLiteOpenHelper mOpenHelper;
    private volatile boolean mNotifyChange;
    protected SQLiteDatabase mDb;

    private final ThreadLocal<Boolean> mApplyingBatch = new ThreadLocal<Boolean>();
    private static final int SLEEP_AFTER_YIELD_DELAY = 4000;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        mOpenHelper = getDatabaseHelper(context);
        return true;
    }

    protected abstract SQLiteOpenHelper getDatabaseHelper(Context context);

    /**
     * The equivalent of the {@link #insert} method, but invoked within a
     * transaction.
     */
    protected abstract Uri insertInTransaction(Uri uri,
                                               ContentValues values);

    /**
     * The equivalent of the {@link #update} method, but invoked within a
     * transaction.
     */
    protected abstract int updateInTransaction(Uri uri,
                                               ContentValues values, String selection, String[] selectionArgs);

    /**
     * The equivalent of the {@link #delete} method, but invoked within a
     * transaction.
     */
    protected abstract int deleteInTransaction(Uri uri, String selection,
                                               String[] selectionArgs);

    protected abstract void notifyChange();

    protected SQLiteOpenHelper getDatabaseHelper() {
        return mOpenHelper;
    }

    private boolean applyingBatch() {
        return mApplyingBatch.get() != null && mApplyingBatch.get();
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri result = null;
        boolean applyingBatch = applyingBatch();
        //检查是否在进行批量处理
        if (!applyingBatch) {
            mDb = mOpenHelper.getWritableDatabase();
            mDb.beginTransactionWithListener(this);
            try {
                result = insertInTransaction(uri, values);
                if (result != null) {
                    mNotifyChange = true;
                }
                mDb.setTransactionSuccessful();
            } finally {
                mDb.endTransaction();
            }

            onEndTransaction();
        } else {
            //如果在批处理操作中，调用insertInTransaction()方法
            result = insertInTransaction(uri, values);
            if (result != null) {
                //如果插入了数据，就打开mNotifyChange标记，以使onEndTransaction()方法知道是否需要省略通知
                mNotifyChange = true;
            }
        }
        return result;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        int numValues = values.length;
        mDb = mOpenHelper.getWritableDatabase();
        mDb.beginTransactionWithListener(this);
        try {
            for (int i = 0; i < numValues; i++) {
                Uri result = insertInTransaction(uri, values[i]);
                if (result != null) {
                    mNotifyChange = true;
                }
                mDb.yieldIfContendedSafely();
            }
            mDb.setTransactionSuccessful();
        } finally {
            mDb.endTransaction();
        }

        onEndTransaction();
        return numValues;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int count = 0;
        boolean applyingBatch = applyingBatch();
        if (!applyingBatch) {
            mDb = mOpenHelper.getWritableDatabase();
            mDb.beginTransactionWithListener(this);
            try {
                count = updateInTransaction(uri, values, selection,
                        selectionArgs);
                if (count > 0) {
                    mNotifyChange = true;
                }
                mDb.setTransactionSuccessful();
            } finally {
                mDb.endTransaction();
            }

            onEndTransaction();
        } else {
            count = updateInTransaction(uri, values, selection, selectionArgs);
            if (count > 0) {
                mNotifyChange = true;
            }
        }

        return count;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        boolean applyingBatch = applyingBatch();
        if (!applyingBatch) {
            mDb = mOpenHelper.getWritableDatabase();
            mDb.beginTransactionWithListener(this);
            try {
                count = deleteInTransaction(uri, selection, selectionArgs);
                if (count > 0) {
                    mNotifyChange = true;
                }
                mDb.setTransactionSuccessful();
            } finally {
                mDb.endTransaction();
            }

            onEndTransaction();
        } else {
            count = deleteInTransaction(uri, selection, selectionArgs);
            if (count > 0) {
                // HARDOCDED FALSE to avoid notifications!
                mNotifyChange = false;
            }
        }
        return count;
    }

    @Override
    public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> operations) throws OperationApplicationException {
        mDb = mOpenHelper.getWritableDatabase();
        // 所有操作都在操作库事物中执行
        mDb.beginTransactionWithListener(this);

        try {
            mApplyingBatch.set(true);
            final int numOperations = operations.size();
            final ContentProviderResult[] results = new ContentProviderResult[numOperations];
            for (int i = 0; i < numOperations; i++) {
                final ContentProviderOperation operation = operations.get(i);
                if (i > 0 && operation.isYieldAllowed()) {
                    mDb.yieldIfContendedSafely(SLEEP_AFTER_YIELD_DELAY);
                }
                //这种实现也是调用apply()方法
                results[i] = operation.apply(this, results, i);
            }
            //结束数据库事务
            mDb.setTransactionSuccessful();
            return results;
        } finally {
            mApplyingBatch.set(false);
            mDb.endTransaction();
            //onEndTransaction负责在所有操作执行完毕后，通知数据发生变化
            onEndTransaction();
        }
    }

    public void onBegin() {
        onBeginTransaction();
    }

    public void onCommit() {
        beforeTransactionCommit();
    }

    public void onRollback() {
        // not used
    }

    protected void onBeginTransaction() {
    }

    protected void beforeTransactionCommit() {
    }

    protected void onEndTransaction() {
        if (mNotifyChange) {
            mNotifyChange = false;
            notifyChange();
        }
    }

}
