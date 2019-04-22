package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by liangshan on 2017/4/12.
 *
 * @Description: 获取小区列表请求实体类
 */

public class ReqGetHousingEstateList extends BaseRequestEntity {
    private String cityId;//市级编号
    private String areaId;//地区编号

    public ReqGetHousingEstateList() {
    }

    public ReqGetHousingEstateList(String cityId, String areaId) {
        this.cityId = cityId;
        this.areaId = areaId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }
}
