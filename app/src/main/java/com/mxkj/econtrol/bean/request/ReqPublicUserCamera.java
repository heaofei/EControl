package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by chan on 2018/12/7.
 *
 * @Description： 业主绑定视频请求实体类
 */

public class ReqPublicUserCamera extends BaseRequestEntity {
    private String deviceId; //视频设备ID
    private String userId; //业主ID
    private String houseId; //房子ID
    private String name; //设备名字
    private String  lable; // label标签


    public ReqPublicUserCamera(String deviceId, String userId, String houseId, String name, String lable) {
        this.deviceId = deviceId;
        this.userId = userId;
        this.houseId = houseId;
        this.name = name;
        this.lable = lable;
    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }
}
