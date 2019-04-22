package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by chan on 2017/11/2.
 *  设备定时，保存或修改
 *
 *  参数 open  0  -关，1-开  time 定时时间  houseId partId  week 重复星期一，星期二，id定时任务
 *
 * @Description：
 */

public class ReqScenePartTimerSave extends BaseRequestEntity {
    private int open;
    private String time;
    private String houseId;
    private String partId;
    private String id;
    private String week;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getOpen() {
        return open;
    }

    public void setOpen(int open) {
        this.open = open;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getPartId() {
        return partId;
    }

    public void setPartId(String partId) {
        this.partId = partId;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }
}
