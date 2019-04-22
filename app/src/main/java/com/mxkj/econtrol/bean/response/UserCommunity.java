package com.mxkj.econtrol.bean.response;

import com.mxkj.econtrol.base.BaseEntity;

import java.util.List;

/**
 * Created by liangshan on 2017/6/12.
 *
 * @Description：
 */

public class UserCommunity extends BaseEntity {
    private List<CommunityContent> communityContentList;
    private String time;

    public List<CommunityContent> getCommunityContentList() {
        return communityContentList;
    }

    public void setCommunityContentList(List<CommunityContent> communityContentList) {
        this.communityContentList = communityContentList;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
