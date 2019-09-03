package youdu.com.yonstone_sdk.okhttp.listener;

/**
 * @author YonStone
 * @date 2019/09/03
 * @desc
 */
public class DisposeDataHandle {
    public DisposeDataListener mListener;
    public Class<?> mClass;
    public String mSourse;

    public DisposeDataHandle(DisposeDataListener mListener) {
        this.mListener = mListener;
    }

    public DisposeDataHandle(DisposeDataListener mListener, Class<?> mClass) {
        this.mListener = mListener;
        this.mClass = mClass;
    }

    public DisposeDataHandle(DisposeDataListener mListener, String mSourse) {
        this.mListener = mListener;
        this.mSourse = mSourse;
    }
}
