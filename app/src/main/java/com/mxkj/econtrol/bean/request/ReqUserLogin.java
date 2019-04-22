package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by liangshan on 2017/3/22.
 *
 * @Destription:用户登录请求实体
 */

public class ReqUserLogin extends BaseRequestEntity {
    private String userName;//	用户名
    private String passWord;//密码，RSA加密

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
