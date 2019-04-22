package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by chan on 2018/1/4.
 *
 * @Description: 获取申请列表  请求实体类
 */

public class ReqGetApplyBindHouseList extends BaseRequestEntity {
    private int page; // 页码，第几页
    private int rows;  //每页返回多少条
    private String houseId;
    private String status;

    public ReqGetApplyBindHouseList(int page, int rows, String houseId, String status) {
        this.page = page;
        this.rows = rows;
        this.houseId = houseId;
        this.status = status;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
}
