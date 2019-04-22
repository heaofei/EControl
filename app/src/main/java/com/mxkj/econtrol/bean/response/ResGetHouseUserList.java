package com.mxkj.econtrol.bean.response;

import com.mxkj.econtrol.base.BaseResponse;

import java.util.List;

/**
 * Created by liangshan on 2017/4/14.
 *
 * @Description: 房子控制用户列表接口响应实体类
 */

public class ResGetHouseUserList extends BaseResponse {
    private List<HouseUser> houseUserList;

    public List<HouseUser> getHouseUserList() {
        return houseUserList;
    }

    public void setHouseUserList(List<HouseUser> houseUserList) {
        this.houseUserList = houseUserList;
    }
}


