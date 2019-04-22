package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by liangshan on 2018/1/19.
 *
 * @Description： 1.3.10.2.    门锁更改房子的部件操作密码
 */

public class ReqLockPartPasswordChange extends BaseRequestEntity {


    private String houseId;
    private String partId;   // 部件id
    private String type;  // "00"-"普通密码","01"-"手势密码"
    private String originPassword;
    private String newPassword;


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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOriginPassword() {
        return originPassword;
    }

    public void setOriginPassword(String originPassword) {
        this.originPassword = originPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
