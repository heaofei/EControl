package com.mxkj.econtrol.bean.response;

import com.mxkj.econtrol.base.BaseTCPCMDResponse;

/**
 * Created by ${  chenjun  } on 2017/11/7.
 */

public class TcpResSearchLockListRespones extends BaseTCPCMDResponse {


    /**
     * userId : 13610295454
     * device : 112f2420
     * command : {"sn":"464E4235362D444F5231314854312E37","shortAddress":"45D7","endPoint":"01","ieeeAddress":"521CDD01008D1500"}
     */

    private String userId;
    private String device;
    private CommandBean command;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
         * sn : 464E4235362D444F5231314854312E37
         * shortAddress : 45D7
         * endPoint : 01
         * ieeeAddress : 521CDD01008D1500
         */

        private String sn;
        private String shortAddress;
        private String endPoint;
        private String ieeeAddress;

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

        public String getShortAddress() {
            return shortAddress;
        }

        public void setShortAddress(String shortAddress) {
            this.shortAddress = shortAddress;
        }

        public String getEndPoint() {
            return endPoint;
        }

        public void setEndPoint(String endPoint) {
            this.endPoint = endPoint;
        }

        public String getIeeeAddress() {
            return ieeeAddress;
        }

        public void setIeeeAddress(String ieeeAddress) {
            this.ieeeAddress = ieeeAddress;
        }
    }
}
