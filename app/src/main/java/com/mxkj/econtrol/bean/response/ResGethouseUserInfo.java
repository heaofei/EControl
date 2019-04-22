package com.mxkj.econtrol.bean.response;

import com.mxkj.econtrol.base.BaseResponse;

/**
 * Created by ${  chenjun  } on 2017/10/25.
 */

public class ResGethouseUserInfo extends BaseResponse {
    private String niceName ;
    private String sex ;
    private String name ;
    private String born ;
    private String address ;
    private String headPicture ;


    public String getNiceName() {
        return niceName;
    }

    public void setNiceName(String niceName) {
        this.niceName = niceName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBorn() {
        return born;
    }

    public void setBorn(String born) {
        this.born = born;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHeadPicture() {
        return headPicture;
    }

    public void setHeadPicture(String headPicture) {
        this.headPicture = headPicture;
    }
}
