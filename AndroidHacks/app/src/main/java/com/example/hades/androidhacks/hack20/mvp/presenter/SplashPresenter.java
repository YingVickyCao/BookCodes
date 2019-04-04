/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.example.hades.androidhacks.hack20.mvp.presenter;

import com.example.hades.androidhacks.hack20.mvp.model.IConnectionStatusModule;
import com.example.hades.androidhacks.hack20.mvp.view.ISplashView;

/**
 * MVP-P,Presenter（主导器）
 * Presenter也要开发API供View调用
 */
public class SplashPresenter implements ISplashPresenter {

    private IConnectionStatusModule mConnectionStatusModule;
    private ISplashView mSplashView;

    public SplashPresenter() {
    }

    public SplashPresenter(IConnectionStatusModule connectionStatus) {
        mConnectionStatusModule = connectionStatus;
    }

    @Override
    public void setView(ISplashView view) {
        this.mSplashView = view;
    }

    @Override
    public void setConnectionStatusModule(IConnectionStatusModule mConnectionStatusModule) {
        this.mConnectionStatusModule = mConnectionStatusModule;
    }


    protected ISplashView getView() {
        return mSplashView;
    }

    /**
     * Presenter 通过接口访问视图。
     * Presenter 并不知道该接口是由 Activity 实现的。因此，在单元测试中更容易 mock 视图。
     */
    @Override
    public void didFinishLoading() {
        ISplashView view = getView();

        if (mConnectionStatusModule.isOnline()) {
            view.moveToMainView();
        } else {
            // presenter—–>View
            view.hideProgress();
            view.showNoInternetErrorMsg();
        }
    }
}
