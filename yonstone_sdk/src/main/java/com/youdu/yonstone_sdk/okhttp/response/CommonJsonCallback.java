package com.youdu.yonstone_sdk.okhttp.response;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.Response;
import com.youdu.yonstone_sdk.okhttp.exception.OkHttpException;
import com.youdu.yonstone_sdk.okhttp.listener.DisposeDataHandle;
import com.youdu.yonstone_sdk.okhttp.listener.DisposeDataListener;
import com.youdu.yonstone_sdk.okhttp.listener.DisposeHandleCookieListener;

/**
 * @author YonStone
 * @date 2019/09/03
 * @desc 定义类对原Callback执行的成功/失败回调进行定制修改
 */
public class CommonJsonCallback implements Callback {
    /**
     * the logic layer exception, may alter in different app
     */
    protected final String RESULT_CODE = "ecode";//有返回对于http请求来说是成功的,但还有可能是业务逻辑上的错误
    protected final int REQUEST_CODE_VALUE = 0;
    protected final String ERROR_MSG = "emsg";
    protected final String EMPTY_MSG = "";
    protected final String COOKIE_STORE = "Set-Cookie";//decide the server it

    // can has the value of
    // set-cookie2
    /**
     * the java layer exception, do not same to the logic error
     */
    protected final int NETWORK_ERROR = -1; // the network relative error
    protected final int JSON_ERROR = -2; // the JSON relative error
    protected final int OTHER_ERROR = -3; // the unknow error
    private Handler mDeliveryHandler;
    private DisposeDataListener mListener;
    private Class<?> mClass;

    public CommonJsonCallback(DisposeDataHandle handle) {
        this.mDeliveryHandler = new Handler(Looper.getMainLooper());
        this.mListener = handle.mListener;
        this.mClass = handle.mClass;
    }

    @Override
    public void onFailure(Call call, final IOException e) {
        /**
         * 网络请求在非UI线程,因此要转发
         */
        mDeliveryHandler.post(new Runnable() {
            @Override
            public void run() {
                //转发到主线程后,把异常原因和ecode返回
                mListener.onFailure(new OkHttpException(NETWORK_ERROR, e));
            }
        });
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        final String result = response.body().string();
        final ArrayList<String> cookieLists = handleCookie(response.headers());
        mDeliveryHandler.post(new Runnable() {
            @Override
            public void run() {
                handleResponse(result);
                /**
                 * handle the cookie
                 */
                if (mListener instanceof DisposeHandleCookieListener) {
                    ((DisposeHandleCookieListener) mListener).onCookie(cookieLists);
                }
            }
        });
    }

    private ArrayList<String> handleCookie(Headers headers) {
        ArrayList<String> tempList = new ArrayList<String>();
        for (int i = 0; i < headers.size(); i++) {
            if (headers.name(i).equalsIgnoreCase(COOKIE_STORE)) {
                tempList.add(headers.value(i));
            }
        }
        return tempList;
    }

    private void handleResponse(Object response) {
        if (response == null || TextUtils.isEmpty(response.toString())) {
            mListener.onFailure(new OkHttpException(NETWORK_ERROR, EMPTY_MSG));
            return;
        }
        try {
            JSONObject result = new JSONObject(response.toString());
            if (mClass == null) {
                mListener.onSuccess(result);
            } else {
                Gson gson = new Gson();
                Object object = gson.fromJson(response.toString(), mClass);
                if (object != null) {
                    mListener.onSuccess(object);
                } else {
                    mListener.onFailure(new OkHttpException(JSON_ERROR, EMPTY_MSG));
                }
            }
        } catch (JSONException e) {
            mListener.onFailure(new OkHttpException(NETWORK_ERROR, e.getMessage()));
            e.printStackTrace();
        }

    }
}
