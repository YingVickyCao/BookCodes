/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.example.hades.androidhacks.hack26;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.hades.androidhacks.R;

public class SectionAdapter extends ArrayAdapter<String> {

    private Activity activity;

    public SectionAdapter(Activity activity, String[] objects) {
        super(activity, R.layout.activity_hack_26_list_item, R.id.label, objects);
        this.activity = activity;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = activity.getLayoutInflater().inflate(R.layout.activity_hack_26_list_item, parent, false);
        }
        TextView header = view.findViewById(R.id.header);

        String data = getItem(position);
        if (isShowHeader(position, data)) {
            /**
             *  显示分段表头,并更改分段标头的文本内容
             */
            header.setVisibility(View.VISIBLE);
            header.setText(data.substring(0, 1));
        } else {
            header.setVisibility(View.GONE);
        }
        return super.getView(position, view, parent);
    }

    /**
     * 检查列表项目起始字母是否发生改变
     */
    private boolean isShowHeader(int position, String data) {
        return isFirstLine(position) || isInitialsChange(position, data);
    }

    private boolean isFirstLine(int position) {
        return position == 0;
    }

    private boolean isInitialsChange(int position, String data) {
        return getItem(position - 1).charAt(0) != data.charAt(0);
    }
}
