package com.mxkj.econtrol.bean.response;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.Notify;

import java.util.List;

/**
 * Created by liangshan on 2017/4/5.
 *
 * @Description:通知记录响应实体类
 */

public class ResNotifyEntity extends BaseResponse {
    private List<Notify> notify;

    public List<Notify> getNotify() {
        return notify;
    }

    public void setNotify(List<Notify> notify) {
        this.notify = notify;
    }
}

