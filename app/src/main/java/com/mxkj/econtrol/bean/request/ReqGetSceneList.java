package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by liangshan on 2017/4/14.
 *
 * @Description: 获取场景信息请求实体类
 */

public class ReqGetSceneList extends BaseRequestEntity {
    private String houseId; //房子id

    public ReqGetSceneList(String houseId) {
        this.houseId = houseId;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }
}
