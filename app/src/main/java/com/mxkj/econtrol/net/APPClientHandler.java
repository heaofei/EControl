package com.mxkj.econtrol.net;


import com.google.gson.Gson;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.app.Msg;
import com.mxkj.econtrol.base.BaseTCPCMDResponse;
import com.mxkj.econtrol.bean.EventBusPostingMsg;
import com.mxkj.econtrol.bean.EventBusUIMessage;
import com.mxkj.econtrol.bean.response.TcpResHouseRespones;
import com.mxkj.econtrol.bean.response.TcpResLockAlerts;
import com.mxkj.econtrol.bean.response.TcpResPatternRespones;
import com.mxkj.econtrol.bean.response.TcpResSearchLockListRespones;
import com.mxkj.econtrol.bean.tcpcmd.MessageNotify;
import com.mxkj.econtrol.bean.tcpcmd.RegistCMD;
import com.mxkj.econtrol.bean.tcpcmd.SmartPartStateTCP;
import com.mxkj.econtrol.utils.AbToastUtil;
import com.mxkj.econtrol.utils.LogUtil;
import com.mxkj.econtrol.utils.ResourceUtil;

import org.greenrobot.eventbus.EventBus;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


public class APPClientHandler extends SimpleChannelInboundHandler<String> {

    private ByteToCommadDecoder byteToCommadDecoder;


    public APPClientHandler() {
        byteToCommadDecoder = new ByteToCommadDecoder();

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        LogUtil.i("========收到tcp=="+msg);
        Gson gson = new Gson();
        BaseTCPCMDResponse response = gson.fromJson(msg, BaseTCPCMDResponse.class);
        String result = response.getResult();
        response.setData(msg);
        EventBusUIMessage message = new EventBusUIMessage();
        switch (response.getResult()) {
            case "0000":
                //发送的命令执行成功
                message.setMsgType(Msg.TCP_CMD_SUCCESS);
                message.setData(response);
                if (response.getAction().equals("HEART_BEAT")){ // 这里接收到服务器回复的心跳包后，将次数置为0
                    ClientIdleStateHandler.HEART_BEAT_NUM = 0;
                    LogUtil.i("===========收到回复心跳包，num置为0:" );
                }
                break;
            case "1111":
                //智能部件状态改变通知
                message.setMsgType(Msg.SAMRT_PART_STATE_CHANGE);
                SmartPartStateTCP state = gson.fromJson(msg, SmartPartStateTCP.class);
                message.setData(state);
                break;
            case "2222":
                //消息推送
                message.setMsgType(Msg.MESSAGE_NOTIFY);
                message.setData(gson.fromJson(msg, MessageNotify.class));
                break;
            case "0003":
                //未注册tcp服务器导致失败，重新注册
                RegistCMD registCMD = new RegistCMD();
                TcpClient.getInstacne().sendCMD(registCMD);
                //发送的命令执行失败
                message.setMsgType(Msg.TCP_CMD_FAIL);
                message.setData(ResourceUtil.getString(R.string.send_cmd_fail_tip));
                break;
            case "2221":
                //登录失效，账号在其他手机登录
                message.setMsgType(Msg.LOGON_FAILURE);
                break;
            case "2220":
                //提交了的申请审核后结果通知推送
                message.setMsgType(Msg.CHECK_RESULT);
                message.setData(response);
                break;
            case "3333":
                //在家人数有变动
            /*    message.setMsgType(Msg.AT_HOME_STATE_CHANGE);
                message.setData(response);*/
                break;
            case "3334":
                TcpResHouseRespones tcpResHouseRespones = gson.fromJson(msg, TcpResHouseRespones.class);
                // 业主移除家庭成员--》》 将移除消息推送给具体移除的家庭成员
                // {"extraData":{"houseId":"ff8080815cc99db0015d4f3be3bf02d9"},"msg":"","result":"3334"}
                String content = response.toString();
                message.setMsgType(Msg.MY_HOUSE_CHANGE);
                message.setData(tcpResHouseRespones);
                break;
            case "3335":
                TcpResHouseRespones tcpResHouseRespones1 = gson.fromJson(msg, TcpResHouseRespones.class);

                // 业主审核成员结果--》》 将审核结果推送给提交审核者
                //{"extraData":{"houseId":"ff8080815cc99db0015d4f3be3bf02d9","state":"0"},"msg":"","result":"3335"}
                String content1 = response.toString();
                message.setMsgType(Msg.MY_HOUSE_CHANGE);
                message.setData(tcpResHouseRespones1);
                break;
            case "3336":
                TcpResHouseRespones tcpResHouseRespones3 = gson.fromJson(msg, TcpResHouseRespones.class); // 要自定义实体类，因为有可能是多个房子的审核列表 多个houstId
                // 业主申请管理员结果--》》 将审核申请推送给业主
                message.setMsgType(Msg.CHECK_EXAMINE);
                message.setData(tcpResHouseRespones3);
                break;
            case "3337":
                TcpResHouseRespones tcpResHouseRespones2 = gson.fromJson(msg, TcpResHouseRespones.class);
                // 旧业主转让管理权--》》 推送给新业主确认接收
                message.setMsgType(Msg.HOUSE_MANAGER_SWITCH_ASSIGN);
                message.setData(tcpResHouseRespones2);
                break;
            case "3338":
//                TcpResHouseRespones tcpResHouseRespones2 = gson.fromJson(msg, TcpResHouseRespones.class);
                // 业主更换后通知成员
                message.setMsgType(Msg.HOUSE_MANAGER_SWITCH_HANDLE);
                message.setData(response);
                break;
            case "4440":
            case "4441":
            case "4442":
            case "4443":
               /* TcpResPatternRespones tcpResPatternRespones = gson.fromJson(msg, TcpResPatternRespones.class);
                message.setMsgType(Msg.MY_PART_CHANGE);
                message.setData(tcpResPatternRespones);*/

                // 模式发生变化
                message.setMsgType(Msg.MY_PART_CHANGE);
                SmartPartStateTCP state2 = gson.fromJson(msg, SmartPartStateTCP.class);
                message.setData(state2);


                break;
            case "1112": // 搜索门锁，TCP返回内容
                TcpResSearchLockListRespones tcpResSearchLockListRespones = gson.fromJson(msg, TcpResSearchLockListRespones.class);
                // 模式发生变化
                message.setMsgType(Msg.EVENBUS_SEARCH_LOCK_RESULT);
                message.setData(tcpResSearchLockListRespones);
                break;
            case "1113": // 门锁警报消息
                TcpResLockAlerts tcpResLockAlerts = gson.fromJson(msg, TcpResLockAlerts.class);
                message.setMsgType(Msg.EVENBUS_LOCK_ALERTS);
                message.setData(tcpResLockAlerts);
                break;
            case "1114": // 门锁电池消息
                TcpResLockAlerts tcpResLockAlerts2 = gson.fromJson(msg, TcpResLockAlerts.class);
                message.setMsgType(Msg.EVENBUS_LOCK_BATTER_MESSAGE);
                message.setData(tcpResLockAlerts2);
                break;
            default:
                //发送的命令执行失败
                message.setMsgType(Msg.TCP_CMD_FAIL);
                message.setData(response);
                break;
        }
        EventBus.getDefault().post(message);
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client active ");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client close ");
        super.channelInactive(ctx);
        EventBus.getDefault().post(new EventBusPostingMsg(Msg.CLEAR_TCP_CMD_LISENTER, null));
        //如果非手动断开的，重新连接
        if (!TcpClient.isClose)
            TcpClient.getInstacne().init();
    }


}