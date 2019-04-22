package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by liangshan on 2017/4/19.
 *
 * @Description： 获取  点赞，添加收藏，阅读数，通用请求类
 */

public class ReqPublicCommunityUser extends BaseRequestEntity {
    // //参数  communityId 文章ID userId   ）
    private String communityId;//
    private String userId;//


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
