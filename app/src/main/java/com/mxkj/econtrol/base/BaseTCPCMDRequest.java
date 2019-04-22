package com.mxkj.econtrol.base;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.utils.DigitalFor62Helper;
import com.mxkj.econtrol.utils.SharedPreferencesUtils;

import java.io.Serializable;

/**
 * Created by liangshan on 2017/4/10.
 *
 * @Description:
 */

public class BaseTCPCMDRequest extends BaseEntity {
    private String serial;//请求系列号
    private String device;//设备编号
    private String userId;//用户编号
    private String token;//访问token
    private String action;//命令
    private String houseId;//房子id
    private String partId;//部件id

    public BaseTCPCMDRequest() {
        setDevice(APP.DeviceId);
        if (APP.user != null) {
            setToken(APP.user.getToken());
//            setUserId(APP.user.getUserId());
            setUserId(APP.user.getUserName()); // 后面改为手机号，传过去，用来绑定tcp通道号
        }
    }

    public String getSerial() {

        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String toJsonStr() {
        Gson gson = new Gson();
        return JSON.toJSONString(this);
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

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
