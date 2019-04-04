/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.example.hades.androidhacks.hack35;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.example.hades.androidhacks.R;

public class Hack35Activity extends Activity {

    private static final int PICK_PICTURE = 10;
    private static final int TAKE_PICTURE = 11;
    private static final int PICK_OR_TAKE_PICTURE = 12;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hack_35);
    }

    /**
     * 拍照
     */
    public void onTakePicture(View v) {
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Intent chooserIntent = Intent.createChooser(takePhotoIntent, getString(R.string.pick_picture));
        startActivityForResult(chooserIntent, TAKE_PICTURE);
    }

    /**
     * 从相册中选择照片
     */
    public void onPickPicture(View v) {
        Intent pickIntent = new Intent(Intent.ACTION_GET_CONTENT);
        pickIntent.setType("image/*");
        Intent chooserIntent = Intent.createChooser(pickIntent, getString(R.string.take_picture));
        startActivityForResult(chooserIntent, PICK_PICTURE);
    }

    /**
     * 整合两种Intent
     */
    public void onTakeAndPickPicture(View v) {
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        Intent pickIntent = new Intent(Intent.ACTION_GET_CONTENT);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(pickIntent, getString(R.string.pick_both));
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{takePhotoIntent});

        startActivityForResult(chooserIntent, PICK_OR_TAKE_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }
        Toast.makeText(this, "onActivityResult with req code: " + requestCode, Toast.LENGTH_SHORT).show();
    }
}
