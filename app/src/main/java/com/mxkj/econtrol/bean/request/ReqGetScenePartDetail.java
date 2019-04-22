package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by chan on 2017/11/2.
 *
 * @Description：
 */

public class ReqGetScenePartDetail extends BaseRequestEntity {
    private String id;//部件id

    public ReqGetScenePartDetail(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
