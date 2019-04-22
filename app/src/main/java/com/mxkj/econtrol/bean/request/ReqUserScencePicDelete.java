package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by liangshan on 2017/5/10.
 *
 * @Description： 用户场景图片删除请求实体类
 */

public class ReqUserScencePicDelete extends BaseRequestEntity {
    private String scenceId;

    public ReqUserScencePicDelete() {
    }

    public ReqUserScencePicDelete(String scenceId) {
        this.scenceId = scenceId;
    }

    public String getScenceId() {
        return scenceId;
    }

    public void setScenceId(String scenceId) {
        this.scenceId = scenceId;
    }
}
