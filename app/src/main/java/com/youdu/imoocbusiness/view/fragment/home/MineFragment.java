package com.youdu.imoocbusiness.view.fragment.home;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.youdu.imoocbusiness.R;
import com.youdu.imoocbusiness.activity.LoginActivity;
import com.youdu.imoocbusiness.activity.SettingActivity;
import com.youdu.imoocbusiness.manager.UserManager;
import com.youdu.imoocbusiness.module.update.UpdateModel;
import com.youdu.imoocbusiness.module.user.User;
import com.youdu.imoocbusiness.network.http.RequestCenter;
import com.youdu.imoocbusiness.service.update.UpdateService;
import com.youdu.imoocbusiness.util.Util;
import com.youdu.imoocbusiness.view.CommonDialog;
import com.youdu.imoocbusiness.view.fragment.BaseFragment;
import com.youdu.yonstone_sdk.adutil.ImageLoaderManager;
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

    private LoginBroadcastReceiver mReceiver = new LoginBroadcastReceiver();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerBroadcast();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterBroadcast();
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
                if (!UserManager.getInstance().hasLogined()) {
                    toLogin();
                }
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

    private void registerBroadcast() {
        IntentFilter filter = new IntentFilter(LoginActivity.LOGIN_ACTION);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mReceiver, filter);
    }

    private void unregisterBroadcast() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mReceiver);
    }

    private void toLogin() {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
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
                            Intent intent = new Intent(getActivity(), UpdateService.class);
                            getActivity().startService(intent);
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

    /**
     * 自定义广播，登录后更新UI
     */
    private class LoginBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            User user = UserManager.getInstance().getUser();
            if (user != null) {
                if (mLoginedLayout.getVisibility() == View.GONE) {
                    mLoginLayout.setVisibility(View.GONE);
                    mLoginedLayout.setVisibility(View.VISIBLE);
                    mUserNameView.setText(user.data.name);
                    mTickView.setText(user.data.tick);
                    ImageLoaderManager.getInstance(activity)
                            .displayImage(mPhotoView, user.data.photoUrl);
                }
            }
        }
    }
}

