package com.mxkj.econtrol.bean.response;

import com.mxkj.econtrol.base.BaseResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chan on 2018/6/5.
 *
 * @Description： 用户操作日志的adapter实体类
 */

public class AdapterHouseControlLogList extends BaseResponse {


    private  List<HouseControlLogList> controlLogLists;


    public List<HouseControlLogList> getControlLogLists() {
        return controlLogLists;
    }

    public void setControlLogLists(List<HouseControlLogList> controlLogLists) {
        this.controlLogLists = controlLogLists;
    }

    public static class HouseControlLogList{
        private String show;
        private long createTime;
        private String headPicture;
        private String id;
        private String mood;
        private String niceName;
        private String userName;
        private String command;// 具体的操作指令

        public String getCommand() {
            return command;
        }

        public void setCommand(String command) {
            this.command = command;
        }

        public String getShow() {
            return show;
        }

        public void setShow(String show) {
            this.show = show;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
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

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }
}
