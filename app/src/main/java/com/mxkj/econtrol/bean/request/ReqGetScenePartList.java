package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by liangshan on 2017/4/15.
 *
 * @Description： 获取场景下所有部件和状态的请求实体类
 */

public class ReqGetScenePartList extends BaseRequestEntity {
    private String sceneId; //场景id

    public ReqGetScenePartList(String sceneId) {
        this.sceneId = sceneId;
    }

    public String getSceneId() {
        return sceneId;
    }

    public void setSceneId(String sceneId) {
        this.sceneId = sceneId;
    }
}
