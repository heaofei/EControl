package com.mxkj.econtrol.bean.response;

import com.mxkj.econtrol.base.BaseResponse;

import java.util.List;

/**
 * Created by liangshan on 2017/4/14.
 *
 * @Description:
 */

public class ResGetUserHouseList extends BaseResponse {
    private List<MyHouse> houseList;

    public List<MyHouse> getHouseList() {
        return houseList;
    }

    public void setHouseList(List<MyHouse> houseList) {
        this.houseList = houseList;
    }
}
