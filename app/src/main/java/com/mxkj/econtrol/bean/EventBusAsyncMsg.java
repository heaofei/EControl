package com.mxkj.econtrol.bean;

/**
 * Created by liangshan on 2017/3/24.
 *
 * @Destription:
 */

public class EventBusAsyncMsg extends EventBusMessage {

    public EventBusAsyncMsg() {
    }

    public EventBusAsyncMsg(int msgType, Object data) {
        this.msgType = msgType;
        this.data = data;
    }
}
