package com.mxkj.econtrol.bean.tcpcmd;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by liangshan on 2017/4/11.
 *
 * @Description: 部件的命令（包含所有部件的属性）
 *
 *    发送TCP报文给快思聪主机的（设备状态操作） 实体类
 */

public class Command {
    @JSONField(ordinal = 1)
    private String type="01";//报文类型，请参考详细定义
    @JSONField(ordinal = 2)
    private String n;//设备名称,格式为:灯：编码(LI)+三位数字；如：LI001 空调：编码(AC)+三位数字；如：AC001
    @JSONField(ordinal = 3)
    private String d;//	灯光调光度：0-100(必须三位数)
    @JSONField(ordinal = 4)
    private String rgb;//	灯光颜色值：0-255(必须三位数)

    @JSONField(ordinal = 3)
    private String lt;//	门锁类型：01 青松  02耶鲁
    @JSONField(ordinal = 4)
    private String token;// 加密后token


    @JSONField(ordinal = 6)
    private String a;//动作；0、关；1、开；
    @JSONField(ordinal = 7)
    private String t;//调温(19-30)  private String f;// 风速1：高；2：中；3：低
    @JSONField(ordinal = 8)
    private String f;// 风速1：高；2：中；3：低
    @JSONField(ordinal = 9)
    private String m;//	模式：1、制冷；2、制热；3、送风；4、除湿；

    private String pm;//PM2.5值
    private String rt;//房间温度


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getN() {
        return n;
    }

    public void setN(String n) {
        this.n = n;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
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

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
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

    public String getLt() {
        return lt;
    }

    public void setLt(String lt) {
        this.lt = lt;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
