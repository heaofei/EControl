package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by chan on 2017/10/31.
 *
 * @Description: 获场景模式请求实体类
 */

public class ReqPatternList extends BaseRequestEntity {
    private String houseId; //房子id
    private String userId; //房子id

    public ReqPatternList(String houseId) {
        this.houseId = houseId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }
}
