package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by liangshan on 2017/4/12.
 *
 * @Description: 获取楼盘单位列表信息请求实体类
 */

public class ReqGetBuildingList extends BaseRequestEntity {


    private String housingEstatId;

    public ReqGetBuildingList() {
    }

    public ReqGetBuildingList(String housingEstatId) {
        this.housingEstatId = housingEstatId;
    }

    public String getHousingEstatId() {
        return housingEstatId;
    }

    public void setHousingEstatId(String housingEstatId) {
        this.housingEstatId = housingEstatId;
    }
}
