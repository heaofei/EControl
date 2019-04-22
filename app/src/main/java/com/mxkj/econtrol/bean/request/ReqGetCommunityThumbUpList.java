package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by liangshan on 2017/4/19.
 *
 * @Description： 1.3.8.3.    获取点赞信息请求实体类
 */

public class ReqGetCommunityThumbUpList extends BaseRequestEntity {
    private int page;//页码，第几页
    private int rows;//每页返回多少条
    private String communityId;

    public ReqGetCommunityThumbUpList() {
    }

    public ReqGetCommunityThumbUpList(int page, int rows, String communityId) {
        this.page = page;
        this.rows = rows;
        this.communityId = communityId;
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
