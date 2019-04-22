package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by liangshan on 2017/4/12.
 *
 * @Description: 获取地区列表请求实体类
 */

public class ReqGetAreaList extends BaseRequestEntity {
    private String areaCode;
    private String status;  // 0-禁用；1-启用；不传-全部；


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
}
