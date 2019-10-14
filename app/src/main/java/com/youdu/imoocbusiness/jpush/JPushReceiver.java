package com.youdu.imoocbusiness.jpush;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.youdu.imoocbusiness.activity.HomeActivity;
import com.youdu.imoocbusiness.activity.LoginActivity;
import com.youdu.imoocbusiness.manager.UserManager;

import org.json.JSONException;

import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * @author YonStone
 * @date 2019/10/11
 * @desc
 */
public class JPushReceiver extends BroadcastReceiver {
    private static final String TAG = JPushReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        switch (intent.getAction()) {
            case JPushInterface.ACTION_NOTIFICATION_RECEIVED:
                break;
            case JPushInterface.ACTION_NOTIFICATION_OPENED:
                String content = bundle.getString(JPushInterface.EXTRA_EXTRA);
                PushMessage pushMessage = parsePayload(content);
                if (getCurrentTask(context)) {
                    /**
                     * 需要登陆且当前没有登陆才去登陆页面
                     */
                    if (pushMessage.messageType != null && pushMessage.messageType.equals("2")
                            && !UserManager.getInstance().hasLogined()) {
                        Intent pushIntent = LoginActivity.actionView(context, pushMessage);
                        pushIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(pushIntent);
                    } else {
                        /**
                         * 不需要登陆或者已经登陆的Case,直接跳转到内容显示页面
                         */
                        Intent pushIntent = PushMessageActivity.actionView(context, pushMessage);
                        pushIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(pushIntent);
                    }
                } else {
                    Intent mainIntent = new Intent(context, HomeActivity.class);
                    mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    if (pushMessage.messageType != null
                            && pushMessage.messageType.equals("2")) {
                        Intent loginIntent = LoginActivity.actionView(context, pushMessage);
                        context.startActivities(new Intent[]{mainIntent, loginIntent});
                    } else {
                        Intent pushIntent = PushMessageActivity.actionView(context, pushMessage);
                        context.startActivities(new Intent[]{mainIntent, pushIntent});
                    }
                }
                break;
        }

    }

    private PushMessage parsePayload(String content) {
        if (TextUtils.isEmpty(content)) {
            return null;
        }
        try {
            return new PushMessage(content);
        } catch (JSONException e) {
            Log.d(TAG, "Fail to parse notification extra content");
            return null;
        }
    }

    /**
     * 目前app被kill无法收到推送，后续
     * 这个是真正的获取指定包名的应用程序是否在运行(无论前台还是后台)
     *
     * @return
     */
    private boolean getCurrentTask(Context context) {

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> appProcessInfos = activityManager.getRunningTasks(50);
        for (ActivityManager.RunningTaskInfo process : appProcessInfos) {

            if (process.baseActivity.getPackageName().equals(context.getPackageName())
                    || process.topActivity.getPackageName().equals(context.getPackageName())) {

                return true;
            }
        }
        return false;
    }
}
