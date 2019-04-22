package com.mxkj.econtrol.bean.response;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.utils.DateTimeUtil;

import java.util.List;

/**
 * Created by liangshan on 2017/4/22.
 *
 * @Description： 用户操作日志请求实体类
 */

public class ResGetHouseControlLogList extends BaseResponse {
    private List<House> houseList;

    public List<House> getHouseList() {
        return houseList;
    }

    public void setHouseList(List<House> houseList) {
        this.houseList = houseList;
    }

    public static class House {
        private String housingEstate;
        private int totalPages;
        private String building;
        private String houseNo;
        private List<ControlLogList> houseControlLogList;

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public String getHousingEstate() {
            return housingEstate;
        }

        public void setHousingEstate(String housingEstate) {
            this.housingEstate = housingEstate;
        }

        public String getBuilding() {
            return building;
        }

        public void setBuilding(String building) {
            this.building = building;
        }

        public String getHouseNo() {
            return houseNo;
        }

        public void setHouseNo(String houseNo) {
            this.houseNo = houseNo;
        }


        public List<ControlLogList> getHouseControlLogList() {
            return houseControlLogList;
        }

        public void setHouseControlLogList(List<ControlLogList> houseControlLogList) {
            this.houseControlLogList = houseControlLogList;
        }

        public class ControlLogList {
            private String show;
            private long createTime;
            private User user;
            private String command;

            public String getCommand() {
                return command;
            }

            public void setCommand(String command) {
                this.command = command;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }


            public String getShow() {
                return show;
            }

            public void setShow(String show) {
                this.show = show;
            }

            public User getUser() {
                return user;
            }

            public void setUser(User user) {
                this.user = user;
            }
        }



    }
    public static class User{
        private String headPicture;
        private String id;
        private String mood;
        private String niceName;
        private String userName;
        private String sex;

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

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }
    }

}
