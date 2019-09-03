package youdu.com.yonstone_sdk.okhttp.exception;

/**
 * @author YonStone
 * @date 2019/09/03
 * @desc 包含ecode的自定义异常
 */
public class OkHttpException extends Exception {
    public static final long serialVersionUID = 1L;
    private int ecode;
    private Object emsg;

    public OkHttpException(int ecode, Object emsg) {
        this.ecode = ecode;
        this.emsg = emsg;
    }

    public int getEcode() {
        return ecode;
    }

    public Object getEmsg() {
        return emsg;
    }
}
