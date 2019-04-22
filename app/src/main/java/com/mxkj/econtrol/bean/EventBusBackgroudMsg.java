package com.mxkj.econtrol.bean;

/**
 * Created by liangshan on 2017/3/24.
 *
 * @Destription: EventBus 消息类
 */

public class EventBusBackgroudMsg extends EventBusMessage {

    public EventBusBackgroudMsg() {
    }

    public EventBusBackgroudMsg(int msgType, Object data) {
        this.msgType = msgType;
        this.data = data;
    }
}
