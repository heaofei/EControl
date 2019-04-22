package com.mxkj.econtrol.bean.response;

import com.mxkj.econtrol.base.BaseTCPCMDResponse;

/**
 * Created by ${  chenjun  } on 2017/11/7.
 */

public class TcpResHouseRespones extends BaseTCPCMDResponse {

    private ExtraData extraData;//后面加的房子id类

    public ExtraData getExtraData() {
        return extraData;
    }

    public void setExtraData(ExtraData extraData) {
        this.extraData = extraData;
    }

    public class  ExtraData{
        private String houseId;//房子id
        private String state;//状态

        public String getHouseId() {
            return houseId;
        }

        public void setHouseId(String houseId) {
            this.houseId = houseId;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
    }


}
