package com.youdu.imoocbusiness.application;

import android.app.Application;

import com.youdu.yonstone_sdk.video.core.AdSDKManager;

import cn.jpush.android.api.JPushInterface;
import youdu.com.ext_share_login_umeng.ext.ShareSDKManager;

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
        initUMeng();
        initJPush();
    }

    private void initJPush() {
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }

    private void initUMeng() {
        ShareSDKManager.init(this);
    }

    private void initAdSDK() {
        AdSDKManager.init(this);
    }

    public static ImoocApplication getInstance() {
        return mApplication;
    }
}