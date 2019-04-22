package com.mxkj.econtrol.base;

/**
 * Created by liangshan on 2017/4/11.
 *
 * @Description: 命令结果回调接口
 */

public interface TCPCMDResult {
    void onSuccess(String result);

    void onFail(String msg);
}
