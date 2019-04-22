package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by chan on 2017/11/1.
 *
 * @Description: 模式应用接口
 */

public class ReqPatternActive extends BaseRequestEntity {
    private String id;

    public ReqPatternActive(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
