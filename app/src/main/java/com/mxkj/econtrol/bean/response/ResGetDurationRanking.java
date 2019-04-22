package com.mxkj.econtrol.bean.response;


import com.mxkj.econtrol.base.BaseResponse;

import java.util.List;

/**
 *  获取全部设备使用时长接收实体类
 * Created by ${  chenjun  } on 2017/10/31.
 */

public class ResGetDurationRanking extends BaseResponse {


    private String totalMinute;
    private List<DurationRankBean> list;

    public String getTotalCount() {
        return totalMinute;
    }

    public void setTotalCount(String totalCount) {
        this.totalMinute = totalCount;
    }

    public List<DurationRankBean> getList() {
        return list;
    }

    public void setList(List<DurationRankBean> list) {
        this.list = list;
    }

    public class DurationRankBean {
        private String partId;
        private String partCode;
        private String partName;
        private String minute;

        public String getPartId() {
            return partId;
        }

        public void setPartId(String partId) {
            this.partId = partId;
        }

        public String getPartCode() {
            return partCode;
        }

        public void setPartCode(String partCode) {
            this.partCode = partCode;
        }

        public String getPartName() {
            return partName;
        }

        public void setPartName(String partName) {
            this.partName = partName;
        }

        public String getCount() {
            return minute;
        }

        public void setCount(String count) {
            this.minute = count;
        }
    }

}
