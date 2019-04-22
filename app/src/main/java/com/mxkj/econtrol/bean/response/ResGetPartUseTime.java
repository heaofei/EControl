package com.mxkj.econtrol.bean.response;

import com.google.gson.annotations.SerializedName;
import com.mxkj.econtrol.base.BaseResponse;

import java.util.List;

/**
 * Created by ${  chenjun  } on 2019/3/21.
 */

public class ResGetPartUseTime extends BaseResponse {


    /**
     * data : {"day":"2019.03.21","hourLong":206,"num":3,"partList":[{"40288c11687e817b01687e81c9ff0012":[{"day":"2019-03-21","houseId":"40288b116339ea3801633e30821e0001","id":"40288c11699ecbd501699ecc93970002","num":2,"partId":"40288c11687e817b01687e81ca260016","partName":"筒灯2","sceneId":"40288c11687e817b01687e81c9ff0012","useTime":"2.0"}],"40288c11687e817b01687e81ca4f0023":[{"day":"2019-03-21","houseId":"40288b116339ea3801633e30821e0001","id":"40288c11699ecbd501699ecdddc40007","num":1,"partId":"40288c11687e817b01687e81ca590024","partName":"餐厅吊灯","sceneId":"40288c11687e817b01687e81ca4f0023","useTime":"204.0"}]}],"sceneList":[{"id":"40288c11687e817b01687e81c9ff0012","name":"客厅"},{"id":"40288c11687e817b01687e81ca4f0023","name":"餐厅"}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * day : 2019.03.21
         * hourLong : 206.0
         * num : 3
         * partList : [{"40288c11687e817b01687e81c9ff0012":[{"day":"2019-03-21","houseId":"40288b116339ea3801633e30821e0001","id":"40288c11699ecbd501699ecc93970002","num":2,"partId":"40288c11687e817b01687e81ca260016","partName":"筒灯2","sceneId":"40288c11687e817b01687e81c9ff0012","useTime":"2.0"}],"40288c11687e817b01687e81ca4f0023":[{"day":"2019-03-21","houseId":"40288b116339ea3801633e30821e0001","id":"40288c11699ecbd501699ecdddc40007","num":1,"partId":"40288c11687e817b01687e81ca590024","partName":"餐厅吊灯","sceneId":"40288c11687e817b01687e81ca4f0023","useTime":"204.0"}]}]
         * sceneList : [{"id":"40288c11687e817b01687e81c9ff0012","name":"客厅"},{"id":"40288c11687e817b01687e81ca4f0023","name":"餐厅"}]
         */

        private String day;
        private double hourLong;
        private int num;
        private List<PartListBean> partList;
        private List<SceneListBean> sceneList;

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public double getHourLong() {
            return hourLong;
        }

        public void setHourLong(double hourLong) {
            this.hourLong = hourLong;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public List<PartListBean> getPartList() {
            return partList;
        }

        public void setPartList(List<PartListBean> partList) {
            this.partList = partList;
        }

        public List<SceneListBean> getSceneList() {
            return sceneList;
        }

        public void setSceneList(List<SceneListBean> sceneList) {
            this.sceneList = sceneList;
        }

        public static class PartListBean {

            private List<PartListBeanIDBean> partListBeanIDBean;

            public List<PartListBeanIDBean> getPartListBeanIDBean() {
                return partListBeanIDBean;
            }

            public void setPartListBeanIDBean(List<PartListBeanIDBean> partListBeanIDBean) {
                this.partListBeanIDBean = partListBeanIDBean;
            }


            public static class PartListBeanIDBean {
                /**
                 * day : 2019-03-21
                 * houseId : 40288b116339ea3801633e30821e0001
                 * id : 40288c11699ecbd501699ecc93970002
                 * num : 2
                 * partId : 40288c11687e817b01687e81ca260016
                 * partName : 筒灯2
                 * sceneId : 40288c11687e817b01687e81c9ff0012
                 * useTime : 2.0
                 */

                private String day;
                private String houseId;
                private String id;
                private int num;
                private String partId;
                private String partName;
                private String sceneId;
                private String useTime;

                public String getDay() {
                    return day;
                }

                public void setDay(String day) {
                    this.day = day;
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

                public int getNum() {
                    return num;
                }

                public void setNum(int num) {
                    this.num = num;
                }

                public String getPartId() {
                    return partId;
                }

                public void setPartId(String partId) {
                    this.partId = partId;
                }

                public String getPartName() {
                    return partName;
                }

                public void setPartName(String partName) {
                    this.partName = partName;
                }

                public String getSceneId() {
                    return sceneId;
                }

                public void setSceneId(String sceneId) {
                    this.sceneId = sceneId;
                }

                public String getUseTime() {
                    return useTime;
                }

                public void setUseTime(String useTime) {
                    this.useTime = useTime;
                }
            }

        }

        public static class SceneListBean {
            /**
             * id : 40288c11687e817b01687e81c9ff0012
             * name : 客厅
             */

            private String id;
            private String name;
            private String useTime;
            private String num;
            private boolean isClick =false;


            public String getUseTime() {
                return useTime;
            }

            public void setUseTime(String useTime) {
                this.useTime = useTime;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public boolean isClick() {
                return isClick;
            }

            public void setClick(boolean click) {
                isClick = click;
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
        }
    }
}
