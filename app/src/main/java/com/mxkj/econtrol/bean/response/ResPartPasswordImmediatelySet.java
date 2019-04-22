package com.mxkj.econtrol.bean.response;

import com.mxkj.econtrol.base.BaseRequestEntity;
import com.mxkj.econtrol.base.BaseResponse;

/**
 * Created by chan on 2018/1/19.
 *
 * @Description: 青松立即开锁
 */

public class ResPartPasswordImmediatelySet extends BaseRequestEntity {

    private String partId ;
    private String token;

    public ResPartPasswordImmediatelySet(String partId, String token) {
        this.partId = partId;
        this.token = token;
    }

    public String getPartId() {
        return partId;
    }

    public void setPartId(String partId) {
        this.partId = partId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

