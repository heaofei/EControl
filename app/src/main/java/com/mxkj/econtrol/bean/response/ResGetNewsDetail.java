package com.mxkj.econtrol.bean.response;

import com.mxkj.econtrol.base.BaseResponse;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chan on 2018/7/12.
 *
 * @Description： 文章或视频详情 响应实体类
 */

public class ResGetNewsDetail extends BaseResponse {


    /**
     * commentList : [{"commThumbCount":"1","creatTime":1530756682000,"id":"40288b11646804eb0164683552b8000c","msg":"第一次评论","replyCount":"1","replyList":[{"commThumbCount":"0","creatTime":1530756690000,"id":"40288b11646804eb016468357097000d","msg":"回复","replyUser":{"address":"广东省 广州市 海珠区","headPicture":"","id":"40288b1163c94d850163c9be92450017","niceName":"SuperC","sex":"0","userName":"18613134884"},"state":"1","user":{"address":"广东省 广州市 越秀区","headPicture":"/img/user/head/2018/06/06/132651_271127.png","id":"ff8080815cc99ddf015d34c90e200d2b","mood":"serious","niceName":"iOS ","sex":"1","userName":"15013241484"}}],"state":"1","user":{"address":"广东省 广州市 海珠区","headPicture":"","id":"40288b1163c94d850163c9be92450017","niceName":"SuperC","sex":"0","userName":"18613134884"}}]
     * smCommunity : {"collectionCount":1,"commentsCount":3,"creatTime":1530508603000,"haveCollectionUp":"0","haveThumbUp":"0","id":"40288b116458d5330164596befe80007","msg":"6","pic":"1","readingCount":2,"source":"来源1","state":"1","thumbUpCount":0,"title":"6","type":"1"}
     */

    private SmCommunityBean smCommunity;
    private List<CommentList> commentList;
    private int commCount = 0;// 总回复数
    private int totalPages = 0;// 回复的总页数

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getCommCount() {
        return commCount;
    }

    public void setCommCount(int commCount) {
        this.commCount = commCount;
    }

    public SmCommunityBean getSmCommunity() {
        return smCommunity;
    }

    public void setSmCommunity(SmCommunityBean smCommunity) {
        this.smCommunity = smCommunity;
    }

    public List<CommentList> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<CommentList> commentList) {
        this.commentList = commentList;
    }

    public static class SmCommunityBean implements Serializable {
        /**
         * collectionCount : 1
         * commentsCount : 3
         * creatTime : 1530508603000
         * haveCollectionUp : 0
         * haveThumbUp : 0
         * id : 40288b116458d5330164596befe80007
         * msg : 6
         * pic : 1
         * readingCount : 2
         * source : 来源1
         * state : 1
         * thumbUpCount : 0
         * title : 6
         * type : 1
         */

        private int collectionCount;//收藏数
        private int commentsCount;//评论数
        private long creatTime; // 时间
        private int haveCollectionUp;//当前用户是否收藏  0为没收藏，1为已收藏
        private int haveThumbUp;//当前用户是否点赞  0为没点赞，1为已点赞
        private String id;
        private String msg;//内容
        private String pic;//文章图片
        private int readingCount;//阅读数
        private String source;//文章来源
        private String state;
        private int thumbUpCount;//点赞数
        private String title;//标题
        private String type;//文章类型


        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public int getCollectionCount() {
            return collectionCount;
        }

        public void setCollectionCount(int collectionCount) {
            this.collectionCount = collectionCount;
        }

        public int getCommentsCount() {
            return commentsCount;
        }

        public void setCommentsCount(int commentsCount) {
            this.commentsCount = commentsCount;
        }

        public long getCreatTime() {
            return creatTime;
        }

        public void setCreatTime(long creatTime) {
            this.creatTime = creatTime;
        }

        public int getHaveCollectionUp() {
            return haveCollectionUp;
        }

        public void setHaveCollectionUp(int haveCollectionUp) {
            this.haveCollectionUp = haveCollectionUp;
        }

        public int getHaveThumbUp() {
            return haveThumbUp;
        }

        public void setHaveThumbUp(int haveThumbUp) {
            this.haveThumbUp = haveThumbUp;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }


        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public int getReadingCount() {
            return readingCount;
        }

        public void setReadingCount(int readingCount) {
            this.readingCount = readingCount;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }


        public int getThumbUpCount() {
            return thumbUpCount;
        }

        public void setThumbUpCount(int thumbUpCount) {
            this.thumbUpCount = thumbUpCount;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }


}


