package com.mxkj.econtrol.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.Msg;
import com.mxkj.econtrol.base.BaseFragment;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.base.CommonAdapter;
import com.mxkj.econtrol.base.TCPCMDResult;
import com.mxkj.econtrol.bean.EventBusUIMessage;
import com.mxkj.econtrol.bean.response.SmartPart;
import com.mxkj.econtrol.bean.response.SmartPartState;
import com.mxkj.econtrol.bean.tcpcmd.Command;
import com.mxkj.econtrol.bean.tcpcmd.SmartPartCMD;
import com.mxkj.econtrol.bean.tcpcmd.SmartPartStateTCP;
import com.mxkj.econtrol.contract.AgFragmentContract;
import com.mxkj.econtrol.contract.LightSettingContract;
import com.mxkj.econtrol.model.AgFragmentModel;
import com.mxkj.econtrol.net.TcpClient;
import com.mxkj.econtrol.presenter.AgFragmentPresenter;
import com.mxkj.econtrol.ui.activity.LockAddTipsActivity;
import com.mxkj.econtrol.ui.activity.LockPasswordManageActivity;
import com.mxkj.econtrol.ui.adapter.AGSwitchAdapter;
import com.mxkj.econtrol.ui.adapter.LightSwitchAdapter;
import com.mxkj.econtrol.utils.LogUtil;
import com.mxkj.econtrol.utils.MyDialogUtil;
import com.mxkj.econtrol.view.activity.LightSettingActivity;
import com.mxkj.econtrol.view.activity.LockTemporaryPswActivity;

import java.util.List;


/**
 * Created by chanjun on 2018/1/13.
 *
 * @Description: 雾化玻璃
 */

public class AGFragment extends BaseFragment implements CommonAdapter.OnItemClickListener, AgFragmentContract.View {


    private SmartPart mBlower;
    private String mDevice;
    private List<SmartPart> mData;

    private RecyclerView recyclerView;
    private AGSwitchAdapter mAdapter;
    private int mPosition; //操作的位置
    private String mName; //操作部件名字
    private boolean isShowEdit = false; // 是否编辑状态
    private AgFragmentContract.Presenter mPresenter;
    private CleanEditInterface submit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_ag, container, false);
        super.createView(view);
        return contentView;
    }

    @Override
    public void initView() {

        mBlower = (SmartPart) getArguments().getSerializable("smartPart");
        mData = (List<SmartPart>) getArguments().getSerializable("data");
        mDevice = getArguments().getString("device");


        recyclerView = findView(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mAdapter = new AGSwitchAdapter(mData, R.layout.layout_ag_switch_item);
        recyclerView.setAdapter(mAdapter);

        mPresenter = new AgFragmentPresenter(this,new AgFragmentModel());
    }

    @Override
    public void initData() {


    }

    @Override
    public void initListener() {
        mAdapter.setOnItemClickListener(this);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_add:  // 添加门锁

                final Command command = new Command();
                command.setType("01");
                final SmartPartState state = mBlower.getState();
                if (state.getA().equals("00")) {
                    command.setA("01");
                } else {
                    command.setA("00");
                }
                command.setN(mBlower.getCode());
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
                    }
                    @Override
                    public void onFail(String msg) {
                        showToast(msg);
                    }
                });

                break;
        }
    }
    @Override
    public void onItemClick(View view, int position) {
        if (isShowEdit) {  // 雾化玻璃编辑
            mPosition = position ;
         showEditDialog(position);
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
    }

    private void showEditDialog(final int position) {
        MyDialogUtil dialog = new MyDialogUtil(getActivity()) {
            @Override
            public void confirm() {

            }
        };
        dialog.setInputDialogClicklistener(new MyDialogUtil.showInputDialogOnClickInterface() {
            @Override
            public void doEdit(String content) {
                mName = content;
                mPresenter.scenePartEdit(mData.get(position).getId(),content,"0");
            }
        });
        dialog.showInputDialog("修改设备名称", "输入此设备名称", mData.get(position).getName());
    }


    public void showAgEdit(boolean isShowEdit) {
        mAdapter.upDateAdapter(mData, isShowEdit);
        this.isShowEdit = isShowEdit;
    }

    public boolean getEditType() {
        if (getUserVisibleHint()) { // 用户是否可见
            return isShowEdit;
        }else {
            return false;
        }
    }


    public interface CleanEditInterface {
        public void cleanEdit(boolean  clean );
    }
    @Override
    public void setPresenter(AgFragmentContract.Presenter presenter) {

    }
    @Override
    public void scenePartEditSuccess(BaseResponse baseResponse) {
        isShowEdit = false;
        mData.get(mPosition).setName(mName);
        mAdapter.upDateAdapter(mData,false);

        submit = (CleanEditInterface) getActivity();
        submit.cleanEdit(true);
    }
    @Override
    public void scenePartEditFail(String msg) {
        showToast(msg);
    }



    @Override
    public void HandleUIMessage(EventBusUIMessage msg) {
        super.HandleUIMessage(msg);
        switch (msg.getMsgType()) {
            case Msg.SAMRT_PART_STATE_CHANGE:
                LogUtil.i("state:" + msg.getData());
                lightStateChange((SmartPartStateTCP) msg.getData());
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

}
