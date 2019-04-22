package com.mxkj.econtrol.bean.tcpcmd;

import com.mxkj.econtrol.base.BaseTCPCMDRequest;

/**
 * Created by liangshan on 2017/4/11.
 *
 * @Description: 智能部件控制命令
 */

public class SmartPartCMD extends BaseTCPCMDRequest {
    private Command command;
    public SmartPartCMD(){
        setAction("DEVICE_CONTROL");
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }
}
