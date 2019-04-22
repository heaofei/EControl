package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by ${  chenjun  } on 2017/10/27.
 */

public class ReqGetAppPushMessageList extends BaseRequestEntity {

    private Integer page;
    private Integer rows;


    public ReqGetAppPushMessageList(Integer page, Integer rows) {
        this.page = page;
        this.rows = rows;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }
}
