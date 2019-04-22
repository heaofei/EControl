package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by liangshan on 2017/4/19.
 *
 * @Description： 评论接口请求实体类
 */

public class ReqPublicCommunityComment extends BaseRequestEntity {
    private String communityId;
    private String userId; //用户编号
    private String replyUserId;    //回复用户编号
    private String msg;//评论

    public ReqPublicCommunityComment() {
    }

    public ReqPublicCommunityComment(String communityId, String userId, String replyUserId, String msg) {
        this.communityId = communityId;
        this.userId = userId;
        this.replyUserId = replyUserId;
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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
