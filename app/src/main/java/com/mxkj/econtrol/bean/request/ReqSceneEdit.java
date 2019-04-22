package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by chan on 2017/11/1.
 *
 * @Description: 修改房间名名称
 */

public class ReqSceneEdit extends BaseRequestEntity {
    private String id;
    private String name;

    public ReqSceneEdit( ) {
    }

    public ReqSceneEdit(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
