package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by liangshan on 2017/4/12.
 *
 * @Description: 获取房子信息列表请求实体类
 */

public class ReqGetHouseList extends BaseRequestEntity {
    private String buildingId;//楼盘

    public ReqGetHouseList() {
    }

    public ReqGetHouseList(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }
}
