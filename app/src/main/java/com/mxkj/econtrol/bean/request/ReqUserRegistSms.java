package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by liangshan on 2017/4/24.
 *
 * @Description： 1.3.4.2.    用户注册短信认证码请求实体类
 */

public class ReqUserRegistSms extends BaseRequestEntity {
    private String tel;

    public ReqUserRegistSms(String tel) {
        this.tel = tel;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
