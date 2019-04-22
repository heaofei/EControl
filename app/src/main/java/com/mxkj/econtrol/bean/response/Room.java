package com.mxkj.econtrol.bean.response;

import com.mxkj.econtrol.base.BaseEntity;

/**
 * Created by liangshan on 2017/4/14.
 *
 * @Description:
 */

public class Room extends BaseEntity {
    private String id;//	场景的id
    private String name;//场景名称
    private String type;//	场景类型(0-花园，1-卧室，2-厨房，3-客厅，4-餐厅)
    private String code = ""; // CT、KT、ZW.... 用来区分餐厅、客厅、主卧、等
    private String device;//中控设备
    private String houseId;
    private String scencePic;// 大图  （ipad用）
    private String smallPic;// 小图
    private String smallSelectPic;// 选中图片
    private boolean isClick= false;// 选中

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isClick() {
        return isClick;
    }

    public void setClick(boolean click) {
        isClick = click;
    }


    public String getSmallSelectPic() {
        return smallSelectPic;
    }

    public void setSmallSelectPic(String smallSelectPic) {
        this.smallSelectPic = smallSelectPic;
    }

    public String getScencePic() {
        return scencePic;
    }

    public void setScencePic(String scencePic) {
        this.scencePic = scencePic;
    }


    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSmallPic() {
        return smallPic;
    }

    public void setSmallPic(String smallPic) {
        this.smallPic = smallPic;
    }
}
