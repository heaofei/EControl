package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by liangshan on 2017/4/1.
 *
 * @Description:用户提交绑定房子申请接口请求实体
 */

public class ReqUserApplyBindHouse extends BaseRequestEntity {
    private String houseId;//房子标识
    private String bindType;//绑定类型：管理员1,用户：0
    private String realName ;// 真实姓名
    private String idCardFront ;//身份证正面
    private String idCardBack ;//身份证背面
    private String propertyFront ;//物业证明正面

    public ReqUserApplyBindHouse() {
    }

    public ReqUserApplyBindHouse(String houseId, String bindType, String realName, String idCardFront, String idCardBack, String propertyFront) {
        this.houseId = houseId;
        this.bindType = bindType;
        this.realName = realName;
        this.idCardFront = idCardFront;
        this.idCardBack = idCardBack;
        this.propertyFront = propertyFront;
    }

    public void setBindType(String bindType) {
        this.bindType = bindType;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdCardFront() {
        return idCardFront;
    }

    public void setIdCardFront(String idCardFront) {
        this.idCardFront = idCardFront;
    }

    public String getIdCardBack() {
        return idCardBack;
    }

    public void setIdCardBack(String idCardBack) {
        this.idCardBack = idCardBack;
    }

    public String getPropertyFront() {
        return propertyFront;
    }

    public void setPropertyFront(String propertyFront) {
        this.propertyFront = propertyFront;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getBindType() {
        return bindType;
    }
}
