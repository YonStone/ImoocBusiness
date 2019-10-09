package com.youdu.imoocbusiness.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.umeng.socialize.UMShareAPI;
import com.youdu.imoocbusiness.R;
import com.youdu.imoocbusiness.activity.base.BaseActivity;
import com.youdu.imoocbusiness.adapter.PhotoPagerAdapter;
import com.youdu.yonstone_sdk.adutil.Toaster;
import com.youdu.yonstone_sdk.adutil.Utils;

import java.util.ArrayList;
import java.util.Date;

import youdu.com.ext_share_login_umeng.ext.ShareDialog;

public class PhotoViewActivity extends BaseActivity implements View.OnClickListener {
    private static final String PHOTO_LIST = "com.youdu.imoocbusiness.activity.photo_list";
    /**
     * UI
     */
    private ViewPager mPager;
    private TextView mIndictorView;
    private ImageView mShareView;
    /**
     * Data
     */
    private PhotoPagerAdapter mAdapter;
    private ArrayList<String> mPhotoLists; //图片地址
    private int mLength;
    private int mCurrentPos; //用于分享
    private long t1;

    public static Intent actionView(Context context, ArrayList<String> photoList) {
        Intent intent = new Intent(context, PhotoViewActivity.class);
        intent.putStringArrayListExtra(PHOTO_LIST, photoList);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);
        initData();
        initView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    private void initData() {
        mPhotoLists = getIntent().getStringArrayListExtra(PHOTO_LIST);
        mLength = mPhotoLists.size();
    }

    private void initView() {
        mIndictorView = (TextView) findViewById(R.id.indictor_view);
        mIndictorView.setText("1/" + mLength);
        mShareView = (ImageView) findViewById(R.id.share_view);
        mShareView.setOnClickListener(this);
        mPager = (ViewPager) findViewById(R.id.photo_pager);
        mAdapter = new PhotoPagerAdapter(this, mPhotoLists, false);
        mPager.setPageMargin(Utils.dip2px(this, 30));
        mPager.setAdapter(mAdapter);
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mIndictorView.setText(String.valueOf((position + 1)).concat("/").
                        concat(String.valueOf(mLength)));
                mCurrentPos = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.share_view:
                if (t1 == 0) {
                    shareView();
                    t1 = (new Date()).getTime();
                } else {
                    long curTime = (new Date()).getTime();
                    System.out.println("两次单击间隔时间：" + (curTime - t1));
                    //间隔2秒允许点击，可以根据需要修改间隔时间
                    if (curTime - t1 > 2 * 1000) {
                        t1 = curTime;
                        shareView();
                    } else {
                        Toaster.show(activity, "请勿频繁点击！");
                    }
                }
        }
    }

    private void shareView() {
        ShareDialog dialog = new ShareDialog(activity, "标题", "内容",
                "https://www.baidu.com", mPhotoLists.get(mCurrentPos));
        dialog.show();
    }
}
