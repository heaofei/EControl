package com.mxkj.econtrol.bean.response;

import com.mxkj.econtrol.base.BaseEntity;
import com.mxkj.econtrol.base.BaseResponse;

import java.util.List;

/**
 * Created by liangshan on 2017/4/12.
 *
 * @Description: 获取地区信息列表
 */

public class ResGetAreaLsit extends BaseResponse {
    private List<City> areaList;


    public List<City> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<City> areaList) {
        this.areaList = areaList;
    }
}

