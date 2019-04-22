package com.mxkj.econtrol.net;

import android.os.SystemClock;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.app.Config;
import com.mxkj.econtrol.app.Msg;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.base.BaseFragment;
import com.mxkj.econtrol.base.BaseTCPCMDRequest;
import com.mxkj.econtrol.base.BaseTCPCMDResponse;
import com.mxkj.econtrol.base.TCPCMDResult;
import com.mxkj.econtrol.bean.EventBusUIMessage;
import com.mxkj.econtrol.utils.AbToastUtil;
import com.mxkj.econtrol.utils.DigitalFor62Helper;
import com.mxkj.econtrol.utils.LogUtil;
import com.mxkj.econtrol.utils.ResourceUtil;
import com.mxkj.econtrol.view.activity.HouseMainActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.Random;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;


/**
 * Created by liangshan on 2017/4/5.
 *
 * @Description: tcp工具类
 */

public class TcpClient {
    // 连接服务端
    private Channel mChannel;
    private static TcpClient mTcpClient;
    public static boolean isConnecting = false; // 是否连接
    //是否为主动关闭,防止关闭后自动连接
    public static boolean isClose = false;

    private TcpClient() {
    }


    public static TcpClient getInstacne() {
        if (mTcpClient == null) {
            synchronized (TcpClient.class) {
                if (mTcpClient == null) {
                    mTcpClient = new TcpClient();
                }
            }
        }
        return mTcpClient;
    }

    public void init() {
        if (isConnecting) {
            //已经在连接
            return;
        }else {
            if (mChannel != null && mChannel.isOpen()) {
                isClose = true;
                mChannel.close();
                isConnecting = false;
            }
        }
        isClose = false;
        new Thread(new Runnable() {
            @Override
            public void run() {
                int index = 0;
                while (mChannel == null || !mChannel.isActive()) {
                        index++;
                    if (index<5){    // 设置每次重连次数为5次
                        LogUtil.i("========开始连接tcp：第"+index+"次");
                        connect();
                        SystemClock.sleep(10 * 1000);
                    }else if (index>=5){
                        if (mChannel != null && mChannel.isOpen()) {
                            isClose = true;
                            mChannel.close();
                            LogUtil.i("关闭tcp连接");
                            isConnecting = false;
                        }
                        return;
                    }
                }
            }
        }).start();
    }

    //向tcp服务器发命令
    public void sendCMD(BaseTCPCMDRequest cmd) {
        String serial = getSerialString();
        cmd.setSerial(serial);
        String data = cmd.toJsonStr();
        if (mChannel != null && mChannel.isActive()) {
            mChannel.writeAndFlush(data);
            LogUtil.i("========tcp发送命令：" + data);
        } else {
            BaseTCPCMDResponse result = new BaseTCPCMDResponse();
            result.setMsg(ResourceUtil.getString(R.string.send_fial));
            result.setSerial(serial);
            isConnecting=false;
            TcpClient.getInstacne().init();
            EventBus.getDefault().post(new EventBusUIMessage(Msg.TCP_CMD_FAIL, result));
        }

    }
    //向tcp服务器发命令
    public void sendCMD(BaseTCPCMDRequest cmd, TCPCMDResult lisenter) {
        String serial = getSerialString();
        cmd.setSerial(serial);
        String data = cmd.toJsonStr();
        if (mChannel != null && mChannel.isActive()) {
            mChannel.writeAndFlush(data);
            LogUtil.i("========tcp发送命令：" + data);
        } else {
            BaseTCPCMDResponse result = new BaseTCPCMDResponse();
            result.setMsg(ResourceUtil.getString(R.string.send_fial));
            result.setSerial(serial);
            isConnecting=false;
            TcpClient.getInstacne().init();
            EventBus.getDefault().post(new EventBusUIMessage(Msg.TCP_CMD_FAIL, result));
        }

    }

    //向tcp服务器发命令,有结果回调
    public void sendCMD(BaseFragment baseFragment, BaseTCPCMDRequest cmd, TCPCMDResult lisenter) {
        String serial = getSerialString();
        baseFragment.getResultMap().put(serial, lisenter);
        cmd.setSerial(serial);
        String data = cmd.toJsonStr();
        if (mChannel != null && mChannel.isActive()) {
            mChannel.writeAndFlush(data);
            LogUtil.i("========tcp发送命令：" + data);
        } else {
            TcpClient.getInstacne().init();
            BaseTCPCMDResponse result = new BaseTCPCMDResponse();
            result.setMsg(ResourceUtil.getString(R.string.send_fial));
            result.setSerial(serial);
            isConnecting=false;
            TcpClient.getInstacne().init();
            EventBus.getDefault().post(new EventBusUIMessage(Msg.TCP_CMD_FAIL, result));
        }
    }

    //向tcp服务器发命令,有结果回调
    public void sendCMD(BaseActivity baseActivity, BaseTCPCMDRequest cmd, TCPCMDResult lisenter) {
        String serial = getSerialString();
        baseActivity.getResultMap().put(serial, lisenter);
        cmd.setSerial(serial);
        String data = cmd.toJsonStr();
        if (mChannel != null && mChannel.isActive()) {
            mChannel.writeAndFlush(data);
            LogUtil.i("========tcp发送命令：" + data);
        } else {
            TcpClient.getInstacne().init();
            BaseTCPCMDResponse result = new BaseTCPCMDResponse();
            result.setMsg(ResourceUtil.getString(R.string.send_fial));
            result.setSerial(serial);
            isConnecting=false;
            TcpClient.getInstacne().init();
            EventBus.getDefault().post(new EventBusUIMessage(Msg.TCP_CMD_FAIL, result));
        }
    }

    /***开始连接*/
    private void connect() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new APPClientInitializer());

            // 连接服务端
//            mChannel = b.connect(Config.TCP_SERVER, Integer.parseInt(Config.TCP_SERVER_PORT)).sync().channel();
            mChannel = b.connect(Config.TCP_SERVER, Integer.parseInt(Config.TCP_SERVER_PORT)).sync().channel();
            if (mChannel.isActive()) {
                LogUtil.i("=====================连接tcp服务器成功！");
                ClientIdleStateHandler.HEART_BEAT_NUM = 0;
                //连接成功后,如果已登录了就立刻注册tcp
                if (APP.user != null) {
                    BaseTCPCMDRequest regist = new BaseTCPCMDRequest();
                    regist.setAction("REGIST");
                    TcpClient.getInstacne().sendCMD(regist);
                }
                isConnecting = true;
            }

        } catch (Exception interruptedException) {
            interruptedException.printStackTrace();
            isConnecting = false;
            LogUtil.e("=====================连接tcp服务器失败=" + interruptedException.toString());
        } finally {
//            group.shutdownGracefully();
        }
    }

    public void close() {
        if (mChannel != null && mChannel.isOpen()) {
            isClose = true;
            mChannel.close();
            LogUtil.i("关闭tcp连接");
        }
    }

    /**
     * 随机产生编码
     *
     * @param
     * @return 随机字符串
     */
    public static String getSerialString() {
        String base = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 6; i++) {
            int number = random.nextInt(base.length());
            sb.append(number);
        }
        long s = System.currentTimeMillis() * 1000000 + Integer.parseInt(sb.toString());
        return DigitalFor62Helper.to62(s, 12);
    }
}
