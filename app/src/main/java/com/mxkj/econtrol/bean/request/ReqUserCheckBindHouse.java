package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by liangshan on 2017/4/14.
 *
 * @Description: 业主或者户主审核用户申请
 */

public class ReqUserCheckBindHouse extends BaseRequestEntity {
    private String houseId;//房子编码
    private String userId;//用户编码
    private String state;//状态：1通过，2拒绝

    public ReqUserCheckBindHouse(String houseId, String userId, String state) {
        this.houseId = houseId;
        this.userId = userId;
        this.state = state;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
