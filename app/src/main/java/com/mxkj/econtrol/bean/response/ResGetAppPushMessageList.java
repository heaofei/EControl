package com.mxkj.econtrol.bean.response;

import com.google.gson.annotations.SerializedName;
import com.mxkj.econtrol.base.BaseResponse;

import java.util.List;

/**
 * Created by ${  chenjun  } on 2017/10/27.
 */

public class ResGetAppPushMessageList extends BaseResponse {


    /**
     * page : {"content":[{"content":"用户15013241484,申请为控制成员，绑定房子富力新天地富力21001","fromUser":{"address":"富力新天地第一单元1602","headPicture":"/img/user/head/2017/10/26/173652_977115.png","id":"ff8080815cc99ddf015d34c90e200d2b","mood":"serious","niceName":"ray","sex":"1","userName":"15013241484"},"insertTime":"2017-10-26 17:52:01.0","pushId":"40288b045f57cae3015f58187e090055","refId":"40288b045f57cae3015f58187e090054","refType":"USER_APPLY_BIND_HOUSE","state":"2","title":"用户绑定消息"}],"firstPage":true,"lastPage":true,"number":0,"numberOfElements":1,"size":20,"totalElements":1,"totalPages":1}
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
         * content : [{"content":"用户15013241484,申请为控制成员，绑定房子富力新天地富力21001","fromUser":{"address":"富力新天地第一单元1602","headPicture":"/img/user/head/2017/10/26/173652_977115.png","id":"ff8080815cc99ddf015d34c90e200d2b","mood":"serious","niceName":"ray","sex":"1","userName":"15013241484"},"insertTime":"2017-10-26 17:52:01.0","pushId":"40288b045f57cae3015f58187e090055","refId":"40288b045f57cae3015f58187e090054","refType":"USER_APPLY_BIND_HOUSE","state":"2","title":"用户绑定消息"}]
         * firstPage : true
         * lastPage : true
         * number : 0
         * numberOfElements : 1
         * size : 20
         * totalElements : 1
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

        public static class ContentBean {
            /**
             * content : 用户15013241484,申请为控制成员，绑定房子富力新天地富力21001
             * fromUser : {"address":"富力新天地第一单元1602","headPicture":"/img/user/head/2017/10/26/173652_977115.png","id":"ff8080815cc99ddf015d34c90e200d2b","mood":"serious","niceName":"ray","sex":"1","userName":"15013241484"}
             * insertTime : 2017-10-26 17:52:01.0
             * pushId : 40288b045f57cae3015f58187e090055
             * refId : 40288b045f57cae3015f58187e090054
             * refType : USER_APPLY_BIND_HOUSE
             * state : 2
             * title : 用户绑定消息
             */

            private String content;
            private FromUserBean fromUser;
            private String insertTime;
            private String pushId;
            private String refId;
            private String refType;   // SYSTEM_NOTIFY  系统通知  USER_APPLY_BIND_HOUSE 用户申请绑定
            private String state;
            private String title;
            private String red;
            private RefData refData;


            public RefData getRefData() {
                return refData;
            }

            public void setRefData(RefData refData) {
                this.refData = refData;
            }
            public class RefData{
                private String bindState; // 表示绑定状态 ， 0 待审核 1 通过   2 拒绝

                public String getBindState() {
                    return bindState;
                }

                public void setBindState(String bindState) {
                    this.bindState = bindState;
                }
            }

            public String getRed() {
                return red;
            }

            public void setRed(String red) {
                this.red = red;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public FromUserBean getFromUser() {
                return fromUser;
            }

            public void setFromUser(FromUserBean fromUser) {
                this.fromUser = fromUser;
            }

            public String getInsertTime() {
                return insertTime;
            }

            public void setInsertTime(String insertTime) {
                this.insertTime = insertTime;
            }

            public String getPushId() {
                return pushId;
            }

            public void setPushId(String pushId) {
                this.pushId = pushId;
            }

            public String getRefId() {
                return refId;
            }

            public void setRefId(String refId) {
                this.refId = refId;
            }

            public String getRefType() {
                return refType;
            }

            public void setRefType(String refType) {
                this.refType = refType;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public static class FromUserBean {
                /**
                 * address : 富力新天地第一单元1602
                 * headPicture : /img/user/head/2017/10/26/173652_977115.png
                 * id : ff8080815cc99ddf015d34c90e200d2b
                 * mood : serious
                 * niceName : ray
                 * sex : 1
                 * userName : 15013241484
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
