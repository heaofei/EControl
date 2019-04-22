package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by liangshan on 2017/4/14.
 *
 * @Description: 解除绑定控制用户
 */

public class ReqUnbindHouseUser extends BaseRequestEntity {
    public ReqUnbindHouseUser(String houseUserId) {
        this.houseUserId = houseUserId;
    }

    public String getHouseUserId() {
        return houseUserId;
    }

    public void setHouseUserId(String houseUserId) {
        this.houseUserId = houseUserId;
    }

    private String houseUserId;//房子用户绑定编号


}
