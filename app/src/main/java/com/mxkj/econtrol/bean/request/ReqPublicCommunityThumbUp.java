package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by liangshan on 2017/4/19.
 *
 * @Description： 1.3.8.6.    点赞信息请求实体类
 */

public class ReqPublicCommunityThumbUp extends BaseRequestEntity {
    private String communityId;
    private String userId;//

    public ReqPublicCommunityThumbUp(String communityId, String userId) {
        this.communityId = communityId;
        this.userId = userId;
    }

    public ReqPublicCommunityThumbUp() {
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
}
