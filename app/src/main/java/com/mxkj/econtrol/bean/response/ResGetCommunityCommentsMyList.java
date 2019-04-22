package com.mxkj.econtrol.bean.response;

import com.google.gson.annotations.SerializedName;
import com.mxkj.econtrol.base.BaseResponse;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chan on 2018/7/13.
 *
 * @Description: 获取我的评论列表数据
 */

public class ResGetCommunityCommentsMyList extends BaseResponse {


    /**
     * page : {"content":[{"creatTime":1531820555000,"haveCommentThumbUp":"0","id":"40288b1164a79bb60164a79ebc0b0008","msg":"二级评论","replyList":[{"creatTime":1531820538000,"haveCommentThumbUp":"0","id":"40288b1164a79bb60164a79e792b0005","msg":"二楼。对文章的一级评论","replyUser":"0","state":"1","user":{"address":"","headPicture":"/img/user/head/2018/05/10/114128_174446.png","id":"ff8080815f2d4436015f341b2aeb0024","niceName":"131","sex":"0","userName":"13189054152"}}],"replyUser":"40288b1164a79bb60164a79e792b0005","state":"1","user":{"address":"北京 北京市 东城区","headPicture":"/img/user/head/2018/06/20/093641_508087.png","id":"40288b1162b860960162b8da31a70041","niceName":"176 2099 9268","sex":"0","userName":"17620999268"},"title":"大图帖子02"},{"creatTime":1531820514000,"haveCommentThumbUp":"0","id":"40288b1164a79bb60164a79e1c4d0002","msg":"发出， 对文章的一级 评论","replyList":[{"creatTime":1531820739000,"haveCommentThumbUp":"0","id":"40288b1164a79bb60164a7a18cac000e","msg":"写的非常好222222","replyUser":"40288b1164a79bb60164a79e1c4d0002","state":"1","user":{"address":"","headPicture":"/img/user/head/2018/05/10/114128_174446.png","id":"ff8080815f2d4436015f341b2aeb0024","niceName":"131","sex":"0","userName":"13189054152"}},{"creatTime":1531820728000,"haveCommentThumbUp":"0","id":"40288b1164a79bb60164a7a1616b000d","msg":"写的非常好","replyUser":"40288b1164a79bb60164a79e1c4d0002","state":"1","user":{"address":"","headPicture":"/img/user/head/2018/05/10/114128_174446.png","id":"ff8080815f2d4436015f341b2aeb0024","niceName":"131","sex":"0","userName":"13189054152"}}],"replyUser":"0","state":"1","title":"大图帖子02","user":{"address":"北京 北京市 东城区","headPicture":"/img/user/head/2018/06/20/093641_508087.png","id":"40288b1162b860960162b8da31a70041","niceName":"176 2099 9268","sex":"0","userName":"17620999268"}}],"firstPage":true,"lastPage":true,"number":0,"numberOfElements":2,"size":20,"sort":[{"ascending":false,"direction":"DESC","property":"creatTime"}],"totalElements":2,"totalPages":1}
     */

    private PageBean page;

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public static class PageBean {
        /**
         * content : [{"creatTime":1531820555000,"haveCommentThumbUp":"0","id":"40288b1164a79bb60164a79ebc0b0008","msg":"二级评论","replyList":[{"creatTime":1531820538000,"haveCommentThumbUp":"0","id":"40288b1164a79bb60164a79e792b0005","msg":"二楼。对文章的一级评论","replyUser":"0","state":"1","user":{"address":"","headPicture":"/img/user/head/2018/05/10/114128_174446.png","id":"ff8080815f2d4436015f341b2aeb0024","niceName":"131","sex":"0","userName":"13189054152"}}],"replyUser":"40288b1164a79bb60164a79e792b0005","state":"1","user":{"address":"北京 北京市 东城区","headPicture":"/img/user/head/2018/06/20/093641_508087.png","id":"40288b1162b860960162b8da31a70041","niceName":"176 2099 9268","sex":"0","userName":"17620999268"}},{"creatTime":1531820514000,"haveCommentThumbUp":"0","id":"40288b1164a79bb60164a79e1c4d0002","msg":"发出， 对文章的一级 评论","replyList":[{"creatTime":1531820739000,"haveCommentThumbUp":"0","id":"40288b1164a79bb60164a7a18cac000e","msg":"写的非常好222222","replyUser":"40288b1164a79bb60164a79e1c4d0002","state":"1","user":{"address":"","headPicture":"/img/user/head/2018/05/10/114128_174446.png","id":"ff8080815f2d4436015f341b2aeb0024","niceName":"131","sex":"0","userName":"13189054152"}},{"creatTime":1531820728000,"haveCommentThumbUp":"0","id":"40288b1164a79bb60164a7a1616b000d","msg":"写的非常好","replyUser":"40288b1164a79bb60164a79e1c4d0002","state":"1","user":{"address":"","headPicture":"/img/user/head/2018/05/10/114128_174446.png","id":"ff8080815f2d4436015f341b2aeb0024","niceName":"131","sex":"0","userName":"13189054152"}}],"replyUser":"0","state":"1","title":"大图帖子02","user":{"address":"北京 北京市 东城区","headPicture":"/img/user/head/2018/06/20/093641_508087.png","id":"40288b1162b860960162b8da31a70041","niceName":"176 2099 9268","sex":"0","userName":"17620999268"}}]
         * firstPage : true
         * lastPage : true
         * number : 0
         * numberOfElements : 2
         * size : 20
         * sort : [{"ascending":false,"direction":"DESC","property":"creatTime"}]
         * totalElements : 2
         * totalPages : 1
         */

        private boolean firstPage;
        private boolean lastPage;
        private int number;
        private int numberOfElements;
        private int size;
        private int totalElements;
        private int totalPages;
        private List<ContentBean> content;
        private List<SortBean> sort;

        public boolean isFirstPage() {
            return firstPage;
        }

        public void setFirstPage(boolean firstPage) {
            this.firstPage = firstPage;
        }

        public boolean isLastPage() {
            return lastPage;
        }

        public void setLastPage(boolean lastPage) {
            this.lastPage = lastPage;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public int getNumberOfElements() {
            return numberOfElements;
        }

        public void setNumberOfElements(int numberOfElements) {
            this.numberOfElements = numberOfElements;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getTotalElements() {
            return totalElements;
        }

        public void setTotalElements(int totalElements) {
            this.totalElements = totalElements;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public List<ContentBean> getContent() {
            return content;
        }

        public void setContent(List<ContentBean> content) {
            this.content = content;
        }

        public List<SortBean> getSort() {
            return sort;
        }

        public void setSort(List<SortBean> sort) {
            this.sort = sort;
        }

        public static class ContentBean implements Serializable{
            /**
             * creatTime : 1531820555000
             * haveCommentThumbUp : 0
             * id : 40288b1164a79bb60164a79ebc0b0008
             * msg : 二级评论
             * replyList : [{"creatTime":1531820538000,"haveCommentThumbUp":"0","id":"40288b1164a79bb60164a79e792b0005","msg":"二楼。对文章的一级评论","replyUser":"0","state":"1","user":{"address":"","headPicture":"/img/user/head/2018/05/10/114128_174446.png","id":"ff8080815f2d4436015f341b2aeb0024","niceName":"131","sex":"0","userName":"13189054152"}}]
             * replyUser : 40288b1164a79bb60164a79e792b0005
             * state : 1
             * user : {"address":"北京 北京市 东城区","headPicture":"/img/user/head/2018/06/20/093641_508087.png","id":"40288b1162b860960162b8da31a70041","niceName":"176 2099 9268","sex":"0","userName":"17620999268"}
             * title : 大图帖子02
             */

            private long creatTime;
            private String haveCommentThumbUp;
            private String commThumbCount;// 点赞总数
            private String id;
            private String msg;
            private String replyUser;
            private String state;
            private MyCommListBean myCommList;
            private UserBean user;
            private String title;
            private List<ReplyListBean> replyList;


            public String getCommThumbCount() {
                return commThumbCount;
            }

            public void setCommThumbCount(String commThumbCount) {
                this.commThumbCount = commThumbCount;
            }

            public MyCommListBean getMyCommList() {
                return myCommList;
            }

            public void setMyCommList(MyCommListBean myCommList) {
                this.myCommList = myCommList;
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



            public String getReplyUser() {
                return replyUser;
            }

            public void setReplyUser(String replyUser) {
                this.replyUser = replyUser;
            }

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

            public UserBean getUser() {
                return user;
            }

            public void setUser(UserBean user) {
                this.user = user;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public List<ReplyListBean> getReplyList() {
                return replyList;
            }

            public void setReplyList(List<ReplyListBean> replyList) {
                this.replyList = replyList;
            }

            public static class UserBean implements Serializable{
                /**
                 * address : 北京 北京市 东城区
                 * headPicture : /img/user/head/2018/06/20/093641_508087.png
                 * id : 40288b1162b860960162b8da31a70041
                 * niceName : 176 2099 9268
                 * sex : 0
                 * userName : 17620999268
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

          public static class MyCommListBean implements Serializable{
                /**
                 * "creatTime": 1531820514000,
                 "haveCommentThumbUp": "0",
                 "id": "40288b1164a79bb60164a79e1c4d0002",
                 "msg": "发出， 对文章的一级 评论",
                 "replyUser": "0",
                 "state": "1",
                 */

                private long creatTime;
                private String haveCommentThumbUp;
                private String id;
                private String msg;
                private String replyUser;
                private String state;
              private UserBean user;


              public UserBean getUser() {
                  return user;
              }

              public void setUser(UserBean user) {
                  this.user = user;
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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getReplyUser() {
        return replyUser;
    }

    public void setReplyUser(String replyUser) {
        this.replyUser = replyUser;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}

            public static class ReplyListBean implements Serializable{
                /**
                 * creatTime : 1531820538000
                 * haveCommentThumbUp : 0
                 * id : 40288b1164a79bb60164a79e792b0005
                 * msg : 二楼。对文章的一级评论
                 * replyUser : 0
                 * state : 1
                 * user : {"address":"","headPicture":"/img/user/head/2018/05/10/114128_174446.png","id":"ff8080815f2d4436015f341b2aeb0024","niceName":"131","sex":"0","userName":"13189054152"}
                 */

                private long creatTime;
                private String haveCommentThumbUp; // 是否点赞
                private String commThumbCount;// 点赞总数
                private String id;
                private String msg;
                private String replyUser;
                private String state;
                private UserBean user;

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


                public String getReplyUser() {
                    return replyUser;
                }

                public void setReplyUser(String replyUser) {
                    this.replyUser = replyUser;
                }

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

                public UserBean getUser() {
                    return user;
                }

                public void setUser(UserBean user) {
                    this.user = user;
                }


            }
        }

        public static class SortBean {
            /**
             * ascending : false
             * direction : DESC
             * property : creatTime
             */

            private boolean ascending;
            private String direction;
            private String property;

            public boolean isAscending() {
                return ascending;
            }

            public void setAscending(boolean ascending) {
                this.ascending = ascending;
            }

            public String getDirection() {
                return direction;
            }

            public void setDirection(String direction) {
                this.direction = direction;
            }

            public String getProperty() {
                return property;
            }

            public void setProperty(String property) {
                this.property = property;
            }
        }
    }
}

