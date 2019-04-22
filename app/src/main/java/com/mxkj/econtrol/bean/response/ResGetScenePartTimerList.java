package com.mxkj.econtrol.bean.response;

import com.mxkj.econtrol.base.BaseResponse;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ${  chenjun  } on 2017/11/2.
 *
 * @Description: 获取场景的部件设备-操作的定时列表信息响应类
 */

public class ResGetScenePartTimerList extends BaseResponse {


    private List<TimerListBean> timerList;
    private int totalPages;//总数

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<TimerListBean> getTimerList() {
        return timerList;
    }

    public void setTimerList(List<TimerListBean> timerList) {
        this.timerList = timerList;
    }

    public static class TimerListBean implements Serializable{
        /**
         * id : 40288b1164eefe700164ef057ea00003
         * name : 风3,门锁1
         * open : 1
         * partList : [{"partId":"40288b116266f178016266f3ff110004","partName":"风3","partType":"NF003"},{"partId":"40288b11642018ce016425563a500032","partName":"门锁1","partType":"LK001"}]
         * sceneList : [{"sceneCount":"1","sceneName":"餐厅"}]
         * status : 1
         * time : 03:00
         * week : 周三,
         */

        private String id;
        private String name;
        private int open;
        private String status;
        private String time;
        private String week;
        private List<PartListBean> partList;
        private List<SceneListBean> sceneList;

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

        public int getOpen() {
            return open;
        }

        public void setOpen(int open) {
            this.open = open;
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

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
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

        public static class PartListBean implements Serializable{
            /**
             * partId : 40288b116266f178016266f3ff110004
             * partName : 风3
             * partType : NF003
             */

            private String partId;
            private String partName;
            private String partType;

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

            public String getPartType() {
                return partType;
            }

            public void setPartType(String partType) {
                this.partType = partType;
            }
        }

        public static class SceneListBean implements Serializable{
            /**
             * sceneCount : 1
             * sceneName : 餐厅
             */

            private String id;
            private String sceneCount;
            private String sceneName;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getSceneCount() {
                return sceneCount;
            }

            public void setSceneCount(String sceneCount) {
                this.sceneCount = sceneCount;
            }

            public String getSceneName() {
                return sceneName;
            }

            public void setSceneName(String sceneName) {
                this.sceneName = sceneName;
            }
        }
    }
}
