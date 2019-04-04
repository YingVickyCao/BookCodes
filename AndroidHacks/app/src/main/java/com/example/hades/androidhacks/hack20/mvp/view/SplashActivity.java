/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.example.hades.androidhacks.hack20.mvp.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.hades.androidhacks.R;
import com.example.hades.androidhacks.hack20.mvp.model.ConnectionStatusModule;
import com.example.hades.androidhacks.hack20.mvp.presenter.ISplashPresenter;
import com.example.hades.androidhacks.hack20.mvp.presenter.SplashPresenter;

/**
 * MVP-V,View（视图）
 */
public class SplashActivity extends Activity implements ISplashView, View.OnClickListener {
    private static final String TAG = SplashActivity.class.getSimpleName();

    private TextView mTextView;
    private ProgressBar mProgressBar;

    /**
     * View—–>Presenter
     * 从视图界面出发，用户要请求数据，而Presenter是具体实现者，所以Presenter要提供方法代View的实现者调用，并且View的实现中必须要有Presenter的引用。
     */
    private ISplashPresenter mPresenter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hack_20_splash);

        mTextView = findViewById(R.id.splash_text);
        mProgressBar = findViewById(R.id.splash_progress_bar);
        findViewById(R.id.didFinishLoading).setOnClickListener(this);

        mPresenter = new SplashPresenter();
        /**
         * 为当前 Activity 设置主导器
         */
        mPresenter.setView(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        didFinishLoading();
    }

    private void didFinishLoading() {
        mPresenter.setConnectionStatusModule(new ConnectionStatusModule());
        mPresenter.didFinishLoading();
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showNoInternetErrorMsg() {
        mTextView.setText("No internet");
    }

    @Override
    public void moveToMainView() {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.didFinishLoading:
                didFinishLoading();
                break;
        }
    }

    private Activity getActivity() {
        return SplashActivity.this;
    }
}
