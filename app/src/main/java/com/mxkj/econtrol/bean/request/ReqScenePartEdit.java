package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by chan on 2017/11/2.
 *
 * @Description：
 */

public class ReqScenePartEdit extends BaseRequestEntity {
    private String id; //部件id
    private String stateTimer; // 开关状态
    private String name; // 设备名字


    public ReqScenePartEdit() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStateTimer() {
        return stateTimer;
    }

    public void setStateTimer(String stateTimer) {
        this.stateTimer = stateTimer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
