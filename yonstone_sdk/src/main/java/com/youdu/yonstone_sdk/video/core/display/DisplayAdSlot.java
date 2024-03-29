package com.youdu.yonstone_sdk.video.core.display;

import android.view.ViewGroup;

import com.youdu.yonstone_sdk.constant.SDKConstant;
import com.youdu.yonstone_sdk.okhttp.CommonOkHttpClient;
import com.youdu.yonstone_sdk.okhttp.HttpConstant;
import com.youdu.yonstone_sdk.okhttp.listener.DisposeDataHandle;
import com.youdu.yonstone_sdk.okhttp.listener.DisposeDataListener;
import com.youdu.yonstone_sdk.okhttp.request.CommonRequest;
import com.youdu.yonstone_sdk.video.core.BaseRenderer;
import com.youdu.yonstone_sdk.video.core.module.AdInstance;


/**
 * Created by renzhiqiang on 16/10/27.
 */

public class DisplayAdSlot implements DisposeDataListener, ImageAdRenderer.ImageRendererListener {

    //UI
    private ViewGroup mParentView;
    //Data
    private BaseRenderer mAdRenderer; //图片类型广告
    private AdInstance mInstance = null; //服务器返回广告实例
    private DisplayConextListener mContextListener;

    public DisplayAdSlot(ViewGroup parentView) {
        mParentView = parentView;
        load();
    }

    //发送开机图广告请求
    private void load() {
        CommonOkHttpClient.sendRequest(CommonRequest.createPostRequest(HttpConstant.DISPLAY_AD_URL, null),
                new DisposeDataHandle(this, AdInstance.class));
    }

    public void setContextListener(DisplayConextListener listener) {
        mContextListener = listener;
    }

    @Override
    public void onSuccess(Object responseObj) {
        mInstance = (AdInstance) responseObj;
        switch (mInstance.type) {
            //返回的是图片类型，则创建图片渲染器
            case SDKConstant.MATERIAL_IMAGE:
                mAdRenderer = new ImageAdRenderer(mParentView);
                ((ImageAdRenderer) mAdRenderer).setImageRendererListener(this);
                mAdRenderer.onShow(mInstance.values.get(0).resource);
                break;
            //返回的是Html类型的广告，创建Html渲染器
            case SDKConstant.MATERIAL_HTML:
                break;
        }

    }

    @Override
    public void onFailure(Object reasonObj) {
        if (mContextListener != null) {
            mContextListener.onLoadingFailed();
        }
        //这应该去加载本地广告
    }

    @Override
    public void onLoadingComplete() {
        if (mContextListener != null) {
            mContextListener.onLoadingComplete();
        }
    }

    @Override
    public void onLoadingFailed() {
        if (mContextListener != null) {
            mContextListener.onLoadingFailed();
        }
    }

    public void onDispose() {
        if (mAdRenderer != null) {
            mAdRenderer.onDispose();
        }
    }

    public interface DisplayConextListener {

        void onLoadingComplete();

        void onLoadingFailed();
    }
}
