package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by liangshan on 2017/4/17.
 *
 * @Description： 1.3.7.1.    获取房子所有的智能设备部件信息请求实体类
 */

public class ReqGetHouseAllPartList extends BaseRequestEntity {
    private String houseId;//	房子编号

    public ReqGetHouseAllPartList(String houseId) {
        this.houseId = houseId;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }
}
