package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by liangshan on 2017/4/23.
 *
 * @Description： 1.3.10.1.    获取推送信息请求实体类
 */

public class ReqGetAppPushMessage extends BaseRequestEntity {
    private String pushId;//推送信息编号
    private String fromUserId;//发送人编号

    public ReqGetAppPushMessage() {
    }

    public ReqGetAppPushMessage(String pushId, String fromUserId) {
        this.pushId = pushId;
        this.fromUserId = fromUserId;
    }

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
}
