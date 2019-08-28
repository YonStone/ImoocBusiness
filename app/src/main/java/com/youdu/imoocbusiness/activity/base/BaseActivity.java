package com.youdu.imoocbusiness.activity.base;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.youdu.imoocbusiness.util.StatusBarUtil;

/**
 * @author: Yonstone
 * @function: 所有Activity的基类，用来处理一些公共事件，如：数据统计
 * @date: 19/08/28
 */
public class BaseActivity extends AppCompatActivity {
    public String TAG;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reverseStatusColor();
    }
    /**
     * 隐藏状态栏
     */
    public void hiddenStatusBar() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 改变状态栏颜色
     *
     * @param color
     */
    public void changeStatusBarColor(@ColorRes int color) {
        StatusBarUtil.setStatusBarColor(this, color);
    }

    /**
     * 调整状态栏为亮模式，这样状态栏的文字颜色就为深模式了。
     */
    private void reverseStatusColor() {
        StatusBarUtil.statusBarLightMode(this);
    }
}
