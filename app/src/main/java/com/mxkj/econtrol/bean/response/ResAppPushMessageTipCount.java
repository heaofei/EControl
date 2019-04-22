package com.mxkj.econtrol.bean.response;

import com.mxkj.econtrol.base.BaseResponse;

/**
 * Created by chan on 2018/1/3.
 *
 * @Description:
 */

public class ResAppPushMessageTipCount extends BaseResponse {


    /**
     * house_msg_admin_notify_member : {"num":1}
     * house_msg_notify_member : {"num":57}
     * totalCount : 98
     * user_apply_bind_house : {"num":4}
     */

    private HouseMsgAdminNotifyMemberBean house_msg_admin_notify_member;
    private HouseMsgNotifyMemberBean house_msg_notify_member;
    private int totalCount;
    private UserApplyBindHouseBean user_apply_bind_house;
    private SystemNotify system_notify;
    private HouseSwitchManagerAssign house_switch_manager_assign;

    public HouseSwitchManagerAssign getHouse_switch_manager_assign() {
        return house_switch_manager_assign;
    }
    public void setHouse_switch_manager_assign(HouseSwitchManagerAssign house_switch_manager_assign) {
        this.house_switch_manager_assign = house_switch_manager_assign;
    }
    public SystemNotify getSystem_notify() {
        return system_notify;
    }

    public void setSystem_notify(SystemNotify system_notify) {
        this.system_notify = system_notify;
    }

    public HouseMsgAdminNotifyMemberBean getHouse_msg_admin_notify_member() {
        return house_msg_admin_notify_member;
    }

    public void setHouse_msg_admin_notify_member(HouseMsgAdminNotifyMemberBean house_msg_admin_notify_member) {
        this.house_msg_admin_notify_member = house_msg_admin_notify_member;
    }

    public HouseMsgNotifyMemberBean getHouse_msg_notify_member() {
        return house_msg_notify_member;
    }

    public void setHouse_msg_notify_member(HouseMsgNotifyMemberBean house_msg_notify_member) {
        this.house_msg_notify_member = house_msg_notify_member;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public UserApplyBindHouseBean getUser_apply_bind_house() {
        return user_apply_bind_house;
    }

    public void setUser_apply_bind_house(UserApplyBindHouseBean user_apply_bind_house) {
        this.user_apply_bind_house = user_apply_bind_house;
    }

    public static class HouseMsgAdminNotifyMemberBean {
        /**
         * num : 1
         */

        private int num;

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }

    public static class HouseMsgNotifyMemberBean {
        /**
         * num : 57
         */

        private int num;

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }

    public static class UserApplyBindHouseBean {
        /**
         * num : 4
         */

        private int num;

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }
    public static class SystemNotify {
        /**
         * num : 4
         */

        private int num;

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }
    public static class HouseSwitchManagerAssign {
        /**
         * num : 4
         */

        private int num = 0;

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }
}
