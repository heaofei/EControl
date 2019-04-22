package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by liangshan on 2017/5/18.
 *
 * @Description：
 */

public class ReqUnbindHouseUser4Reject extends BaseRequestEntity {
    private String houseUserId;

    public ReqUnbindHouseUser4Reject(String houseUserId) {
        this.houseUserId = houseUserId;
    }

    public String getHouseUserId() {
        return houseUserId;
    }

    public void setHouseUserId(String houseUserId) {
        this.houseUserId = houseUserId;
    }
}
