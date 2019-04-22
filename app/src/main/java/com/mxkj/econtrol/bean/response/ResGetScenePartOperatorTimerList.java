package com.mxkj.econtrol.bean.response;

import com.mxkj.econtrol.base.BaseResponse;

import java.util.List;

/**
 * Created by ${  chenjun  } on 2017/11/2.
 *
 * @Description: 获取场景的部件设备-操作的定时列表信息响应类
 */

public class ResGetScenePartOperatorTimerList extends BaseResponse {


    /**
     * list : [{"id":"40288b115f7bf669015f7c6fee86002e","status":"0","time":"00:00"},{"id":"40288b115f7bf669015f7c7023de002f","status":"0","time":"00:00"}]
     * operator : {"code":"0001","id":"40288b115f7bf669015f7c07535d0008","name":"定时打开","partId":"ff8080815cc99db0015d4f41ca1e02ee","partType":"1","status":"0"}
     */

    private OperatorBean operator;
    private List<ListBean> list;

    public OperatorBean getOperator() {
        return operator;
    }

    public void setOperator(OperatorBean operator) {
        this.operator = operator;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class OperatorBean {
        /**
         * code : 0001
         * id : 40288b115f7bf669015f7c07535d0008
         * name : 定时打开
         * partId : ff8080815cc99db0015d4f41ca1e02ee
         * partType : 1
         * status : 0
         */

        private String code;
        private String id;
        private String name;
        private String partId;
        private String partType;
        private String status;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPartId() {
            return partId;
        }

        public void setPartId(String partId) {
            this.partId = partId;
        }

        public String getPartType() {
            return partType;
        }

        public void setPartType(String partType) {
            this.partType = partType;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public static class ListBean {
        /**
         * id : 40288b115f7bf669015f7c6fee86002e
         * status : 0
         * time : 00:00
         */

        private String id;
        private String status;
        private String time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
