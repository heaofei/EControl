package com.mxkj.econtrol.bean.response;

import com.mxkj.econtrol.base.BaseEntity;

/**
 * Created by liangshan on 2017/4/14.
 *
 * @Description:
 */

public class MyHouse extends BaseEntity {
    private String id;//房子Id
    private String housingEstate;//小区名称
    private String building;//幢号
    private String houseNo;//房号
    private String state;//绑定状态:0、申请；1、审核  2、拒绝
    private String bindType;//绑定类型，0为管理员，1为普通用户
    private String pic;//房子图片
    private int isDefault;//是否默认房子 默认状态:0、不；1、是
    private String houseUserId;

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }

    public String getBindType() {
        return bindType;
    }

    public void setBindType(String bindType) {
        this.bindType = bindType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        id = id;
    }

    public String getHousingEstate() {
        return housingEstate;
    }

    public void setHousingEstate(String housingEstate) {
        this.housingEstate = housingEstate;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }


    public String getHouseUserId() {
        return houseUserId;
    }

    public void setHouseUserId(String houseUserId) {
        this.houseUserId = houseUserId;
    }
}
