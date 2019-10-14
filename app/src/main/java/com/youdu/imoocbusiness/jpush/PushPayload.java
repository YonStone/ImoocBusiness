package com.youdu.imoocbusiness.jpush;

/**
 * @author YonStone
 * @date 2019/10/12
 * @desc
 */
public interface PushPayload {
    String messageType();

    String messageUrl();

    String messageContent();
}
