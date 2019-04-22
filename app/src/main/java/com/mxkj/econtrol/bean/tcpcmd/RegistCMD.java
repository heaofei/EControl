package com.mxkj.econtrol.bean.tcpcmd;

import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.base.BaseTCPCMDRequest;

/**
 * Created by liangshan on 2017/4/10.
 *
 * @Description:
 */

public class RegistCMD extends BaseTCPCMDRequest {
    private double x;//	经度
    private double y; //	纬度

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }


    public RegistCMD() {
        setAction("REGIST");
        x = APP.longitude;
        y = APP.latitude;
    }
}
