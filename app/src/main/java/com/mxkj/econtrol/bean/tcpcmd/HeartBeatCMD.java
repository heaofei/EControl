package com.mxkj.econtrol.bean.tcpcmd;

import com.mxkj.econtrol.base.BaseTCPCMDRequest;

/**
 * Created by liangshan on 2017/4/10.
 *
 * @Description: 心跳命令
 */

public class HeartBeatCMD extends BaseTCPCMDRequest {
    private String time;

    public HeartBeatCMD() {
        setAction("HEART_BEAT");
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
