package com.mxkj.econtrol.bean.response;

/**
 * Created by liangshan on 2017/4/12.
 *
 * @Description:
 */

import com.mxkj.econtrol.base.BaseEntity;

public class Building extends BaseEntity {
    private String buidingId;//楼盘编码
    private String buidingName;//楼盘名称

    public String getBuidingId() {
        return buidingId;
    }

    public void setBuidingId(String buidingId) {
        this.buidingId = buidingId;
    }

    public String getBuidingName() {
        return buidingName;
    }

    public void setBuidingName(String buidingName) {
        this.buidingName = buidingName;
    }

    @Override
    public String toString() {
        return buidingName;
    }
}

