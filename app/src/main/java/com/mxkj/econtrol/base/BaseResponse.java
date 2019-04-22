package com.mxkj.econtrol.base;

/**
 * Created by liangshan on 2017/3/23.
 *
 * @Destription: 请求响应基类
 */

public class BaseResponse extends BaseEntity {
    private String state="";//当state=0000时为成功，其他为失败
    private String msg="";//接口返回信息

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
