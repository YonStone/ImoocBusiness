package com.youdu.imoocbusiness.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youdu.imoocbusiness.R;
import com.youdu.imoocbusiness.activity.base.BaseActivity;
import com.youdu.imoocbusiness.adapter.CourseCommentAdapter;
import com.youdu.imoocbusiness.manager.UserManager;
import com.youdu.imoocbusiness.module.course.BaseCourseModel;
import com.youdu.imoocbusiness.module.course.CourseCommentValue;
import com.youdu.imoocbusiness.module.user.User;
import com.youdu.imoocbusiness.network.http.RequestCenter;
import com.youdu.imoocbusiness.util.Util;
import com.youdu.imoocbusiness.view.course.CourseDetailFooterView;
import com.youdu.imoocbusiness.view.course.CourseDetailHeaderView;
import com.youdu.yonstone_sdk.adutil.Toaster;
import com.youdu.yonstone_sdk.okhttp.listener.DisposeDataListener;

public class CourseDetailActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private static final String COURSE_ID = "com.youdu.imoocbusiness.activity.courseId";

    /**
     * UI
     */
    private ImageView mBackView;
    private ListView mListView;
    private ImageView mLoadingView;
    private RelativeLayout mBottomLayout;
    private ImageView mJianPanView;
    private EditText mInputEditView;
    private TextView mSendView;
    private CourseDetailHeaderView headerView;
    private CourseDetailFooterView footerView;
    private CourseCommentAdapter mAdapter;
    /**
     * Data
     */
    private String mCourseID;
    private BaseCourseModel mData;
    private String tempHint = ""; //输入框hint

    public static Intent actionView(Context context) {
        return new Intent(context, CourseDetailActivity.class);
    }

    public static Intent actionView(Context context, String courseId) {
        Intent intent = new Intent(context, CourseDetailActivity.class);
        intent.putExtra(COURSE_ID, courseId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        initData();
        intiView();
        requestDetail();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        initData();
        intiView();
        requestDetail();
    }

    private void initData() {
        mCourseID = getIntent().getStringExtra(COURSE_ID);
    }

    private void intiView() {
        mBackView = (ImageView) findViewById(R.id.back_view);
        mBackView.setOnClickListener(this);
        mListView = (ListView) findViewById(R.id.comment_list_view);
        mListView.setOnItemClickListener(this);
        mListView.setVisibility(View.GONE);
        mLoadingView = (ImageView) findViewById(R.id.loading_view);
        mLoadingView.setVisibility(View.VISIBLE);
        AnimationDrawable anim = (AnimationDrawable) mLoadingView.getDrawable();
        anim.start();

        mBottomLayout = (RelativeLayout) findViewById(R.id.bottom_layout);
        mJianPanView = (ImageView) findViewById(R.id.jianpan_view);
        mJianPanView.setOnClickListener(this);
        mInputEditView = (EditText) findViewById(R.id.comment_edit_view);
        mSendView = (TextView) findViewById(R.id.send_view);
        mSendView.setOnClickListener(this);
        mBottomLayout.setVisibility(View.GONE);

        intoEmptyState();
    }

    private void requestDetail() {
        RequestCenter.requestCourseDetail(mCourseID, new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                mData = (BaseCourseModel) responseObj;
                updateUI();
            }

            @Override
            public void onFailure(Object reasonObj) {

            }
        });
    }

    private void updateUI() {
        mLoadingView.setVisibility(View.GONE);
        mListView.setVisibility(View.VISIBLE);
        mAdapter = new CourseCommentAdapter(this, mData.data.body);
        mListView.setAdapter(mAdapter);
        if (headerView != null) {
            mListView.removeHeaderView(headerView);
        }
        headerView = new CourseDetailHeaderView(activity, mData.data.head);
        mListView.addHeaderView(headerView);
        if (footerView != null) {
            mListView.removeFooterView(footerView);
        }
        footerView = new CourseDetailFooterView(activity, mData.data.footer);
        mListView.addFooterView(footerView);
        mBottomLayout.setVisibility(View.VISIBLE);
    }

    /**
     * EditText进入编辑状态
     */
    private void intoEditState(String hint) {
        mInputEditView.requestFocus();
        mInputEditView.setHint(hint);
        Util.showSoftInputMethod(this, mInputEditView);
    }

    public void intoEmptyState() {
        tempHint = "";
        mInputEditView.setText("");
        mInputEditView.setHint(getString(R.string.input_comment));
        Util.hideSoftInputMethod(this, mInputEditView);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_view:
                finish();
                break;
            case R.id.send_view:
                if (UserManager.getInstance().hasLogined()) {
                    String content = mInputEditView.getText().toString().trim();
                    if (!TextUtils.isEmpty(content)) {
                        mAdapter.addComment(assembleCommentValue(content));
                        intoEmptyState();
                    } else {
                        Toaster.show(activity, "评论不能为空");
                    }
                } else {
                    startActivity(new Intent(activity, LoginActivity.class));
                }
                break;
            case R.id.jianpan_view:
                mInputEditView.requestFocus();
                Util.showSoftInputMethod(this, mInputEditView);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        int cursor = i - mListView.getHeaderViewsCount();
        //头尾不响应onItemClick点击
        if (cursor >= 0 && cursor < mAdapter.getCommentCount()) {
            if (UserManager.getInstance().hasLogined()) {
                CourseCommentValue value = (CourseCommentValue) mAdapter.getItem(cursor);
                if (value.userId.equals(UserManager.getInstance().getUser().data.userId)) {
                    intoEmptyState();
                    Toaster.show(activity, "不能评论自己");
                } else {
                    //不是自己的评论，可以回复
                    tempHint = getString(R.string.comment_hint_head).concat(value.name).
                            concat(getString(R.string.comment_hint_footer));
                    intoEditState(tempHint);
                }
            } else {
                startActivity(new Intent(activity, LoginActivity.class));
            }
        }
    }

    /**
     * 组装CommentValue对象
     *
     * @return
     */
    private CourseCommentValue assembleCommentValue(String comment) {
        User user = UserManager.getInstance().getUser();
        CourseCommentValue value = new CourseCommentValue();
        value.name = user.data.name;
        value.logo = user.data.photoUrl;
        value.userId = user.data.userId;
        value.type = 1;
        value.text = tempHint + comment;
        return value;
    }
}
