package com.youdu.imoocbusiness.view.fragment.home;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.youdu.imoocbusiness.R;
import com.youdu.imoocbusiness.module.recommend.BaseRecommendModel;
import com.youdu.imoocbusiness.network.http.RequestCenter;
import com.youdu.imoocbusiness.view.fragment.BaseFragment;

import youdu.com.yonstone_sdk.okhttp.exception.OkHttpException;
import youdu.com.yonstone_sdk.okhttp.listener.DisposeDataListener;

/**
 * @author: Yonstone
 * @function:
 * @date: 19/08/28
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener, AdapterView.OnItemClickListener {
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
//    private CourseAdapter mAdapter;
    private BaseRecommendModel mRecommendData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestRecommendData();
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

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
