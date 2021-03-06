/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.example.hades.androidhacks.hack43.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.hades.androidhacks.R;
import com.example.hades.androidhacks.hack43.provider.NoBatchNumbersContentProvider;

public class NoBatchAdapter extends CursorAdapter {
    private LayoutInflater mInflater;
    private final int mIdIndex;
    private final int mNumberIndex;

    public NoBatchAdapter(Context context, Cursor c) {
        super(context, c, true);
        mInflater = LayoutInflater.from(context);

        mIdIndex = c.getColumnIndexOrThrow(NoBatchNumbersContentProvider.COLUMN_ID);
        mNumberIndex = c.getColumnIndexOrThrow(NoBatchNumbersContentProvider.COLUMN_TEXT);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = mInflater.inflate(R.layout.activity_hack_43_list_item, parent, false);

        ViewHolder holder = new ViewHolder();
        holder.id = (TextView) view.findViewById(R.id.list_item_id);
        holder.number = (TextView) view.findViewById(R.id.list_item_number);
        view.setTag(holder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.id.setText(cursor.getString(mIdIndex));
        holder.number.setText(cursor.getString(mNumberIndex));
    }

    private static final class ViewHolder {
        public TextView id;
        public TextView number;
    }
}
