package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by chan on 2017/11/24.
 *
 * @Description: 获取控制排名的日期信息
 */

public class ReqGetRankingDateList extends BaseRequestEntity {
    private String houseId;
    private String dateType;//时间类型（month-月，day-日）

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }
}
