/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.example.hades.androidhacks.hack27;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.hades.androidhacks.R;

import java.util.List;

public class NumbersAdapter extends ArrayAdapter<Integer> {
    private LayoutInflater mInflator;
    private NumbersAdapterDelegate mDelegate;

    public NumbersAdapter(Context context, List<Integer> objects) {
        super(context, 0, objects);
        mInflator = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View cv, ViewGroup parent) {
        if (null == cv) {
            cv = mInflator.inflate(R.layout.activity_hack_27_number_row, parent, false);
        }

        final Integer value = getItem(position);
        TextView tv = cv.findViewById(R.id.numbers_row_text);
        tv.setText(String.valueOf(value));

        View button = cv.findViewById(R.id.numbers_row_button);
        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //2.删除对象
                if (null != mDelegate) {
                    mDelegate.removeItem(value);
                }
            }
        });

        return cv;
    }

    //3.为适配器设置委托对象
    public void setDelegate(NumbersAdapterDelegate delegate) {
        mDelegate = delegate;
    }

    //1.定义委托接口
    public static interface NumbersAdapterDelegate {
        void removeItem(Integer value);
    }
}
