package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by chan on 2017/11/1.
 *
 * @Description: 模式删除接口
 */

public class ReqPatternDelete extends BaseRequestEntity {
    private String id;

    public ReqPatternDelete(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
