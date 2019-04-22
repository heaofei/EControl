package com.mxkj.econtrol.bean.response;

import com.mxkj.econtrol.base.BaseResponse;

/**
 * Created by liangshan on 2018/1/19.
 *
 * @Description: 门锁密码验证
 */

public class ResLockPasswordGrant extends BaseResponse {

    private String errorCode = "";
    private String password; //RSA 秘钥加密
    private String restErrorTimes="";
    private String token="";
//    private Integer totalErrorTimes;



    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getRestErrorTimes() {
        return restErrorTimes;
    }

    public void setRestErrorTimes(String restErrorTimes) {
        this.restErrorTimes = restErrorTimes;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

