package com.mxkj.econtrol.bean.response;

import com.mxkj.econtrol.base.BaseEntity;

/**
 * Created by liangshan on 2017/4/19.
 *
 * @Description： 社区消息内容实体类
 */

public class CommunityContent extends BaseEntity {

    /**
     * haveCollectionUp 是否收藏  haveThumbUp是否点赞 thumbUpCount点赞数 readingCount阅读数 source来源 type 1专题 2大图 3小图4视频
     */


    private String title; // 标题
    private String id;//编号
    private String msg;//内容
    private String pic;//	封面
    private long updateTime;//	发布时间
    private String source;//来源
    private int haveCollectionUp;//是否收藏
    private int readingCount;//阅读数
    private int thumbUpCount;//点赞数
    private int commentsCount;//评论数
    private int type;//类型 : 1专题 2大图 3小图 4视频
    private User user;//发表人
    private int haveThumbUp;//是否已点赞
    private int width;//图片宽
    private int height;// 图片高
    private String picList [];// 多图集合
    private int position ;// 操作位置



    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String[] getPicList() {
        return picList;
    }

    public void setPicList(String[] picList) {
        this.picList = picList;
    }

    public int getHaveThumbUp() {
        return haveThumbUp;
    }

    public void setHaveThumbUp(int haveThumbUp) {
        this.haveThumbUp = haveThumbUp;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


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

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public int getThumbUpCount() {
        return thumbUpCount;
    }

    public void setThumbUpCount(int thumbUpCount) {
        this.thumbUpCount = thumbUpCount;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getHaveCollectionUp() {
        return haveCollectionUp;
    }

    public void setHaveCollectionUp(int haveCollectionUp) {
        this.haveCollectionUp = haveCollectionUp;
    }

    public int getReadingCount() {
        return readingCount;
    }

    public void setReadingCount(int readingCount) {
        this.readingCount = readingCount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static class User extends BaseEntity {
        private String id;//用户编号
        private String userName;//用户名
        private String niceName = "";//昵称
        private String headPicture = "";//用户头像

        public String getHeadPicture() {
            return headPicture;
        }

        public void setHeadPicture(String headPicture) {
            this.headPicture = headPicture;
        }


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userIduserName) {
            this.userName = userIduserName;
        }


        public String getNiceName() {
            return niceName;
        }

        public void setNiceName(String niceName) {
            this.niceName = niceName;
        }
    }

}
