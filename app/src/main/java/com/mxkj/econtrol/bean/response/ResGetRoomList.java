package com.mxkj.econtrol.bean.response;

import com.google.gson.annotations.SerializedName;
import com.mxkj.econtrol.base.BaseResponse;

import java.util.List;

/**
 * Created by chanjun on 2017/8/3.
 *
 * @Description: 获取房间列表接口响应类
 */

public class ResGetRoomList extends BaseResponse {
    public List<Room> sceneList; //场景列表
    private List<Model> modelList;//模式列表
    private String serviceUrl = "";
    private String thePartUrl = "";
    private House house;
    private MasterBean master;
    private SmInfoBeanBean smInfoBean;//设备列表



    public SmInfoBeanBean getSmInfoBean() {
        return smInfoBean;
    }

    public void setSmInfoBean(SmInfoBeanBean smInfoBean) {
        this.smInfoBean = smInfoBean;
    }


    public MasterBean getMaster() {
        return master;
    }

    public void setMaster(MasterBean master) {
        this.master = master;
    }

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

    public static class MasterBean{
        /**
         * "headPicuture": "/img/user/head/2018/01/17/173851_769565.png",
         "realName": "",
         "userId": "402871815f3338e3015f334cbda20003",
         "userName": "15766227629"
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



    public static class SmInfoBeanBean {
        private List<RoomBean> room;
        private List<List<SmartPart>> roomPart;

        public List<RoomBean> getRoom() {
            return room;
        }

        public void setRoom(List<RoomBean> room) {
            this.room = room;
        }

        public List<List<SmartPart>> getRoomPart() {
            return roomPart;
        }

        public void setRoomPart(List<List<SmartPart>> roomPart) {
            this.roomPart = roomPart;
        }

        public static class RoomBean {
            /**
             * code : HY-001
             * id : ff8080815cc99db0015d4f3be3c502da
             * isScene : 1
             * name : 入户花园
             * order : 1
             * sceneType : {"cCode":"HY","cDes":"","cName":"入户花园","cOrder":1,"cStatus":"00","cValue":"HY","iPid":"40288f815c0fa164015c0faa97470003","id":"40288f815c0fa164015c0fabf5e40006"}
             * state : 1
             */

            private String code;
            private String id;
            private String isScene;
            private String name;
            private int order;
            private SceneTypeBean sceneType;
            @SerializedName("state")
            private String stateX;

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

            public String getIsScene() {
                return isScene;
            }

            public void setIsScene(String isScene) {
                this.isScene = isScene;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getOrder() {
                return order;
            }

            public void setOrder(int order) {
                this.order = order;
            }

            public SceneTypeBean getSceneType() {
                return sceneType;
            }

            public void setSceneType(SceneTypeBean sceneType) {
                this.sceneType = sceneType;
            }

            public String getStateX() {
                return stateX;
            }

            public void setStateX(String stateX) {
                this.stateX = stateX;
            }

            public static class SceneTypeBean {
                /**
                 * cCode : HY
                 * cDes :
                 * cName : 入户花园
                 * cOrder : 1
                 * cStatus : 00
                 * cValue : HY
                 * iPid : 40288f815c0fa164015c0faa97470003
                 * id : 40288f815c0fa164015c0fabf5e40006
                 */

                private String cCode;
                private String cDes;
                private String cName;
                private int cOrder;
                private String cStatus;
                private String cValue;
                private String iPid;
                private String id;

                public String getCCode() {
                    return cCode;
                }

                public void setCCode(String cCode) {
                    this.cCode = cCode;
                }

                public String getCDes() {
                    return cDes;
                }

                public void setCDes(String cDes) {
                    this.cDes = cDes;
                }

                public String getCName() {
                    return cName;
                }

                public void setCName(String cName) {
                    this.cName = cName;
                }

                public int getCOrder() {
                    return cOrder;
                }

                public void setCOrder(int cOrder) {
                    this.cOrder = cOrder;
                }

                public String getCStatus() {
                    return cStatus;
                }

                public void setCStatus(String cStatus) {
                    this.cStatus = cStatus;
                }

                public String getCValue() {
                    return cValue;
                }

                public void setCValue(String cValue) {
                    this.cValue = cValue;
                }

                public String getIPid() {
                    return iPid;
                }

                public void setIPid(String iPid) {
                    this.iPid = iPid;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }
            }
        }

    }



}

