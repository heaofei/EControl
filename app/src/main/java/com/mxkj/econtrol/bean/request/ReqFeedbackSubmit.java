package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by chan on 2017/10/30.
 *
 * 意见反馈
 */

public class ReqFeedbackSubmit extends BaseRequestEntity {

    private String content; // 内容200仔以内
    private String phone;    // 手机号 20字以内

    public ReqFeedbackSubmit(String content, String phone) {
        this.content = content;
        this.phone = phone;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
