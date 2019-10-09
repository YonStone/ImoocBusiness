package youdu.com.ext_share_login_umeng.ext;

import android.content.Context;

import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

import youdu.com.ext_share_login_umeng.BuildConfig;

/**
 * @author YonStone
 * @date 2019/10/09
 * @desc
 */
public class ShareSDKManager {
    public static void init(Context context) {
        UMConfigure.init(context, BuildConfig.UMENG_KEY
                , "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
        PlatformConfig.setWeixin(BuildConfig.WX_KEY, BuildConfig.WX_SECRET);
        PlatformConfig.setQQZone(BuildConfig.QQ_KEY, BuildConfig.QQ_SECRET);
        //        PlatformConfig.setSinaWeibo(BuildConfig.SINA_KEY, BuildConfig.SINA_SECRET, "http://www.limingtang.cn");
    }
}
