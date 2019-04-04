package com.example.hades.androidhacks.hack20.mvp.presenter;

import com.example.hades.androidhacks.hack20.mvp.model.IConnectionStatusModule;
import com.example.hades.androidhacks.hack20.mvp.view.ISplashView;

/**
 * Created by hades on 17/03/2018.
 */

/**
 * MVP - P,Presenter层
 * 同时对View和Model对接，所以内部必须持有它们的接口引用。
 */
public interface ISplashPresenter {
    void setView(ISplashView view);

    void setConnectionStatusModule(IConnectionStatusModule mConnectionStatusModule);

    void didFinishLoading();

}
