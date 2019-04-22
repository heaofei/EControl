package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;
import com.mxkj.econtrol.base.BaseResponse;

/**
 * Created by liangshan on 2017/4/19.
 *
 * @Description： 1.3.8.8.    删除获取评论信息请求实体类
 */

public class ReqDeleteCommunityComments extends BaseRequestEntity {
    private String communityCommentsId;

    public String getCommunityCommentsId() {
        return communityCommentsId;
    }

    public void setCommunityCommentsId(String communityCommentsId) {
        this.communityCommentsId = communityCommentsId;
    }

    public ReqDeleteCommunityComments(String communityCommentsId) {
        this.communityCommentsId = communityCommentsId;

    }
}
