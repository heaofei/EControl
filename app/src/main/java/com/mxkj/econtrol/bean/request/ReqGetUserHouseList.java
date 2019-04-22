package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by liangshan on 2017/4/14.
 *
 * @Description: 获取用户绑定的房子列表
 */

public class ReqGetUserHouseList extends BaseRequestEntity {
    private String userId;

    public ReqGetUserHouseList() {
    }

    public ReqGetUserHouseList(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
