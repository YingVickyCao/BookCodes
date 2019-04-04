/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.example.hades.androidhacks.hack20.mvp.view;

/**
 * MVP - V,View层的接口定义及实现
 * Activity/Fragment用来专注视图的表现。
 */
public interface ISplashView {
    void showProgress();

    void hideProgress();

    void showNoInternetErrorMsg();

    void moveToMainView();
}
