package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by liangshan on 2017/4/14.
 *
 * @Description: 房子控制用户列表接口请求实体类
 */

public class ReqGetHouseUserList extends BaseRequestEntity {
    private String houseId;

    public ReqGetHouseUserList(String houseId) {
        this.houseId = houseId;
    }

    public ReqGetHouseUserList() {
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }
}
