/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.example.hades.androidhacks.hack26;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.TextView;

import com.example.hades.androidhacks.mockdata.Countries;
import com.example.hades.androidhacks.R;

public class Hack26Activity extends ListActivity {

    /**
     * 用于访问分段表头
     */
    private TextView topHeader;
    private int topVisiblePosition = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hack_26);

        topHeader = findViewById(R.id.header);
        setListAdapter(new SectionAdapter(this, Countries.COUNTRIES));

        setTopHeader(0);
        getListView().setOnScrollListener(
                new AbsListView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(AbsListView view, int scrollState) {
                        // Empty.
                    }

                    @Override
                    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                        setTopHeader(firstVisibleItem);
                    }
                });
    }

    /**
     * 开始创建或者滚动滚动列表时，找到分段表头对应字母，并更新其内容。
     */
    private void setTopHeader(int pos) {
        if (!isNeedUpdateTopHeader(pos)) {
        }
        topVisiblePosition = pos;
        final String text = Countries.COUNTRIES[pos].substring(0, 1);
        topHeader.setText(text);
    }

    private boolean isNeedUpdateTopHeader(int pos) {
        return pos != topVisiblePosition;
    }
}
