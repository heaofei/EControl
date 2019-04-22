package com.mxkj.econtrol.utils.gateway;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;


/**
 * Created by lidongxing on 2018/3/2 0002.
 */

public class GatewayService {
    private boolean isThreadRunning = false;
    private boolean isFinishSearch = false;
    public void addGateway(final Context context, final IAddGatewayCallback callback) {
        final Thread stopThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(30000);

                    if (isThreadRunning) {
                        GatewaySearcher.getInstance().stopSearch();
                        if (!isFinishSearch) {
                        callback.onFailed("添加网关超时");
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                isThreadRunning = false;
            }
        });
        isThreadRunning = true;
        stopThread.start();

   /*     final IPassThroughMessageListener listener = new IPassThroughMessageListener() {
            @Override
            public void onReceiveMessage(String message) {
                PassThroughMessageResponse response = JSON.parseObject(message, PassThroughMessageResponse.class);

                PushMessageManager.getInstance().removePassThroughMessageListener(PassThroughMessageTypeConstant.TYPE_GATEWAY_BIND, this);

                if(response.isResult()==true) {
                    callback.onSuccess(response.getData().toString());
                } else {
                    callback.onFailed(response.getData().toString());
                }
            }
        };*/

        GatewaySearcher.getInstance().startSearch(new IGatewaySearchListener() {
            @Override
            public void onDeviceFound(DeviceInfo deviceInfo) {
                Log.i("msg","========deviceInfo===");
                Log.i("msg","==========id="+deviceInfo.getId());
                Log.i("msg","==========pw="+deviceInfo.getPassword());
                Log.i("msg","==========sn="+deviceInfo.getSn());
                isFinishSearch = true;
                callback.onSuccess(deviceInfo);

            }
        });
    }
}
