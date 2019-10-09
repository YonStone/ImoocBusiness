package youdu.com.ext_share_login_umeng.ext;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.youdu.yonstone_sdk.adutil.LogUtils;
import com.youdu.yonstone_sdk.adutil.Toaster;

import youdu.com.ext_share_login_umeng.R;

import static com.umeng.socialize.bean.SHARE_MEDIA.QQ;
import static com.umeng.socialize.bean.SHARE_MEDIA.WEIXIN;

/**
 * @author YonStone
 * @date 2019/10/08
 * @desc
 */
public class ShareDialog extends Dialog implements View.OnClickListener {
    /**
     * UI
     */
    private RelativeLayout mWeixinLayout;
    private RelativeLayout mWeixinMomentLayout;
    private RelativeLayout mQQLayout;
    private RelativeLayout mQZoneLayout;
    private RelativeLayout mDownloadLayout;
    private TextView mCancelView;

    private Activity activity;
    private String content;
    private String targetUrl;
    private String targetImage;
    private String title;
    private UMShareAPI umShareAPI;

    public ShareDialog(Activity activity, String title, String content, String targetUrl, String targetImage) {
        super(activity, R.style.SheetDialogStyle);
        this.activity = activity;
        this.title = title;
        this.content = content;
        this.targetImage = targetImage;
        this.targetUrl = targetUrl;
        umShareAPI = UMShareAPI.get(activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_share_layout);
        initView();
    }

    private void initView() {
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = lp.MATCH_PARENT;
        dialogWindow.setAttributes(lp);

        mWeixinLayout = (RelativeLayout) findViewById(R.id.weixin_layout);
        mWeixinLayout.setOnClickListener(this);
        mWeixinMomentLayout = (RelativeLayout) findViewById(R.id.moment_layout);
        mWeixinMomentLayout.setOnClickListener(this);
        mQQLayout = (RelativeLayout) findViewById(R.id.qq_layout);
        mQQLayout.setOnClickListener(this);
        mQZoneLayout = (RelativeLayout) findViewById(R.id.qzone_layout);
        mQZoneLayout.setOnClickListener(this);
        mCancelView = (TextView) findViewById(R.id.cancel_view);
        mCancelView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.weixin_layout) {
            shareToPlatform(SHARE_MEDIA.WEIXIN);
            this.dismiss();
        } else if (view.getId() == R.id.moment_layout) {
            shareToPlatform(SHARE_MEDIA.WEIXIN_CIRCLE);
            this.dismiss();
        } else if (view.getId() == R.id.qq_layout) {
            shareToPlatform(SHARE_MEDIA.QQ);
            this.dismiss();
        } else if (view.getId() == R.id.qzone_layout) {
            shareToPlatform(SHARE_MEDIA.QZONE);
            this.dismiss();
        }
    }

    public void shareToPlatform(SHARE_MEDIA platform) {
        boolean isInstall;
        switch (platform) {
            case QQ:
                isInstall = umShareAPI.isInstall(activity, QQ);
                if (!isInstall) {
                    Toaster.show(activity, "请先安装QQ");
                    return;
                }
                platform(platform);
                break;
            case WEIXIN:
                isInstall = umShareAPI.isInstall(activity, WEIXIN);
                if (!isInstall) {
                    Toaster.show(activity, "请先安装微信");
                    return;
                }
                platform(platform);
                break;
            case WEIXIN_CIRCLE:
                isInstall = umShareAPI.isInstall(activity, WEIXIN);
                if (!isInstall) {
                    Toaster.show(activity, "请先安装微信");
                    return;
                }
                platform(platform);
                break;
            case SINA:
                isInstall = umShareAPI.isInstall(activity, WEIXIN);
                if (!isInstall) {
                    Toaster.show(activity, "请先安装新浪微博");
                    return;
                }
                platform(platform);
                break;
            default:
                platform(platform);
                break;
        }
    }

    /**
     * 暂时只分享链接
     */
    private void platform(SHARE_MEDIA platform) {
        if (platform == null) {
            return;
        }
        UMWeb web = new UMWeb(targetUrl);
        web.setTitle(title);
        UMImage umImage = new UMImage(activity, targetImage);
        web.setThumb(umImage);
        web.setDescription(content);
        new ShareAction(activity)
                .withMedia(web)
                .setPlatform(platform)
                .withText(content)
                .setCallback(mShareListener)
                .share();
    }

    /**
     * 分享回调
     */
    private UMShareListener mShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            String platformName = "";
            switch (share_media) {
                case QQ:
                    platformName = "QQ";
                    break;
                case QZONE:
                    platformName = "QQ空间";
                    break;
                case WEIXIN:
                    platformName = "微信";
                    break;
                case WEIXIN_CIRCLE:
                    platformName = "微信朋友圈";
                    break;
                case SINA:
                    platformName = "新浪微博";
                    break;
            }
            Toaster.show(activity, platformName + "分享完成");
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            String platformName = "";
            switch (share_media) {
                case WEIXIN_CIRCLE:
                    platformName = "朋友圈";
                    break;
                case WEIXIN:
                    platformName = "微信";
                    break;
                case QQ:
                    platformName = "QQ";
                    break;
                case QZONE:
                    platformName = "QQ空间";
                    break;
                case SINA:
                    platformName = "新浪微博";
                    break;
            }

            Toaster.show(activity, platformName + "分享失败");
            LogUtils.i(">>>>>>>>>", "onError: " + throwable.toString());
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            String platformName = "";
            switch (share_media) {
                case WEIXIN_CIRCLE:
                    platformName = "朋友圈";
                    break;
                case WEIXIN:
                    platformName = "微信";
                    break;
                case QQ:
                    platformName = "QQ";
                    break;

                case QZONE:
                    platformName = "QQ空间";
                    break;

                case SINA:
                    platformName = "新浪微博";
                    break;
            }
            Toaster.show(activity, platformName + "分享取消了");
        }
    };
}
