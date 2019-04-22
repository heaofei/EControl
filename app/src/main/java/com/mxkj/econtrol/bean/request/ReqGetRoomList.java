package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by chanjun on 2017/4/14.
 *
 * @Description: 获取房间列表请求实体类
 */

public class ReqGetRoomList extends BaseRequestEntity {
    private String houseId; //房子id
    private String userId; //用户id

    public ReqGetRoomList(String houseId) {
        this.houseId = houseId;
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
}
