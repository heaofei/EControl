package com.mxkj.econtrol.bean.response;


import com.mxkj.econtrol.base.BaseResponse;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ${  chenjun  } on 2017/10/31.
 */

public class ResGetPatternList extends BaseResponse {


    /**
     * list : [{"createTime":"2017-10-31 13:51:18.0","descriptions":"描述-3","id":"40288b115f70fbe5015f70fbe9e90002","name":"模式-3","status":"0","updateTime":""},{"createTime":"2017-10-31 13:51:18.0","descriptions":"描述-4","id":"40288b115f70fbe5015f70fbea0e0003","name":"模式-4","status":"1","updateTime":""},{"createTime":"2017-10-31 13:51:18.0","descriptions":"描述-6","id":"40288b115f70fbe5015f70fbea4f0005","name":"模式-6","status":"0","updateTime":""},{"createTime":"2017-10-31 13:51:18.0","descriptions":"描述-7","id":"40288b115f70fbe5015f70fbea6d0006","name":"模式-7","status":"0","updateTime":""},{"createTime":"2017-10-31 13:51:18.0","descriptions":"描述-8","id":"40288b115f70fbe5015f70fbea940007","name":"模式-8","status":"0","updateTime":""},{"createTime":"2017-10-31 13:51:18.0","descriptions":"描述-9","id":"40288b115f70fbe5015f70fbeac20008","name":"模式-9","status":"0","updateTime":""}]
     * msg : SUCCESS
     * state : 0000
     */

    private List<ListBeaner> list;

    public List<ListBeaner> getList() {
        return list;
    }

    public void setList(List<ListBeaner> list) {
        this.list = list;
    }

    public static class ListBeaner {
        /**
         * createTime : 2017-10-31 13:51:18.0
         * descriptions : 描述-3
         * id : 40288b115f70fbe5015f70fbe9e90002
         * name : 模式-3
         * status : 0
         * updateTime :
         */

        private String createTime;
        private String descriptions;
        private String id;
        private String name;  // 模式类型名称
        private String msName; // 模式名称
        private String status; // 模式状态：status 0 未应用，1-已应用
        private String updateTime;
        private String type; //模式类型：type 0-全关模式；1-全开模式；2-自定模式；
        private String typeCode = ""; // CT、KT、ZW.... 用来区分餐厅、客厅、主卧、等
        private String pic =  "";  // 默认图片
        private String selectPic =  "";// 选中图片
        private String num =  "";
        private String editCode =  ""; // 该区域编辑场景模式所需要的编号
        private boolean isClick ;


        public String getMsName() {
            return msName;
        }

        public void setMsName(String msName) {
            this.msName = msName;
        }

        public String getSelectPic() {
            return selectPic;
        }

        public void setSelectPic(String selectPic) {
            this.selectPic = selectPic;
        }

        public String getEditCode() {
            return editCode;
        }

        public void setEditCode(String editCode) {
            this.editCode = editCode;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getTypeCode() {
            return typeCode;
        }

        public void setTypeCode(String typeCode) {
            this.typeCode = typeCode;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public boolean isClick() {
            return isClick;
        }

        public void setClick(boolean click) {
            isClick = click;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getDescriptions() {
            return descriptions;
        }

        public void setDescriptions(String descriptions) {
            this.descriptions = descriptions;
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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }
}
