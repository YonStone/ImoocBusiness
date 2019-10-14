package com.youdu.imoocbusiness.jpush;

import android.text.TextUtils;

import com.youdu.imoocbusiness.module.BaseModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author vision
 * @function 极光推送消息实体，包含所有的数据字段。
 */
public class PushMessage extends BaseModel implements PushPayload {

    //    1不需要登录 2需要登录
    public String messageType;
    public String messageUrl;
    public String messageContent;
//
//    public PushMessage(String messageType, String messageUrl, String messageContent) {
//        this.messageType = messageType;
//        this.messageUrl = messageUrl;
//        this.messageContent = messageContent;
//    }

    public PushMessage(String content) throws JSONException {
        JSONObject jsonObject = new JSONObject(content);
        if (!jsonObject.isNull("messageType")) {
            if (!TextUtils.isEmpty(jsonObject.getString("messageType"))) {
                this.messageType = jsonObject.getString("messageType");
            } else {
                this.messageType = "1";
            }
        } else {
            this.messageType = "1";
        }
        if (!jsonObject.isNull("messageUrl")) {
            if (!TextUtils.isEmpty(jsonObject.getString("messageUrl"))) {
                this.messageUrl = jsonObject.getString("messageUrl");
            } else {
                this.messageUrl = "https://www.baidu.com";
            }
        } else {
            this.messageUrl = "https://www.baidu.com";
        }
        if (!jsonObject.isNull("messageContent")) {
            if (!TextUtils.isEmpty(jsonObject.getString("messageContent"))) {
                this.messageContent = jsonObject.getString("messageContent");
            } else {
                this.messageContent = "出来吧，推送王";
            }
        } else {
            this.messageContent = "出来吧，推送王";
        }
    }

    @Override
    public String messageType() {
        return messageType;
    }

    @Override
    public String messageUrl() {
        return messageUrl;
    }

    @Override
    public String messageContent() {
        return messageContent;
    }
}
