package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by chan on 2018/8/17.
 *
 *
 *  授权ipda登陆
 */

public class ReqQrLogin extends BaseRequestEntity {

    private String userName; // 用户名
    private String passWord;    //
    private String rId;    // ipad获取的rid
    private String head;    // 后面加来验证ipad登陆的

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

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

    public String getrId() {
        return rId;
    }

    public void setrId(String rId) {
        this.rId = rId;
    }
}
