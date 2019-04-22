package com.mxkj.econtrol.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.base.BaseFragment;
import com.mxkj.econtrol.base.TCPCMDResult;
import com.mxkj.econtrol.bean.tcpcmd.Command;
import com.mxkj.econtrol.bean.tcpcmd.SmartPartCMD;
import com.mxkj.econtrol.net.TcpClient;


/**
 * Created by chan on 2018/3/21.
 * <p>
 * 测试使用
 *
 * @Description:
 */

public class TestFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        super.createView(view);
        return contentView;
    }

    @Override
    public void initView() {




    }

    @Override
    public void initData() {
    }


    @Override
    public void initListener() {

    }

    private void openDivice() {
       /* final Command command = new Command();
        command.setType("01");
        command.setA("01");
        command.setN("LI001");
        SmartPartCMD smartPartCMD = new SmartPartCMD();
        smartPartCMD.setCommand(command);
//        smartPartCMD.setDevice(mDevice);
        TcpClient.getInstacne().sendCMD(this, smartPartCMD, new TCPCMDResult() {
            @Override
            public void onSuccess(String result) {
                showToast(result);
            }

            @Override
            public void onFail(String msg) {
                showToast(msg);
            }
        });*/


        final Command command = new Command();
        command.setType("01");
        command.setN("LK001");
        command.setLt("01");
        command.setToken("7A66DE35E22936321FAB664022235E140659338E9232DDCC7D6752166A52A067");
        SmartPartCMD smartPartCMD = new SmartPartCMD();
        smartPartCMD.setCommand(command);
//        smartPartCMD.setDevice(mDevice);
        TcpClient.getInstacne().sendCMD(this, smartPartCMD, new TCPCMDResult() {
            @Override
            public void onSuccess(String result) {
                showToast(result);
            }

            @Override
            public void onFail(String msg) {
                showToast(msg);
            }
        });
    }


}
