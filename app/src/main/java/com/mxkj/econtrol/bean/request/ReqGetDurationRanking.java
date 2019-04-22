package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by chan on 2017/11/24.
 *
 * @Description: 获取全部设备使用时长请求实体类
 */

public class ReqGetDurationRanking extends BaseRequestEntity {
    private String houseId;
    private String dateValue; //时间值( yyyy-MM-dd)
    private String dateType;  //时间类型（month-月，day-日）


    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getDateValue() {
        return dateValue;
    }

    public void setDateValue(String dateValue) {
        this.dateValue = dateValue;
    }

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }
}
