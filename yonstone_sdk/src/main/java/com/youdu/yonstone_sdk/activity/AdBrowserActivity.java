package com.youdu.yonstone_sdk.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.youdu.yonstone_sdk.R;

public class AdBrowserActivity extends AppCompatActivity {
    public static final String KEY_URL = "url";

    public static Intent actionView(Context context, String url) {
        Intent intent = new Intent(context, AdBrowserActivity.class);
        intent.putExtra(KEY_URL, url);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_browser);
    }
}
