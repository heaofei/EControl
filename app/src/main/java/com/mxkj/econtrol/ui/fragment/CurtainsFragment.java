package com.mxkj.econtrol.ui.fragment;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.app.Msg;
import com.mxkj.econtrol.base.BaseFragment;
import com.mxkj.econtrol.base.CommonAdapter;
import com.mxkj.econtrol.base.TCPCMDResult;
import com.mxkj.econtrol.bean.EventBusMessage;
import com.mxkj.econtrol.bean.EventBusUIMessage;
import com.mxkj.econtrol.bean.response.SmartPart;
import com.mxkj.econtrol.bean.response.SmartPartState;
import com.mxkj.econtrol.bean.tcpcmd.Command;
import com.mxkj.econtrol.bean.tcpcmd.SmartPartCMD;
import com.mxkj.econtrol.net.TcpClient;

import java.io.IOException;
import java.util.List;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;


/**
 * Created by chanjun on 2017/9/13.
 *
 * @Description: 窗帘控制
 */

public class CurtainsFragment extends BaseFragment  {

    private SmartPart mBlower;
    private String mPartId;

    private Button bt_bt_open,bt_bt_close,bt_bt_stop;
    private ImageView iv_tips;
    private GifImageView iv_gifView;
    SmartPartCMD smartPartCMD = new SmartPartCMD();
    Command command = new Command();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_curtains, container, false);
        super.createView(view);
        return contentView;
    }

    @Override
    public void initView() {
        bt_bt_open = findView(R.id.bt_bt_open);
        bt_bt_close = findView(R.id.bt_bt_close);
        bt_bt_stop = findView(R.id.bt_bt_stop);
        iv_tips = findView(R.id.iv_tips);
        iv_gifView = findView(R.id.iv_gifView);

    }

    @Override
    public void initData() {
        mBlower = (SmartPart) getArguments().getSerializable("smartPart");
        mPartId = getArguments().getString("device");
        SmartPartState state = mBlower.getState();
        if (state.getA().equals("01")) {                  // 开状态
        } else if (state.getA().equals("00")) {          // 关状态
        }else if (state.getA().equals("02")) {          // 停状态
        }

    }
    @Override
    public void initListener() {
        bt_bt_open.setOnClickListener(this);
        bt_bt_close.setOnClickListener(this);
        bt_bt_stop.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.bt_bt_open:
                setGif("01");
                sendCMD("01"); // 打开窗帘
                break;
            case R.id.bt_bt_close:
                setGif("00");
                sendCMD("00"); // 关闭窗帘
                break;
            case R.id.bt_bt_stop:
                setGif("02");
                sendCMD("02"); // 停止窗帘


                break;

        }
    }

    private void sendCMD(String type){
        /***
         * 开：{"type":"01","n":"CU001","a":"01","serial":"121309575460"}
         关：{"type":"01","n":"CU001","a":"00","serial":"121309575460"}
         停：{"type":"01","n":"CU001","a":"02","serial":"121309575460"}
         */
        command.setN(mBlower.getCode());
        command.setType("01");

        switch (type){
            case "01":
        command.setA("01");
                break;
            case "02":
        command.setA("02");
                break;
            case "00":
        command.setA("00");
                break;
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

    /**
     * 设置窗帘gif
     * （因为窗帘电机没有记录刻度，暂时只能用gif）
     * @param gif
     */
    public void setGif(String gif) {
        switch (gif){
            case "01":      // 开
                iv_gifView.setVisibility(View.VISIBLE);
                iv_tips.setVisibility(View.VISIBLE);
                iv_tips.setImageResource(R.drawable.ic_cu_opening);
        try {
            GifDrawable gifDrawable = new GifDrawable(getResources(), R.drawable.ic_cu_open);
            iv_gifView.setImageDrawable(gifDrawable);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
                break;
            case "00":      // 关
                iv_gifView.setVisibility(View.VISIBLE);
                iv_tips.setVisibility(View.VISIBLE);
                iv_tips.setImageResource(R.drawable.ic_clclosing);

                try {
                    GifDrawable gifDrawable = new GifDrawable(getResources(), R.drawable.ic_cu_close);
                    iv_gifView.setImageDrawable(gifDrawable);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                break;
            case "02":     // 停
                iv_gifView.setVisibility(View.GONE);
                iv_tips.setImageResource(R.drawable.ic_cu_stop);
                iv_tips.setVisibility(View.INVISIBLE);
                break;
        }

    }

}
