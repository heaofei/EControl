package com.mxkj.econtrol.bean.response;


import com.mxkj.econtrol.base.BaseResponse;

import java.util.List;

/**
 *  获取全部设备使用次数接收实体类
 * Created by ${  chenjun  } on 2017/10/31.
 */

public class ResGetTimeRanking extends BaseResponse {


    private String totalCount;
    private List<TimeRankBean> list;

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public List<TimeRankBean> getList() {
        return list;
    }

    public void setList(List<TimeRankBean> list) {
        this.list = list;
    }

    public class TimeRankBean {
        private String partId;
        private String partCode;
        private String partName;
        private String count;

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
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }
    }

}
