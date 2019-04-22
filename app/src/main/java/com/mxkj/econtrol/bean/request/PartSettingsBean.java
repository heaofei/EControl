package com.mxkj.econtrol.bean.request;

/**
 * Created by ${  chenjun  } on 2017/11/1.
 */

public class PartSettingsBean {

        private String partId;
        private String  switchStatus;

        public String getPartId() {
            return partId;
        }

        public void setPartId(String partId) {
            this.partId = partId;
        }

        public String getSwitchStatus() {
            return switchStatus;
        }

        public void setSwitchStatus(String switchStatus) {
            this.switchStatus = switchStatus;
        }

}
