package com.mxkj.econtrol.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

import com.mxkj.econtrol.bean.tcpcmd.HeartBeatCMD;
import com.mxkj.econtrol.net.TcpClient;
import com.mxkj.econtrol.utils.DateTimeUtil;
import com.mxkj.econtrol.utils.LogUtil;

/**
 * 心跳服务，每隔十秒往服务发一个心跳包（没有使用）
 *
 * //连续启动Service
 Intent intentOne = new Intent(this, HeartBeatServicie.class);
 startService(intentOne);
 */
public class HeartBeatServicie extends Service {
    private Thread mThread;


    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.i("service start");
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    SystemClock.sleep(10 * 1000);
                    HeartBeatCMD heartBeatCMD = new HeartBeatCMD();
                    heartBeatCMD.setTime(DateTimeUtil.getSecond());
                    TcpClient.getInstacne().sendCMD(heartBeatCMD);
                    LogUtil.i("service -----");
                }
            }
        });
        mThread.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        LogUtil.i("service start");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public boolean onUnbind(Intent intent) {
        LogUtil.i("onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mThread.stop();
    }


}
