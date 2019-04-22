package com.mxkj.econtrol.view.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.app.Config;
import com.mxkj.econtrol.app.Msg;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.base.BaseTCPCMDRequest;
import com.mxkj.econtrol.base.BaseTCPCMDResponse;
import com.mxkj.econtrol.base.CommonAdapter;
import com.mxkj.econtrol.base.TCPCMDResult;
import com.mxkj.econtrol.bean.EventBusMessage;
import com.mxkj.econtrol.bean.request.ReqAppPushMessageReply;
import com.mxkj.econtrol.bean.request.ReqGetAppPushMessage;
import com.mxkj.econtrol.bean.response.ResAppPushMessageReply;
import com.mxkj.econtrol.bean.response.ResGetAppPushMessage;
import com.mxkj.econtrol.bean.response.ResGetAtHomeUserList;
import com.mxkj.econtrol.bean.response.ResGetSceneList;
import com.mxkj.econtrol.bean.tcpcmd.Command;
import com.mxkj.econtrol.bean.tcpcmd.MessageNotify;
import com.mxkj.econtrol.bean.tcpcmd.RegistCMD;
import com.mxkj.econtrol.bean.tcpcmd.SmartPartCMD;
import com.mxkj.econtrol.bean.tcpcmd.SmartPartStateTCP;
import com.mxkj.econtrol.contract.HouseMainContract;
import com.mxkj.econtrol.model.HouseMainModel;
import com.mxkj.econtrol.net.APISubcriber;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;
import com.mxkj.econtrol.net.TcpClient;
import com.mxkj.econtrol.presenter.HouseMainPresenter;
import com.mxkj.econtrol.ui.activity.WebViewActivity;
import com.mxkj.econtrol.ui.adapter.MsgUserListAdapter;
import com.mxkj.econtrol.ui.adapter.RoomAdapter;
import com.mxkj.econtrol.utils.LocationUtil;
import com.mxkj.econtrol.utils.LogUtil;
import com.mxkj.econtrol.widget.TipDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by liangshan on 2017/3/31.
 *
 * @Description: 场景主页
 */

public class
HouseMainActivity extends BaseActivity implements HouseMainContract.View, CommonAdapter.OnItemClickListener {

    private RecyclerView rVScenes, mRecycleusers;
    private LinearLayout mLlLeft, mLlRight;
    private HouseMainContract.Presenter mPresenter;
    private List<ResGetAtHomeUserList.AtHomeUser> mUsers = new ArrayList<>();
    private MsgUserListAdapter mUserAdapter;
    private ImageView imvUserPic;
    private TextView tvSceneName;
    private TextView mTvGoHomeModel, mTvLeaveHomeModel;
    private RelativeLayout mRlGoHomeModel, mRlReaveHomeModel;
    private ImageView mImvGoHomeModel, mImvReaveHomeModel;
    private String mThePartUrl, mServiceUrl;
    private ResGetSceneList.House mHouse;
    //保存tcp命令
    private Map<String, TCPCMDResult> mTcpCmdResultMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_scens_main);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        rVScenes = findView(R.id.rv_scenes);
        mLlLeft = findView(R.id.ll_bottom_left);
        mLlRight = findView(R.id.ll_bottom_right);
        mRecycleusers = findView(R.id.recycler_users);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecycleusers.setLayoutManager(layoutManager);
        imvUserPic = findView(R.id.imv_user_pic);
        tvSceneName = findView(R.id.tv_scene_name);
        mTvGoHomeModel = findView(R.id.tv_go_home_model);
        mTvLeaveHomeModel = findView(R.id.tv_leave_home_model);
        mRlGoHomeModel = findView(R.id.rl_go_home);
        mRlReaveHomeModel = findView(R.id.rl_leave_home_model);
        mImvGoHomeModel = findView(R.id.imv_go_home);
        mImvReaveHomeModel = findView(R.id.imv_leave_home);


    }

    @Override
    protected void initData() {
        mPresenter = new HouseMainPresenter(this, new HouseMainModel());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        rVScenes.setLayoutManager(gridLayoutManager);
        if (APP.user == null) {
            startToActivity(LoginActivity.class);
            finish();
        }
        if (!TextUtils.isEmpty(APP.user.getHeadPicture()))
            Glide.with(this).load(Config.BASE_PIC_URL + APP.user.getHeadPicture()).into(imvUserPic);


    }

    @Override
    protected void initListener() {
        mLlLeft.setOnClickListener(this);
        mLlRight.setOnClickListener(this);
        mRlReaveHomeModel.setOnClickListener(this);
        mRlGoHomeModel.setOnClickListener(this);
        imvUserPic.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getSceneList();
        mPresenter.getAtHomeUserList();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ll_bottom_left:
                Intent intent = new Intent(this, WebViewActivity.class);
                intent.putExtra("type", 1);
                intent.putExtra("position", 0);
                intent.putExtra("url", mThePartUrl);
                intent.putExtra("otherUrl", mServiceUrl);
                intent.putExtra("title", "本地链接");
                intent.putExtra("page", tvSceneName.getText());
                startToActivity(intent);
                break;
            case R.id.ll_bottom_right:
                Intent intent0 = new Intent(this, WebViewActivity.class);
                intent0.putExtra("url", mServiceUrl);
                intent0.putExtra("type", 1);
                intent0.putExtra("position", 2);
                intent0.putExtra("otherUrl", mThePartUrl);
                intent0.putExtra("title", "小区物业");
                intent0.putExtra("page", tvSceneName.getText());
                startToActivity(intent0);
                break;
            case R.id.imv_user_pic:
                startToActivity(MainActivity.class);
                finish();
                break;
            case R.id.rl_leave_home_model:
                ResGetSceneList.Model reaveHome = (ResGetSceneList.Model) mRlReaveHomeModel.getTag();
                setModel(reaveHome);
                break;
            case R.id.rl_go_home:
                ResGetSceneList.Model goHomemodel = (ResGetSceneList.Model) mRlGoHomeModel.getTag();
                setModel(goHomemodel);
                break;
        }
    }

    /**
     * @Desc: 设置模式
     * @author:liangshan
     */
    public void setModel(ResGetSceneList.Model model) {
        SmartPartCMD smartPartCMD = new SmartPartCMD();
        Command command = new Command();
        command.setN(model.getCode());
        command.setA(model.getValue());
        smartPartCMD.setDevice(APP.DeviceId);
        smartPartCMD.setCommand(command);
        showLoading();
        TcpClient.getInstacne().sendCMD(this, smartPartCMD, new TCPCMDResult() {
            @Override
            public void onSuccess(String result) {
                dismissLoading();
            }

            @Override
            public void onFail(String msg) {
                showToast(msg);
                dismissLoading();
            }
        });
    }

    /**
     * @Desc: 当模式改变时, 初始化模式图标，文字
     * @author:liangshan
     */
    public void initModelState(SmartPartStateTCP state) {
        if (!APP.DeviceId.equals(state.getDevice())) {
            //当中控设备不是当前房子的中控设备
            return;
        }

        //首先恢复没选中状态
        mTvGoHomeModel.setTextColor(0xfff72971);
        mImvGoHomeModel.setImageResource(R.drawable.ic_model_home);
        mRlGoHomeModel.setBackgroundResource(R.drawable.model_btn_bg_selector);
        mTvLeaveHomeModel.setTextColor(0xfff72971);
        mRlReaveHomeModel.setBackgroundResource(R.drawable.model_btn_bg_selector);
        mImvReaveHomeModel.setImageResource(R.drawable.ic_leave_home_normal);
        Command command = state.getCommand();
        ResGetSceneList.Model reaveHomeModel = (ResGetSceneList.Model) mRlReaveHomeModel.getTag();
        ResGetSceneList.Model goHomeModel = (ResGetSceneList.Model) mRlGoHomeModel.getTag();
        if (command.getN().equals(goHomeModel.getCode())) {
            if (command.getA().equals(goHomeModel.getValue())) {
                mTvGoHomeModel.setTextColor(0xffffffff);
                mImvGoHomeModel.setImageResource(R.drawable.ic_model_home_active);
                mRlGoHomeModel.setBackgroundResource(R.drawable.model_btn_active_bg_selector);
            } else if (command.getA().equals(reaveHomeModel.getValue())) {
                mTvLeaveHomeModel.setTextColor(0xffffffff);
                mImvReaveHomeModel.setImageResource(R.drawable.ic_leave_home_active);
                mRlReaveHomeModel.setBackgroundResource(R.drawable.model_btn_active_bg_selector);
            }

        }
    }

    /**
     * @Desc: 获取消息推送的具体内容
     * @author:liangshan
     */
    public void getMessageInfo(String pushId, String userId) {
        ReqGetAppPushMessage reqGetAppPushMessage = new ReqGetAppPushMessage(pushId, userId);
        RetrofitUtil.getInstance().getmApiService()
                .getAppPushMessage(reqGetAppPushMessage.toJsonStr())
                .compose(new APITransFormer<ResGetAppPushMessage>())
                .subscribe(new APISubcriber<ResGetAppPushMessage>() {
                    @Override
                    public void onFail(ResGetAppPushMessage baseResponse,String msg) {
                        showToast(msg);
                    }

                    @Override
                    public void onSuccess(ResGetAppPushMessage resGetAppPushMessage) {
                        showPushMessage(resGetAppPushMessage);
                    }
                });

    }

    /**
     * @Desc: 显示推送的消息内容
     * @author:liangshan
     */
    private void showPushMessage(ResGetAppPushMessage message) {
        String type = message.getRefType();
        if (type.equals("USER_APPLY_BIND_HOUSE")) {
            //用户申请绑定房子业务类型
            showCheckBindApply(message);
        } else {
            //修改心情推送
            showMoodPush(message);
        }


    }

    /**
     * @Desc: 显示用户修改心情推送的消息
     * @author:liangshan
     */
    private void showMoodPush(final ResGetAppPushMessage message) {
        TipDialog tipDialog = new TipDialog(this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlePushMessage(message, "1");
            }
        });
        tipDialog.setCancelVisible(View.GONE);
        tipDialog.setContent(message.getContent());
        tipDialog.setSureStr(getString(R.string.i_know));
        tipDialog.show();
    }

    /**
     * @Desc: 显示审核绑定申请类型消息
     * @author:liangshan
     */
    public void showCheckBindApply(final ResGetAppPushMessage message) {
        TipDialog tipDialog = new TipDialog(this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlePushMessage(message, "1");
            }
        });
        tipDialog.setContent(message.getContent());
        tipDialog.setCancelStr(getString(R.string.reject));
        tipDialog.setSureStr(getString(R.string.agree));
        tipDialog.setCancelOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlePushMessage(message, "2");
            }
        });
        tipDialog.show();
    }

    /**
     * @Desc: 推送信息处理
     * @author:liangshan
     */
    public void handlePushMessage(ResGetAppPushMessage message, String state) {
        final ReqAppPushMessageReply request = new ReqAppPushMessageReply();
        request.setFromUserId(message.getFromUser().getId());
        request.setPushId(message.getPushId());
        request.setReplyState(state);
        RetrofitUtil.getInstance().getmApiService()
                .appPushMessageReply(request.toJsonStr())
                .compose(new APITransFormer<ResAppPushMessageReply>())
                .subscribe(new APISubcriber<ResAppPushMessageReply>() {
                    @Override
                    public void onFail(ResAppPushMessageReply baseResponse,String msg) {

                    }

                    @Override
                    public void onSuccess(ResAppPushMessageReply response) {
                        if (response.getPushResult() != null && response.getPushResult().getIsMember().equals("0")) {

                            //如果是处理了管理员申请的消息并且是同意后，显示是否删除其它原有控制用户，退出该activity
                            showDeleteUser(response.getPushResult().getHouseBindUserId());
                        } else {
                            mPresenter.getAtHomeUserList();
                        }
                    }
                });

    }

    /**
     * @Desc: s显示是否解除原有控制用户
     * @author:liangshan
     */
    private void showDeleteUser(final String houseBindUserId) {
        TipDialog tipDialog = new TipDialog(this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.deleteOtherHomeUser(houseBindUserId);
            }
        });
        tipDialog.setContent(getString(R.string.delete_other_user_tip));
        tipDialog.show();
    }

    @Override
    public void setPresenter(HouseMainContract.Presenter presenter) {

    }

    @Override
    public void getSceneListSuccess(final ResGetSceneList result) {
        RoomAdapter adapter = new RoomAdapter(result.getSceneList(), R.layout.layout_sences_item);
        mServiceUrl = result.getServiceUrl();
        mThePartUrl = result.getThePartUrl();
        mHouse = result.getHouse();
        tvSceneName.setText(mHouse.getHouseNo());
        if (result.getSceneList().size() > 0) {
            APP.DeviceId = result.getSceneList().get(0).getDevice();
            //尝试更新位置
            LocationUtil.updateLocation(this);
            //向tcp服务器注册
            RegistCMD registCMD = new RegistCMD();
            TcpClient.getInstacne().sendCMD(registCMD);
        }
        rVScenes.setAdapter(adapter);
        adapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(HouseMainActivity.this, RoomActivity.class);
                intent.putExtra("scenes", (ArrayList) result.getSceneList());
                intent.putExtra("index", position);
                intent.putExtra("houseId", getHouseId());
                startToActivity(intent);
                mUsers.clear();
            }
        });
        if (result.getModelList().size() >= 2) {
            ResGetSceneList.Model model01 = result.getModelList().get(0);
            mRlGoHomeModel.setTag(model01);
//            mTvGoHomeModel.setText(model01.getName());
            ResGetSceneList.Model model02 = result.getModelList().get(1);
            mRlReaveHomeModel.setTag(model02);
//            mTvLeaveHomeModel.setText(model02.getName());
        }
    }

    @Override
    public void getSceneListFali(String msg) {

    }

    @Override
    public String getHouseId() {
        return getIntent().getStringExtra("houseId");
    }

    @Override
    public void getAtHomeUserListSuccess(List<ResGetAtHomeUserList.AtHomeUser> users) {

        mUsers = users;
        Iterator<ResGetAtHomeUserList.AtHomeUser> iterator = mUsers.iterator();
        while (iterator.hasNext()) {
            ResGetAtHomeUserList.AtHomeUser user = iterator.next();

            if (user.getUserId().equals(APP.user.getUserId())) {
                //移除自己
                iterator.remove();

            } else if (user.getPushList().size() == 0 && user.getIsAtHome() == 0) {
                //移除没在家并且没消息的
                iterator.remove();
            }
        }

        mUserAdapter = new MsgUserListAdapter(mUsers, R.layout.layout_msg_user_item);
        mRecycleusers.setAdapter(mUserAdapter);
        mUserAdapter.setOnItemClickListener(this);


    }


    @Override
    public void getAtHomeUserListFail(String msg) {
        showToast(msg);
    }

    @Override
    public void deleteOtherHomeUserSuccess() {
        //更换管理权后删除其它原有控制用户情况下，退出该activity
        startToActivity(MainActivity.class);
        finish();
    }

    @Override
    public void deleteOtherHomeUserFail(String msg) {
        showToast(msg);
    }


    @Override
    protected void handleMessage(EventBusMessage msg) {
        super.handleMessage(msg);
        switch (msg.getMsgType()) {
            case Msg.MESSAGE_NOTIFY:
                if (!isForeground) {
                    return;
                }
                LogUtil.i("收到消息");
                MessageNotify data = (MessageNotify) msg.getData();
                mPresenter.getAtHomeUserList();
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                Notification notify = new Notification.Builder(this)
                        .setSmallIcon(R.drawable.ic_no_head)
                        .setContentTitle(data.getTitle())
                        .setContentText(data.getTitle())
                        .getNotification();
                //      .setContentIntent(pendingIntent).setNumber(1).getNotification();
                notify.flags = Notification.FLAG_AUTO_CANCEL; // FL
                notify.defaults = Notification.DEFAULT_ALL;
                manager.notify(1, notify);
                break;
            case Msg.CLOSE_HOUSE_MAIN_ACTIVITY:
                finish();
                break;
            case Msg.AT_HOME_STATE_CHANGE:
                //在家人数变化
                mPresenter.getAtHomeUserList();
                break;
            case Msg.SAMRT_PART_STATE_CHANGE:
                if (isForeground) {
                    //模式也当做一种部件，当有部件状态改变时也要判断是不是模式状态改变
                    initModelState((SmartPartStateTCP) msg.getData());
                }
                break;
            case Msg.TCP_CMD_SUCCESS:
                BaseTCPCMDResponse response = (BaseTCPCMDResponse) msg.getData();
                if (mTcpCmdResultMap != null && mTcpCmdResultMap.containsKey(response.getSerial())) {
                    mTcpCmdResultMap.get(response.getSerial()).onSuccess(response.getData());
                    mTcpCmdResultMap.remove(response.getSerial());
                }
                LogUtil.i("success:" + response.getData());

                break;
            case Msg.TCP_CMD_FAIL:
                BaseTCPCMDResponse result = (BaseTCPCMDResponse) msg.getData();
                if (mTcpCmdResultMap != null && mTcpCmdResultMap.containsKey(result.getSerial())) {
                    mTcpCmdResultMap.get(result.getSerial()).onFail(result.getMsg());
                    mTcpCmdResultMap.remove(result.getSerial());
                }
                LogUtil.i("Fail:" + result.getData());

                break;
            case Msg.CLEAR_TCP_CMD_LISENTER:
                if (mTcpCmdResultMap != null) {
                    mTcpCmdResultMap.clear();
                }
                break;

        }
    }


    @Override
    public void onItemClick(View view, int position) {
        ResGetAtHomeUserList.AtHomeUser user = mUsers.get(position);
        if (user.getPushList().size() > 0) {
            getMessageInfo(user.getPushList().get(0).getPushId(), user.getUserId());
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.cancelAll();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseTCPCMDRequest baseTCPCMDRequest = new BaseTCPCMDRequest();
        baseTCPCMDRequest.setAction("DEVICE_UNBIND");
        TcpClient.getInstacne().sendCMD(baseTCPCMDRequest);
        APP.DeviceId = null;
        if (mTcpCmdResultMap != null) {
            mTcpCmdResultMap.clear();
        }

    }

   /* @Override
    public void onBackPressed() {
        super.onBackPressed();
        startToActivity(MainActivity.class);
        finish();
    }*/

    public Map<String, TCPCMDResult> getResultMap() {
        if (mTcpCmdResultMap == null) {
            mTcpCmdResultMap = new HashMap<>();
        }
        return mTcpCmdResultMap;
    }
}
