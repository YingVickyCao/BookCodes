/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.example.hades.androidhacks.hack37;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Audio.Media;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.hades.androidhacks.R;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;

import io.reactivex.functions.Consumer;

public class Hack37Activity extends Activity {
    private static final String FOLDER_PATH = Environment.getExternalStorageDirectory().getPath() + "/60AH/hack037/";
    private static final String LOOP1_PATH = FOLDER_PATH + "loop1.mp3";
    private static final String LOOP2_PATH = FOLDER_PATH + "loop2.mp3";
    private boolean mIsGrantedPermission = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hack_37);
    }

    public void onLoop1Click(View v) {
        grantPermission();
        if (!canWriteInExternalStorage()) {
            Toast.makeText(this, "Can't write file", Toast.LENGTH_SHORT).show();
            return;
        }

        //首先将文件存储到外部存储器中
        Log.d("TAG", "LOOP1: " + LOOP1_PATH);
        MediaUtils.saveRaw(this, R.raw.loop1, LOOP1_PATH);

        //完成插入媒体文件所需的所有字段
        ContentValues values = new ContentValues(5);
        values.put(Media.ARTIST, "Android");
        values.put(Media.ALBUM, "60AH");
        values.put(Media.TITLE, "hack043");
        values.put(Media.MIME_TYPE, "audio/mp3");
        values.put(Media.DATA, LOOP1_PATH);

        //通过uri将所有值插入到ContentProvider中
        getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, values);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    // TODO:
    public void onLoop2Click(View v) {
        grantPermission();
        if (!canWriteInExternalStorage()) {
            Toast.makeText(this, "Can't write file", Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        //首先将文件保存到外部存储器
        MediaUtils.saveRaw(this, R.raw.loop2, LOOP2_PATH);

//        Uri uri = Uri.parse("file://" + LOOP2_PATH);
//        Intent i = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
//        //发送广播，请求扫描并添加指定的文件
//        sendBroadcast(i);


        Uri uri = Uri.fromFile(new File(LOOP2_PATH));
        // SendBroadcastPermission: action:android.intent.action.MEDIA_SCANNER_SCAN_FILE, mPermissionType:0
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(uri);

//        //发送广播，请求扫描并添加指定的文件
        sendBroadcast(intent);
    }

    private void grantPermission() {
        RxPermissions rxPermissions = new RxPermissions(this);
        // Must be done during an initialization phase like onCreate
        rxPermissions
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        if (granted) { // Always true pre-M
                            // I can control the camera now
                            mIsGrantedPermission = true;

                        } else {
                            // Oups permission denied
                            mIsGrantedPermission = false;
                        }
                    }
                });


    }

    private boolean canWriteInExternalStorage() {
        if (!mIsGrantedPermission) {
            return false;
        }

        boolean mExternalStorageAvailable = false;
        boolean mExternalStorageWriteable = false;
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // We can read and write the media
            mExternalStorageAvailable = mExternalStorageWriteable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            // We can only read the media
            mExternalStorageAvailable = true;
            mExternalStorageWriteable = false;
        } else {
            // Something else is wrong. It may be one of many other states,
            // but all we need to know is we can neither read nor write
            mExternalStorageAvailable = mExternalStorageWriteable = false;
        }

        return mExternalStorageAvailable && mExternalStorageWriteable;
    }
}
