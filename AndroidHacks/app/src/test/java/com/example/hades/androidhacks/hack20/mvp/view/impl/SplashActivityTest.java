package com.example.hades.androidhacks.hack20.mvp.view.impl;

import com.example.hades.androidhacks.hack20.mvp.model.IConnectionStatusModule;
import com.example.hades.androidhacks.hack20.mvp.presenter.SplashPresenter;
import com.example.hades.androidhacks.hack20.mvp.view.ISplashView;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by hades on 17/03/2018.
 */
public class SplashActivityTest {
    @Test
    public void testViewShouldShowNoInternetMsgWhenThereIsNoInternet() {
        IConnectionStatusModule statusModule = mock(IConnectionStatusModule.class);
        when(statusModule.isOnline()).thenReturn(false);

        ISplashView mockedSplashView = mock(ISplashView.class);

        SplashPresenter splashPresenter = new SplashPresenter();
        splashPresenter.setConnectionStatusModule(statusModule);

        splashPresenter.setView(mockedSplashView);

        splashPresenter.didFinishLoading();

        verify(mockedSplashView).hideProgress();
        verify(mockedSplashView).showNoInternetErrorMsg();
    }

    @Test
    public void testViewShouldMoveToMoveToMainViewWhenInternetIsOn() {
        IConnectionStatusModule statusModule = mock(IConnectionStatusModule.class);
        when(statusModule.isOnline()).thenReturn(true);

        ISplashView mockedSplashView = mock(ISplashView.class);

        SplashPresenter splashPresenter = new SplashPresenter();
        splashPresenter.setConnectionStatusModule(statusModule);

        splashPresenter.setView(mockedSplashView);

        splashPresenter.didFinishLoading();

        // Mockito提供verify关键字来实现校验方法是否被调用
        verify(mockedSplashView).moveToMainView();
    }
}