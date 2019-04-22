package com.mxkj.econtrol.bean.response;

import com.mxkj.econtrol.base.BaseEntity;
import com.mxkj.econtrol.base.BaseResponse;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ${  chenjun  } on 2018/7/16.
 * 图文详情的评论实体， 回复评论的实体
 */

public class CommentList  extends BaseEntity {

        /**
         * commThumbCount : 1
         * creatTime : 1530756682000
         * id : 40288b11646804eb0164683552b8000c
         * msg : 第一次评论
         * replyCount : 1
         * replyList : [{"commThumbCount":"0","creatTime":1530756690000,"id":"40288b11646804eb016468357097000d","msg":"回复","replyUser":{"address":"广东省 广州市 海珠区","headPicture":"","id":"40288b1163c94d850163c9be92450017","niceName":"SuperC","sex":"0","userName":"18613134884"},"state":"1","user":{"address":"广东省 广州市 越秀区","headPicture":"/img/user/head/2018/06/06/132651_271127.png","id":"ff8080815cc99ddf015d34c90e200d2b","mood":"serious","niceName":"iOS ","sex":"1","userName":"15013241484"}}]
         * state : 1
         * user : {"address":"广东省 广州市 海珠区","headPicture":"","id":"40288b1163c94d850163c9be92450017","niceName":"SuperC","sex":"0","userName":"18613134884"}
         */

        private String commThumbCount = "0";// 点赞总数
        private int haveCommentThumbUp;// 当前用户是否点赞  0为没点赞，1为已点赞
        private long creatTime;  // 时间
        private String id;
        private String msg;
        private int replyCount = 0;//二级回复数量
        private String state;
        private UserBean user;
        private List<CommentList> replyList;//评论回复内容
        private int position ;// 操作位置


    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getHaveCommentThumbUp() {
            return haveCommentThumbUp;
        }

        public void setHaveCommentThumbUp(int haveCommentThumbUp) {
            this.haveCommentThumbUp = haveCommentThumbUp;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }


        public int getReplyCount() {
            return replyCount;
        }

        public void setReplyCount(int replyCount) {
            this.replyCount = replyCount;
        }


        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public List<CommentList> getReplyList() {
            return replyList;
        }

        public void setReplyList(List<CommentList> replyList) {
            this.replyList = replyList;
        }

        public static class UserBean implements Serializable {
            /**
             * address : 广东省 广州市 海珠区
             * headPicture :
             * id : 40288b1163c94d850163c9be92450017
             * niceName : SuperC
             * sex : 0
             * userName : 18613134884
             */

            private String address;
            private String headPicture;
            private String id;
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
