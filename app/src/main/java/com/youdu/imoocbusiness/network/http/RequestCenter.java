package com.youdu.imoocbusiness.network.http;

import com.youdu.imoocbusiness.module.course.BaseCourseModel;
import com.youdu.imoocbusiness.module.recommend.BaseRecommendModel;
import com.youdu.imoocbusiness.module.update.UpdateModel;
import com.youdu.imoocbusiness.module.user.User;
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

    /**
     * 用户登陆请求
     *
     * @param listener
     * @param userName
     * @param passwd
     */
    public static void login(String userName, String passwd, DisposeDataListener listener) {

        RequestParams params = new RequestParams();
        params.put("mb", userName);
        params.put("pwd", passwd);
        RequestCenter.postRequest(HttpConstants.LOGIN, params, listener, User.class);
    }

    /**
     * 请求课程详情
     *
     * @param listener
     */
    public static void requestCourseDetail(String courseId, DisposeDataListener listener) {
        RequestParams params = new RequestParams();
        params.put("courseId", courseId);
        RequestCenter.postRequest(HttpConstants.COURSE_DETAIL, params, listener, BaseCourseModel.class);
    }
}
