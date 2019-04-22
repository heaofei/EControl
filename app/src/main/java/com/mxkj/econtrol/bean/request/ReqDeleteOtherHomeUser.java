package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by liangshan on 2017/5/24.
 *
 * @Description： 1.3.6.7.    删除其它控制用户请求实体类
 */

public class ReqDeleteOtherHomeUser extends BaseRequestEntity {

    private String houseBindUserId;

    public ReqDeleteOtherHomeUser(String houseId) {
        this.houseBindUserId = houseId;
    }

    public String getHouseBindUserId() {
        return houseBindUserId;
    }

    public void setHouseBindUserId(String houseBindUserId) {
        this.houseBindUserId = houseBindUserId;
    }
}
