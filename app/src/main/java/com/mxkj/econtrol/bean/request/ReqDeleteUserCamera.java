package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by chan on 2018/12/13.
 *
 * @Description： 视频监控删除请求实体类
 */

public class ReqDeleteUserCamera extends BaseRequestEntity {
    private String userId ; //业主ID
    private String houseId; //房子ID
    private String id; //视频监控摄像头UUID



    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
