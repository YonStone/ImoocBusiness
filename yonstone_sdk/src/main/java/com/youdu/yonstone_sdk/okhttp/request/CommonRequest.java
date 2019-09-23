package com.youdu.yonstone_sdk.okhttp.request;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Request;

/**
 * @author YonStone
 * @date 2019/08/29
 * @desc
 */
public class CommonRequest {
    public static Request createGetRequest(String url, RequestParams params) {

        return createGetRequest(url, params, null);
    }

    /**
     * 可以带请求头的Get请求
     *
     * @param url
     * @param params
     * @param headerParams
     * @return
     */
    public static Request createGetRequest(String url, RequestParams params, RequestParams headerParams) {
        StringBuilder urlBuilder = new StringBuilder(url).append("?");
        if (params != null) {
            for (Map.Entry<String, String> entry : params.urlParams.entrySet()) {
                urlBuilder.append(entry.getKey()).append("=")
                        .append(entry.getValue()).append("&");
            }
        }
        //添加请求头
        Headers.Builder headBuilder = new Headers.Builder();
        if (headerParams != null) {
            for (Map.Entry<String, String> entry : headerParams.urlParams.entrySet()) {
                headBuilder.add(entry.getKey(), entry.getValue());
            }
        }
        Headers headers = headBuilder.build();
        return new Request.Builder().url(urlBuilder.substring(0, urlBuilder.length()))
                .headers(headers)
                .build();
    }

    public static Request createPostRequest(String url, RequestParams params) {
        return createPostRequest(url, params, null);
    }

    /**
     * 可以带请求头的Post请求
     *
     * @param url
     * @param params
     * @param headerParams
     * @return
     */
    public static Request createPostRequest(String url, RequestParams params, RequestParams headerParams) {
        FormBody.Builder bodyBuilder = new FormBody.Builder();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.urlParams.entrySet()) {
                bodyBuilder.add(entry.getKey(), entry.getValue());
            }
        }
        FormBody formBody = bodyBuilder.build();

        //添加请求头
        Headers.Builder headBuilder = new Headers.Builder();
        if (headerParams != null) {
            for (Map.Entry<String, String> entry : headerParams.urlParams.entrySet()) {
                headBuilder.add(entry.getKey(), entry.getValue());
            }
        }
        Headers headers = headBuilder.build();
        return new Request.Builder().url(url).post(formBody)
                .headers(headers)
                .build();
    }

    /**
     * @param url
     * @param params
     * @return
     */
    public static Request createMonitorRequest(String url, RequestParams params) {
        StringBuilder urlBuilder = new StringBuilder(url).append("&");
        if (params != null && params.hasParams()) {
            for (Map.Entry<String, String> entry : params.urlParams.entrySet()) {
                urlBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }
        return new Request.Builder().url(urlBuilder.substring(0, urlBuilder.length() - 1)).get().build();
    }
}
