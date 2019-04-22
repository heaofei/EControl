package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by liangshan on 2017/4/19.
 *
 * @Description： 1.3.8.4.    发布社区信息请求实体类
 */

public class ReqPublicCommunity extends BaseRequestEntity {
    private String msg;//	信息
    private String pic;//	图片
    private String estateId;//
    private int height;//图片高
    private int width;//图片高

    public ReqPublicCommunity() {
    }

    public ReqPublicCommunity(String msg, String pic, String estateId, int width, int height) {
        this.msg = msg;
        this.pic = pic;
        this.estateId = estateId;
        this.height = height;
        this.width = width;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getEstateId() {
        return estateId;
    }

    public void setEstateId(String estateId) {
        this.estateId = estateId;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
