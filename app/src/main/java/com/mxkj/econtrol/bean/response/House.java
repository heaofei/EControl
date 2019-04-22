package com.mxkj.econtrol.bean.response;

import com.mxkj.econtrol.base.BaseEntity;

/**
 * Created by liangshan on 2017/4/12.
 *
 * @Description:
 */
public class House extends BaseEntity {
    private String houseId;//房子编码
    private String houseName;//房子名称
    private String deviceNo;//房子控设备编号
    private String buidingId;//楼盘编码
    private String state;//绑定状态:0、申请；1、审核
    private String bindType;//绑定类型，0为管理员，1为普通用户

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getBuidingId() {
        return buidingId;
    }

    public void setBuidingId(String buidingId) {
        this.buidingId = buidingId;
    }

    @Override
    public String toString() {
        return houseName;
    }
}
