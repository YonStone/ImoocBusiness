package com.youdu.imoocbusiness.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.youdu.yonstone_sdk.constant.ExtraConstant;
import com.youdu.imoocbusiness.R;
import com.youdu.imoocbusiness.activity.base.BaseActivity;
import com.youdu.imoocbusiness.manager.DialogManager;
import com.youdu.imoocbusiness.manager.UserManager;
import com.youdu.imoocbusiness.jpush.PushMessage;
import com.youdu.imoocbusiness.module.user.User;
import com.youdu.imoocbusiness.network.http.RequestCenter;
import com.youdu.yonstone_sdk.adutil.Toaster;
import com.youdu.yonstone_sdk.okhttp.listener.DisposeDataListener;


public class LoginActivity extends BaseActivity implements View.OnClickListener {
    /**
     * UI
     */
    private EditText mUserNameView;
    private EditText mPasswordView;
    private TextView mLoginView;
    public static final String LOGIN_ACTION = "login_action";
    /**
     * data
     */
    private PushMessage mPushMessage; // 推送过来的消息
    private boolean fromPush; // 是否从推送到此页面

    public static Intent actionView(Context context, PushMessage pushMessage) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(ExtraConstant.EXTRA_PUSH_MESSAGE, pushMessage);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initData();
        initView();
    }

    private void initData() {
        Intent intent = getIntent();
        mPushMessage = (PushMessage) intent.getSerializableExtra(ExtraConstant.EXTRA_PUSH_MESSAGE);
    }

    private void initView() {
        changeStatusBarColor(R.color.white);
        mUserNameView = findViewById(R.id.associate_email_input);
        mPasswordView = (EditText) findViewById(R.id.login_input_password);
        mLoginView = (TextView) findViewById(R.id.login_button);
        mLoginView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_button:
                login();
                break;
        }
    }

    private void login() {
        String userName = mUserNameView.getText().toString().trim();
        String password = mPasswordView.getText().toString().trim();

        if (TextUtils.isEmpty(userName)) {
            return;
        }

        if (TextUtils.isEmpty(password)) {
            return;
        }

        DialogManager.getInstnce().showProgressDialog(this);

        RequestCenter.login(userName, password, new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                DialogManager.getInstnce().dismissProgressDialog();

                /**
                 * 这部分可以封装起来，封装为到一个登陆流程类中
                 */
                User user = (User) responseObj;
                UserManager.getInstance().setUser(user);//保存当前用户单例对象
//                connectToSever();
                sendLoginBroadcast();
                /**
                 * 还应该将用户信息存入数据库，这样可以保证用户打开应用后总是登陆状态
                 * 只有用户手动退出登陆时候，将用户数据从数据库中删除。
                 */
                insertUserInfoIntoDB();

                if (mPushMessage != null) { //从推送过来的
                    Intent intent = new Intent(LoginActivity.actionView(activity, mPushMessage));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                finish();//销毁当前登陆页面
            }

            @Override
            public void onFailure(Object reasonObj) {
                Toaster.show(activity, "登录失败");
                DialogManager.getInstnce().dismissProgressDialog();
            }
        });
    }

    /**
     * 用户信息存入数据库，以使让用户一打开应用就是一个登陆过的状态
     */
    private void insertUserInfoIntoDB() {

    }

    //发送孩子指定类型广播
    private void sendLoginBroadcast() {
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(LOGIN_ACTION));
    }
}
