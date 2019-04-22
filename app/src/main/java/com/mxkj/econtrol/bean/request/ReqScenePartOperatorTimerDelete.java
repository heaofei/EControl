package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by chan on 2017/11/2.
 * 场景的部件设备操作定时器删除- 请求实体类
 * @Description：
 */

public class ReqScenePartOperatorTimerDelete extends BaseRequestEntity {
    private String id; //id

    public ReqScenePartOperatorTimerDelete(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



}
