package com.mxkj.econtrol.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.app.Msg;
import com.mxkj.econtrol.bean.EventBusAsyncMsg;
import com.mxkj.econtrol.bean.EventBusBackgroudMsg;
import com.mxkj.econtrol.bean.EventBusMessage;
import com.mxkj.econtrol.bean.EventBusPostingMsg;
import com.mxkj.econtrol.bean.EventBusUIMessage;
import com.mxkj.econtrol.bean.response.TcpResLockAlerts;
import com.mxkj.econtrol.net.TcpClient;
import com.mxkj.econtrol.ui.activity.SettingActivity;
import com.mxkj.econtrol.utils.LogUtil;
import com.mxkj.econtrol.utils.MyDialogUtil;
import com.mxkj.econtrol.utils.SharedPreferencesUtils;
import com.mxkj.econtrol.view.activity.LoginActivity;
import com.mxkj.econtrol.widget.LoadingDialog;
import com.mxkj.econtrol.widget.TipDialog;
import com.zhy.autolayout.AutoLayoutActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;

/**
 * Created by mx1 on 2017/3/21.
 * activity基类
 */

public abstract class BaseFragmentActivity extends AutoLayoutActivity implements View.OnClickListener {

    //当前activity是否位于前台
    protected boolean isForeground = false;
    protected View contentView;
    protected LoadingDialog mLoadView;
    protected boolean isClose = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

//        if (Build.VERSION.SDK_INT >= 21) {
//            View decorView = getWindow().getDecorView();
//            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            decorView.setSystemUiVisibility(option);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }
        EventBus.getDefault().register(this);
        initView();
        initData();
        initListener();
        contentView = getWindow().getDecorView().findViewById(android.R.id.content);
        mLoadView = new LoadingDialog(this);

    }

    protected <T extends View> T findView(int id) {
        return (T) findViewById(id);
    }

    //初始化view
    protected abstract void initView();

    //初始化数据
    protected abstract void initData();

    //初始化监听器
    protected abstract void initListener();

    //显示toast，默认是短时间显示
    protected void showToast(String message) {
        showToast(message, Toast.LENGTH_SHORT);
    }

    //显示toast
    protected void showToast(String message, int time) {
        Toast.makeText(this, message, time).show();
    }


    /**
     * 统一跳转Activity
     *
     * @param intent
     */
    protected void startToActivity(Intent intent) {
        startActivity(intent);
        // 第一个参数是目标Activity进入时的动画，第二个参数是当前Activity退出时的动画
//        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    /**
     * 统一跳转Activity
     *
     * @param cls
     */
    protected void startToActivity(Class cls) {
        startToActivity(new Intent(this, cls));
    }

    @Override
    protected void onResume() {
        super.onResume();
        isForeground = true;
        boolean isconnect = TcpClient.isConnecting;
        if (!TcpClient.isConnecting) {
//            TcpClient.getInstacne().init();
            LogUtil.i("===tcp=开始连接=");
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        isForeground = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        APP.activities.remove(this);
        if (APP.activities.size() == 0) {
            //是最后一个activity
            TcpClient.getInstacne().close();
            System.exit(0);
        }

    }

    @Override
    public void onClick(View v) {

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
            case Msg.SHOW_LOADING:
                showLoading();
                break;
            case Msg.DISMISS_LOADING:
                dismissLoading();
                break;
            case Msg.CLEAR_ACITVITY:
                if (isClose)
                    finish();
                break;
            case Msg.LOGON_FAILURE:
                //登录失效通知
                if (isForeground)
                    showLoginTip();
                TcpClient.getInstacne().close();
                break;

            case Msg.EVENBUS_LOCK_ALERTS:   //门锁警报消息，在所有界面都可以收到这个警报弹窗
                if (isForeground) {
                    TcpResLockAlerts data = (TcpResLockAlerts) msg.getData();
                    if (data.getCommand().getAlarmCode().equals("04")) { // 防拆报警
                        showAlertsDialog("防拆报警");
                    } else if (data.getCommand().getAlarmCode().equals("05")) { // 未关锁报警

                    } else if (data.getCommand().getAlarmCode().equals("06")) { // 胁迫报警

                    } else if (data.getCommand().getAlarmCode().equals("07")) { // 假锁报警

                    } else if (data.getCommand().getAlarmCode().equals("33")) { // 非法操作报警
                        showAlertsDialog("非法操作报警");
                    }
                }
                break;
            default:
                break;
        }
    }

    MyDialogUtil myDialogUtil;
    /**
     * 显示警报对话框
     */
    private void showAlertsDialog(String alerts) {
        if (myDialogUtil == null) {
            myDialogUtil = new MyDialogUtil(this) {
                @Override
                public void confirm() {
                }
            };
            myDialogUtil.showLockAlertTipDialog(alerts,"知道了");
        } else {
            myDialogUtil.showLockAlertTipDialog(alerts,"知道了");
        }
    }

    /**
     * 显示加载中
     */
    public void showLoading() {
        if (mLoadView != null && isForeground) {
            mLoadView.showProgressDialog("加载中");
        }
    }

    /**
     * 取消加载中
     */
    public void dismissLoading() {
        if (mLoadView != null) {
            mLoadView.dialogDismiss();
        }
    }

    /**
     * @Desc: 登录失效时提示款
     * @author:liangshan
     */
    protected void showLoginTip() {
        MyDialogUtil dialogUtil = new MyDialogUtil(this) {
            @Override
            public void confirm() {
                isClose = false;
                EventBus.getDefault().post(new EventBusUIMessage(Msg.CLEAR_ACITVITY));
                SharedPreferencesUtils.setParam(BaseFragmentActivity.this, "token", "");
                SharedPreferencesUtils.setParam(BaseFragmentActivity.this, "userName", "");
                APP.user = null;
                startToActivity(LoginActivity.class);
                finish();
            }
        };
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String date = sDateFormat.format(new java.util.Date());
        dialogUtil.showConfirmTipDialog(getString(R.string.logon_failure_tip02), getString(R.string.logon_failure_tip1) + date + getString(R.string.logon_failure_tip));
    }

    @Override
    public void finish() {
        super.finish();
//        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}
