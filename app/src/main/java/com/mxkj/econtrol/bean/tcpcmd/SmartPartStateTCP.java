package com.mxkj.econtrol.bean.tcpcmd;

import com.mxkj.econtrol.base.BaseEntity;

/**
 * Created by liangshan on 2017/4/11.
 *
 * @Description: 智能部件（包含所有智能的属性），当部件状态发生变化时，中控设备发来的消息实体类
 */

public class SmartPartStateTCP extends BaseEntity {

    /**
     * userId : test01
     * result : 1111
     * device : HFSF000001
     * command : {"type":"00","n":"AC001","a":"0"}
     */

    private String userId;
    private String result;
    private String device;
    private String serial;
    private Command command;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }
}
