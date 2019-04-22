package com.mxkj.econtrol.bean.response;

import com.mxkj.econtrol.base.BaseResponse;

import java.util.List;

/**
 * Created by liangshan on 2018/1/19.
 *
 * @Description: 临时密码
 */

public class ResLockPartPasswordTemporarySet extends BaseResponse {

    private String errorCode;
    private String password; //RSA 秘钥加密
    private int timeout=0;


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

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}

