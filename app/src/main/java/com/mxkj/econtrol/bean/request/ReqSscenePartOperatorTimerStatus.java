package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by chan on 2017/11/2.
 *
 * @Description：
 */

public class ReqSscenePartOperatorTimerStatus extends BaseRequestEntity {
    private String id; //部件id
    private String status; // 开关状态


    public ReqSscenePartOperatorTimerStatus(String id, String status) {
        this.id = id;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String state) {
        this.status = state;
    }
}
