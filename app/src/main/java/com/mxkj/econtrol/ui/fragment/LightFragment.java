package com.mxkj.econtrol.ui.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.Msg;
import com.mxkj.econtrol.base.BaseFragment;
import com.mxkj.econtrol.base.TCPCMDResult;
import com.mxkj.econtrol.bean.EventBusMessage;
import com.mxkj.econtrol.bean.EventBusUIMessage;
import com.mxkj.econtrol.bean.response.SmartPart;
import com.mxkj.econtrol.bean.response.SmartPartState;
import com.mxkj.econtrol.bean.tcpcmd.Command;
import com.mxkj.econtrol.bean.tcpcmd.SmartPartCMD;
import com.mxkj.econtrol.bean.tcpcmd.SmartPartStateTCP;
import com.mxkj.econtrol.net.TcpClient;
import com.mxkj.econtrol.ui.adapter.LightSwitchAdapter;
import com.mxkj.econtrol.utils.LogUtil;
import com.mxkj.econtrol.view.activity.LightSettingActivity;


import java.util.List;


/**
 * Created by chan on 2018/4/18
 *
 * @Description:
 */

public class LightFragment extends BaseFragment {
    private RecyclerView mRecyclerViewLight;
    private LightSwitchAdapter mAdapter;
    private List<SmartPart> mData;
    private String mDevice; //中控设备
    private int mPosition; //操作的位置

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_light, container, false);
        super.createView(view);
        return contentView;
    }

    @Override
    public void initView() {
        mRecyclerViewLight = findView(R.id.recycle_light);
    }

    @Override
    public void initData() {
        Bundle bundle = getArguments();
        mData = (List<SmartPart>) bundle.getSerializable("data");
        mDevice = bundle.getString("device");
//        mRecyclerViewLight.setLayoutManager(new GridLayoutManager(getActivity(), 1));
      /*  LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);*/
        mRecyclerViewLight.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new LightSwitchAdapter(mData, R.layout.layout_light_switch_item, this);
        mRecyclerViewLight.setAdapter(mAdapter);
    }


    @Override
    public void initListener() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void handleMessage(EventBusMessage msg) {
        super.handleMessage(msg);
        switch (msg.getMsgType()) {
            case Msg.SAMRT_PART_STATE_CHANGE:
                break;
        }
    }

/*    @Override
    public void onItemClick(View view, int position) {
        if (true) {  // 灯光编辑
            Intent intent = new Intent(getActivity(), LightSettingActivity.class);
            intent.putExtra("lightName", mData.get(position).getName());
            intent.putExtra("deviceId", mData.get(position).getId());
            startActivityForResult(intent, 1);
            mPosition = position;
//            mAdapter.upDateAdapter(mData, false); // 跳转后取消编辑状态
//            isShowEdit= false;                    // 跳转后取消编辑状态
//            EventBus.getDefault().post(new EventBusUIMessage(Msg.CLEAR_ACITVITY));
        } else {           // 开关操作
            final Command command = new Command();
            command.setType("01");
            SmartPart smartPart = mData.get(position);
            final SmartPartState state = smartPart.getState();
            if (state.getA().equals("00")) {
                command.setA("01");
            } else {
                command.setA("00");
            }
            command.setN(smartPart.getCode());
            SmartPartCMD smartPartCMD = new SmartPartCMD();
            smartPartCMD.setCommand(command);
            smartPartCMD.setDevice(mDevice);
            TcpClient.getInstacne().sendCMD(this, smartPartCMD, new TCPCMDResult() {
                @Override
                public void onSuccess(String result) {
                    if (state.getA().equals("00")) {
                        state.setA("01");
                    } else {
                        state.setA("00");
                    }
                    mAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFail(String msg) {
                    showToast(msg);
                }
            });
        }
    }*/

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.rl_item:
                SmartPart smartPart = (SmartPart) v.getTag();
                mPosition = smartPart.getPosition();
                Intent intent = new Intent(getActivity(), LightSettingActivity.class);
                intent.putExtra("deviceId", smartPart.getId());
                intent.putExtra("smartPart", smartPart); // 把部件信息传过去
                startActivityForResult(intent, 1);
                break;
            case R.id.iv_open:
                SmartPart smartPart1 = (SmartPart) v.getTag();
                openLight(smartPart1);
                break;
            default:
                break;
        }
    }

    private void openLight(SmartPart smartPart) {
        final Command command = new Command();
        command.setType("01");
        final SmartPartState state = smartPart.getState();
        if (state.getA().equals("00")) {
            command.setA("01");
        } else {
            command.setA("00");
        }
        command.setN(smartPart.getCode());
        SmartPartCMD smartPartCMD = new SmartPartCMD();
        smartPartCMD.setCommand(command);
        smartPartCMD.setDevice(mDevice);
        TcpClient.getInstacne().sendCMD(this, smartPartCMD, new TCPCMDResult() {
            @Override
            public void onSuccess(String result) {
                if (state.getA().equals("00")) {
                    state.setA("01");
                } else {
                    state.setA("00");
                }
                mAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFail(String msg) {
                showToast(msg);
            }
        });
    }


    private void openDimming(final Command command) {
        SmartPartCMD smartPartCMD = new SmartPartCMD();
        smartPartCMD.setCommand(command);
        smartPartCMD.setDevice(mDevice);
        TcpClient.getInstacne().sendCMD(this, smartPartCMD, new TCPCMDResult() {
            @Override
            public void onSuccess(String result) {
               /* if (state.getA().equals("00")) {
                    state.setA("01");
                } else {
                    state.setA("00");
                }
                mAdapter.notifyDataSetChanged();*/
//               showToast("===="+result);
            }

            @Override
            public void onFail(String msg) {
                showToast(msg);
            }
        });
    }

    @Override
    public void HandleUIMessage(EventBusUIMessage msg) {
        super.HandleUIMessage(msg);
        switch (msg.getMsgType()) {
            case Msg.SAMRT_PART_STATE_CHANGE:
                LogUtil.i("state:" + msg.getData());
                lightStateChange((SmartPartStateTCP) msg.getData());
                break;
            case Msg.ACTIVITY_LIGHT_CHANGE:
                LogUtil.i("state:" + msg.getData());
                openDimming((Command) msg.getData());
                break;
        }
    }

    public void lightStateChange(SmartPartStateTCP state) {
        if (mData == null || !mDevice.equals(state.getDevice())) {
            //当中控设备不是当前房子的中控设备
            return;
        }
        Command command = state.getCommand();
        for (SmartPart sm : mData) {
            //找出状态改变的部件
            if (sm.getCode().equals(command.getN())) {
                //如果状态和现在的不一样
                if (command.getA() != null && !command.getA().equals(sm.getState().getA())) {
                    sm.getState().setA(command.getA());
                    mAdapter.notifyDataSetChanged();
                }
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && null != data) {
            String lightName = data.getStringExtra("lightName");
            mData.get(mPosition).setName(lightName);
            mAdapter.upDateAdapter(mData);
        }
    }

}
