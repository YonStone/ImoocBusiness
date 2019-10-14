package com.youdu.imoocbusiness.jpush;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.youdu.imoocbusiness.R;
import com.youdu.imoocbusiness.activity.base.BaseActivity;
import com.youdu.yonstone_sdk.activity.AdBrowserActivity;
import com.youdu.yonstone_sdk.constant.ExtraConstant;

/**
 * Created by qndroid on 16/11/19.
 *
 * @function 显示推送的消息界面
 */
public class PushMessageActivity extends BaseActivity {

    /**
     * UI
     */
    private TextView mTypeView;
    private TextView mTypeValueView;
    private TextView mContentView;
    private TextView mContentValueView;

    /**
     * data
     */
    private PushMessage mPushMessage;

    public static Intent actionView(Context context, PushMessage pushMessage) {
        Intent intent = new Intent(context, PushMessageActivity.class);
        intent.putExtra(ExtraConstant.EXTRA_PUSH_MESSAGE, pushMessage);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jpush_layout);
        initData();
        initView();
    }

    //初始化推送过来的数据
    private void initData() {
        mPushMessage = (PushMessage) getIntent().getSerializableExtra(ExtraConstant.EXTRA_PUSH_MESSAGE);
    }

    private void initView() {
        mTypeView = (TextView) findViewById(R.id.message_type_view);
        mTypeValueView = (TextView) findViewById(R.id.message_type_value_view);
        mContentView = (TextView) findViewById(R.id.message_content_view);
        mContentValueView = (TextView) findViewById(R.id.message_content_value_view);

        mTypeValueView.setText(mPushMessage.messageType);
        mContentValueView.setText(mPushMessage.messageContent);
        if (!TextUtils.isEmpty(mPushMessage.messageUrl)) {
            //跳转到web页面
            gotoWebView();
        }
    }

    private void gotoWebView() {
        Intent intent = new Intent(AdBrowserActivity.actionView(this, mPushMessage.messageUrl));
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
