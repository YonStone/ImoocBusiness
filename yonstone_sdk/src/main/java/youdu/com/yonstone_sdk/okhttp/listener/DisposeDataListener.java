package youdu.com.yonstone_sdk.okhttp.listener;

/**
 * @author YonStone
 * @date 2019/09/03
 * @desc
 */
public interface DisposeDataListener {
    /**
     * 请求成功回调事件处理
     */
    public void onSuccess(Object responseObj);

    /**
     * 请求失败回调事件处理
     */
    public void onFailure(Object reasonObj);
}
