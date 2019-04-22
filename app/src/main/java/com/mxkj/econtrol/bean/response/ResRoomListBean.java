package com.mxkj.econtrol.bean.response;

import java.util.List;

/**
 * Created by ${  chenjun  } on 2018/11/16.
 */

public class ResRoomListBean {

    private List<Room> smInfoBean;
    private boolean scenePartTotalisNull =true; // 常用列表数据，是否为空


    public boolean isScenePartTotalisNull() {
        return scenePartTotalisNull;
    }

    public void setScenePartTotalisNull(boolean scenePartTotalisNull) {
        this.scenePartTotalisNull = scenePartTotalisNull;
    }

    public List<Room> getSmInfoBean() {
        return smInfoBean;
    }

    public void setSmInfoBean(List<Room> smInfoBean) {
        this.smInfoBean = smInfoBean;
    }
}
