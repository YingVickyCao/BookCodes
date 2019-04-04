/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.example.hades.androidhacks.hack30.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.hades.androidhacks.hack30.model.Country;
import com.example.hades.androidhacks.hack30.view.CountryView;

import java.util.List;


public class CountryAdapter extends ArrayAdapter<Country> {

    public CountryAdapter(Context context, int textViewResourceId, List<Country> objects) {
        super(context, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = new CountryView(getContext());
        }

        Country country = getItem(position);

        CountryView countryView = (CountryView) convertView;
        countryView.setTitle(country.getName());

        return convertView;
    }
}
