package com.mxkj.econtrol.bean;

/**
 * Created by liangshan on 2017/3/24.
 *
 * @Destription:
 */

public class EventBusMessage {
    //消息类型，用于区分消息
    protected int msgType;
    //消息的内容
    protected Object data;


    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
