package com.mxkj.econtrol.utils;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.app.Msg;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.base.TCPCMDResult;
import com.mxkj.econtrol.bean.EventBusUIMessage;
import com.mxkj.econtrol.bean.response.SmartPart;
import com.mxkj.econtrol.bean.response.SmartPartState;
import com.mxkj.econtrol.bean.tcpcmd.Command;
import com.mxkj.econtrol.bean.tcpcmd.SmartPartCMD;
import com.mxkj.econtrol.bean.tcpcmd.SmartPartStateTCP;
import com.mxkj.econtrol.net.TcpClient;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by ${  chenjun  } on 2018/8/1.
 * 设备操作工具类，用来处理发送操作指令
 */

public class DeviceOperationUtil {

    private static Context context;

    public static boolean openDevice(BaseActivity activity, SmartPart smartPart) {
        context = activity;
        final boolean[] sendResult = {false};

        final Command command = new Command();
        final SmartPartState state = smartPart.getState();
        if (smartPart.getType().equals("1") && smartPart.getFunctions().getD() == 1) { // 调光的灯   并且 支持调光模块
            if (state.getA().equals("01") || !state.getD().equals("00")) { // 不是有亮度的情况下，
                command.setD("00");
            } else {
                if (!TextUtils.isEmpty(state.getD())  &&  !state.getD().equals("00")) {
                    command.setD(state.getD());
                }else {
                    command.setD("09");
                }
            }
        } else {
            command.setType("01");
            if (state.getA().equals("00")) {
                command.setA("01");
            } else {
                command.setA("00");
            }
        }
        command.setN(smartPart.getCode());
        SmartPartCMD smartPartCMD = new SmartPartCMD();
        smartPartCMD.setCommand(command);
        smartPartCMD.setPartId(smartPart.getId());
        smartPartCMD.setHouseId(getHouseId());
        TcpClient.getInstacne().sendCMD(activity, smartPartCMD, new TCPCMDResult() {
            @Override
            public void onSuccess(String result) {
                sendResult[0] = true;
            }

            @Override
            public void onFail(String msg) {
                sendResult[0] = false;
            }
        });
        return sendResult[0];
    }

    /*****窗帘控制部分****/
    public static boolean openCU(BaseActivity activity, SmartPart smartPart,String type) {
        context = activity;
        final boolean[] sendResult = {false};

        final Command command = new Command();
        final SmartPartState state = smartPart.getState();

        if (type.equals("opening")) {
            command.setA("01");
        }else if (type.equals("closeding")){
            command.setA("00");
        }else {
            command.setA("02");
        }
        command.setType("01");
        command.setN(smartPart.getCode());
        SmartPartCMD smartPartCMD = new SmartPartCMD();
        smartPartCMD.setCommand(command);
        smartPartCMD.setPartId(smartPart.getId());
        smartPartCMD.setHouseId(getHouseId());
        TcpClient.getInstacne().sendCMD(activity, smartPartCMD, new TCPCMDResult() {
            @Override
            public void onSuccess(String result) {
                sendResult[0] = true;
            }

            @Override
            public void onFail(String msg) {
                sendResult[0] = false;
            }
        });
        return sendResult[0];
    }


    public static boolean openExperienceDevice(BaseActivity activity, SmartPart smartPart) {
        context = activity;
        final boolean[] sendResult = {false};
        final SmartPartState state = smartPart.getState();
        //智能部件状态改变通知
        EventBusUIMessage message = new EventBusUIMessage();
        message.setMsgType(Msg.SAMRT_PART_STATE_CHANGE);
        SmartPartStateTCP smartPartStateTCP = new SmartPartStateTCP();
        smartPartStateTCP.setDevice(APP.DeviceId);
        smartPartStateTCP.setResult("1111"); // 1111是部件状态改变
        final Command command = new Command();
        command.setType("01");
        if (state.getA().equals("00")) {
            command.setA("01");
        } else {
            command.setA("00");
        }
        command.setN(smartPart.getCode());
        command.setT(smartPart.getState().getT() + "");
        command.setD(smartPart.getState().getD());
        command.setRgb(smartPart.getState().getRgb());
        command.setM(smartPart.getState().getM());
        command.setPm(smartPart.getState().getPm());
        command.setRt(smartPart.getState().getRt());

        smartPartStateTCP.setCommand(command);
        message.setData(smartPartStateTCP);
        EventBus.getDefault().post(message);

        return sendResult[0];
    }


    public static String getHouseId() {
        return (String) SharedPreferencesUtils.getParam(context, "houseId", "");
    }

}
