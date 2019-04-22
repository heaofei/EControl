package com.mxkj.econtrol.bean.response;

import com.mxkj.econtrol.base.BaseResponse;

import java.util.List;

/**
 * Created by liangshan on 2017/4/14.
 *
 * @Description: 获取场景列表接口响应类
 */

public class ResGetSceneList extends BaseResponse {
    private List<Room> sceneList; //场景列表
    private List<Model> modelList;//模式列表
    private String serviceUrl = "";
    private String thePartUrl = "";
    private House house;

    public List<Model> getModelList() {
        return modelList;
    }

    public void setModelList(List<Model> modelList) {
        this.modelList = modelList;
    }


    public List<Room> getSceneList() {
        return sceneList;
    }

    public void setSceneList(List<Room> sceneList) {
        this.sceneList = sceneList;
    }

    public String getThePartUrl() {
        return thePartUrl;
    }

    public void setThePartUrl(String thePartUrl) {
        this.thePartUrl = thePartUrl;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public static class Model {
        private String name;//显示名称
        private String code;//编码
        private String value; //值

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class House {


        private String buiding;
        private String estate;
        private String houseNo;
        private String id;

        public String getBuiding() {
            return buiding;
        }

        public void setBuiding(String buiding) {
            this.buiding = buiding;
        }

        public String getEstate() {
            return estate;
        }

        public void setEstate(String estate) {
            this.estate = estate;
        }

        public String getHouseNo() {
            return houseNo;
        }

        public void setHouseNo(String houseNo) {
            this.houseNo = houseNo;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}

