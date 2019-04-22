package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by chan on 2017/10/30.
 *
 * @Description:系统通知列表  请求实体类
 */

public class ReqGetSysAnnouncement extends BaseRequestEntity {
    private int page; // 页码，第几页
    private int rows;  //每页返回多少条

    public ReqGetSysAnnouncement(int page, int rows) {
        this.page = page;
        this.rows = rows;
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
