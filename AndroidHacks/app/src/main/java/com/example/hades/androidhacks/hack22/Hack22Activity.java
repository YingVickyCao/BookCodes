/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.example.hades.androidhacks.hack22;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.hades.androidhacks.R;
import com.example.hades.androidhacks.androidlib.hack022.LoginActivity;

public class Hack22Activity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hack_22);
    }

    public void onClick(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }
}
