package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by liangshan on 2017/4/26.
 *
 * @Description： 获取商城房子列表请求实体类
 */

public class ReqGetShopProductList extends BaseRequestEntity {
    private int page;//页码，第几页
    private String rows;//每页返回多少条
    private String type;//	类型
    private String title;//搜索关键字

    public ReqGetShopProductList() {
    }

    public ReqGetShopProductList(int page, String rows, String type, String title) {
        this.page = page;
        this.rows = rows;
        this.type = type;
        this.title = title;
    }


    public String getRows() {
        return rows;
    }

    public void setRows(String rows) {
        this.rows = rows;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
