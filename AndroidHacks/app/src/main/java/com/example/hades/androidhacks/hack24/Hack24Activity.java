/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.example.hades.androidhacks.hack24;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.hades.androidhacks.R;

public class Hack24Activity extends Activity {

    private ListView mListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hack_24);
        mListView = (ListView) findViewById(R.id.my_list_view);
        mListView.setEmptyView(findViewById(R.id.empty_view));
    }
}
