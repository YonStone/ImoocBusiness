package com.youdu.yonstone_sdk.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.youdu.yonstone_sdk.R;
import com.youdu.yonstone_sdk.constant.ExtraConstant;

public class AdBrowserActivity extends AppCompatActivity {
    private WebView webView;
    private String url;

    public static Intent actionView(Context context, String url) {
        Intent intent = new Intent(context, AdBrowserActivity.class);
        intent.putExtra(ExtraConstant.EXTRA_KEY_URL, url);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_browser);
        initData();
        initView();
    }

    private void initData() {
        url = getIntent().getStringExtra(ExtraConstant.EXTRA_KEY_URL);
    }

    private void initView() {
        webView = findViewById(R.id.webView);
        //不现实水平滚动条
        webView.setHorizontalScrollBarEnabled(false);
        //不现实垂直滚动条
        webView.setVerticalScrollBarEnabled(false);
        //滚动条在WebView内侧显示
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        //滚动条在WebView外侧显示
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        //获取触摸焦点
        webView.requestFocusFromTouch();
        webView.setWebViewClient(new WebViewClient() {
            //重写shouldOverrideUrlLoading方法，使点击链接后不使用其他的浏览器打开。
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                //如果不需要其他对点击链接事件的处理返回true，否则返回false
                return true;
            }
        });


        //声明WebSettings子类
        WebSettings webSettings = webView.getSettings();
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        //其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式

        webView.loadUrl(url);
    }

}