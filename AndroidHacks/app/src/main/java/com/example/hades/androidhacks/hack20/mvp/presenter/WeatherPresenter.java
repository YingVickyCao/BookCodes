/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.example.hades.androidhacks.hack20.mvp.presenter;

import com.example.hades.androidhacks.hack20.mvp.model.IWeatherModel;
import com.example.hades.androidhacks.hack20.mvp.view.IWeatherView;

/**
 * MVP-P,Presenter（主导器）
 * Presenter也要开发API供View调用
 */
public class WeatherPresenter implements IWeatherPresenter {
    private IWeatherModel mWeatherModel;
    private IWeatherView mWeatherView;

    public WeatherPresenter() {
    }

    @Override
    public void setView(IWeatherView view) {
        this.mWeatherView = view;
    }

    /**
     * 因为Presenter持有View的引用，所以在这里要将View.interface注入到Presenter当中。
     */
    @Override
    public void setWeatherModel(IWeatherModel mWeatherModel) {
        this.mWeatherModel = mWeatherModel;
    }

    protected IWeatherView getView() {
        return mWeatherView;
    }

    /**
     * 供View层调用，用来请求天气数据*
     */
    @Override
    public void requestWeatherInfo() {
        getNetworkInfo();
    }

    private void getNetworkInfo() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    //打开等待对话框
                    showWaitingDialog();
                    //模拟网络耗时
                    Thread.sleep(6000);

                    String info = "21度，晴转多云";
                    //保存到Model层
                    saveInfo(info);

                    /**
                     * 从Model层获取数据，为了演示效果，实际开发中根据情况需要。
                     * 从Model层获取数据时，数据源很多：内存缓存、数据库、本地缓存、SF、网络....
                     * 这里的数据源仅仅是mock。
                     */
                    String localInfo = localInfo();

                    //通知View层改变视图
                    updateWeatherInfo(localInfo);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    //取消对话框
                    dismissWaitingDialog();
                }
            }
        }).start();
    }

    /**
     * presenter—–>View
     * presenter操作View，是通过View.interface，也就是View层定义的接口。
     */
    private void showWaitingDialog() {
        if (mWeatherView != null) {
            mWeatherView.showWaitingDialog();
        }
    }

    private void dismissWaitingDialog() {
        if (mWeatherView != null) {
            mWeatherView.dismissWaitingDialog();
        }
    }

    private void updateWeatherInfo(String info) {
        if (mWeatherView != null) {
            mWeatherView.showWeatherInfo(info);
        }
    }


    /**
     * Presenter —-> Model
     * presenter获取到了数据，可以交给Model处理
     */
    private void saveInfo(String info) {
        mWeatherModel.setInfo(info);
    }

    /**
     * Presenter <—- Model
     * Model处理完数据后它也能对Presenter提供数据。Presenter可以通过Model对象获取本地数据。
     */
    private String localInfo() {
        return mWeatherModel.getInfo();
    }
}
