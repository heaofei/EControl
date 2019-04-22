package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by chan on 2017/11/2.
 * 获取场景的部件设备-操作的定时列表信息-请求实体类
 * @Description：
 */

public class ReqGetScenePartOperatorTimerList extends BaseRequestEntity {
    private String operatorId;//上级定时id


    public ReqGetScenePartOperatorTimerList(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }
}
