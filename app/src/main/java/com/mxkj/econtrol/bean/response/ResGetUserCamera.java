package com.mxkj.econtrol.bean.response;

import com.mxkj.econtrol.base.BaseResponse;

import java.util.List;

/**
 * Created by chan on 2018/12/13.
 *
 * @Description: 获取用户绑定的摄像头列表， （非业主的就是被分享列表）
 */

public class ResGetUserCamera extends BaseResponse {


    /**
     * page : {"content":[{"creatTime":1544764902000,"deviceId":"ABCDEFGF","houseId":"ff8080815cc99db0015d4f3f7c8702e6","id":"40288c1167ab18570167ab29cb3b0004","ip":"113.119.9.100","name":"摄像头1号：ABCDEFGF","port":"9000","userId":"ff8080815cc99ddf015d4e932a2c0de7"}],"firstPage":true,"lastPage":true,"number":0,"numberOfElements":1,"size":20,"sort":[{"ascending":false,"direction":"DESC","property":"creatTime"}],"totalElements":1,"totalPages":1}
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
         * content : [{"creatTime":1544764902000,"deviceId":"ABCDEFGF","houseId":"ff8080815cc99db0015d4f3f7c8702e6","id":"40288c1167ab18570167ab29cb3b0004","ip":"113.119.9.100","name":"摄像头1号：ABCDEFGF","port":"9000","userId":"ff8080815cc99ddf015d4e932a2c0de7"}]
         * firstPage : true
         * lastPage : true
         * number : 0
         * numberOfElements : 1
         * size : 20
         * sort : [{"ascending":false,"direction":"DESC","property":"creatTime"}]
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

        public static class ContentBean {
            /**
             * creatTime : 1544764902000
             * deviceId : ABCDEFGF
             * houseId : ff8080815cc99db0015d4f3f7c8702e6
             * id : 40288c1167ab18570167ab29cb3b0004
             * ip : 113.119.9.100
             * name : 摄像头1号：ABCDEFGF
             * port : 9000
             * userId : ff8080815cc99ddf015d4e932a2c0de7
             */

            private long creatTime;
            private String deviceId;
            private String houseId;
            private String id;
            private String ip;
            private String name;
            private String port;
            private String userId;
            private String lable;

            public String getLable() {
                return lable;
            }

            public void setLable(String lable) {
                this.lable = lable;
            }

            public long getCreatTime() {
                return creatTime;
            }

            public void setCreatTime(long creatTime) {
                this.creatTime = creatTime;
            }

            public String getDeviceId() {
                return deviceId;
            }

            public void setDeviceId(String deviceId) {
                this.deviceId = deviceId;
            }

            public String getHouseId() {
                return houseId;
            }

            public void setHouseId(String houseId) {
                this.houseId = houseId;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getIp() {
                return ip;
            }

            public void setIp(String ip) {
                this.ip = ip;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPort() {
                return port;
            }

            public void setPort(String port) {
                this.port = port;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
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


