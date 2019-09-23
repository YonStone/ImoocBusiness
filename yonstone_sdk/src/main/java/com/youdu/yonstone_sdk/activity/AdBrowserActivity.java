package com.youdu.yonstone_sdk.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.youdu.yonstone_sdk.R;

public class AdBrowserActivity extends AppCompatActivity {
    public static final String KEY_URL = "url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_browser);
    }
}
