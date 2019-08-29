package youdu.com.yonstone_sdk.okhttp.request;

import java.util.Map;

import okhttp3.Request;

/**
 * @author YonStone
 * @date 2019/08/29
 * @desc
 */
public class CommonRequest {
    public static Request createGetRequest(String url, RequestParams params) {
        StringBuilder urlBuilder = new StringBuilder(url).append("?");
        if (params != null) {
            for (Map.Entry<String, String> entry : params.urlParams.entrySet()) {
                urlBuilder.append(entry.getKey()).append("=")
                        .append(entry.getValue()).append("&");
            }
        }
        return new Request.Builder().url(urlBuilder.substring(0, urlBuilder.length())).build();
    }
}
