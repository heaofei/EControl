package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by chan on 2018/4/26.
 *
 * @Description: 部件-部件解绑网关请求实体类
 */

public class ReqPartUnbindGateway extends BaseRequestEntity {

    private String partId;//部件id


    public String getPartId() {
        return partId;
    }

    public void setPartId(String partId) {
        this.partId = partId;
    }
}
