package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by liangshan on 2017/4/19.
 *
 * @Description： 取消点赞
 */

public class ReqDeleteCommunityThumbUp extends BaseRequestEntity {
    private String communityThumbUpId;//编号

    public ReqDeleteCommunityThumbUp(String communityThumbUpId) {
        this.communityThumbUpId = communityThumbUpId;
    }

    public String getCommunityThumbUpId() {
        return communityThumbUpId;
    }

    public void setCommunityThumbUpId(String communityThumbUpId) {
        this.communityThumbUpId = communityThumbUpId;
    }
}
