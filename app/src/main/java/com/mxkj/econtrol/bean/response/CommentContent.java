package com.mxkj.econtrol.bean.response;

import com.mxkj.econtrol.base.BaseEntity;

/**
 * Created by liangshan on 2017/4/19.
 *
 * @Description： 评论消息实体类
 */

public class CommentContent extends BaseEntity {
    private String id;//评论编号
    private String msg;//评论信息
    private String communityId;//文章编号
    private String creatTime;//发布时间
    private User user; //发表评论的用户

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getReplyUser() {
        return replyUser;
    }

    public void setReplyUser(User rePlyUser) {
        this.replyUser = rePlyUser;
    }

    private User replyUser;//回复的用户

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }

    public static class User {
        private String id;//用户编号
        private String userName;//用户名称
        private String headPicture;//用户头像
        private String niceName = "";//昵称

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getHeadPicture() {
            return headPicture;
        }

        public void setHeadPicture(String headPicture) {
            this.headPicture = headPicture;
        }

        public String getNiceName() {
            return niceName;
        }

        public void setNiceName(String niceName) {
            this.niceName = niceName;
        }
    }

}
