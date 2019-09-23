package com.youdu.imoocbusiness.application;

import android.app.Application;

import com.youdu.yonstone_sdk.video.core.AdSDKManager;

/**
 * create by Yonstone on 2019-08-28
 */
public class ImoocApplication extends Application {
    public static ImoocApplication mApplication = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        initAdSDK();
    }

    private void initAdSDK() {
        AdSDKManager.init(this);
    }

    public static ImoocApplication getInstance() {
        return mApplication;
    }
}
