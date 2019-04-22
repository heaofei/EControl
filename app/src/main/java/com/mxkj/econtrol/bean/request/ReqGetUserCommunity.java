package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by liangshan on 2017/6/12.
 *
 * @Description： 获取个人社区列表请求实体类
 */

public class ReqGetUserCommunity extends BaseRequestEntity {
    private int page;//页码，第几页
    private int rows;//每页返回多少条

    private String userId;

    public ReqGetUserCommunity() {
    }

    public ReqGetUserCommunity(int page, int rows, String userId) {
        this.page = page;
        this.rows = rows;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
