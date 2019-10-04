package com.youdu.imoocbusiness.network.http;

import com.youdu.imoocbusiness.module.recommend.BaseRecommendModel;
import com.youdu.imoocbusiness.module.update.UpdateModel;
import com.youdu.yonstone_sdk.okhttp.CommonOkHttpClient;
import com.youdu.yonstone_sdk.okhttp.listener.DisposeDataHandle;
import com.youdu.yonstone_sdk.okhttp.listener.DisposeDataListener;
import com.youdu.yonstone_sdk.okhttp.request.CommonRequest;
import com.youdu.yonstone_sdk.okhttp.request.RequestParams;

/**
 * @author YonStone
 * @date 2019/09/06
 * @desc
 */
public class RequestCenter {
    //根据参数发送所有post请求
    public static void postRequest(String url, RequestParams params,
                                   DisposeDataListener listener, Class<?> clazz) {
        CommonOkHttpClient.sendRequest(CommonRequest.createPostRequest(url, params),
                new DisposeDataHandle(listener, clazz)
        );

    }

    public static void requestRecommendData(DisposeDataListener listener) {
        postRequest(HttpConstants.HOME_RECOMMAND, null, listener, BaseRecommendModel.class);
    }

    public static void checkVersion(DisposeDataListener listener) {
        postRequest(HttpConstants.CHECK_UPDATE, null, listener, UpdateModel.class);
    }
}
