package com.mxkj.econtrol.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.app.Msg;
import com.mxkj.econtrol.base.BaseFragment;
import com.mxkj.econtrol.base.TCPCMDResult;
import com.mxkj.econtrol.bean.EventBusUIMessage;
import com.mxkj.econtrol.bean.response.SmartPart;
import com.mxkj.econtrol.bean.response.SmartPartState;
import com.mxkj.econtrol.bean.tcpcmd.Command;
import com.mxkj.econtrol.bean.tcpcmd.SmartPartCMD;
import com.mxkj.econtrol.bean.tcpcmd.SmartPartStateTCP;
import com.mxkj.econtrol.net.TcpClient;
import com.mxkj.econtrol.utils.LogUtil;

import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;


/**
 * Created by chanjun on 2018/3/30.
 *
 * @Description: 地暖
 */

public class FloorHeatFragment extends BaseFragment {

    private SmartPart mBlower;
    private String mPartId;

    private GifImageView iv_gifView;
    private ImageView iv_temperature_reduce, iv_temperature_add; //温度加减
    private TextView tv_temperature, tv_room_temperature; // 当前温度，房间温度
    private TextView tv_temperature_type;
    private Button bt_bt_open;// 开关
    private View view_title_line;//
    private View view_center_line;//
    private RelativeLayout rl_control;//

    SmartPartCMD smartPartCMD = new SmartPartCMD();
    Command command = new Command();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_floorheat, container, false);
        super.createView(view);
        return contentView;
    }

    @Override
    public void initView() {
        bt_bt_open = findView(R.id.bt_bt_open);
        iv_temperature_reduce = findView(R.id.iv_temperature_reduce);
        iv_temperature_add = findView(R.id.iv_temperature_add);
        tv_temperature = findView(R.id.tv_temperature);
        tv_room_temperature = findView(R.id.tv_room_temperature);
        iv_gifView = findView(R.id.iv_gifView);
        view_title_line = findView(R.id.view_title_line);
        view_center_line = findView(R.id.view_center_line);
        rl_control = findView(R.id.rl_control);
        tv_temperature_type = findView(R.id.tv_temperature_type);

    }

    @Override
    public void initData() {
        mBlower = (SmartPart) getArguments().getSerializable("smartPart");
        mPartId = getArguments().getString("device");
        initSwitch();//初始化地暖状态

    }

    private void initSwitch() {
        //初始化地暖状态
        if (mBlower.getState() != null && mBlower.getState().getA().equals("01")) {// 开状态
            if (mBlower.getFunctions() != null && mBlower.getFunctions().getT() == 1) {  // 有开光功能
                iv_temperature_reduce.setVisibility(View.VISIBLE);
                iv_temperature_add.setVisibility(View.VISIBLE);
                tv_room_temperature.setVisibility(View.VISIBLE);
                tv_temperature_type.setText("当前温度");
            } else {                                                                         // 没有开光功能
                iv_temperature_reduce.setVisibility(View.GONE);
                iv_temperature_add.setVisibility(View.GONE);
                tv_room_temperature.setVisibility(View.GONE);
                tv_temperature_type.setText("当前室温");
            }
            initTemp();
            try {
                GifDrawable gifDrawable = new GifDrawable(getResources(), R.drawable.ic_floorheat_gifbg);
                iv_gifView.setImageDrawable(gifDrawable);
                iv_gifView.setVisibility(View.VISIBLE);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            bt_bt_open.setBackgroundResource(R.drawable.ic_bt_open);
            rl_control.setVisibility(View.VISIBLE);
            view_center_line.setVisibility(View.VISIBLE);
            view_title_line.setVisibility(View.GONE);

        } else {                                                               // 关状态
            //关状态
            bt_bt_open.setBackgroundResource(R.drawable.ic_bt_closed);
            tv_room_temperature.setVisibility(View.INVISIBLE);
            iv_gifView.setVisibility(View.GONE);
            view_center_line.setVisibility(View.VISIBLE);
            view_title_line.setVisibility(View.VISIBLE);
            rl_control.setVisibility(View.GONE);
        }


    }

    //初始化温度
    private void initTemp() {
        if (mBlower.getFunctions() != null && mBlower.getFunctions().getT() == 1) {  // 有开光功能
            if (!TextUtils.isEmpty(mBlower.getState().getRt())) {
                tv_room_temperature.setText("室温" + mBlower.getState().getRt() + "°C");
                tv_temperature.setText("" + mBlower.getState().getT());
            }
        }else {                                                                        // 没有开光功能
            if (!TextUtils.isEmpty(mBlower.getState().getRt())) {
                tv_temperature.setText("室温" + mBlower.getState().getRt() + "°C");
            }
        }
    }

    @Override
    public void initListener() {
        bt_bt_open.setOnClickListener(this);
        iv_temperature_reduce.setOnClickListener(this);
        iv_temperature_add.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.bt_bt_open:
                sendCMD("switch", "");
                break;
            case R.id.iv_temperature_reduce:
                int tem = mBlower.getState().getT();
                if (tem == 0) {
                    tem = 23;
                } else {
                    tem -= 1;
                }
                sendCMD("temperature", tem + "");
                break;
            case R.id.iv_temperature_add:
                int temp = mBlower.getState().getT();
                if (temp == 0) {
                    temp = 23;
                } else {
                    temp += 1;
                }
                sendCMD("temperature", temp + "");
                break;
        }
    }

    private void sendCMD(String type, String temperature) {
        /***
         * 【关】
         {"action":"DEVICE_CONTROL","command":{"type":"01","n":"FH001","a":"00"},"device":"SHZJHFSJ151301","serial":"01oUZc1hT6in",
         "token":"7194FC8E20938234008ED05469AE404D75BCC966EA15E6F50B36E8B82849F5A2","userId":"15766227629"}
         【开】
         {"action":"DEVICE_CONTROL","command":{"type":"01","n":"FH001","a":"01"},"device":"SHZJHFSJ151301","serial":"01oUZc1hT6in",
         "token":"7194FC8E20938234008ED05469AE404D75BCC966EA15E6F50B36E8B82849F5A2","userId":"15766227629"}
         【温度】
         {"action":"DEVICE_CONTROL","command":{"type":"01","n":"FH001","t":"20"},"device":"SHZJHFSJ151301","serial":"01oUZcOss3X4",
         "token":"7194FC8E20938234008ED05469AE404D75BCC966EA15E6F50B36E8B82849F5A2","userId":"15766227629"}
         */
        command.setN(mBlower.getCode());
        command.setType("01");

        if (type.equals("switch")) {     // 调节开关
            if (mBlower.getState() != null && mBlower.getState().getA().equals("01")) {// 开状态
                command.setA("00");
            } else { // 关
                command.setA("01");
            }
        } else {                         // 调节温度
            command.setT(temperature);
        }
        smartPartCMD.setPartId(mPartId);
        smartPartCMD.setCommand(command);

        if (APP.isExperience) { // 体验账号下， 不做实际指令发送
            return;
        }
        TcpClient.getInstacne().sendCMD(this, smartPartCMD, new TCPCMDResult() {
            @Override
            public void onSuccess(String result) {
//                showToast("停止");
            }

            @Override
            public void onFail(String msg) {
                showToast(msg);
            }
        });
    }


    private long exitTime = 0;

    @Override
    public void HandleUIMessage(EventBusUIMessage msg) {
        super.HandleUIMessage(msg);
        switch (msg.getMsgType()) {
            case Msg.SAMRT_PART_STATE_CHANGE:
                LogUtil.i("state:" + msg.getData());
                String contenew = new Gson().toJson((SmartPartStateTCP) msg.getData());
                if ((System.currentTimeMillis() - exitTime) > 2000) {
                    stateChange((SmartPartStateTCP) msg.getData());
                }
                break;
        }
    }

    private void stateChange(SmartPartStateTCP state) {
        /**
         * 地暖反馈：
         【室温】
         {"userId":"15766227629","result":"1111","device":"SHZJHFSJ151301","serial":"01oUZcOss3X4","command":{"type":"00","n":"FH001","rt":"25"}}
         */
        if (!APP.DeviceId.equals(state.getDevice())) {
            //当消息的中控设备不是当前房子的中控设备
            return;
        }
        if (state.getCommand().getN().equals(mBlower.getCode())) {
            Command command = state.getCommand();

            if (command.getRt() != null) {
                mBlower.getState().setRt(command.getRt());
                initTemp();
            }
            if (command.getT() != null) {
                mBlower.getState().setT(Integer.valueOf(command.getT()));
                initTemp();
            }
            if (command.getA() != null) {
                mBlower.getState().setA(command.getA());
                initSwitch();
            }

        }

    }

}
