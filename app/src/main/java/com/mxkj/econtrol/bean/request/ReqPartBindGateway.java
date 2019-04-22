package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by chan on 2018/4/26.
 *
 * @Description: 部件-部件绑定网关请求实体类
 */

public class ReqPartBindGateway extends BaseRequestEntity {
    private String gatewayType;//类型 00-飞比
    private String gatewayName;//网关名字
    private String gatewayId;//网关id
    private String gatewaySn;//网关sn
    private String gatewayPassword;//网关id
    private String partId;//部件id


    public String getGatewayType() {
        return gatewayType;
    }

    public void setGatewayType(String gatewayType) {
        this.gatewayType = gatewayType;
    }

    public String getGatewayName() {
        return gatewayName;
    }

    public void setGatewayName(String gatewayName) {
        this.gatewayName = gatewayName;
    }

    public String getGatewaySn() {
        return gatewaySn;
    }

    public void setGatewaySn(String gatewaySn) {
        this.gatewaySn = gatewaySn;
    }

    public String getPartId() {
        return partId;
    }

    public void setPartId(String partId) {
        this.partId = partId;
    }

    public String getGatewayId() {
        return gatewayId;
    }

    public void setGatewayId(String gatewayId) {
        this.gatewayId = gatewayId;
    }

    public String getGatewayPassword() {
        return gatewayPassword;
    }

    public void setGatewayPassword(String gatewayPassword) {
        this.gatewayPassword = gatewayPassword;
    }
}
