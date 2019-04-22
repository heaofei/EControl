package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by chan on 2018/1/4.
 *
 * @Description: 审核申请接口 请求实体类
 */

public class ReqApplyBindHouseAudit extends BaseRequestEntity {

    private String applyNo;
    private String status;

    public ReqApplyBindHouseAudit(String applyNo, String status) {
        this.applyNo = applyNo;
        this.status = status;
    }

    public String getApplyNo() {
        return applyNo;
    }

    public void setApplyNo(String applyNo) {
        this.applyNo = applyNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
