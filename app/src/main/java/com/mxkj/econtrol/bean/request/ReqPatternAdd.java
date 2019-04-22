package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by chanjun on 2017/10/31.
 *
 * @Description: 增加模式请求实体类
 */

public class ReqPatternAdd extends BaseRequestEntity {
    private String id; //模式id

    public ReqPatternAdd(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
