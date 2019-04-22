package com.mxkj.econtrol.bean.response;

import com.mxkj.econtrol.base.BaseResponse;

import java.util.List;

/**
 * Created by chan on 2017/11/6.
 *
 * @Description:
 */

public class ResGetUserInfo extends BaseResponse {

    private String userName;
    private String sex;
    private String email;
    private String address;
    private String born;
    private String headPicture;
    private String moon;
    private String moonTime;
    private String niceName;
    private String realName;
    private String defaultHouseId ="";
    private String defaultHouseName ="";
    private String defaultBuidingId ="";
    private String defaultBuidingName ="";
    private String defaultEstateId ="";
    private String defaultEstateName ="";
    private String bindType;//绑定类型，0为管理员，1为普通用户


    public String getBindType() {
        return bindType;
    }

    public void setBindType(String bindType) {
        this.bindType = bindType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBorn() {
        return born;
    }

    public void setBorn(String born) {
        this.born = born;
    }

    public String getHeadPicture() {
        return headPicture;
    }

    public void setHeadPicture(String headPicture) {
        this.headPicture = headPicture;
    }

    public String getMoon() {
        return moon;
    }

    public void setMoon(String moon) {
        this.moon = moon;
    }

    public String getMoonTime() {
        return moonTime;
    }

    public void setMoonTime(String moonTime) {
        this.moonTime = moonTime;
    }

    public String getNiceName() {
        return niceName;
    }

    public void setNiceName(String niceName) {
        this.niceName = niceName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getDefaultHouseId() {
        return defaultHouseId;
    }

    public void setDefaultHouseId(String defaultHouseId) {
        this.defaultHouseId = defaultHouseId;
    }

    public String getDefaultHouseName() {
        return defaultHouseName;
    }

    public void setDefaultHouseName(String defaultHouseName) {
        this.defaultHouseName = defaultHouseName;
    }

    public String getDefaultBuidingId() {
        return defaultBuidingId;
    }

    public void setDefaultBuidingId(String defaultBuidingId) {
        this.defaultBuidingId = defaultBuidingId;
    }

    public String getDefaultBuidingName() {
        return defaultBuidingName;
    }

    public void setDefaultBuidingName(String defaultBuidingName) {
        this.defaultBuidingName = defaultBuidingName;
    }

    public String getDefaultEstateId() {
        return defaultEstateId;
    }

    public void setDefaultEstateId(String defaultEstateId) {
        this.defaultEstateId = defaultEstateId;
    }

    public String getDefaultEstateName() {
        return defaultEstateName;
    }

    public void setDefaultEstateName(String defaultEstateName) {
        this.defaultEstateName = defaultEstateName;
    }
}
