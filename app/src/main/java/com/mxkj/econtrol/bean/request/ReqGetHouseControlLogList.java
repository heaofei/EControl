package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by liangshan on 2017/4/21.
 *
 * @Description： 1.3.7.5.    用户操作日志请求实体类
 */

public class ReqGetHouseControlLogList extends BaseRequestEntity {
    private String userId;
    private String houseNo;// 传的是房子id
    private int pageIndex;// 当前页
    private int pageSize;// 条数

    public ReqGetHouseControlLogList(String userId, String houseNo, int pageIndex, int pageSize) {
        this.userId = userId;
        this.houseNo = houseNo;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
