package com.mxkj.econtrol.bean.response;

import com.mxkj.econtrol.base.BaseEntity;

/**
 * Created by liangshan on 2017/4/12.
 *
 * @Description:
 */
public class HousingEstat extends BaseEntity {
    private String housingEstatId;//小区编码
    private String housingEstatName;//小区名称

    public String getHousingEstatId() {
        return housingEstatId;
    }

    public void setHousingEstatId(String housingEstatId) {
        this.housingEstatId = housingEstatId;
    }

    public String getHousingEstatName() {
        return housingEstatName;
    }

    public void setHousingEstatName(String housingEstatName) {
        this.housingEstatName = housingEstatName;
    }

    @Override
    public String toString() {
        return housingEstatName;
    }
}
