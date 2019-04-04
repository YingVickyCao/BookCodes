/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.example.hades.androidhacks.hack30;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hades.androidhacks.mockdata.Countries;
import com.example.hades.androidhacks.R;
import com.example.hades.androidhacks.hack30.adapter.CountryAdapter;
import com.example.hades.androidhacks.hack30.model.Country;

import java.util.ArrayList;
import java.util.List;

public class Hack30Activity extends Activity {
    private ListView mListView;
    private CountryAdapter mAdapter;
    private List<Country> mCountries;
    private String mToastFmt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hack_30);

        createCountriesList();
        mToastFmt = getString(R.string.toast_fmt);
        mAdapter = new CountryAdapter(this, -1, mCountries);
        mListView = findViewById(R.id.activity_main_list);
        mListView.setAdapter(mAdapter);
        
    }

    public void onPickCountryClick(View v) {
        /**
         * 不管使用singleChoice还是multipleChoice，所选列表项的位置信息都会被自动保存 - ListView.getCheckedItemPosition()。
         */
        int pos = mListView.getCheckedItemPosition();

        if (ListView.INVALID_POSITION != pos) {
            String msg = String.format(mToastFmt, mCountries.get(pos).getName());
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }

    private void createCountriesList() {
        mCountries = new ArrayList<Country>(Countries.COUNTRIES.length);
        for (int i = 0; i < Countries.COUNTRIES.length; i++) {
            Country country = new Country();
            country.setName(Countries.COUNTRIES[i]);
            mCountries.add(country);
        }
    }

}
