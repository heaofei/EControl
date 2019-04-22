package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by liangshan on 2017/3/28.
 *
 * @Description: 获取验证码请求实体类
 */

public class ReqSUserSms extends BaseRequestEntity {

    private String tel; //手机号码

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
