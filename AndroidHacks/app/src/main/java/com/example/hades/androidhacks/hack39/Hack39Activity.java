/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.example.hades.androidhacks.hack39;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.hades.androidhacks.R;

/**
 * 使用真机测试。模拟器测试失败，因为没有自带 market app。
 * todo：手机浏览器可以检测是否安装了某个 app，如果没有提示安装，否则，在该app中打开。
 */
public class Hack39Activity extends Activity {
    private final static String APP_NAME = "com.skype.android.verizon";
    private final static String APP_MAIN_NAME = "com.skype.android.verizon.SkypeActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hack_39);
    }

    public void openAppClick(View v) {
        if (!ActivityHelper.isApplicationInstalled(this, APP_NAME)) {
            ActivityHelper.showDownloadDialog(this, APP_NAME);
        } else {
            useInstalledApp();
        }
    }

    private void useInstalledApp() {
        // 如果安装了 app，则打开某一个东西
//        Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_VIEW);
//        Uri uri = Uri.parse("layar://teather/?action=refresh");
//        intent.setData(uri);
//        startActivity(intent);

        Intent i = new Intent();
        ComponentName cn = new ComponentName(APP_NAME, APP_MAIN_NAME);
        i.setComponent(cn);
        startActivityForResult(i, RESULT_OK);
    }
}
