package com.example.hades.androidhacks.hack20.mvp.model;

/**
 * Created by hades on 17/03/2018.
 */

/**
 * MVP - M, Model
 */
public class WeatherModelImpl implements IWeatherModel {
    private String mWeatherInfo = "windy";

    @Override
    public String getInfo() {
        return mWeatherInfo;
    }

    @Override
    public void setInfo(String info) {
        mWeatherInfo = info;
    }
}
