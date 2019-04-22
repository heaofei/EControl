package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by chan
 *
 * @Description: 房子控制用户信息接口请求实体类
 */

public class ReqGetHouseUserInfo extends BaseRequestEntity {
    private String userId;

    public ReqGetHouseUserInfo(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
