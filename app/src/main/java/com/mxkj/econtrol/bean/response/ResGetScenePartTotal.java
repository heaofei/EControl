package com.mxkj.econtrol.bean.response;

import com.mxkj.econtrol.base.BaseResponse;

import java.util.List;

/**
 * Created by liangshan on 2017/4/14.
 *
 * @Description:
 */

public class ResGetScenePartTotal extends BaseResponse {
    private List<SmartPart> partTotal;

    public List<SmartPart> getPartTotal() {
        return partTotal;
    }

    public void setPartTotal(List<SmartPart> partTotal) {
        this.partTotal = partTotal;
    }
}
