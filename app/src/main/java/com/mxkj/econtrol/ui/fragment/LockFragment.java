package com.mxkj.econtrol.ui.fragment;

import android.content.Intent;
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
import com.mxkj.econtrol.app.Msg;
import com.mxkj.econtrol.base.BaseFragment;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.base.TCPCMDResult;
import com.mxkj.econtrol.bean.EventBusMessage;
import com.mxkj.econtrol.bean.EventBusUIMessage;
import com.mxkj.econtrol.bean.response.ResGetScenePartDetail;
import com.mxkj.econtrol.bean.response.ResPartPasswordImmediatelySet;
import com.mxkj.econtrol.bean.response.SmartPart;
import com.mxkj.econtrol.bean.response.SmartPartState;
import com.mxkj.econtrol.bean.response.TcpResLockAlerts;
import com.mxkj.econtrol.bean.tcpcmd.Command;
import com.mxkj.econtrol.bean.tcpcmd.SmartPartCMD;
import com.mxkj.econtrol.contract.LockFragmentContract;
import com.mxkj.econtrol.model.LockFragmentModel;
import com.mxkj.econtrol.net.APISubcriber;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;
import com.mxkj.econtrol.net.TcpClient;
import com.mxkj.econtrol.presenter.LockFragmentPresenter;
import com.mxkj.econtrol.ui.activity.LockAddTipsActivity;
import com.mxkj.econtrol.ui.activity.LockGesturePasswordActivity;
import com.mxkj.econtrol.ui.activity.LockPasswordManageActivity;
import com.mxkj.econtrol.utils.LogUtil;
import com.mxkj.econtrol.utils.MyDialogUtil;
import com.mxkj.econtrol.view.activity.LockSubmitVerificationActivity;
import com.mxkj.econtrol.view.activity.SearchGatewayActivity;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by chanjun on 2018/1/13.
 *
 * @Description: 门锁控制
 */

public class LockFragment extends BaseFragment implements LockFragmentContract.View {


    private Button btn_add;
    private Button btn_open;
    private TextView tv_password;
    private RelativeLayout rl_temporary;
    private TextView tv_temporary;
    private TextView tv_know;
    private TextView tv_tips;
    private ImageView iv_lock_bg;
    private LinearLayout ll_bottom_psw;
    private RelativeLayout rl_battery_alerts;

    private SmartPart mBlower;
    private String mDevice;
    private String mPartId;// 门锁部件的id
    private boolean isOwner;
    private LockFragmentContract.Presenter mPresenter;
    private String gesturePasswordStatus; // uninited - 未初始化； locked - 已锁定；normal - 正常；
    private String applyGesturePasswordStatus; //notSubmited - 未提交   waiting - 等待审核  rejected - 拒绝  passed - 通过审核；
    private String lockPermissions; ////权限标识，01-开锁,00-关锁
    private String mLockbrand; //门锁品牌
    private boolean isAddGateway; //是否添加了网关
    private boolean isAddLock; //是否添加了门锁
    private String mSnId;
    private String mLockName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_lock, container, false);
        super.createView(view);
        return contentView;
    }

    @Override
    public void initView() {

        mBlower = (SmartPart) getArguments().getSerializable("smartPart");
        mDevice = getArguments().getString("device");
        isOwner = getArguments().getBoolean("isOwner");

        btn_add = findView(R.id.btn_add);
        tv_password = findView(R.id.tv_password);
        rl_temporary = findView(R.id.rl_temporary);
        tv_temporary = findView(R.id.tv_temporary);
        tv_know = findView(R.id.tv_know);
        tv_tips = findView(R.id.tv_tips);
        iv_lock_bg = findView(R.id.iv_lock_bg);
        btn_open = findView(R.id.btn_open);
        ll_bottom_psw = findView(R.id.ll_bottom_psw);
        rl_battery_alerts = findView(R.id.rl_battery_alerts);
        mPresenter = new LockFragmentPresenter(this, new LockFragmentModel());

        lockPermissions = mBlower.getPermissions();
        gesturePasswordStatus = mBlower.getState().getLockState().getGesturePasswordStatus();
        applyGesturePasswordStatus = mBlower.getState().getLockState().getApplyGesturePasswordStatus();

    }

    @Override
    public void initData() {
        mLockbrand = mBlower.getBrand().getCode();//  门锁品牌
        if (mLockbrand.equals("01")) { // 耶鲁门锁
//            btn_open.setText("添加门锁");
        } else {                        // 青松门锁

        }
    }

    @Override
    public void initListener() {
        btn_add.setOnClickListener(this);
        tv_password.setOnClickListener(this);
        tv_temporary.setOnClickListener(this);
        tv_know.setOnClickListener(this);
        btn_open.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_add:  // 添加门锁
                if (isAddGateway) { // 已经添加了网关
                    Intent intent = new Intent(getActivity(), LockAddTipsActivity.class);
                    intent.putExtra("partId", mPartId);
                    intent.putExtra("GatewaySn", mSnId);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), SearchGatewayActivity.class);
                    intent.putExtra("partId", mPartId);
                    startActivity(intent);
                }
                break;
            case R.id.tv_password: // 密码管理
                Intent intent = new Intent(getActivity(), LockPasswordManageActivity.class);
                intent.putExtra("lockId", mBlower.getId()); // 锁id
                intent.putExtra("isOwner", isOwner); // 是否业主
                startActivity(intent);
                break;
            case R.id.tv_temporary:  // 临时开锁

                // 密码未初始化       ---》初始化
                // 密码正常或被锁
                // 业主有权限      ----》临时密码
                // 业主没有权限    ----》提醒提交审核
                // 不是业主有权限    ----》临时密码
                // 不是业主没有权限    -----》提示联系业主


                Intent intent1 = new Intent(getActivity(), LockGesturePasswordActivity.class);
                intent1.putExtra("partPasswordGrant", true); // 验证密码
                intent1.putExtra("temporary", true); // 临时密码
                intent1.putExtra("lockId", getLockId()); // 锁id
                startActivity(intent1);
                break;
            case R.id.btn_open:  // 立即开锁
                /***
                 * --------------------------------------------------------------------------------------------------------------------
                 * 还差是否添加门锁的判断
                 */
                if (gesturePasswordStatus.equals(Msg.LOCK_GESTURE_PASSWORD_STATUES_UNINITED)) {// 密码状态未初始化
                    if (lockPermissions.indexOf("a") != -1) { // 必须有权限的情况下
                    } else {
                    }
                    MyDialogUtil dialogUtil = new MyDialogUtil(getActivity()) {
                        @Override
                        public void confirm() {
                            Intent intent = new Intent(getActivity(), LockGesturePasswordActivity.class);
                            intent.putExtra("partPasswordInit", true); // 初始密码
                            intent.putExtra("lockId", getLockId()); // 锁id
                            startActivity(intent);
                        }
                    };
                    dialogUtil.showLockGestureTipDialog("开启手势密码", "为了您的门锁安全\n请设置手势密码用于解锁", "暂不设置", "立即设置");
                } else if (gesturePasswordStatus.equals(Msg.LOCK_GESTURE_PASSWORD_STATUES_LOCKED)) { // 手势密码被锁定
                    if (isOwner) { // 业主手势密码被锁定情况下
                        Intent intent01 = new Intent(getActivity(), LockSubmitVerificationActivity.class);
                        intent01.putExtra("lockId", getLockId()); // 锁id
                        startActivity(intent01);
                    }
                } else if (gesturePasswordStatus.equals(Msg.LOCK_GESTURE_PASSWORD_STATUES_NORMAL)) {  // 手势密码正常
                    if (lockPermissions.indexOf("a") != -1) { // 必须有权限的情况下
                        Intent intent01 = new Intent(getActivity(), LockGesturePasswordActivity.class);
                        intent01.putExtra("partPasswordGrant", true); // 验证密码
                        intent01.putExtra("temporary", false); // 临时密码
                        intent01.putExtra("lockId", getLockId()); // 锁id
                        startActivityForResult(intent01, 1);
                    }
                }
                break;
            case R.id.tv_know:
                rl_battery_alerts.setVisibility(View.GONE);
                break;

        }
    }


    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getScenePartDetail(getDeviceId());  // 获取部件详情
    }

    public String getLockId() {
        return mBlower.getId();
    }

    public boolean getLockStatue() {
        return isAddLock;
    }

    public boolean getGatewayStatue() {
        return isAddGateway;
    }

    public String getGatewaySn() {
        return mSnId;
    }

    public String getlockName() {
        return mLockName;
    }

    @Override
    public String getDeviceId() {
        return mBlower.getId();
    }

    @Override
    public void getScenePartDetailSuccess(ResGetScenePartDetail resGetScenePartDetail) {

        String content = new Gson().toJson(resGetScenePartDetail);

        lockPermissions = resGetScenePartDetail.getPart().getPermissions();
        gesturePasswordStatus = resGetScenePartDetail.getPart().getState().getLockState().getGesturePasswordStatus();
        applyGesturePasswordStatus = resGetScenePartDetail.getPart().getState().getLockState().getApplyGesturePasswordStatus();

        mPartId = resGetScenePartDetail.getPart().getId();

        mLockbrand = mBlower.getBrand().getCode();//  门锁品牌
        if (mLockbrand.equals("01")) { // 耶鲁门锁
            btn_add.setVisibility(View.GONE);
            upDateLockState(); //  显示最新锁的状态
        } else {                        // 青松门锁
            if (resGetScenePartDetail.getPart().getGateway() != null && !TextUtils.isEmpty(resGetScenePartDetail.getPart().getGateway().getId())) {
                isAddGateway = true;
            } else {   // 判断是否添加网关
                isAddGateway = false;
            }
            if (resGetScenePartDetail.getPart() != null && !TextUtils.isEmpty(resGetScenePartDetail.getPart().getSn())) {
                isAddLock = true;
            } else {       // 判断是否添加门锁
                isAddLock = false;
            }
            if (isAddGateway && isAddLock) {
                upDateLockState(); //  显示最新锁的状态
            } else {
                ll_bottom_psw.setVisibility(View.INVISIBLE);
            }
            upDateGatewayState();
        }
        if (resGetScenePartDetail.getPart()!=null && resGetScenePartDetail.getPart().getPower()==0) { // 门锁电量状态 返回 1正常 0低电压
            rl_battery_alerts.setVisibility(View.VISIBLE);
        }
        if (resGetScenePartDetail.getPart().getFunctions() !=null &&
                resGetScenePartDetail.getPart().getFunctions().getTp() == 1) { // 有临时密码功能的情况下
            rl_temporary.setVisibility(View.VISIBLE);
        } else {
            rl_temporary.setVisibility(View.GONE);
        }
        mSnId = resGetScenePartDetail.getPart().getGateway().getSnid();
        mLockName = resGetScenePartDetail.getPart().getIeeeAddress();

    }


    @Override
    public void getScenePartDetailFail(String msg) {
//        upDateLockState(); //  显示最新锁的状态
    }

    @Override
    public void setPresenter(LockFragmentContract.Presenter presenter) {

    }


    /***
     * 显示最新锁的状态
     */
    private void upDateLockState() {

 /*       if (gesturePasswordStatus.equals(Msg.LOCK_GESTURE_PASSWORD_STATUES_UNINITED)) { //手势密码未初始化
            tv_tips.setText("请初始化手势密码");
            btn_open.setVisibility(View.VISIBLE);
            ll_bottom_psw.setVisibility(View.INVISIBLE);
        } else if (gesturePasswordStatus.equals(Msg.LOCK_GESTURE_PASSWORD_STATUES_NORMAL) || gesturePasswordStatus.equals(Msg.LOCK_GESTURE_PASSWORD_STATUES_LOCKED)) { ////手势密码正常   或 手势密码已锁定
            if (isOwner && lockPermissions.indexOf("01") != -1) { //业主有权限
                // 去判断审核状态
                tv_tips.setText("");
                btn_open.setVisibility(View.VISIBLE);
                ll_bottom_psw.setVisibility(View.VISIBLE);
            } else if (isOwner && lockPermissions.indexOf("01") == -1) { // 业主没权限
                // 去判断审核状态
                applyGesturePasswordStatus();
            } else if (!isOwner && lockPermissions.indexOf("01") != -1) { // 不是业主有权限
                tv_tips.setText("");
                btn_open.setVisibility(View.VISIBLE);
                ll_bottom_psw.setVisibility(View.VISIBLE);
            } else if (!isOwner && lockPermissions.indexOf("01") == -1) { // 不是业主没权限
                tv_tips.setText("您已失去解锁权限，重新获得解锁权限需要该住宅的管理员对您开放权限。");
                btn_open.setVisibility(View.GONE);
                ll_bottom_psw.setVisibility(View.INVISIBLE);
            }
        }
*/

    /*    if (gesturePasswordStatus.equals(Msg.LOCK_GESTURE_PASSWORD_STATUES_UNINITED)) {   // 手势密码未初始化
            tv_tips.setText("请初始化手势密码");
            btn_open.setVisibility(View.VISIBLE);
            ll_bottom_psw.setVisibility(View.INVISIBLE);
        } else if (gesturePasswordStatus.equals(Msg.LOCK_GESTURE_PASSWORD_STATUES_LOCKED)) {    //手势密码已锁定
            if (isOwner) { // 是业主，提交审核资料
                // 去判断审核状态
                applyGesturePasswordStatus();
            } else { // 不是业主，提醒联系业主
                tv_tips.setText("您已失去解锁权限，重新获得解锁权限需要该住宅的管理员对您开放权限。");
                btn_open.setVisibility(View.GONE);
                ll_bottom_psw.setVisibility(View.INVISIBLE);
            }
        } else if (gesturePasswordStatus.equals(Msg.LOCK_GESTURE_PASSWORD_STATUES_NORMAL)) {     //手势密码正常
            if (isOwner && lockPermissions.indexOf("a") != -1) { //业主有权限
                // 去判断审核状态
                tv_tips.setText("");
                btn_open.setVisibility(View.VISIBLE);
                ll_bottom_psw.setVisibility(View.VISIBLE);
            } else if (isOwner && lockPermissions.indexOf("a") == -1) { // 业主没权限
                // 去判断审核状态
                applyGesturePasswordStatus();
            } else if (!isOwner && lockPermissions.indexOf("a") != -1) { // 不是业主有权限
                tv_tips.setText("");
                btn_open.setVisibility(View.VISIBLE);
                ll_bottom_psw.setVisibility(View.VISIBLE);
            } else if (!isOwner && lockPermissions.indexOf("a") == -1) { // 不是业主没权限
                tv_tips.setText("您已失去解锁权限，重新获得解锁权限需要该住宅的管理员对您开放权限。");
                btn_open.setVisibility(View.GONE);
                ll_bottom_psw.setVisibility(View.INVISIBLE);
            }
        }
*/

        if (lockPermissions.indexOf("a") != -1) {       /**有开锁权限*/
            if (gesturePasswordStatus.equals(Msg.LOCK_GESTURE_PASSWORD_STATUES_UNINITED)) {   // 手势密码未初始化
                tv_tips.setText("请初始化手势密码");
                btn_open.setVisibility(View.VISIBLE);
                ll_bottom_psw.setVisibility(View.INVISIBLE);
            } else if (gesturePasswordStatus.equals(Msg.LOCK_GESTURE_PASSWORD_STATUES_LOCKED)) {    //手势密码已锁定
                if (isOwner) { // 是业主，提交审核资料
                    // 去判断审核状态
                    applyGesturePasswordStatus();
                } else { // 不是业主，提醒联系业主
                    tv_tips.setText("您已失去解锁权限，重新获得解锁权限需要该住宅的管理员对您开放权限。");
                    btn_open.setVisibility(View.GONE);
                    ll_bottom_psw.setVisibility(View.INVISIBLE);
                }
            } else if (gesturePasswordStatus.equals(Msg.LOCK_GESTURE_PASSWORD_STATUES_NORMAL)) {     //手势密码正常
                tv_tips.setText("");
                btn_open.setVisibility(View.VISIBLE);
                ll_bottom_psw.setVisibility(View.VISIBLE);
            }
        } else {                                     /***没开锁权限*/
            if (isOwner) { // 是业主，提交审核资料
                // 去判断审核状态
                applyGesturePasswordStatus();
            } else { // 不是业主，提醒联系业主
                tv_tips.setText("您已失去解锁权限，重新获得解锁权限需要该住宅的管理员对您开放权限。");
                btn_open.setVisibility(View.GONE);
                ll_bottom_psw.setVisibility(View.INVISIBLE);
            }
        }
    }

    /**
     * 显示网关、门锁状态
     */
    private void upDateGatewayState() {
        if (isAddGateway && isAddLock) {
            iv_lock_bg.setImageResource(R.drawable.ic_lock_bg);
        } else {
            iv_lock_bg.setImageResource(R.drawable.ic_lock_empty);
        }

        if (isOwner) {
            if (isAddGateway) { // 已添加了网关
                if (isAddLock) {
                    btn_open.setVisibility(View.VISIBLE);
                    btn_add.setVisibility(View.GONE);
                } else {
                    btn_open.setVisibility(View.GONE);
                    btn_add.setVisibility(View.VISIBLE);
                    tv_tips.setText("您尚未绑定门锁");
                    btn_add.setText("绑定门锁");
                }
            } else {
                btn_open.setVisibility(View.GONE);
                btn_add.setVisibility(View.VISIBLE);
                tv_tips.setText("您尚未绑定网关");
                btn_add.setText("添加网关");
            }
        } else {
            if (!isAddGateway || !isAddLock) { // 已添加了网关
                btn_open.setVisibility(View.GONE);
                btn_add.setVisibility(View.GONE);
                tv_tips.setText("请联系业主，添加门锁");
            } else if (lockPermissions.indexOf("a") == -1) {
                btn_open.setVisibility(View.GONE);
                btn_add.setVisibility(View.GONE);
            } else {
                btn_open.setVisibility(View.VISIBLE);
                btn_add.setVisibility(View.GONE);
            }
        }
    }


    /***
     * 审核状态判断
     *
     手势密码状态
     gesturePasswordStatus
     uninited - 未初始化；
     locked - 已锁定；
     normal - 正常；
     手势密码申请状态
     applyGesturePasswordStatus
     notSubmited - 未提交；
     waiting - 等待审核；
     rejected - 拒绝；
     passed - 通过审核；

     */
    private void applyGesturePasswordStatus() {
        if (applyGesturePasswordStatus.equals(Msg.LOCK_APPLY_PASSWORD_STATUES_REJECTED)) {  // 手手势密码提交 ：拒绝  或  /
            tv_tips.setText("您提交的审核被拒绝，需要重新提交验证");
            btn_open.setVisibility(View.VISIBLE);
            btn_open.setText("立即验证");
            ll_bottom_psw.setVisibility(View.INVISIBLE);
        } else if (applyGesturePasswordStatus.equals(Msg.LOCK_APPLY_PASSWORD_STATUES_NOT_SUBMIT)) {  //  手势密码提交 ：未提交
            tv_tips.setText("您已失去解锁权限，需进行身份认证");
            btn_open.setVisibility(View.VISIBLE);
            btn_open.setText("立即验证");
            ll_bottom_psw.setVisibility(View.INVISIBLE);
        } else if (applyGesturePasswordStatus.equals(Msg.LOCK_APPLY_PASSWORD_STATUES_WAITING)) {  // 手势密码提交 ：等待审核
            tv_tips.setText("您已提交身份验证，请耐心等待");
            btn_open.setVisibility(View.VISIBLE);
            btn_open.setBackgroundResource(R.drawable.login_regist_bg48);
            ll_bottom_psw.setVisibility(View.INVISIBLE);
        } else if (applyGesturePasswordStatus.equals(Msg.LOCK_APPLY_PASSWORD_STATUES_PASSED)) {  // 手势密码提交 ：通过审核
            tv_tips.setText("请初始化手势密码");
            btn_open.setVisibility(View.VISIBLE);
            btn_open.setText("立即开锁");
            ll_bottom_psw.setVisibility(View.VISIBLE);
        }
    }


    /**
     * 处理eventBus消息
     *
     * @param msg
     */
    protected void handleMessage(EventBusMessage msg) {
        super.handleMessage(msg);
        if (msg.getMsgType() == Msg.LOCK_APPLY_SUCCESS) { // 门锁审核提交后
            mPresenter.getScenePartDetail(getDeviceId());
        } else if (msg.getMsgType() == Msg.LOCK_PESSWORD_GRANT_ERROR) { // 手势密码错误5次
            mPresenter.getScenePartDetail(getDeviceId());
        } else if (msg.getMsgType() == Msg.LOCK_ADD_SUCCESS) { // 门锁添加成功
            btn_add.setVisibility(View.GONE);
            btn_open.setVisibility(View.VISIBLE);
        } else if (msg.getMsgType() == Msg.EVENBUS_LOCK_BATTER_MESSAGE) { // 门锁电量消息
            TcpResLockAlerts data = (TcpResLockAlerts) msg.getData();
            if ( data.getCommand().getPowerId()!=null  && data.getCommand().getPowerId().equals("0001")) { // powerId 0000 正常 0001 低压
                rl_battery_alerts.setVisibility(View.VISIBLE);
            }
//
        }

           /* // 测试门锁警报信息
            String content = "{\"userId\":\"17620999268\",\"result\":\"1113\",\"device\":\"112f2420\",\"command\":{\"houseName\":\"富力新天地 T01-T1001\",\"houseId\":\"40288b1161fe0ba80161fe389c86000d\",\"alarmCode\":\"33\"}}";
            Gson gson = new Gson();
            TcpResLockAlerts tcpResLockAlerts = gson.fromJson(content, TcpResLockAlerts.class);
            EventBusUIMessage message = new EventBusUIMessage();
            message.setMsgType(Msg.EVENBUS_LOCK_ALERTS);
            message.setData(tcpResLockAlerts);
            EventBus.getDefault().post(message);
            */


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && null != data) {
            if (mLockbrand.equals("01")) { // 耶鲁门锁
                String token = data.getStringExtra("token");
                   openYLLock(token);
            } else {                        // 青松门锁
                String token = data.getStringExtra("token");
                if (!TextUtils.isEmpty(token)) {
                    openQSLock(token);
                }else {
                    showToast("token错误，不能操作开锁");
                }
            }
        }
    }


    private void openYLLock(String token) {
        final Command command = new Command();
        command.setType("01");
        command.setN(mBlower.getCode());
        command.setLt(mBlower.getBrand().getCode());
        command.setA("01");
        command.setToken(token);

        SmartPartCMD smartPartCMD = new SmartPartCMD();
        smartPartCMD.setCommand(command);
        smartPartCMD.setPartId(mDevice);
        TcpClient.getInstacne().sendCMD(this, smartPartCMD, new TCPCMDResult() {
            @Override
            public void onSuccess(String result) {
//                showToast(result);
                LogUtil.i("============tcp开锁成功");
            }

            @Override
            public void onFail(String msg) {
//                showToast(msg);
            }
        });
    }

    public void setmSnId(String mSnId) {
        this.mSnId = mSnId;
    }

    /***青松开锁接口*/
    private void openQSLock(String token) {
        ResPartPasswordImmediatelySet resPartPasswordImmediatelySet = new ResPartPasswordImmediatelySet(mPartId, token);

        RetrofitUtil.getInstance().getmApiService()
                .partPasswordImmediatelySet(resPartPasswordImmediatelySet.toJsonStr())
                .compose(new APITransFormer<BaseResponse>())
                .subscribe(new APISubcriber<BaseResponse>() {
                    @Override
                    public void onFail(BaseResponse baseResponse, String msg) {
                        LogUtil.i("=========开锁失败=="+msg);
                        showToast("开锁失败");
                    }
                    @Override
                    public void onSuccess(BaseResponse response) {
                        String content = new Gson().toJson(response);
                        showToast("请按把手开门");
                    }
                });
    }


}

