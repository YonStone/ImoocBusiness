package com.youdu.imoocbusiness.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.youdu.imoocbusiness.R;
import com.youdu.imoocbusiness.module.recommend.RecommendBodyValue;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import youdu.com.yonstone_sdk.adutil.ImageLoaderManager;

/**
 * @author YonStone
 * @date 2019/09/06
 * @desc
 */
public class CourseAdapter extends BaseAdapter {
    /**
     * Common
     */
    private static final int CARD_COUNT = 4;
    private static final int VIDEO_TYPE = 0x00;
    private static final int CARD_TYPE_SINGLE = 0x02;
    private static final int CARD_TYPE_ONE = 0x01;
    private static final int CARD_TYPE_THREE = 0x03;

    private ImageLoaderManager mImagerLoader;
    private LayoutInflater mInflater;
    private ViewHolder mViewHolder;
    private ArrayList<RecommendBodyValue> mData;
    private Context mContext;

    public CourseAdapter(Context context, ArrayList<RecommendBodyValue> data) {
        mContext = context;
        mData = data;
        mInflater = LayoutInflater.from(mContext);
        mImagerLoader = ImageLoaderManager.getInstance(mContext);
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemViewType(int position) {
        RecommendBodyValue value = (RecommendBodyValue) getItem(position);
        return value.type;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        int type = getItemViewType(i);
        final RecommendBodyValue value = (RecommendBodyValue) getItem(i);
        //无tag时
        if (convertView == null) {
            switch (type) {
//                case VIDEO_TYPE:
//                    //显示video卡片
//                    mViewHolder = new ViewHolder();
//                    convertView = mInflater.inflate(R.layout.item_video_layout, parent, false);
//                    mViewHolder.mVieoContentLayout = (RelativeLayout)
//                            convertView.findViewById(R.id.video_ad_layout);
//                    mViewHolder.mLogoView = (CircleImageView) convertView.findViewById(R.id.item_logo_view);
//                    mViewHolder.mTitleView = (TextView) convertView.findViewById(R.id.item_title_view);
//                    mViewHolder.mInfoView = (TextView) convertView.findViewById(R.id.item_info_view);
//                    mViewHolder.mFooterView = (TextView) convertView.findViewById(R.id.item_footer_view);
//                    mViewHolder.mShareView = (ImageView) convertView.findViewById(R.id.item_share_view);
                //为对应布局创建播放器
//                    mAdsdkContext = new VideoAdContext(mViewHolder.mVieoContentLayout,
//                            new Gson().toJson(value), null);
//                    mAdsdkContext.setAdResultListener(new AdContextInterface() {
//                        @Override
//                        public void onAdSuccess() {
//                        }
//
//                        @Override
//                        public void onAdFailed() {
//                        }
//
//                        @Override
//                        public void onClickVideo(String url) {
//                            Intent intent = new Intent(mContext, AdBrowserActivity.class);
//                            intent.putExtra(AdBrowserActivity.KEY_URL, url);
//                            mContext.startActivity(intent);
//                        }
//                    });
//                    break;
//                case CARD_TYPE_ONE:
//                    mViewHolder = new ViewHolder();
//                    convertView = mInflater.inflate(R.layout.item_product_card_one_layout, parent, false);
//                    mViewHolder.mLogoView = (CircleImageView) convertView.findViewById(R.id.item_logo_view);
//                    mViewHolder.mTitleView = (TextView) convertView.findViewById(R.id.item_title_view);
//                    mViewHolder.mInfoView = (TextView) convertView.findViewById(R.id.item_info_view);
//                    mViewHolder.mFooterView = (TextView) convertView.findViewById(R.id.item_footer_view);
//                    mViewHolder.mPriceView = (TextView) convertView.findViewById(R.id.item_price_view);
//                    mViewHolder.mFromView = (TextView) convertView.findViewById(R.id.item_from_view);
//                    mViewHolder.mZanView = (TextView) convertView.findViewById(R.id.item_zan_view);
//                    mViewHolder.mProductLayout = (LinearLayout) convertView.findViewById(R.id.product_photo_layout);
//                    break;
                case CARD_TYPE_SINGLE:
                    mViewHolder = new ViewHolder();
                    convertView = mInflater.inflate(R.layout.item_product_card_two_layout, parent, false);
                    mViewHolder.mLogoView = (CircleImageView) convertView.findViewById(R.id.item_logo_view);
                    mViewHolder.mTitleView = (TextView) convertView.findViewById(R.id.item_title_view);
                    mViewHolder.mInfoView = (TextView) convertView.findViewById(R.id.item_info_view);
                    mViewHolder.mFooterView = (TextView) convertView.findViewById(R.id.item_footer_view);
                    mViewHolder.mProductView = (ImageView) convertView.findViewById(R.id.product_photo_view);
                    mViewHolder.mPriceView = (TextView) convertView.findViewById(R.id.item_price_view);
                    mViewHolder.mFromView = (TextView) convertView.findViewById(R.id.item_from_view);
                    mViewHolder.mZanView = (TextView) convertView.findViewById(R.id.item_zan_view);
                    break;
//                case CARD_TYPE_THREE:
//                    mViewHolder = new ViewHolder();
//                    convertView = mInflater.inflate(R.layout.item_product_card_three_layout, null, false);
//                    mViewHolder.mViewPager = (ViewPager) convertView.findViewById(R.id.pager);
//                    //add data
//                    ArrayList<RecommendBodyValue> recommandList = Util.handleData(value);
//                    mViewHolder.mViewPager.setPageMargin(Utils.dip2px(mContext, 12));
//                    mViewHolder.mViewPager.setAdapter(new HotSalePagerAdapter(mContext, recommandList));
//                    mViewHolder.mViewPager.setCurrentItem(recommandList.size() * 100);
//                    break;
            }
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        //填充item的数据
        switch (type) {
//            case VIDEO_TYPE:
//                mImagerLoader.displayImage(mViewHolder.mLogoView, value.logo);
//                mViewHolder.mTitleView.setText(value.title);
//                mViewHolder.mInfoView.setText(value.info.concat(mContext.getString(R.string.tian_qian)));
//                mViewHolder.mFooterView.setText(value.text);
//                mViewHolder.mShareView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        ShareDialog dialog = new ShareDialog(mContext, false);
//                        dialog.setShareType(Platform.SHARE_VIDEO);
//                        dialog.setShareTitle(value.title);
//                        dialog.setShareTitleUrl(value.site);
//                        dialog.setShareText(value.text);
//                        dialog.setShareSite(value.title);
//                        dialog.setShareTitle(value.site);
//                        dialog.setUrl(value.resource);
//                        dialog.show();
//                    }
//                });
//                break;
//            case CARD_TYPE_ONE:
//                mImagerLoader.displayImage(mViewHolder.mLogoView, value.logo);
//                mViewHolder.mTitleView.setText(value.title);
//                mViewHolder.mInfoView.setText(value.info.concat(mContext.getString(R.string.tian_qian)));
//                mViewHolder.mFooterView.setText(value.text);
//                mViewHolder.mPriceView.setText(value.price);
//                mViewHolder.mFromView.setText(value.from);
//                mViewHolder.mZanView.setText(mContext.getString(R.string.dian_zan).concat(value.zan));
//                mViewHolder.mProductLayout.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(mContext, PhotoViewActivity.class);
//                        intent.putStringArrayListExtra(PhotoViewActivity.PHOTO_LIST, value.url);
//                        mContext.startActivity(intent);
//                    }
//                });
//                mViewHolder.mProductLayout.removeAllViews();
//                //动态添加多个imageview
//                for (String url : value.url) {
//                    mViewHolder.mProductLayout.addView(createImageView(url));
//                }
//                break;
            case CARD_TYPE_SINGLE:
                mImagerLoader.displayImage(mViewHolder.mLogoView, value.logo);
                mViewHolder.mTitleView.setText(value.title);
                mViewHolder.mInfoView.setText(value.info.concat(mContext.getString(R.string.tian_qian)));
                mViewHolder.mFooterView.setText(value.text);
                mViewHolder.mPriceView.setText(value.price);
                mViewHolder.mFromView.setText(value.from);
                mViewHolder.mZanView.setText(mContext.getString(R.string.dian_zan).concat(value.zan));
                //为单个ImageView加载远程图片
                mImagerLoader.displayImage(mViewHolder.mProductView, value.url.get(0));
                break;
            case CARD_TYPE_THREE:
                break;
        }
        return convertView;
    }

    private static class ViewHolder {
        //所有Card共有属性
        private CircleImageView mLogoView;
        private TextView mTitleView;
        private TextView mInfoView;
        private TextView mFooterView;
        //Video Card特有属性
        private RelativeLayout mVieoContentLayout;
        private ImageView mShareView;

        //Video Card外所有Card具有属性
        private TextView mPriceView;
        private TextView mFromView;
        private TextView mZanView;
        //Card One特有属性
        private LinearLayout mProductLayout;
        //Card Two特有属性
        private ImageView mProductView;
        //Card Three特有属性
        private ViewPager mViewPager;
    }
}
