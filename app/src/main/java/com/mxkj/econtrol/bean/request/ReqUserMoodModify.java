package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by liangshan on 2017/4/19.
 *
 * @Description： 用户心情修改请求实体类
 */

public class ReqUserMoodModify extends BaseRequestEntity {
    private String userId;//用户编号
    private String mood;//用户心情

    public ReqUserMoodModify() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public ReqUserMoodModify(String userId, String mood) {

        this.userId = userId;
        this.mood = mood;
    }
}
