package com.mxkj.econtrol.bean;

import com.mxkj.econtrol.base.BaseEntity;

import java.util.List;

/**
 * Created by liangshan on 2017/4/5.
 *
 * @Description:
 */

public class Notify extends BaseEntity {
    private String titile;

    private List<String> content;

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }

    public String getTitile() {
        return titile;
    }

    public void setTitile(String titile) {
        this.titile = titile;
    }
}