package com.mxkj.econtrol.net;

import com.mxkj.econtrol.base.TCPCMDResult;
import com.mxkj.econtrol.bean.tcpcmd.HeartBeatCMD;
import com.mxkj.econtrol.utils.DateTimeUtil;
import com.mxkj.econtrol.utils.LogUtil;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * Created by liangshan on 2017/5/24.
 *
 * @Description：
 */

public class ClientIdleStateHandler extends ChannelInboundHandlerAdapter {

    public static int HEART_BEAT_NUM = 0;

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            LogUtil.i(event.state() + "");
            if (event.state().equals(IdleState.WRITER_IDLE)) {
                /***
                 * tcp重连机制，在APPClientHandler类接收到服务器返回心跳包后，将此书置为0，超过3次没收到服务器心跳包后，将重连TCP
                 */
                if (HEART_BEAT_NUM < 3) {
                    //一段时间内没有读写操作，往服务器发个心跳包
                    HeartBeatCMD heartBeatCMD = new HeartBeatCMD();
                    heartBeatCMD.setTime(DateTimeUtil.getSecond());
                    TcpClient.getInstacne().sendCMD(heartBeatCMD);
                    // 先将这里累加，3次之后，再下一步
                    HEART_BEAT_NUM += 1;
                    LogUtil.i("==========输出心跳包=="+HEART_BEAT_NUM);
                }else {
                // 将tcpclient 里面的 isConnecting 设为false
                    if (TcpClient.isConnecting){ // 这里进行重连一次就可以了， 因为在TcpClient连接里面：连接失败后会重新连接5次，多次会造成OOM
                         LogUtil.i("==========没有收到服务器心跳包，即将开始重连==");
                         TcpClient.isConnecting = false;
                         TcpClient.getInstacne().init();
                    }
                }
            }
        }
    }

    /**
     * //这里是断线要进行的操作
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        LogUtil.i("==========tcp断线了大哥01==");
    }

    //这里是出现异常的话要进行的操作
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        LogUtil.i("==========tcp断线了大哥02==");
    }
}
