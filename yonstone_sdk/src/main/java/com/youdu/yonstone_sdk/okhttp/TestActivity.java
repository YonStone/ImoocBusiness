package com.youdu.yonstone_sdk.okhttp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.youdu.yonstone_sdk.okhttp.exception.OkHttpException;
import com.youdu.yonstone_sdk.okhttp.listener.DisposeDataHandle;
import com.youdu.yonstone_sdk.okhttp.listener.DisposeDataListener;
import com.youdu.yonstone_sdk.okhttp.request.CommonRequest;

/**
 * @author YonStone
 * @date 2019/08/29
 * @desc
 */
public class TestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 用okhttp发送一个最基本的请求
     */
    private void sendRequest() {
        //创建okHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //创建一个Request
        Request request = new Request.Builder()
                .url("https://www.imooc.com/")
                .build();
//        FormBody.Builder builder = new FormBody.Builder();
//        builder.add("key", "value");
//        FormBody body = builder.build();
//        Request request = new Request.Builder()
//                .url("https://www.imooc.com/")
//                .post(body)
//                .build();
        //new call
        Call call = okHttpClient.newCall(request);
        //请求队列
        call.enqueue(new Callback() {
            //失败响应
            @Override
            public void onFailure(Call call, IOException e) {

            }

            //成功响应
            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    /**
     * 封装后的网络请求
     */
    private void test() {
//        CommonOkHttpClient.sendRequest(
//                CommonRequest.createGetRequest("www.imooc.com", null),
//                new CommonJsonCallback(new DisposeDataHandle(new DisposeDataListener() {
//                    @Override
//                    public void onSuccess(Object responseObj) {
//
//                    }
//
//                    @Override
//                    public void onFailure(Object reasonObj) {
//
//                    }
//                })));
        CommonOkHttpClient.sendRequest(
                CommonRequest.createGetRequest("www.imooc.com", null),
                new DisposeDataHandle(new DisposeDataListener() {
                    @Override
                    public void onSuccess(Object responseObj) {

                    }

                    @Override
                    public void onFailure(OkHttpException reasonObj) {

                    }
                })
        );
    }
}
