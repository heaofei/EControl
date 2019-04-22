package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by liangshan on 2018/1/19.
 *
 * @Description： 1.3.10.2.    锁授权列表
 */

public class ReqLockGetPartPermissionsList extends BaseRequestEntity {


    private String houseId;
    private String partId;


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

}
