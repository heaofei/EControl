package com.mxkj.econtrol.bean.response;

import com.mxkj.econtrol.base.BaseEntity;
import com.mxkj.econtrol.base.BaseResponse;

import java.util.List;

/**
 * Created by liangshan on 2017/4/12.
 *
 * @Description: 获取房子列表响应实体类
 */

public class ResGetHouseList extends BaseResponse {
    private List<House> houseList;


    public List<House> getHouseList() {
        return houseList;
    }

    public void setHouseList(List<House> houseList) {
        this.houseList = houseList;
    }
}

