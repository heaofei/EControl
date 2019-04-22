package com.mxkj.econtrol.base;

import com.google.gson.Gson;

/**
 * Created by liangshan on 2017/3/22.
 *
 * @Destription: 请求实体类积累
 */

public class BaseRequestEntity extends BaseEntity {

    /**
     * 将请求实体转为json字符串
     *
     * @return
     */
    public String toJsonStr() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
