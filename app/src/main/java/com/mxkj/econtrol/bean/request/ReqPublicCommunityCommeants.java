package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by liangshan on 2017/4/19.
 *
 * @Description： 获取评论接口请求实体类
 */

public class ReqPublicCommunityCommeants extends BaseRequestEntity {
    // //参数 msg 评论内容 communityId 文章ID userId   replyUserId回复人ID（非必填）
    private String msg;//
    private String communityId;//
    private String userId;//
    private String replyUserId;//


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReplyUserId() {
        return replyUserId;
    }

    public void setReplyUserId(String replyUserId) {
        this.replyUserId = replyUserId;
    }
}
