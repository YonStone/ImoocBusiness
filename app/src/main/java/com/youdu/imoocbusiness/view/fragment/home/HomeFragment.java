package com.youdu.imoocbusiness.view.fragment.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.youdu.imoocbusiness.R;
import com.youdu.imoocbusiness.view.fragment.BaseFragment;

/**
 * @author: Yonstone
 * @function:
 * @date: 19/08/28
 */
public class HomeFragment extends BaseFragment {
    /**
     * UI
     */
    private View mContentView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        mContentView = inflater.inflate(R.layout.fragment_home_layout, container, false);
        return mContentView;
    }
}
