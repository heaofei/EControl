package com.mxkj.econtrol.bean.response;

import com.mxkj.econtrol.base.BaseEntity;

import java.io.Serializable;

/**
 * Created by liangshan on 2017/4/17.
 *
 * @Description： 智能部件状态（包含所有部件的状态）
 *
 *     收到的快思聪主机反馈的TCP报文（设备状态） 实体类
 *
 */

public class SmartPartState extends BaseEntity {

    private String a = "00";//动作；00、关；01、开； 02 停 （窗帘有停的动作）
    private String m = "01";//	模式：01、制冷；02、制热；03、送风；04、除湿；
    private String f = "03";// 风速01：高；02：中；03：低
    private int t = 25;//调温(18-32)
    private String pm;//pm2.5值
    private String rt;//房间温度

    private String n = "";//
    private String d = "";//
    private String rgb = "";//

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }


    public int getT() {
        return t;
    }

    public void setT(int t) {
        this.t = t;
    }

    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }

    public String getF() {
        return f;
    }

    public void setF(String f) {
        this.f = f;
    }

    public String getPm() {
        return pm;
    }

    public void setPm(String pm) {
        this.pm = pm;
    }

    public String getRt() {
        return rt;
    }

    public void setRt(String rt) {
        this.rt = rt;
    }

    public String getN() {
        return n;
    }

    public void setN(String n) {
        this.n = n;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getRgb() {
        return rgb;
    }

    public void setRgb(String rgb) {
        this.rgb = rgb;
    }

    private LockStateBean lockState;  // 门锁状态，只有门锁才有的
    public LockStateBean getLockState() {
        return lockState;
    }
    public void setLockState(LockStateBean lockState) {
        this.lockState = lockState;
    }
    public static class LockStateBean implements Serializable {

        private String gesturePasswordStatus = "";
        private String applyGesturePasswordStatus = "";
        public String getGesturePasswordStatus() {
            return gesturePasswordStatus;
        }
        public void setGesturePasswordStatus(String gesturePasswordStatus) {
            this.gesturePasswordStatus = gesturePasswordStatus;
        }
        public String getApplyGesturePasswordStatus() {
            return applyGesturePasswordStatus;
        }
        public void setApplyGesturePasswordStatus(String applyGesturePasswordStatus) {
            this.applyGesturePasswordStatus = applyGesturePasswordStatus;
        }
    }

}
