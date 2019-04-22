package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by liangshan on 2017/4/27.
 *
 * @Description： 1.3.6.4.    获取在家用户列表请求实体类
 */

public class ReqGetAtHomeUserList extends BaseRequestEntity {
    private String houseId;

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public ReqGetAtHomeUserList(String houseId) {
        this.houseId = houseId;
    }
}
