package com.mxkj.econtrol.bean.response;

import com.mxkj.econtrol.base.BaseResponse;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liangshan on 2017/4/17.
 *
 * @Description：
 */

public class ResGetHouseAllPartList extends BaseResponse {

    private List<SceneListBean> sceneList;

    public List<SceneListBean> getSceneList() {
        return sceneList;
    }

    public void setSceneList(List<SceneListBean> sceneList) {
        this.sceneList = sceneList;
    }

    public static class SceneListBean implements Serializable{
        /**
         * id : ff8080815cc99db0015d4f3be3c502da
         * name : 入户阳台
         * partList : [{"code":"AC0000000023","id":"ff8080815cc99db0015d4f42161202ef","name":"空调","type":"4028a0815b7a45f2015b7a4a10a90009"},{"code":"NF000032","id":"ff8080815cc99db0015d4f4253f202f0","name":"新风机","type":"4028a0815b7a45f2015b7a4e3924000b"},{"code":"LI0000000005","id":"ff8080815cc99db0015d4f41ca1e02ee","name":"灯01","type":"4028a0815b7a45f2015b7a49c1b90007"}]
         */

        private String id;
        private String name;
        private List<PartListBean> partList;

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

        public List<PartListBean> getPartList() {
            return partList;
        }

        public void setPartList(List<PartListBean> partList) {
            this.partList = partList;
        }

        public static class PartListBean implements Serializable{
            /**
             * code : AC0000000023
             * id : ff8080815cc99db0015d4f42161202ef
             * name : 空调
             * type : 4028a0815b7a45f2015b7a4a10a90009
             */

            private String code;
            private String id;
            private String name;
            private String type;
            private String switchStatus= "0"; // 0关  1开

            public String getSwitchStatus() {
                return switchStatus;
            }

            public void setSwitchStatus(String switchStatus) {
                this.switchStatus = switchStatus;
            }

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

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
