package com.mxkj.econtrol.base;

import com.google.gson.Gson;

/**
 * Created by liangshan on 2017/4/10.
 *
 * @Description: tcp命令返回结果基础类
 */

public class BaseTCPCMDResponse extends BaseEntity {
    private String result;//返回状态，请参考结果状态定义
    private String serial;//请求系列号
    private String msg;//	描述
    private String data;//保存原来的json字符串
    private String action;//行为： 注册、控制、心跳、注销


    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String toJsonStr() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
