package com.mxkj.econtrol.bean.response;

import com.mxkj.econtrol.base.BaseResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liangshan on 2017/4/15.
 *
 * @Description： 获取场景下所有部件和状态的响应实体类
 */

public class ResGetScenePartList extends BaseResponse {
    private List<SmartPart> smPartList;

    public List<SmartPart> getSmPartList() {
        return smPartList;
    }

    public void setSmPartList(List<SmartPart> smPartList) {
        this.smPartList = smPartList;
    }

    public List<SmartPart> getLightList() { // 灯光
        List<SmartPart> lightList = new ArrayList<>();
        for (SmartPart sp : smPartList) {
            if (sp.getType().equals("1")) {
                lightList.add(sp);
            }
        }
        return lightList;
    }

    public List<SmartPart> getBlowerList() {  // 新风
        List<SmartPart> blowertList = new ArrayList<>();
        for (SmartPart sp : smPartList) {
            if (sp.getType().equals("3")) {
                blowertList.add(sp);
            }
        }
        return blowertList;
    }

    public List<SmartPart> getAirConditionerList() { // 空调
        List<SmartPart> airConditionerList = new ArrayList<>();
        for (SmartPart sp : smPartList) {
            if (sp.getType().equals("2")) {
                airConditionerList.add(sp);
            }
        }
        return airConditionerList;
    }
    public List<SmartPart> getCurtainsList() { // 窗帘
        List<SmartPart> curtainsList = new ArrayList<>();
        for (SmartPart sp : smPartList) {
            if (sp.getType().equals("4")) {
                curtainsList.add(sp);
            }
        }
        return curtainsList;
    }
    public List<SmartPart> getLockList() { // 门锁
        List<SmartPart> lockList = new ArrayList<>();
        for (SmartPart sp : smPartList) {
            if (sp.getType().equals("5")) {
                lockList.add(sp);
            }
        }
        return lockList;
    }
    public List<SmartPart> getAGList() { // 雾化玻璃
        List<SmartPart> lockList = new ArrayList<>();
        for (SmartPart sp : smPartList) {
            if (sp.getType().equals("6")) {
                lockList.add(sp);
            }
        }
        return lockList;
    }
}


