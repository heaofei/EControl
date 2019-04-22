package com.mxkj.econtrol.bean;

/**
 * Created by liangshan on 2017/3/24.
 *
 * @Destription:
 */

public class EventBusPostingMsg extends EventBusMessage {


    public EventBusPostingMsg() {
    }

    public EventBusPostingMsg(int msgType, Object data) {
        this.msgType = msgType;
        this.data = data;
    }
}
