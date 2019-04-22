package com.mxkj.econtrol.utils.gateway;

/**
 * Created by alex on 2017/12/31.
 */

public interface IAddGatewayCallback {
    void onFailed(String failedMessage);
    void onSuccess(DeviceInfo deviceInfo);
}
