package com.mxkj.econtrol.bean.response;

import com.google.gson.annotations.SerializedName;
import com.mxkj.econtrol.base.BaseResponse;

import java.util.List;

/**
 * Created by chanjun on 2017/8/3.
 *
 * @Description: 获取房间列表接口响应类
 */

public class ResGetRoomList02 extends BaseResponse {


    /**
     * house : {"buiding":"富力2","estate":"富力新天地","houseNo":"1004","id":"ff8080815cc99db0015d4f3be3bf02d9"}
     * master : {"headPicuture":"/img/user/head/2018/06/06/154657_527499.png","realName":"","userId":"ff8080815cc99ddf015d4e932a2c0de7","userName":"13610295454"}
     * modelList : [{"code":"MD001","name":"回家模块","value":"01"},{"code":"MD001","name":"离家模块","value":"00"}]
     * sceneList : [{"device":"00001","houseId":"ff8080815cc99db0015d4f3be3bf02d9","id":"ff8080815cc99db0015d4f3be3c502da","name":"入户花园","scencePic":"/img/scence/HY","smallPic":"/img/scence/20180424103309.ico","type":"HY"},{"device":"00001","houseId":"ff8080815cc99db0015d4f3be3bf02d9","id":"ff8080815cc99db0015d4f3be3c602db","name":"客厅","scencePic":"/img/scence/1","smallPic":"/img/scence/20180424103351.ico","type":"KT"},{"device":"00001","houseId":"ff8080815cc99db0015d4f3be3bf02d9","id":"ff8080816005cc0c016011033466001d","name":"次卧3","scencePic":"/img/scence/20180423173837.png","smallPic":"/img/scence/20180424103641.ico","type":"CW"}]
     * serviceUrl :
     * smInfoBean : {"room":[{"code":"HY-001","id":"ff8080815cc99db0015d4f3be3c502da","isScene":"1","name":"入户花园","order":1,"sceneType":{"cCode":"HY","cDes":"","cName":"入户花园","cOrder":1,"cStatus":"00","cValue":"HY","iPid":"40288f815c0fa164015c0faa97470003","id":"40288f815c0fa164015c0fabf5e40006"},"state":"1"},{"code":"KT-001","id":"ff8080815cc99db0015d4f3be3c602db","isScene":"1","name":"客厅","order":2,"sceneType":{"cCode":"KT","cDes":"","cName":"客厅","cOrder":8,"cStatus":"00","cValue":"KT","iPid":"40288f815c0fa164015c0faa97470003","id":"40288f815c0fa164015c0fc8943b0018"},"state":"1"},{"code":"CW-003","id":"ff8080816005cc0c016011033466001d","isScene":"1","name":"次卧3","order":3,"sceneType":{"cCode":"CW","cDes":"","cName":"次卧","cOrder":3,"cStatus":"00","cValue":"CW","iPid":"40288f815c0fa164015c0faa97470003","id":"40288f815c0fa164015c0fae2ebc000c"},"state":"1"}],"roomPart":[[{"brand":{"code":"02","id":"40288b1162199f1b016219be85c30002","name":"青松"},"code":"LK001","functions":{"a":1,"tp":1},"gateway":{"id":"","name":"","snid":""},"id":"40288b1163ed48060163f7bd932e001a","kt_f":"","kt_m":"","kt_t":"","name":"门锁","permissions":"tp,a","power":"1","rgb":"","state":{"lockState":{"applyGesturePasswordStatus":"notSubmited","gesturePasswordStatus":"uninited"}},"stateTimer":"","type":"5"}],[{"brand":{"code":"00","id":"","name":"无"},"code":"LI000001","functions":{"a":1,"d":0,"rgb":0},"gateway":{"id":"","name":"","snid":""},"id":"ff8080815cc99db0015d4f3f608102e5","kt_f":"","kt_m":"","kt_t":"","name":"等010","permissions":"d,a,rgb","power":"1","rgb":"","state":{},"stateTimer":"1","type":"1"},{"brand":{"code":"00","id":"","name":"无"},"code":"AC00000023","functions":{"a":1,"f":0,"m":0,"t":0},"gateway":{"id":"","name":"","snid":""},"id":"ff8080815cc99db0015d4f3fea4602ec","kt_f":"","kt_m":"","kt_t":"","name":"空调","permissions":"f,t,a,m","power":"1","rgb":"","state":{},"stateTimer":"0","type":"2"},{"brand":{"code":"00","id":"","name":"无"},"code":"LI0000002","functions":{"a":1,"d":0,"rgb":0},"gateway":{"id":"","name":"","snid":""},"id":"ff8080815cc99db0015d4f3fa53f02eb","kt_f":"","kt_m":"","kt_t":"","name":"灯02","permissions":"d,a,rgb","power":"1","rgb":"","state":{},"stateTimer":"1","type":"1"},{"brand":{"code":"00","id":"","name":"无"},"code":"LI0000000454","functions":{"a":1,"d":0,"rgb":0},"gateway":{"id":"","name":"","snid":""},"id":"ff8080815d4f7dee015d4fbb318e0010","kt_f":"","kt_m":"","kt_t":"","name":"灯","permissions":"d,a,rgb","power":"1","rgb":"","state":{},"stateTimer":"1","type":"1"},{"brand":{"code":"00","id":"","name":"无"},"code":"NF00000003","functions":{"a":1,"f":0},"gateway":{"id":"","name":"","snid":""},"id":"ff8080815cc99db0015d4f40322d02ed","kt_f":"","kt_m":"","kt_t":"","name":"风","permissions":"f,a","power":"1","rgb":"","state":{},"stateTimer":"0","type":"3"},{"brand":{"code":"00","id":"","name":"无"},"code":"LI004","functions":{"a":1,"d":1,"rgb":1},"gateway":{"id":"","name":"","snid":""},"id":"40288b11623ce0cb01623cf587a20002","kt_f":"","kt_m":"","kt_t":"","name":"灯4","permissions":"d,a,rgb","power":"1","rgb":"","state":{},"stateTimer":"1","type":"1"},{"brand":{"code":"00","id":"","name":"无"},"code":"LI005","functions":{"a":1,"d":1,"rgb":1},"gateway":{"id":"","name":"","snid":""},"id":"40288b11623ce0cb01623cf5f9660003","kt_f":"","kt_m":"","kt_t":"","name":"灯5","permissions":"d,a,rgb","power":"1","rgb":"","state":{},"stateTimer":"","type":"1"}],[{"brand":{"code":"00","id":"","name":"无"},"code":"FH001","functions":{"a":1,"t":1},"gateway":{"id":"","name":"","snid":""},"id":"40288b116274d0e7016274d2eb8e0003","kt_f":"","kt_m":"","kt_t":"","name":"地热","permissions":"t,a","power":"1","rgb":"","state":{},"stateTimer":"","type":"7"},{"brand":{"code":"00","id":"","name":"无"},"code":"FH002","functions":{"a":1,"t":0},"gateway":{"id":"","name":"","snid":""},"id":"40288b116274d0e70162759d32e60006","kt_f":"","kt_m":"","kt_t":"","name":"死地热","permissions":"t,a","power":"1","rgb":"","state":{},"stateTimer":"","type":"7"}]]}
     * thePartUrl :
     */

    private HouseBean house;
    private MasterBean master;
    private String serviceUrl;

    private String thePartUrl;
    private List<ModelListBean> modelList;
    private List<SceneListBean> sceneList;

    public HouseBean getHouse() {
        return house;
    }

    public void setHouse(HouseBean house) {
        this.house = house;
    }

    public MasterBean getMaster() {
        return master;
    }

    public void setMaster(MasterBean master) {
        this.master = master;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }



    public String getThePartUrl() {
        return thePartUrl;
    }

    public void setThePartUrl(String thePartUrl) {
        this.thePartUrl = thePartUrl;
    }

    public List<ModelListBean> getModelList() {
        return modelList;
    }

    public void setModelList(List<ModelListBean> modelList) {
        this.modelList = modelList;
    }

    public List<SceneListBean> getSceneList() {
        return sceneList;
    }

    public void setSceneList(List<SceneListBean> sceneList) {
        this.sceneList = sceneList;
    }

    public static class HouseBean {
        /**
         * buiding : 富力2
         * estate : 富力新天地
         * houseNo : 1004
         * id : ff8080815cc99db0015d4f3be3bf02d9
         */

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

    public static class MasterBean {
        /**
         * headPicuture : /img/user/head/2018/06/06/154657_527499.png
         * realName :
         * userId : ff8080815cc99ddf015d4e932a2c0de7
         * userName : 13610295454
         */

        private String headPicuture;
        private String realName;
        private String userId;
        private String userName;

        public String getHeadPicuture() {
            return headPicuture;
        }

        public void setHeadPicuture(String headPicuture) {
            this.headPicuture = headPicuture;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }


    public static class ModelListBean {
        /**
         * code : MD001
         * name : 回家模块
         * value : 01
         */

        private String code;
        private String name;
        private String value;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class SceneListBean {
        /**
         * device : 00001
         * houseId : ff8080815cc99db0015d4f3be3bf02d9
         * id : ff8080815cc99db0015d4f3be3c502da
         * name : 入户花园
         * scencePic : /img/scence/HY
         * smallPic : /img/scence/20180424103309.ico
         * type : HY
         */

        private String device;
        private String houseId;
        private String id;
        private String name;
        private String scencePic;
        private String smallPic;
        private String type;

        public String getDevice() {
            return device;
        }

        public void setDevice(String device) {
            this.device = device;
        }

        public String getHouseId() {
            return houseId;
        }

        public void setHouseId(String houseId) {
            this.houseId = houseId;
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

        public String getScencePic() {
            return scencePic;
        }

        public void setScencePic(String scencePic) {
            this.scencePic = scencePic;
        }

        public String getSmallPic() {
            return smallPic;
        }

        public void setSmallPic(String smallPic) {
            this.smallPic = smallPic;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}

