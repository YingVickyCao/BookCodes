/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.example.hades.androidhacks.hack27;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hades.androidhacks.R;

import java.util.ArrayList;

//1.实现NumberAdapterDelegate接口
public class Hack27Activity extends Activity implements NumbersAdapter.NumbersAdapterDelegate {
    private static final String TAG = Hack27Activity.class.getCanonicalName();

    private ListView mListView;
    private ArrayList<Integer> mNumbers;
    private NumbersAdapter mAdapter;
    private EditText inputNum;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hack_27);

        inputNum = findViewById(R.id.inputNum);
        mListView = findViewById(R.id.main_listview);
        mNumbers = new ArrayList<>();
        mAdapter = new NumbersAdapter(this, mNumbers);
        mListView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //2.在onResume()方法中注册委托对象
        mAdapter.setDelegate(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //3.在onPause()方法中取消注册委托对象
        mAdapter.setDelegate(null);
    }

    //从列表中移除指定项，然后通知适配器绑定的数据发生变化
    @Override
    public void removeItem(Integer value) {
        mNumbers.remove(value);
        Toast.makeText(this, "Removed object: " + value, Toast.LENGTH_SHORT).show();
        mAdapter.notifyDataSetChanged();
    }

    public void addNumber(View v) {
        String value = inputNum.getText().toString().trim();
        mNumbers.add(Integer.valueOf(value));
        mAdapter.notifyDataSetChanged();
    }
}
