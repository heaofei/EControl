package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by liangshan on 2018/1/19.
 *
 * @Description： 1.3.10.2.    业主忘记密码提交审核
 */

public class ReqLockUserApplyPartPassword extends BaseRequestEntity {


    private String type;  //type 00-业主忘记手势密码，01-成员忘记手势密码
    private String remark;
    private String idcardFront;
    private String idcardBack;
    private String idcardHold;
    private String partId;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIdcardFront() {
        return idcardFront;
    }

    public void setIdcardFront(String idcardFront) {
        this.idcardFront = idcardFront;
    }

    public String getIdcardBack() {
        return idcardBack;
    }

    public void setIdcardBack(String idcardBack) {
        this.idcardBack = idcardBack;
    }

    public String getIdcardHold() {
        return idcardHold;
    }

    public void setIdcardHold(String idcardHold) {
        this.idcardHold = idcardHold;
    }

    public String getPartId() {
        return partId;
    }

    public void setPartId(String partId) {
        this.partId = partId;
    }
}
