package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by liangshan on 2018/1/19.
 *
 * @Description： 1.3.10.2.    临时密码
 */

public class ReqLockPartPasswordTemporarySet extends BaseRequestEntity {


    private String houseId;
    private String partId;   // 部件id
    private String token;   // 部件id


    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getPartId() {
        return partId;
    }

    public void setPartId(String partId) {
        this.partId = partId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
