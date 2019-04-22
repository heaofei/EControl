package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by liangshan on 2017/4/19.
 *
 * @Description： 获取评论接口请求实体类
 */

public class ReqGetCommunityCommentsList extends BaseRequestEntity {
    private int page;//页码，第几页
    private int rows;//每页返回多少条
    private String communityId;//
    private String userId;//


    public ReqGetCommunityCommentsList( String communityId, int page, int rows) {
        this.page = page;
        this.rows = rows;
        this.communityId = communityId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }
}
