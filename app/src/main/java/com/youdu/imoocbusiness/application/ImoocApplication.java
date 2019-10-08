package com.youdu.imoocbusiness.application;

import android.app.Application;

import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.youdu.imoocbusiness.BuildConfig;
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
        initUMeng();
    }

    private void initUMeng() {
        UMConfigure.init(this, "5d9bfd990cafb2f3b80000e6"
                , "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
//        PlatformConfig.setWeixin(BuildConfig.WX_KEY, BuildConfig.WX_SECRET);
//        PlatformConfig.setSinaWeibo(BuildConfig.SINA_KEY, BuildConfig.SINA_SECRET, "http://www.limingtang.cn");
        PlatformConfig.setQQZone("1109934148", "KEY90YIPGf7CF4ehGV3");
    }

    private void initAdSDK() {
        AdSDKManager.init(this);
    }

    public static ImoocApplication getInstance() {
        return mApplication;
    }
}
