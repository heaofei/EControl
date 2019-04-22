package com.mxkj.econtrol.base;

import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.app.Msg;
import com.mxkj.econtrol.bean.EventBusAsyncMsg;
import com.mxkj.econtrol.bean.EventBusBackgroudMsg;
import com.mxkj.econtrol.bean.EventBusMessage;
import com.mxkj.econtrol.bean.EventBusPostingMsg;
import com.mxkj.econtrol.bean.EventBusUIMessage;
import com.mxkj.econtrol.bean.request.ReqAppPushMessageReply;
import com.mxkj.econtrol.bean.request.ReqGetAppPushMessageList;
import com.mxkj.econtrol.bean.request.ReqHouseManagerSwitchHandle;
import com.mxkj.econtrol.bean.response.ResAppPushMessageReply;
import com.mxkj.econtrol.bean.response.ResGetAppPushMessageList;
import com.mxkj.econtrol.bean.response.TcpResHouseRespones;
import com.mxkj.econtrol.bean.response.TcpResLockAlerts;
import com.mxkj.econtrol.bean.tcpcmd.HeartBeatCMD;
import com.mxkj.econtrol.net.APISubcriber;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;
import com.mxkj.econtrol.net.TcpClient;
import com.mxkj.econtrol.utils.AbToastUtil;
import com.mxkj.econtrol.utils.DateTimeUtil;
import com.mxkj.econtrol.utils.LogUtil;
import com.mxkj.econtrol.utils.MyDialogUtil;
import com.mxkj.econtrol.utils.SharedPreferencesUtils;
import com.mxkj.econtrol.utils.StatusBarUtils;
import com.mxkj.econtrol.view.activity.ApplyBindHouseListActivity;
import com.mxkj.econtrol.view.activity.LoginActivity;
import com.mxkj.econtrol.view.activity.MessageListActivity;
import com.mxkj.econtrol.widget.LoadingDialog;
import com.mxkj.econtrol.widget.TipDialog;
import com.umeng.message.PushAgent;
import com.zhy.autolayout.AutoLayoutActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mx1 on 2017/3/21.
 * activity基类
 */

public abstract class BaseActivity extends AutoLayoutActivity implements View.OnClickListener {

    //当前activity是否位于前台
    protected boolean isForeground = false;
    protected View contentView;
    protected LoadingDialog mLoadView;
    //收到关闭activity消息时是否关闭
    protected boolean isClose = true;

    //保存tcp命令
    protected Map<String, TCPCMDResult> mTcpCmdResultMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        APP.activities.add(this);
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

        setStatusBar(true,R.drawable.gradient_background );// 设置状态栏

        PushAgent.getInstance(this).onAppStart();
    }


    /**
     * 设置状态栏状态栏
     * @return
     */
    public void setStatusBar(boolean isShow, int drawable) {
        if (isShow){
        StatusBarUtils.with(this)
                .setIsActionBar(false)
                .clearActionBarShadow()
                .setDrawable(getResources().getDrawable(drawable))
                .init();
        }
    }

    /**
     * 根据Id查找view
     *
     * @param resId
     * @param <T>
     * @return
     */
    protected <T extends View> T findView(int resId) {
        return (T) super.findViewById(resId);
    }

    //初始化view
    protected abstract void initView();

    //初始化数据
    protected abstract void initData();

    //初始化监听器
    protected abstract void initListener();

    //显示toast，默认是短时间显示
    protected void showToast(String message) {
        AbToastUtil.showToast(this,message);
//        showToast(message, Toast.LENGTH_SHORT);
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
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        isForeground = false;
        if (mTcpCmdResultMap != null) {
            mTcpCmdResultMap.clear();
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        APP.activities.remove(this);
        if (APP.activities.size() == 0) {
//            最后一个activity
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
        LogUtil.e(this.getClass().getSimpleName() + "接受到消息：" + msg.getMsgType());
        switch (msg.getMsgType()) {
            case Msg.SHOW_LOADING:
                showLoading();
                break;
            case Msg.DISMISS_LOADING:
                dismissLoading();
                break;
            case Msg.SAMRT_PART_STATE_CHANGE:
                LogUtil.i("state:" + msg.getData());
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
                //----------------------------------------------------------------------
            case Msg.TCP_CMD_SUCCESS: // tcp发送的命令执行成功
                BaseTCPCMDResponse response = (BaseTCPCMDResponse) msg.getData();
                if (mTcpCmdResultMap != null && mTcpCmdResultMap.containsKey(response.getSerial())) {
                    mTcpCmdResultMap.get(response.getSerial()).onSuccess(response.getData());
                    mTcpCmdResultMap.remove(response.getSerial());
                }
                LogUtil.i("success:" + (String) response.getData());
                break;
            case Msg.TCP_CMD_FAIL:  // tcp发送的命令执行失败
                BaseTCPCMDResponse result = (BaseTCPCMDResponse) msg.getData();
                if (mTcpCmdResultMap != null && mTcpCmdResultMap.containsKey(result.getSerial())) {
                    mTcpCmdResultMap.get(result.getSerial()).onFail(result.getMsg());
                    mTcpCmdResultMap.remove(result.getSerial());
                }
                LogUtil.i("Fail:" + (String) result.getData());
                break;
            case Msg.CLEAR_TCP_CMD_LISENTER:  // tcp发送的命令清除
                if (mTcpCmdResultMap != null) {
                    mTcpCmdResultMap.clear();
                }
                break;
            //---------------------------------------------------------------------------
            case Msg.CHECK_RESULT:
               /* if (isForeground) {
                    showTipsDialog(response.getMsg());
                    BaseTCPCMDResponse response = (BaseTCPCMDResponse) msg.getData();
                    sendNofity(getString(R.string.requet_check_result), response.getMsg());
                }*/
                break;
            case Msg.CHECK_EXAMINE: // 业主收到成员的审核消息
                //在FragmentMy 的 handleMessage 接收 CHECK_EXAMINE 消息处理，不在这里统一处理
              /*  if (isForeground) {
                    TcpResHouseRespones response = (TcpResHouseRespones) msg.getData();
                    sendNofity("消息：", response.getMsg());
                    showCheckDialog(response.getMsg(),response.getExtraData().getHouseId());
                }*/
                break;
            case Msg.MY_HOUSE_CHANGE:   // 房子信息发送变化
                if (isForeground) {
                    BaseTCPCMDResponse response1 = (BaseTCPCMDResponse) msg.getData();
                    sendNofity("消息：", response1.getMsg());
                    showTipsDialog(response1.getMsg());
                }
                break;
            case Msg.HOUSE_MANAGER_SWITCH_ASSIGN:   //旧业主转让指管理权给新业主后，新业主接收处理
                if (isForeground) {
                    TcpResHouseRespones response2 = (TcpResHouseRespones) msg.getData();
                    houseManagerSwitchAssignDialog(response2.getMsg(),response2.getExtraData().getHouseId());
                }
                break;
            case Msg.HOUSE_MANAGER_SWITCH_HANDLE:   //业主更换后通知成员
                if (isForeground) {
                    BaseTCPCMDResponse response1 = (BaseTCPCMDResponse) msg.getData();
                    showTipsDialog(response1.getMsg());
                }
                break;
            case Msg.EVENBUS_LOCK_ALERTS:   //门锁警报消息，在所有界面都可以收到这个警报弹窗
                if (isForeground) {
                    TcpResLockAlerts data = (TcpResLockAlerts) msg.getData();
                    if (data.getCommand().getAlarmCode().equals("04")) { // 防拆报警
                        showAlertsDialog("防拆报警");
                    }else if (data.getCommand().getAlarmCode().equals("05")){ // 未关锁报警

                    }else if (data.getCommand().getAlarmCode().equals("06")){ // 胁迫报警

                    }else if (data.getCommand().getAlarmCode().equals("07")){ // 假锁报警

                    }else if (data.getCommand().getAlarmCode().equals("33")){ // 非法操作报警
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
//        if (myDialogUtil == null) {
            myDialogUtil = new MyDialogUtil(this) {
                @Override
                public void confirm() {
                }
            };
            myDialogUtil.showLockAlertTipDialog(alerts,"知道了");
       /* } else {
            myDialogUtil.showLockAlertTipDialog(alerts,"知道了");
        }*/
    }

    public  void showTipsDialog(String content){
        MyDialogUtil dialog = new MyDialogUtil(this) {
            @Override
            public void confirm() {

            }
        };
        dialog.showConfirmTipDialog("提示",content);
    }

    /**
     * 收到成员审核消息
     * @param content
     */
    public  void showCheckDialog(String content, final String houseId){
        MyDialogUtil dialog = new MyDialogUtil(this) {
            @Override
            public void confirm() {
                Intent intent  = new Intent(BaseActivity.this,ApplyBindHouseListActivity.class);
                intent.putExtra("type","MESSAGE");
                intent.putExtra("houseId",houseId);
                startActivity(intent);
            }
        };
        dialog.showPerfectTipDialog("提示",content,"取消","查看");
    }

     /**
     * 收到房子转让处理消息
     * @param content
     */
     private Dialog dialog;
    public  void houseManagerSwitchAssignDialog(String content, final String houseId){

        if (dialog==null) {
            dialog = new Dialog(this, R.style.Alert_Dialog_Style);
            TextView tv_title;
            TextView tv_content;
            TextView tv_confirm = null;
            TextView tv_cancel;
            View view = null;

            view = LayoutInflater.from(this).inflate(R.layout.dialog_sure_again, null);
            tv_title = (TextView) view.findViewById(R.id.tv_title);
            tv_content = (TextView) view.findViewById(R.id.tv_content);
            tv_confirm = (TextView) view.findViewById(R.id.tv_confirm);
            tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);

            tv_title.setText("提示");
            tv_content.setText(content);
            tv_cancel.setText("拒绝");
            tv_confirm.setText("同意");
            tv_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    houseManagerSwitchAssign(houseId,"0"); // accepted 0-拒绝，1-同意
                    dialog.dismiss();
                }
            });
            tv_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    houseManagerSwitchAssign(houseId,"1"); // accepted 0-拒绝，1-同意
                    dialog.dismiss();
                }
            });
            dialog.setContentView(view, new ViewGroup.LayoutParams(/*(int) (TheApp.screenWidth * 0.8),// 设置为屏幕宽度的0.9*/
                    (int) (APP.screenWidth * 0.7),
                    android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT));//设置为屏幕高度的0.8
            // 设置显示宽高
            dialog.show();
            // 设置点击外围解散
            dialog.setCanceledOnTouchOutside(false);
        }else {
            dialog.show();
        }

    }

    // 请求处理业主确认转让权限
    private void houseManagerSwitchAssign(String houseId, String accepted) {
        final ReqHouseManagerSwitchHandle request = new ReqHouseManagerSwitchHandle();
        request.setHouseId(houseId);
        request.setAccepted(accepted);
        RetrofitUtil.getInstance().getmApiService()
                .houseManagerSwitchHandle(request.toJsonStr())
                .compose(new APITransFormer<BaseResponse>())
                .subscribe(new APISubcriber<BaseResponse>() {
                    @Override
                    public void onFail(BaseResponse baseResponse,String msg) {
                        showToast(msg);
                    }
                    @Override
                    public void onSuccess(BaseResponse response) {
//                        String content = new Gson().toJson(response);
//                        showToast(response.getMsg());
                    }
                });
    }



    protected void sendNofity(String title, String msg) {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notify = new Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_no_head)
                .setContentTitle(title)
                .setContentText(msg).getNotification();
        //      .setContentIntent(pendingIntent).setNumber(1).getNotification();
        notify.flags = Notification.FLAG_AUTO_CANCEL; // FL
        notify.defaults = Notification.DEFAULT_ALL;
        manager.notify(1, notify);
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
                SharedPreferencesUtils.setParam(BaseActivity.this, "token", "");
                SharedPreferencesUtils.setParam(BaseActivity.this, "userName", "");
                APP.user = null;
                startToActivity(LoginActivity.class);
                finish();
            }
        };

        SimpleDateFormat sDateFormat  =   new    SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String    date    =    sDateFormat.format(new  java.util.Date());
        dialogUtil.showConfirmTipDialog(getString(R.string.logon_failure_tip02),getString(R.string.logon_failure_tip1)+date+getString(R.string.logon_failure_tip));
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

    @Override
    public void finish() {
        super.finish();
        //  overridePendingTransition(android.R.anim.slide_in_left, R.anim.activity_out);
    }

    public boolean onTouchEvent(MotionEvent event) {
        if(null != this.getCurrentFocus()){
            /**
             * 点击空白位置 隐藏软键盘
             */
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super .onTouchEvent(event);
    }


    public Map getResultMap() {
        if (mTcpCmdResultMap == null) {
            mTcpCmdResultMap = new HashMap<>();
        }
        return mTcpCmdResultMap;
    }
}
