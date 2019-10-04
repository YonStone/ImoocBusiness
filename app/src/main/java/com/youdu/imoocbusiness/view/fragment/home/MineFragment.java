package com.youdu.imoocbusiness.view.fragment.home;

import android.Manifest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.youdu.imoocbusiness.R;
import com.youdu.imoocbusiness.activity.SettingActivity;
import com.youdu.imoocbusiness.module.update.UpdateModel;
import com.youdu.imoocbusiness.network.http.RequestCenter;
import com.youdu.imoocbusiness.util.Util;
import com.youdu.imoocbusiness.view.CommonDialog;
import com.youdu.imoocbusiness.view.fragment.BaseFragment;
import com.youdu.yonstone_sdk.adutil.Toaster;
import com.youdu.yonstone_sdk.okhttp.listener.DisposeDataListener;

import de.hdodenhof.circleimageview.CircleImageView;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * @author: Yonstone
 * @function:
 * @date: 19/08/28
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {

    private RelativeLayout mLoginLayout;
    private CircleImageView mPhotoView;
    private TextView mLoginInfoView;
    private TextView mLoginView;
    private RelativeLayout mLoginedLayout;
    private TextView mUserNameView;
    private TextView mTickView;
    private TextView mVideoPlayerView;
    private TextView mShareView;
    private TextView mQrCodeView;
    private TextView mUpdateView;
    private static final int RC_STORAGE = 0X01;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mContentView = inflater.inflate(R.layout.fragment_mine_layout, container, false);
        initView(mContentView);
        return mContentView;
    }

    private void initView(View mContentView) {
        mLoginLayout = (RelativeLayout) mContentView.findViewById(R.id.login_layout);
        mLoginLayout.setOnClickListener(this);
        mLoginedLayout = (RelativeLayout) mContentView.findViewById(R.id.logined_layout);
        mLoginedLayout.setOnClickListener(this);

        mPhotoView = (CircleImageView) mContentView.findViewById(R.id.photo_view);
        mPhotoView.setOnClickListener(this);
        mLoginView = (TextView) mContentView.findViewById(R.id.login_view);
        mLoginView.setOnClickListener(this);
        mVideoPlayerView = (TextView) mContentView.findViewById(R.id.video_setting_view);
        mVideoPlayerView.setOnClickListener(this);
        mShareView = (TextView) mContentView.findViewById(R.id.share_imooc_view);
        mShareView.setOnClickListener(this);
        mQrCodeView = (TextView) mContentView.findViewById(R.id.my_qrcode_view);
        mQrCodeView.setOnClickListener(this);
        mLoginInfoView = (TextView) mContentView.findViewById(R.id.login_info_view);
        mUserNameView = (TextView) mContentView.findViewById(R.id.username_view);
        mTickView = (TextView) mContentView.findViewById(R.id.tick_view);

        mUpdateView = (TextView) mContentView.findViewById(R.id.update_view);
        mUpdateView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.share_imooc_view:
                //分享Imooc课网址
                break;
            case R.id.login_layout:
            case R.id.login_view:
                //未登陆，则跳轉到登陸页面
                break;
            case R.id.my_qrcode_view:
                break;
            case R.id.video_setting_view:
                activity.startActivity(SettingActivity.actionView(activity));
                break;
            case R.id.update_view:
                String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE};
                if (EasyPermissions.hasPermissions(activity, perms)) {
                    checkVersion();
                } else {
                    EasyPermissions.requestPermissions(this,
                            getString(R.string.permission_external_storage), RC_STORAGE, perms);
                }
                break;
        }
    }

    private void checkVersion() {
        RequestCenter.checkVersion(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                final UpdateModel updateModel = (UpdateModel) responseObj;
                if (Util.getVersionCode(activity) < updateModel.data.currentVersion) {
                    //说明有新版本,开始下载
                    CommonDialog dialog = new CommonDialog(activity, "您有新版本",
                            getString(R.string.update_title), "安装",
                            getString(R.string.cancel), new CommonDialog.DialogClickListener() {
                        @Override
                        public void onDialogClick() {
                            Toaster.show(activity, "执行更新代码");
                        }
                    });
                    dialog.show();
                } else {
                    Toaster.show(activity, "已是最新版本");
                }
            }

            @Override
            public void onFailure(Object reasonObj) {
                Toaster.show(activity, "请求失败");
            }
        });
    }
}

