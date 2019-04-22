package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by liangshan on 2017/5/9.
 *
 * @Description： 用户场景图片修改请求实体类
 */

public class ReqUserScencePicModify extends BaseRequestEntity {
    private String scenceId;//	场景id
    private String pic;//图片(Base64编码)

    public ReqUserScencePicModify() {
    }

    public ReqUserScencePicModify(String scenceId, String pic) {
        this.scenceId = scenceId;
        this.pic = pic;
    }

    public String getScenceId() {
        return scenceId;
    }

    public void setScenceId(String scenceId) {
        this.scenceId = scenceId;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
