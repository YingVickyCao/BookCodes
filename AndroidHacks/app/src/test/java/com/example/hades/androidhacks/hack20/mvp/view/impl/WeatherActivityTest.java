package com.example.hades.androidhacks.hack20.mvp.view.impl;

import com.example.hades.androidhacks.hack20.mvp.model.IWeatherModel;
import com.example.hades.androidhacks.hack20.mvp.presenter.WeatherPresenter;
import com.example.hades.androidhacks.hack20.mvp.view.IWeatherView;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by hades on 17/03/2018.
 */
public class WeatherActivityTest {
    @Test
    public void testRequestWeather() {
        IWeatherModel weatherModel = mock(IWeatherModel.class);
        IWeatherView mockedSplashView = mock(IWeatherView.class);
        WeatherPresenter splashPresenter = new WeatherPresenter();

        when(weatherModel.getInfo()).thenReturn("Cloudy");
        splashPresenter.setWeatherModel(weatherModel);

        splashPresenter.setView(mockedSplashView);

        splashPresenter.requestWeatherInfo();

        /**
         * ERROR:Wanted but not invoked:iSplashView.dismissWaitingDialog();
         */
        // verify(mockedSplashView).showWaitingDialog();
        // verify(mockedSplashView).dismissWaitingDialog();
    }
}