package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * 获取系统通知实体类
 * Created by ${  chenjun  } on 2017/10/27.
 */

public class ReqGetSysAnnouncementDetail extends BaseRequestEntity {

    private String id;


    public ReqGetSysAnnouncementDetail(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
