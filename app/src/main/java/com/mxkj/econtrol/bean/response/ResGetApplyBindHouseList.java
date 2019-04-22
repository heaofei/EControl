package com.mxkj.econtrol.bean.response;

import com.mxkj.econtrol.base.BaseResponse;

import java.util.List;

/**
 * Created by chan on 2018/1/4.
 *
 * @Description:
 */

public class ResGetApplyBindHouseList extends BaseResponse {


    /**
     * page : {"content":[{"applyNo":"HBA20171220171844","buildingId":"ff8080815cc99db0015d4f3a83fc02c4","buildingName":"富力2","createTime":"2017-12-20 17:18","estateId":"ff8080815cc99db0015d4ea75caf02b7","estateName":"富力新天地","houseId":"ff8080815cc99db0015d4f3be3bf02d9","houseNo":"3009","realName":"ro","status":"0","statusText":"待审核","userId":"40288b115fec1dd3015fecd188500069","userName":"13250333738"},{"applyNo":"HBA20171220171743","buildingId":"ff8080815cc99db0015d4f3a83fc02c4","buildingName":"富力2","createTime":"2017-12-20 17:17","estateId":"ff8080815cc99db0015d4ea75caf02b7","estateName":"富力新天地","houseId":"ff8080815cc99db0015d4f3be3bf02d9","houseNo":"3009","realName":"ff","status":"0","statusText":"待审核","userId":"40288b115fec1dd3015fecd188500069","userName":"13250333738"},{"applyNo":"HBA20171220141943","buildingId":"ff8080815cc99db0015d4f3a83fc02c4","buildingName":"富力2","createTime":"2017-12-20 14:19","estateId":"ff8080815cc99db0015d4ea75caf02b7","estateName":"富力新天地","houseId":"ff8080815cc99db0015d4f3be3bf02d9","houseNo":"3009","realName":"安卓测试","status":"0","statusText":"待审核","userId":"402871815f3338e3015f335139cc0007","userName":"17620999268"}],"firstPage":true,"lastPage":false,"number":0,"numberOfElements":3,"size":3,"totalElements":22,"totalPages":8}
     */

    private PageBean page;
    private int passed= 0;  // 通过的
    private int reject= 0;  // 拒绝
    private int waiting= 0;  // 等待

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public int getPassed() {
        return passed;
    }

    public void setPassed(int passed) {
        this.passed = passed;
    }

    public int getReject() {
        return reject;
    }

    public void setReject(int reject) {
        this.reject = reject;
    }

    public int getWaiting() {
        return waiting;
    }

    public void setWaiting(int waiting) {
        this.waiting = waiting;
    }

    public static class PageBean {
        /**
         * content : [{"applyNo":"HBA20171220171844","buildingId":"ff8080815cc99db0015d4f3a83fc02c4","buildingName":"富力2","createTime":"2017-12-20 17:18","estateId":"ff8080815cc99db0015d4ea75caf02b7","estateName":"富力新天地","houseId":"ff8080815cc99db0015d4f3be3bf02d9","houseNo":"3009","realName":"ro","status":"0","statusText":"待审核","userId":"40288b115fec1dd3015fecd188500069","userName":"13250333738"},{"applyNo":"HBA20171220171743","buildingId":"ff8080815cc99db0015d4f3a83fc02c4","buildingName":"富力2","createTime":"2017-12-20 17:17","estateId":"ff8080815cc99db0015d4ea75caf02b7","estateName":"富力新天地","houseId":"ff8080815cc99db0015d4f3be3bf02d9","houseNo":"3009","realName":"ff","status":"0","statusText":"待审核","userId":"40288b115fec1dd3015fecd188500069","userName":"13250333738"},{"applyNo":"HBA20171220141943","buildingId":"ff8080815cc99db0015d4f3a83fc02c4","buildingName":"富力2","createTime":"2017-12-20 14:19","estateId":"ff8080815cc99db0015d4ea75caf02b7","estateName":"富力新天地","houseId":"ff8080815cc99db0015d4f3be3bf02d9","houseNo":"3009","realName":"安卓测试","status":"0","statusText":"待审核","userId":"402871815f3338e3015f335139cc0007","userName":"17620999268"}]
         * firstPage : true
         * lastPage : false
         * number : 0
         * numberOfElements : 3
         * size : 3
         * totalElements : 22
         * totalPages : 8
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
             * applyNo : HBA20171220171844
             * buildingId : ff8080815cc99db0015d4f3a83fc02c4
             * buildingName : 富力2
             * createTime : 2017-12-20 17:18
             * estateId : ff8080815cc99db0015d4ea75caf02b7
             * estateName : 富力新天地
             * houseId : ff8080815cc99db0015d4f3be3bf02d9
             * houseNo : 3009
             * realName : ro
             * status : 0
             * statusText : 待审核
             * userId : 40288b115fec1dd3015fecd188500069
             * userName : 13250333738
             */

            private String applyNo;
            private String buildingId;
            private String buildingName;
            private String createTime;
            private String estateId;
            private String estateName;
            private String houseId;
            private String houseNo;
            private String realName;
            private String status;    //0、未审核，1、审核，2、拒绝',
            private String statusText;
            private String userId;
            private String userName;
            private String headPicture;

            public String getHeadPicture() {
                return headPicture;
            }

            public void setHeadPicture(String headPicture) {
                this.headPicture = headPicture;
            }

            public String getApplyNo() {
                return applyNo;
            }

            public void setApplyNo(String applyNo) {
                this.applyNo = applyNo;
            }

            public String getBuildingId() {
                return buildingId;
            }

            public void setBuildingId(String buildingId) {
                this.buildingId = buildingId;
            }

            public String getBuildingName() {
                return buildingName;
            }

            public void setBuildingName(String buildingName) {
                this.buildingName = buildingName;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getEstateId() {
                return estateId;
            }

            public void setEstateId(String estateId) {
                this.estateId = estateId;
            }

            public String getEstateName() {
                return estateName;
            }

            public void setEstateName(String estateName) {
                this.estateName = estateName;
            }

            public String getHouseId() {
                return houseId;
            }

            public void setHouseId(String houseId) {
                this.houseId = houseId;
            }

            public String getHouseNo() {
                return houseNo;
            }

            public void setHouseNo(String houseNo) {
                this.houseNo = houseNo;
            }

            public String getRealName() {
                return realName;
            }

            public void setRealName(String realName) {
                this.realName = realName;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getStatusText() {
                return statusText;
            }

            public void setStatusText(String statusText) {
                this.statusText = statusText;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
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
