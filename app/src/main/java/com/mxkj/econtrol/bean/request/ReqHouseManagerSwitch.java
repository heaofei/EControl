package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by liangshan on 2017/5/25.
 *
 * @Description：
 */

public class ReqHouseManagerSwitch extends BaseRequestEntity {

    private String toUserId;
    private String houseId;
    private String remove;

    public ReqHouseManagerSwitch(String toUserId, String houseId, String remove) {
        this.toUserId = toUserId;
        this.houseId = houseId;
        this.remove = remove;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }


    public String getRemove() {
        return remove;
    }
}
