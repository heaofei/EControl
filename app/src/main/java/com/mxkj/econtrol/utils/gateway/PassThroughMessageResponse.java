package com.mxkj.econtrol.utils.gateway;

/**
 * Created by lidongxing on 2018/3/1 0001.
 */

public class PassThroughMessageResponse {
    private boolean result;
    private Object data;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
