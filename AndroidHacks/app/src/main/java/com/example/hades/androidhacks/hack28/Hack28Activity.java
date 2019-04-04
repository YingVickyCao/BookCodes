package com.example.hades.androidhacks.hack28;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Gallery;
import android.widget.ListView;

import com.example.hades.androidhacks.R;
import com.example.hades.androidhacks.hack28.adapter.ImageAdapter;

public class Hack28Activity extends Activity {

    private static final String[] NUMBERS = {"1", "2", "3", "4", "5", "6", "7", "8"};
    private Gallery mGallery;
    private ListView mListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hack_28);

        mListView = findViewById(R.id.main_listview);

        View headerView = getHeaderView();
        mGallery = getGallery(headerView);
        /**
         将这个头视图添加到ListView
         */
        mListView.addHeaderView(headerView, null, false);
        mListView.setAdapter(getAdapter());

        mListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mGallery.setSelection(position - 1, true);
            }
        });
    }

    private ArrayAdapter<String> getAdapter() {
        return new ArrayAdapter<>(this, R.layout.activity_hack_28_list_item, NUMBERS);
    }

    private View getHeaderView() {
        View headerView = LayoutInflater.from(this).inflate(R.layout.activity_hack_28_header, mListView, false);
        headerView.setLayoutParams(new ListView.LayoutParams(ListView.LayoutParams.MATCH_PARENT, ListView.LayoutParams.WRAP_CONTENT));
        return headerView;
    }

    private Gallery getGallery(View mHeader) {
        mGallery = mHeader.findViewById(R.id.listview_header_gallery);
        mGallery.setAdapter(new ImageAdapter(this));
        return mGallery;
    }
}
