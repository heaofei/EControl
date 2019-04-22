package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by liangshan on 2017/4/19.
 *
 * @Description： 获取社区信息接口请求实体类
 */

public class ReqGetCommunityList extends BaseRequestEntity {

    private int page;//页码，第几页
    private int rows;//每页返回多少条
    private String userId;//
    private String houseId;//

    public ReqGetCommunityList(int page, int rows) {
        this.page = page;
        this.rows = rows;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
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
