package com.youdu.imoocbusiness.view.fragment.home;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.youdu.imoocbusiness.R;
import com.youdu.imoocbusiness.adapter.CourseAdapter;
import com.youdu.imoocbusiness.module.recommend.BaseRecommendModel;
import com.youdu.imoocbusiness.network.http.RequestCenter;
import com.youdu.imoocbusiness.view.fragment.BaseFragment;
import com.youdu.imoocbusiness.view.home.HomeHeaderLayout;
import com.youdu.yonstone_sdk.adutil.Toaster;
import com.youdu.yonstone_sdk.okhttp.exception.OkHttpException;
import com.youdu.yonstone_sdk.okhttp.listener.DisposeDataListener;
import com.youdu.yonstone_sdk.zxing.CaptureActivity;
import com.youdu.yonstone_sdk.zxing.Intents;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * @author: Yonstone
 * @function:
 * @date: 19/08/28
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener, AdapterView.OnItemClickListener, EasyPermissions.PermissionCallbacks {
    /**
     * UI
     */
    private View mContentView;
    private ListView mListView;
    private TextView mQRCodeView;
    private TextView mCategoryView;
    private TextView mSearchView;
    private ImageView mLoadingView;
    /**
     * data
     */
    private CourseAdapter mAdapter;
    private BaseRecommendModel mRecommendData;
    private static final int RC_CAMERA = 0X01;
    private static final int REQUEST_CODE_SCAN = 0X11;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestRecommendData();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_SCAN:
                if (resultCode == Activity.RESULT_OK) {
                    String result = data.getStringExtra(Intents.Scan.RESULT);
                    Toaster.show(activity, result);
//                    String code = data.getStringExtra("SCAN_RESULT");
//                    if (code.contains("http") || code.contains("https")) {
//                        Intent intent = new Intent(mContext, AdBrowserActivity.class);
//                        intent.putExtra(AdBrowserActivity.KEY_URL, code);
//                        startActivity(intent);
//                    } else {
//                        Toast.makeText(mContext, code, Toast.LENGTH_SHORT).show();
//                    }
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        String message = "";
        if (requestCode == RC_CAMERA) {
            message = "本项认证需要摄像头权限，点击确认后在新页面权限管理处，开启相机权限";
        }
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this)
                    .setTitle("权限提示")
                    .setRationale(message).build().show();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_home_layout, container, false);
        initView();
        return mContentView;
    }

    private void initView() {
        mQRCodeView = (TextView) mContentView.findViewById(R.id.qrcode_view);
        mQRCodeView.setOnClickListener(this);
        mCategoryView = (TextView) mContentView.findViewById(R.id.category_view);
        mCategoryView.setOnClickListener(this);
        mSearchView = (TextView) mContentView.findViewById(R.id.search_view);
        mSearchView.setOnClickListener(this);
        mListView = (ListView) mContentView.findViewById(R.id.list_view);
        mListView.setOnItemClickListener(this);
        mLoadingView = (ImageView) mContentView.findViewById(R.id.loading_view);
        AnimationDrawable anim = (AnimationDrawable) mLoadingView.getDrawable();
        anim.start();
    }

    private void requestRecommendData() {
        RequestCenter.requestRecommendData(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                mRecommendData = (BaseRecommendModel) responseObj;
                showSuccessView();
            }

            @Override
            public void onFailure(OkHttpException reasonObj) {
                Log.e("OkHttpError", reasonObj.getEcode() + ". " + reasonObj.getEmsg());
            }

        });
    }

    private void showSuccessView() {
        if (mRecommendData.data.list != null && mRecommendData.data.list.size() > 0) {
            mLoadingView.setVisibility(View.GONE);
            mListView.setVisibility(View.VISIBLE);
            //为listview添加头
            mListView.addHeaderView(new HomeHeaderLayout(activity, mRecommendData.data.head));
            mAdapter = new CourseAdapter(activity, mRecommendData.data.list);
            mListView.setAdapter(mAdapter);
//            mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
//                @Override
//                public void onScrollStateChanged(AbsListView view, int scrollState) {
//                }
//
//                @Override
//                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                    mAdapter.updateAdInScrollView();
//                }
//            });
        } else {
            showErrorView();
        }
    }

    private void showErrorView() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.qrcode_view:
                checkCameraPermissions();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    /**
     * 检测拍摄权限
     */
    @AfterPermissionGranted(RC_CAMERA)
    protected void checkCameraPermissions() {
        String[] perms = {Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(activity, perms)) {//有权限
            startScan();
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.permission_camera),
                    RC_CAMERA, perms);
        }
    }

    private void startScan() {
        Intent intent = new Intent(activity, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SCAN);
    }
}
