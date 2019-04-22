package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by liangshan on 2017/4/25.
 *
 * @Description： 1.3.4.4.    用户登出接口请求实体类
 */

public class ReqUserLogout extends BaseRequestEntity {
    public String getUserName() {
        return userName;
    }

    private String userName;

    public ReqUserLogout(String userName) {
        this.userName = userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
