package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by chan on 2018/4/26.
 *
 * @Description: 网关解绑房子
 */

public class ReqGatewayUnbindHouse extends BaseRequestEntity {
    private String gatewaySn; //网关sn
    private String houseId;  //房子id


    public String getGatewaySn() {
        return gatewaySn;
    }

    public void setGatewaySn(String gatewaySn) {
        this.gatewaySn = gatewaySn;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }
}
