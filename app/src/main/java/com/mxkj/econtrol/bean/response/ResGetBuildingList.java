package com.mxkj.econtrol.bean.response;

import com.mxkj.econtrol.base.BaseResponse;

import java.util.List;

/**
 * Created by liangshan on 2017/4/12.
 *
 * @Description: 获取楼盘单位和房子列表信息响应类
 */

public class ResGetBuildingList extends BaseResponse {
    List<Building> buildingList;

    public List<Building> getBuildingList() {
        return buildingList;
    }

    public void setBuildingList(List<Building> buildingList) {
        this.buildingList = buildingList;
    }
}


