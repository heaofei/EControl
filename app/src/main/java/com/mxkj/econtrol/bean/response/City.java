package com.mxkj.econtrol.bean.response;

import com.mxkj.econtrol.base.BaseEntity;

import java.util.List;

/**
 * Created by liangshan on 2017/4/13.
 *
 * @Description: 城市实体类
 */

public class City extends BaseEntity {
    private String areaId;//地区ID
    private String areaCode;//地区编码
    private String areaName;//地区名称
    private String areaParentCode;//地区父编码
    private List<Area> childList;//下属的区
    private String level;//级别1、城市，2、区域

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaParentCode() {
        return areaParentCode;
    }

    public void setAreaParentCode(String areaParentCode) {
        this.areaParentCode = areaParentCode;
    }

    public List<Area> getChildList() {
        return childList;
    }

    public void setChildList(List<Area> childList) {
        this.childList = childList;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return areaName;
    }
}
