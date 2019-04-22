package com.mxkj.econtrol.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.mxkj.econtrol.app.Msg;
import com.mxkj.econtrol.base.BaseFragment;
import com.mxkj.econtrol.base.BaseTCPCMDResponse;
import com.mxkj.econtrol.bean.EventBusAsyncMsg;
import com.mxkj.econtrol.bean.EventBusBackgroudMsg;
import com.mxkj.econtrol.bean.EventBusMessage;
import com.mxkj.econtrol.bean.EventBusPostingMsg;
import com.mxkj.econtrol.bean.EventBusUIMessage;
import com.mxkj.econtrol.utils.AbToastUtil;
import com.mxkj.econtrol.utils.LogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 重写setUserVisibleHint(),实现懒加载
 * @author chanjun
 *
 */
public abstract class BaseMainFragment extends BaseFragment implements View.OnClickListener{
	
	/** Fragment当前状态是否可见 */
	protected boolean isVisible;



	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		EventBus.getDefault().register(this);
	}


	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		
		if(getUserVisibleHint()) {
			isVisible = true;
			onVisible();
		} else {
			isVisible = false;
			onInvisible();
		}
	}
	
	
	/**
	 * 可见
	 */
	protected void onVisible() {
		lazyLoad();		
	}
	
	
	/**
	 * 不可见
	 */
	protected void onInvisible() {
		
		
	}
	
	
	/** 
	 * 延迟加载
	 * 子类必须重写此方法
	 */
	protected abstract void lazyLoad();

	@Override
	public void onClick(View v) {

	}
	//显示toast，默认是短时间显示
	protected void showToast(String message) {
		AbToastUtil.showToast(getActivity(),message);
//		showToast(message, Toast.LENGTH_SHORT);
	}
	//显示toast
	protected void showToast(String message, int time) {
		Toast.makeText(getActivity(), message, time).show();

	}


	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
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

				break;
			case Msg.TCP_CMD_FAIL:

				break;
			case Msg.CLEAR_TCP_CMD_LISENTER:
				break;
			case Msg.SAMRT_PART_STATE_CHANGE:
				LogUtil.i("state:" + msg.getData());
				break;
			default:
				break;
		}
	}

}
