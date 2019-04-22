package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by liangshan on 2017/3/21.
 *
 * @Destription:app初始化接口请求实体
 */

public class ReqInitailEntity extends BaseRequestEntity {

    private int appVersionCode;//应用软件版本代码
    private String appVersionName;//应用软件版本名称
    private int adVersionCode;// 广告版本代码
    private String adVersionName;//   广告版本名称
    private double x;//纬度
    private double y; //经度

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

    public String getAdVersionName() {
        return adVersionName;
    }

    public void setAdVersionName(String adVersionName) {
        this.adVersionName = adVersionName;
    }

    public int getAppVersionCode() {
        return appVersionCode;
    }

    public void setAppVersionCode(int appVersionCode) {
        this.appVersionCode = appVersionCode;
    }

    public String getAppVersionName() {
        return appVersionName;
    }

    public void setAppVersionName(String appVersionName) {
        this.appVersionName = appVersionName;
    }

    public int getAdVersionCode() {
        return adVersionCode;
    }

    public void setAdVersionCode(int adVersionCode) {
        this.adVersionCode = adVersionCode;
    }


}
