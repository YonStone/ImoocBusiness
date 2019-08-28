package com.youdu.imoocbusiness.application;

import android.app.Application;

/**
 * create by Yonstone on 2019-08-28
 */
public class ImoocApplication extends Application {
    public static ImoocApplication mApplication = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }

    public static ImoocApplication getInstance() {
        return mApplication;
    }
}
