package com.mxkj.econtrol.utils.gateway;

import java.io.Serializable;

/**
 * Created by lidongxing on 2018/2/28 0028.
 */

public class DeviceInfo implements Serializable{
    private String sn;
    private String id;
    private String password;
    private String name;

    public DeviceInfo(String sn, String id, String password) {
        this.sn = sn;
        this.id = id;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
