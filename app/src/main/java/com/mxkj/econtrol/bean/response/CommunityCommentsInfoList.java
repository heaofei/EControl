package com.mxkj.econtrol.bean.response;

import com.google.gson.annotations.SerializedName;
import com.mxkj.econtrol.base.BaseResponse;

import java.util.List;

/**
 * Created by ${  chenjun  } on 2018/7/16.
 * 图文详情的评论实体， 回复评论的实体
 */

public class CommunityCommentsInfoList extends BaseResponse {


    private List<CommentListBean> commentList;
    private int totalPages;  // replyList的总页数
    private int commCount;  // 总评论数

    public int getCommCount() {
        return commCount;
    }

    public void setCommCount(int commCount) {
        this.commCount = commCount;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
    public List<CommentListBean> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<CommentListBean> commentList) {
        this.commentList = commentList;
    }

    public static class CommentListBean {
        /**
         * commThumbCount : 0
         * creatTime : 1533273952000
         * haveCommentThumbUp : 0
         * id : 40288b1164fe39170164fe3fd2980014
         * msg : 一个字
         * replyList : [{"commThumbCount":"1","creatTime":1533274189000,"haveCommentThumbUp":"0","id":"40288b1164fe39170164fe436e2b0018","msg":"了","replyUser":"40288b1164fe39170164fe3fd2980014","state":"1","user":{"address":"广东省 广州市 荔湾区","headPicture":"/img/user/head/2018/06/06/154657_527499.png","id":"ff8080815cc99ddf015d4e932a2c0de7","mood":"哈哈哈还好还好2","niceName":"Android","sex":"0","userName":"13610295454"}}]
         * replyUser : 0
         * state : 1
         * user : {"address":"广东省 广州市 荔湾区","headPicture":"/img/user/head/2018/06/06/154657_527499.png","id":"ff8080815cc99ddf015d4e932a2c0de7","mood":"哈哈哈还好还好2","niceName":"Android","sex":"0","userName":"13610295454"}
         */

        private String commThumbCount;
        private long creatTime;
        private String haveCommentThumbUp;
        private String id;
        @SerializedName("msg")
        private String msgX;
        private String replyUser;
        @SerializedName("state")
        private String stateX;
        private UserBean user;
        private List<ReplyListBean> replyList;





        public String getCommThumbCount() {
            return commThumbCount;
        }

        public void setCommThumbCount(String commThumbCount) {
            this.commThumbCount = commThumbCount;
        }

        public long getCreatTime() {
            return creatTime;
        }

        public void setCreatTime(long creatTime) {
            this.creatTime = creatTime;
        }

        public String getHaveCommentThumbUp() {
            return haveCommentThumbUp;
        }

        public void setHaveCommentThumbUp(String haveCommentThumbUp) {
            this.haveCommentThumbUp = haveCommentThumbUp;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMsgX() {
            return msgX;
        }

        public void setMsgX(String msgX) {
            this.msgX = msgX;
        }

        public String getReplyUser() {
            return replyUser;
        }

        public void setReplyUser(String replyUser) {
            this.replyUser = replyUser;
        }

        public String getStateX() {
            return stateX;
        }

        public void setStateX(String stateX) {
            this.stateX = stateX;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public List<ReplyListBean> getReplyList() {
            return replyList;
        }

        public void setReplyList(List<ReplyListBean> replyList) {
            this.replyList = replyList;
        }

        public static class UserBean {
            /**
             * address : 广东省 广州市 荔湾区
             * headPicture : /img/user/head/2018/06/06/154657_527499.png
             * id : ff8080815cc99ddf015d4e932a2c0de7
             * mood : 哈哈哈还好还好2
             * niceName : Android
             * sex : 0
             * userName : 13610295454
             */

            private String address;
            private String headPicture;
            private String id;
            private String mood;
            private String niceName;
            private String sex;
            private String userName;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

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

            public String getMood() {
                return mood;
            }

            public void setMood(String mood) {
                this.mood = mood;
            }

            public String getNiceName() {
                return niceName;
            }

            public void setNiceName(String niceName) {
                this.niceName = niceName;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }
        }

        public static class ReplyListBean {
            /**
             * commThumbCount : 1
             * creatTime : 1533274189000
             * haveCommentThumbUp : 0
             * id : 40288b1164fe39170164fe436e2b0018
             * msg : 了
             * replyUser : 40288b1164fe39170164fe3fd2980014
             * state : 1
             * user : {"address":"广东省 广州市 荔湾区","headPicture":"/img/user/head/2018/06/06/154657_527499.png","id":"ff8080815cc99ddf015d4e932a2c0de7","mood":"哈哈哈还好还好2","niceName":"Android","sex":"0","userName":"13610295454"}
             */

            private int commThumbCount;// 点赞总数
            private long creatTime;
            private int haveCommentThumbUp;// 当前用户是否点赞  0为没点赞，1为已点赞
            private String id;
            @SerializedName("msg")
            private String msgX;
            private String replyUser;
            @SerializedName("state")
            private String stateX;
            private int position;
            private UserBeanX user;

            public int getCommThumbCount() {
                return commThumbCount;
            }

            public void setCommThumbCount(int commThumbCount) {
                this.commThumbCount = commThumbCount;
            }

            public int getPosition() {
                return position;
            }

            public void setPosition(int position) {
                this.position = position;
            }

            public long getCreatTime() {
                return creatTime;
            }

            public void setCreatTime(long creatTime) {
                this.creatTime = creatTime;
            }

            public int getHaveCommentThumbUp() {
                return haveCommentThumbUp;
            }

            public void setHaveCommentThumbUp(int haveCommentThumbUp) {
                this.haveCommentThumbUp = haveCommentThumbUp;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getMsgX() {
                return msgX;
            }

            public void setMsgX(String msgX) {
                this.msgX = msgX;
            }

            public String getReplyUser() {
                return replyUser;
            }

            public void setReplyUser(String replyUser) {
                this.replyUser = replyUser;
            }

            public String getStateX() {
                return stateX;
            }

            public void setStateX(String stateX) {
                this.stateX = stateX;
            }

            public UserBeanX getUser() {
                return user;
            }

            public void setUser(UserBeanX user) {
                this.user = user;
            }

            public static class UserBeanX {
                /**
                 * address : 广东省 广州市 荔湾区
                 * headPicture : /img/user/head/2018/06/06/154657_527499.png
                 * id : ff8080815cc99ddf015d4e932a2c0de7
                 * mood : 哈哈哈还好还好2
                 * niceName : Android
                 * sex : 0
                 * userName : 13610295454
                 */

                private String address;
                private String headPicture;
                private String id;
                private String mood;
                private String niceName;
                private String sex;
                private String userName;

                public String getAddress() {
                    return address;
                }

                public void setAddress(String address) {
                    this.address = address;
                }

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

                public String getMood() {
                    return mood;
                }

                public void setMood(String mood) {
                    this.mood = mood;
                }

                public String getNiceName() {
                    return niceName;
                }

                public void setNiceName(String niceName) {
                    this.niceName = niceName;
                }

                public String getSex() {
                    return sex;
                }

                public void setSex(String sex) {
                    this.sex = sex;
                }

                public String getUserName() {
                    return userName;
                }

                public void setUserName(String userName) {
                    this.userName = userName;
                }
            }
        }
    }
}
