package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by liangshan on 2017/4/23.
 *
 * @Description： 1.3.10.2.    推送信息处理
 */

public class ReqAppPushMessageReply extends BaseRequestEntity {

    private String pushId;//	推送信息编号
    private String fromUserId;//	发送人编号
    private String replyState;//回复状态
    private String replyMessage;//回复描述

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getReplyState() {
        return replyState;
    }

    public void setReplyState(String replyState) {
        this.replyState = replyState;
    }

    public String getReplyMessage() {
        return replyMessage;
    }

    public void setReplyMessage(String replyMessage) {
        this.replyMessage = replyMessage;
    }
}
