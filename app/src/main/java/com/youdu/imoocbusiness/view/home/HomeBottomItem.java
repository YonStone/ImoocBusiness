package com.youdu.imoocbusiness.view.home;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youdu.imoocbusiness.R;
import com.youdu.imoocbusiness.module.recommend.RecommendFooterValue;

import com.youdu.yonstone_sdk.adutil.ImageLoaderManager;

/**
 * @author YonStone
 * @date 2019/09/07
 * @desc
 */
public class HomeBottomItem extends RelativeLayout {

    private Context mContext;
    /**
     * UI
     */
    private RelativeLayout mRootView;
    private TextView mTitleView;
    private TextView mInfoView;
    private TextView mInterestingView;
    private ImageView mImageOneView;
    private ImageView mImageTwoView;

    /**
     * Data
     */
    private RecommendFooterValue mData;
    private ImageLoaderManager mImageLoader;

    public HomeBottomItem(Context context, RecommendFooterValue data) {
        this(context, null, data);
    }

    public HomeBottomItem(Context context, AttributeSet attrs, RecommendFooterValue data) {
        super(context, attrs);
        mContext = context;
        mData = data;
        mImageLoader = ImageLoaderManager.getInstance(mContext);
        initView();
    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        mRootView = (RelativeLayout) inflater.inflate(R.layout.item_home_recommand_layout, this); //添加到当前布局中
        mTitleView = (TextView) mRootView.findViewById(R.id.title_view);
        mInfoView = (TextView) mRootView.findViewById(R.id.info_view);
        mInterestingView = (TextView) mRootView.findViewById(R.id.interesting_view);
        mImageOneView = (ImageView) mRootView.findViewById(R.id.icon_1);
        mImageTwoView = (ImageView) mRootView.findViewById(R.id.icon_2);
        mRootView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
//                Intent intent = new Intent(mContext, AdBrowserActivity.class);
//                intent.putExtra(AdBrowserActivity.KEY_URL, mData.destationUrl);
//                mContext.startActivity(intent);
            }
        });
        mTitleView.setText(mData.title);
        mInfoView.setText(mData.info);
        mInterestingView.setText(mData.from);
        mImageLoader.displayImage(mImageOneView, mData.imageOne);
        mImageLoader.displayImage(mImageTwoView, mData.imageTwo);
    }
}
