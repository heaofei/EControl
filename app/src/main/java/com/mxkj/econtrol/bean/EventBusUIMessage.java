package com.mxkj.econtrol.bean;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by liangshan on 2017/3/24.
 *
 * @Destription: eventBus发往UI线程的消息类
 */

public class EventBusUIMessage extends EventBusMessage {


    public EventBusUIMessage() {
    }

    public EventBusUIMessage(int msgType) {
        this.msgType = msgType;
    }

    public EventBusUIMessage(int msgType, Object data) {
        this.msgType = msgType;
        this.data = data;
    }

}
