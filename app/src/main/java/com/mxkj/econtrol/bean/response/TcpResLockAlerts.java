package com.mxkj.econtrol.bean.response;

import com.mxkj.econtrol.base.BaseEntity;

/**
 * Created by ${  chenjun  } on 2018/6/13.
 */

public class TcpResLockAlerts extends BaseEntity {


    /**
     * userId : 17620999268
     * result : 1113
     * device : 112f2420
     * command : {"houseName":"富力新天地 T01-T1001","houseId":"40288b1161fe0ba80161fe389c86000d","alarmCode":"33"}
     */

    private String userId;
    private String result;
    private String device;
    private CommandBean command;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public CommandBean getCommand() {
        return command;
    }

    public void setCommand(CommandBean command) {
        this.command = command;
    }

    public static class CommandBean {
        /**
         * houseName : 富力新天地 T01-T1001
         * houseId : 40288b1161fe0ba80161fe389c86000d
         * alarmCode : 33
         */

        private String houseName;
        private String houseId;
        private String alarmCode;
        private String powerId; // powerId 0000 正常 0001 低压

        public String getPowerId() {
            return powerId;
        }

        public void setPowerId(String powerId) {
            this.powerId = powerId;
        }

        public String getHouseName() {
            return houseName;
        }

        public void setHouseName(String houseName) {
            this.houseName = houseName;
        }

        public String getHouseId() {
            return houseId;
        }

        public void setHouseId(String houseId) {
            this.houseId = houseId;
        }

        public String getAlarmCode() {
            return alarmCode;
        }

        public void setAlarmCode(String alarmCode) {
            this.alarmCode = alarmCode;
        }
    }
}
