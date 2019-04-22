package com.mxkj.econtrol.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.mxkj.econtrol.app.Msg;
import com.mxkj.econtrol.bean.EventBusAsyncMsg;
import com.mxkj.econtrol.bean.EventBusBackgroudMsg;
import com.mxkj.econtrol.bean.EventBusMessage;
import com.mxkj.econtrol.bean.EventBusPostingMsg;
import com.mxkj.econtrol.bean.EventBusUIMessage;
import com.mxkj.econtrol.utils.LogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liangshan on 2017/4/12.
 *
 * @Description:
 */

public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    protected View contentView;
    //保存tcp命令
    protected Map<String, TCPCMDResult> mTcpCmdResultMap;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    public void createView(View view) {
        this.contentView = view;
        initView();
        initData();
        initListener();

    }


    public abstract void initView();

    public abstract void initData();

    public abstract void initListener();

    @Override
    public void onStop() {
        super.onStop();
        if (mTcpCmdResultMap != null) {
            mTcpCmdResultMap.clear();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 根据Id查找view
     *
     * @param resId
     * @param <T>
     * @return
     */
    protected <T extends View> T findView(int resId) {
        return (T) contentView.findViewById(resId);
    }

    /**
     * 处理发往backgroud线程的消息
     *
     * @param msg
     */
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void HandleBGMessage(EventBusBackgroudMsg msg) {
        handleMessage(msg);
    }


    /**
     * 处理发往UI线程的消息
     *
     * @param msg
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void HandleUIMessage(EventBusUIMessage msg) {
        handleMessage(msg);
    }

    /**
     * 处理发往ASYNC线程的消息
     *
     * @param msg
     */
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void HandleASYNCMessage(EventBusAsyncMsg msg) {
        handleMessage(msg);
    }

    /**
     * 处理发往POSTING线程的消息
     *
     * @param msg
     */
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void HandleASYNCMessage(EventBusPostingMsg msg) {
        handleMessage(msg);
    }


    /**
     * 处理eventBus消息
     *
     * @param msg
     */
    protected void handleMessage(EventBusMessage msg) {
        switch (msg.getMsgType()) {
            case Msg.TCP_CMD_SUCCESS:
                BaseTCPCMDResponse response = (BaseTCPCMDResponse) msg.getData();
                if (mTcpCmdResultMap != null && mTcpCmdResultMap.containsKey(response.getSerial())) {
                    mTcpCmdResultMap.get(response.getSerial()).onSuccess(response.getData());
                    mTcpCmdResultMap.remove(response.getSerial());
                }
                LogUtil.i("success:" + (String) response.getData());
                break;
            case Msg.TCP_CMD_FAIL:
                BaseTCPCMDResponse result = (BaseTCPCMDResponse) msg.getData();
                if (mTcpCmdResultMap != null && mTcpCmdResultMap.containsKey(result.getSerial())) {
                    mTcpCmdResultMap.get(result.getSerial()).onFail(result.getMsg());
                    mTcpCmdResultMap.remove(result.getSerial());
                }
                LogUtil.i("Fail:" + (String) result.getData());

                break;
            case Msg.CLEAR_TCP_CMD_LISENTER:
                if (mTcpCmdResultMap != null) {
                    mTcpCmdResultMap.clear();
                }
                break;
            case Msg.SAMRT_PART_STATE_CHANGE:
                LogUtil.i("state:" + msg.getData());
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {

    }

    public Map getResultMap() {
        if (mTcpCmdResultMap == null) {
            mTcpCmdResultMap = new HashMap<>();
        }
        return mTcpCmdResultMap;
    }

    //显示toast，默认是短时间显示
    protected void showToast(String message) {
        showToast(message, Toast.LENGTH_SHORT);
    }

    //显示toast
    protected void showToast(String message, int time) {
        Toast.makeText(getActivity(), message, time).show();
    }
}
