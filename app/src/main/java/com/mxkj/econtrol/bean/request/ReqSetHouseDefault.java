package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/***
 * @author  chan
 *
 * 1.3.6.8.	设置默认房子(setDefaultUserHouse)
 *
 */

public class ReqSetHouseDefault extends BaseRequestEntity {
    private String houseId;

    public ReqSetHouseDefault() {
    }

    public ReqSetHouseDefault(String houseId) {
        this.houseId = houseId;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }
}
