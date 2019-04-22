package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by liangshan on 2017/4/19.
 *
 * @Description： 请求，添加文章的评论点赞 实体类
 */

public class ReqCommunityCommentThumbUp extends BaseRequestEntity {
    // //参数  CommunityCommentId 评论ID userId   ）
    private String CommunityCommentId;//
    private String userId;//


    public String getCommunityCommentId() {
        return CommunityCommentId;
    }

    public void setCommunityCommentId(String communityCommentId) {
        CommunityCommentId = communityCommentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
