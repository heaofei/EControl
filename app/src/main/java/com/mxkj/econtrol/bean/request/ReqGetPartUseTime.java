package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by ${  chenjun  } on 2019/3/21.
 */

public class ReqGetPartUseTime extends BaseRequestEntity {
    private String houseId;
    private String day;// 日期


    public ReqGetPartUseTime(String houseId, String day) {
        this.houseId = houseId;
        this.day = day;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
