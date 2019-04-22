package com.mxkj.econtrol.bean.response;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.tcpcmd.MessageNotify;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liangshan on 2017/4/27.
 *
 * @Description： 获取在家用户列表响应实体类
 */

public class ResGetAtHomeUserList extends BaseResponse {
    private List<AtHomeUser> atHomeUserList;

    public List<AtHomeUser> getAtHomeUserList() {
        return atHomeUserList;
    }

    public void setAtHomeUserList(List<AtHomeUser> atHomeUserList) {
        this.atHomeUserList = atHomeUserList;
    }


    public static class AtHomeUser {
        private String userId;//用户编码
        private String userName;//用户名
        private String bindType;//用户类型:0、管理员；1、成口
        private String state;//用户绑定状态0:申请；1、已审核；2、拒绝；
        private String headPicture;//	用户头像
        private List<PushList> pushList;//消息Id列表
        private int isAtHome; //1在家，0不在家


        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getBindType() {
            return bindType;
        }

        public void setBindType(String bindType) {
            this.bindType = bindType;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getHeadPicture() {
            return headPicture;
        }

        public void setHeadPicture(String headPicture) {
            this.headPicture = headPicture;
        }


        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }


        public int getIsAtHome() {
            return isAtHome;
        }


        public List<PushList> getPushList() {
            return pushList;
        }

        public void setPushList(List<PushList> pushList) {
            this.pushList = pushList;
        }

        public void setIsAtHome(int isAtHome) {
            this.isAtHome = isAtHome;
        }
    }

    public static class PushList {
        private String pushId;

        public String getPushId() {
            return pushId;
        }

        public void setPushId(String pushId) {
            this.pushId = pushId;
        }
    }
}
