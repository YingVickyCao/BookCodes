/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.example.hades.androidhacks.hack30.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hades.androidhacks.R;

public class CountryView extends LinearLayout implements Checkable {

    private TextView mTitle;
    private CheckBox mCheckBox;

    public CountryView(Context context) {
        this(context, null);
    }

    public CountryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.activity_hack_30_country_view, this, true);
        mTitle = v.findViewById(R.id.country_view_title);
        mCheckBox = v.findViewById(R.id.country_view_checkbox);
    }

    public void setTitle(String title) {
        mTitle.setText(title);
    }

    /**
     * 重写所有Checkable接口方法
     * 这里每个被实现的接口方法都调用了mCheckBox的相应方法。这意味着，在ListView中选择一行时，会调用CountryView的setChecked()方法。
     */
    @Override
    public boolean isChecked() {
        return mCheckBox.isChecked();
    }

    @Override
    public void setChecked(boolean checked) {
        mCheckBox.setChecked(checked);
    }

    @Override
    public void toggle() {
        mCheckBox.toggle();
    }

}
