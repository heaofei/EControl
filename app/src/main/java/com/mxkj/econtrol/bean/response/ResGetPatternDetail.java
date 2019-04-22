package com.mxkj.econtrol.bean.response;

import com.mxkj.econtrol.base.BaseResponse;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chanjun on 2017/10/31.
 *
 * @Description: 获取模式详情接口响应类
 */

public class ResGetPatternDetail extends BaseResponse {
    private String id;
    private String name;
    private String descriptions;
    private List<ScenePartBean> scenes;

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

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public List<ScenePartBean> getScenes() {
        return scenes;
    }

    public void setScenes(List<ScenePartBean> scenes) {
        this.scenes = scenes;
    }

    public class  ScenePartBean implements Serializable {
        private String id;
        private String name;
        private String active;
        private int count = 0;
        private boolean isClick = false;// 是否选中
        private List<PartSettingBean> partSetting;


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

        public String getActive() {
            return active;
        }

        public void setActive(String active) {
            this.active = active;
        }

        public List<PartSettingBean> getPartSetting() {
            return partSetting;
        }

        public void setPartSetting(List<PartSettingBean> partSetting) {
            this.partSetting = partSetting;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
    public class  PartSettingBean implements Serializable{
        private String settingId;
        private String partId;
        private String partName;
        private String active;
        private String partType; // 设备类型  空调  AC    新风机  NF  灯  LI
        private String switchStatus="0"; // 0关  1开
        private boolean isClick = false;// 是否选中


        public boolean isClick() {
            return isClick;
        }

        public void setClick(boolean click) {
            isClick = click;
        }

        public String getPartType() {
            return partType;
        }

        public void setPartType(String partType) {
            this.partType = partType;
        }

        public String getSettingId() {
            return settingId;
        }

        public void setSettingId(String settingId) {
            this.settingId = settingId;
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

        public String getActive() {
            return active;
        }

        public void setActive(String active) {
            this.active = active;
        }

        public String getSwitchStatus() {
            return switchStatus;
        }

        public void setSwitchStatus(String switchStatus) {
            this.switchStatus = switchStatus;
        }
    }



}

