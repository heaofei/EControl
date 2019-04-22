package com.mxkj.econtrol.ui.fragment;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.app.Msg;
import com.mxkj.econtrol.base.BaseFragment;
import com.mxkj.econtrol.base.TCPCMDResult;
import com.mxkj.econtrol.bean.EventBusUIMessage;
import com.mxkj.econtrol.bean.response.SmartPart;
import com.mxkj.econtrol.bean.response.SmartPartState;
import com.mxkj.econtrol.bean.tcpcmd.Command;
import com.mxkj.econtrol.bean.tcpcmd.SmartPartCMD;
import com.mxkj.econtrol.bean.tcpcmd.SmartPartStateTCP;
import com.mxkj.econtrol.net.TcpClient;
import com.mxkj.econtrol.utils.LogUtil;
import com.mxkj.econtrol.utils.SharedPreferencesUtils;
import com.mxkj.econtrol.view.activity.RoomActivity;
import com.mxkj.econtrol.widget.ProgressBarView;
import com.mxkj.econtrol.widget.SeekArc;

/**
 * Created by liangshan on 2017/3/31.
 *
 * @Description:
 */

public class AirConditionerFragment extends BaseFragment {
    private SmartPart mAirConditioner;
    private String mPartId;
    private int mCurTemp;//当前温度
    private String mCurMode;//当前模式：01、制冷；02、制热；03、送风；04、除湿；
    private ImageView mImvSwitch;
    private TextView mTvWindSpeed;
    private ImageView iv_wind_reduce;// 风速减
    private ImageView iv_wind_add;  // 风速加
    private ImageView imv_wind_speed2;  // 显示风速
    private ImageView imv_cool;  // 制冷
    private ImageView imv_heat;  // 制热
    private ImageView imv_dry;  // 除湿
    private ImageView imv_air;  // 送风
    private TextView tv_cool;  // 制冷
    private TextView tv_heat;  // 制热
    private TextView tv_dry;  // 除湿
    private TextView tv_air;  // 送风
    private LinearLayout ll_model;  // 模式区域
    private RelativeLayout rl_item;  // 空调设置区域
    private View title_view,bottom_view;
    private View view_touming;
    private String mCurSpeed;
    private Dialog selectModelDialog;
    private Dialog selectSpeedDialog;
    private SeekArc mTemp;
    private ImageView mImvAirconditioner;
    private TextView mTvRt;//室温
    private TextView tv_temperature;//空调温度
    private int mProgress=18;//空调温度
    private boolean isPalyAdmin;
    private AnimatorSet mCloseAnimatorSet;
    private AnimatorSet mOpenAnimatorSet;
    private AnimatorSet animatorSet;
    private RotateAnimation mRotateAnimation;   // 中间转速动画

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_air_conditioner, container, false);
        super.createView(view);
        return view;
    }

    @Override
    public void initView() {

        mImvSwitch = findView(R.id.imv_switch);
        mTvWindSpeed = findView(R.id.tv_wind_speed);
        iv_wind_reduce = findView(R.id.iv_wind_reduce);
        iv_wind_add = findView(R.id.iv_wind_add);
        imv_wind_speed2 = findView(R.id.imv_wind_speed2);
        imv_cool = findView(R.id.imv_cool);
        imv_heat = findView(R.id.imv_heat);
        imv_dry = findView(R.id.imv_dry);
        imv_air = findView(R.id.imv_air);
        tv_cool = findView(R.id.tv_cool);
        tv_heat = findView(R.id.tv_heat);
        tv_dry = findView(R.id.tv_dry);
        tv_air = findView(R.id.tv_air);
        ll_model = findView(R.id.ll_model);
        rl_item = findView(R.id.rl_item);
        title_view = findView(R.id.title_view);
        bottom_view = findView(R.id.bottom_view);
        view_touming = findView(R.id.view_touming);

        mTvRt = findView(R.id.tv_rt);
        tv_temperature = findView(R.id.tv_temperature);
        mImvAirconditioner = findView(R.id.imv_airconditioner);
        // 初始化progressbar
        mTemp = findView(R.id.progress_temp);
      /*  mTemp.setProgress(1);
        mTemp.setMax(14);
        mTemp.setSweepAngle(280);
        mTemp.setStartAngle(40);
        mTemp.setArcWidth(26);
        mTemp.setProgressWidth(26);*/
        mTemp.setRoundedEdges(true);
        mTemp.setTouchInSide(false);

    }

    @Override
    public void initData() {
        mAirConditioner = (SmartPart) getArguments().getSerializable("smartPart");
        mPartId = getArguments().getString("device");
        mRotateAnimation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        initModelState();  // 初始化模式
        initSpeedState();  // 初始化风速
        initTemp();  // 初始化温度
        SmartPartState state = mAirConditioner.getState();
        if (state.getA().equals("01")) {  // 空调状态 ：开
            rl_item.setVisibility(View.VISIBLE);
            ll_model.setVisibility(View.VISIBLE);
            mTvRt.setVisibility(View.VISIBLE);
            title_view.setVisibility(View.GONE);
            bottom_view.setVisibility(View.GONE);
            mImvAirconditioner.setImageResource(R.drawable.ic_airconditioner_open);
            if (TextUtils.isEmpty(state.getRt())) {
                mTvRt.setText("室温：--℃");
            } else {
                mTvRt.setText("室温：" + state.getRt() + "℃");
            }
            mImvSwitch.setImageResource(R.drawable.ic_bt_open);

        } else if (state.getA().equals("00")) {   // 空调状态 ：关
            rl_item.setVisibility(View.GONE);
            ll_model.setVisibility(View.GONE);
            title_view.setVisibility(View.VISIBLE);
            bottom_view.setVisibility(View.VISIBLE);
            mImvSwitch.setImageResource(R.drawable.ic_bt_closed);
            mImvAirconditioner.setImageResource(R.drawable.ic_airconditioner_close);
        }
    /*    int[] arcColors = new int[]{
                Color.parseColor("#99cccc"),
                Color.parseColor("#ccffff"),
                Color.parseColor("#ffcccc"),
                Color.parseColor("#6699cc"),
                Color.parseColor("#99ccff"),
                Color.parseColor("#6699cc"),
                Color.parseColor("#cc6699"),
                Color.parseColor("#ffff00")
        };
        mSeekArc.setBackgroundColor(arcColors);*/


    }


    @Override
    public void initListener() {

        mImvSwitch.setOnClickListener(this);
        iv_wind_reduce.setOnClickListener(this);
        iv_wind_add.setOnClickListener(this);
        imv_cool.setOnClickListener(this);
        imv_heat.setOnClickListener(this);
        imv_dry.setOnClickListener(this);
        imv_air.setOnClickListener(this);
        view_touming.setOnClickListener(this);

        mTemp.setOnSeekArcChangeListener(new SeekArc.OnSeekArcChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekArc seekArc) {

                if (mProgress>=18 && mProgress<=32){
                    setTemperatureText("SENDCMD", seekArc.getProgress()); // 显示当前选择的温度数字
                }
            }
            @Override
            public void onStartTrackingTouch(SeekArc seekArc) {
            }
            @Override
            public void onProgressChanged(SeekArc seekArc, int progress,
                                          boolean fromUser) {
                setTemperatureText("SETTEXT", progress); // 显示当前选择的温度数字
            }
        });
    }

    public static String getHouseId() {
        return (String) SharedPreferencesUtils.getParam(APP.getInstance(), "houseId", "");
    }


    public void setTemperatureText(String type, int progress) {
        if (progress >= 0 && progress <= 2) {
            progress = 18;
        } else if (progress > 2 && progress <= 5) {
            progress = 19;
        } else if (progress > 5 && progress <= 10) {
            progress = 20;
        } else if (progress > 10 && progress <= 15) {
            progress = 21;
        } else if (progress > 15 && progress <= 20) {
            progress = 22;
        } else if (progress > 20 && progress <= 25) {
            progress = 23;
        } else if (progress > 25 && progress <= 30) {
            progress = 24;
        } else if (progress > 30 && progress <= 35) {
            progress = 25;
        } else if (progress > 35 && progress <= 40) {
            progress = 26;
        } else if (progress > 40 && progress <= 45) {
            progress = 27;
        } else if (progress > 45 && progress <= 50) {
            progress = 28;
        } else if (progress > 50 && progress <= 55) {
            progress = 29;
        } else if (progress > 55 && progress <= 60) {
            progress = 30;
        } else if (progress > 60 && progress <= 63) {
            progress = 31;
        } else if (progress > 63 && progress <= 66) {
            progress = 32;
        }
        mProgress = progress;
        tv_temperature.setText(progress + "");
        if (type.equals("SENDCMD")) {
            changeTemp(mProgress);
        }

    }
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.imv_switch:
                changeA();
                break;
            case R.id.imv_cool:
                changM("01");
                break;
            case R.id.imv_heat:
                changM("02");
                break;
            case R.id.imv_air:
                changM("03");
                break;
            case R.id.imv_dry:
                changM("04");
                break;
            case R.id.iv_wind_reduce:   // 修改风速
                if (mCurSpeed.equals("01")) {   //mCurSpeed = 01  02  03  分别为 大 中 小
                    changeF("02");
//                    showToast("风速中");
                } else if (mCurSpeed.equals("02")) {
                    changeF("03");
//                    showToast("风速小");
                }
                break;
            case R.id.iv_wind_add:  // 修改风速
                if (mCurSpeed.equals("03")) {   //mCurSpeed = 01  02  03  分别为 大 中 小
                    changeF("02");
//                    showToast("风速中");
                } else if (mCurSpeed.equals("02")) {
                    changeF("01");
//                    showToast("风速大");
                }
                break;
            case R.id.view_touming:

                break;
        }
    }

    /**
     * @Desc: 开关
     * @author:liangshan
     */

    public void changeA() {
        SmartPartCMD smartPartCMD = new SmartPartCMD();
        Command command = new Command();
        command.setType("01");
        command.setN(mAirConditioner.getCode());
        if (mAirConditioner.getState().getA().equals("00")) {
            command.setA("01");
        } else {
            command.setA("00");
        }
//        smartPartCMD.setPartId(mPartId);
        smartPartCMD.setCommand(command);
        smartPartCMD.setPartId(mAirConditioner.getId());
        smartPartCMD.setHouseId(getHouseId());
        if (APP.isExperience){
            if (mAirConditioner.getState().getA().equals("00")) {
                mAirConditioner.getState().setA("01");
            } else {
                mAirConditioner.getState().setA("00");
            }
            initSwitchState(); // 显示开关状态
            return;
        }

        TcpClient.getInstacne().sendCMD(this, smartPartCMD, new TCPCMDResult() {
            @Override
            public void onSuccess(String result) {
                if (mAirConditioner.getState().getA().equals("00")) {
                    mAirConditioner.getState().setA("01");
                } else {
                    mAirConditioner.getState().setA("00");
                }
                initSwitchState(); // 显示开关状态
            }
            @Override
            public void onFail(String msg) {
                showToast(msg);
            }
        });
    }

    /**
     * @Desc: 改温度
     * @author:liangshan
     */
    public void changeTemp(final int temp) {
        SmartPartCMD smartPartCMD = new SmartPartCMD();
        Command command = new Command();
        command.setN(mAirConditioner.getCode());
        command.setT(temp + "");
//        smartPartCMD.setPartId(mPartId);
        smartPartCMD.setCommand(command);
        smartPartCMD.setPartId(mAirConditioner.getId());
        smartPartCMD.setHouseId(getHouseId());
        if (APP.isExperience) {
            return;
        }
        TcpClient.getInstacne().sendCMD(this, smartPartCMD, new TCPCMDResult() {
            @Override
            public void onSuccess(String result) {
//                mAirConditioner.getState().setT(mCurTemp);
//                initTemp();
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
                stateChange((SmartPartStateTCP) msg.getData());
                break;
        }
    }

    /**
     * @Desc: 初始化开关的状态
     * @author:liangshan
     */
    public void initSwitchState() {
        SmartPartState state = mAirConditioner.getState();
        if (state.getA().equals("01")) { // 开
            openAirconditioner();

        } else if (state.getA().equals("00")) {  // 关
            closeAirconditioner();

        }
    }

    /**
     * @Desc: 初始化模式
     * @author:liangshan
     */
    public void initModelState() {
        SmartPartState state = mAirConditioner.getState();
        mCurMode = state.getM();
        switch (mCurMode)
        {
            case "01":
                sestBackGround();
                imv_cool.setImageResource(R.drawable.ic_model_cool_active);
                tv_cool.setTextColor(getResources().getColor(R.color.orange));
                break;
            case "02":
                sestBackGround();
                imv_heat.setImageResource(R.drawable.ic_model_heat_active);
                tv_heat.setTextColor(getResources().getColor(R.color.orange));
                break;
            case "03":
                sestBackGround();
                imv_air.setImageResource(R.drawable.ic_model_air_active);
                tv_air.setTextColor(getResources().getColor(R.color.orange));
                break;
            case "04":
                sestBackGround();
                imv_dry.setImageResource(R.drawable.ic_model_dry_active);
                tv_dry.setTextColor(getResources().getColor(R.color.orange));
                break;
        }
    }

    private void sestBackGround() {
        imv_cool.setImageResource(R.drawable.ic_model_cool);
        imv_heat.setImageResource(R.drawable.ic_model_heat);
        imv_dry.setImageResource(R.drawable.ic_model_dry);
        imv_air.setImageResource(R.drawable.ic_model_air);
        tv_cool.setTextColor(getResources().getColor(R.color.text_black_666));
        tv_heat.setTextColor(getResources().getColor(R.color.text_black_666));
        tv_dry.setTextColor(getResources().getColor(R.color.text_black_666));
        tv_air.setTextColor(getResources().getColor(R.color.text_black_666));
    }


    /**
     * @Desc: 初始化风速
     * @author:liangshan
     */
    public void initSpeedState() {
        SmartPartState state = mAirConditioner.getState();
        mCurSpeed = state.getF();
        switch (mCurSpeed)
        {
            case "03":
                mTvWindSpeed.setText(getString(R.string.low_wind_speed));
                imv_wind_speed2.setImageResource(R.drawable.icon_wind_1);
                showAnimation(); // 显示风速动画
                break;
            case "02":
                mTvWindSpeed.setText(getString(R.string.midle_wind_speed));
                imv_wind_speed2.setImageResource(R.drawable.icon_wind_2);
                showAnimation(); // 显示风速动画
                break;
            case "01":
                mTvWindSpeed.setText(getString(R.string.high_wind_speed));
                imv_wind_speed2.setImageResource(R.drawable.icon_wind_3);
                showAnimation(); // 显示风速动画
                break;
        }


    }

    /**
     * @Desc: 初始化温度
     * @author:liangshan
     */
    private void initTemp() {
        SmartPartState state = mAirConditioner.getState();
        mProgress = state.getT();
        if (  18<=state.getT() && state.getT()<=32){ // 在这个范围内才显示视图
            tv_temperature.setText(state.getT() + "");
//        mCurTemp -= 18; // 因为progressde是0-15 所以要减18
//            mTemp.setProgress(mCurTemp-18);
             if (mProgress == 18) {
                 mTemp.setProgress(0);
            } else if (mProgress == 32) {
                 mTemp.setProgress(66);
            } else {
                int temp = (mProgress - 19) * 5 + 2;
                 mTemp.setProgress(temp);
            }
        }

    }

    /**
     * @Desc:关闭空调
     * @author:liangshan
     */
    private void closeAirconditioner() {
        mCloseAnimatorSet = new AnimatorSet();
        ObjectAnimator tvWindSpeedAlpha = ObjectAnimator.ofFloat(ll_model, "Alpha", 1, 0);
        ObjectAnimator tvSwitchAlpha = ObjectAnimator.ofFloat(rl_item, "Alpha", 1, 0);
//        ObjectAnimator tvRtAlpha = ObjectAnimator.ofFloat(mTvRt, "Alpha", 1, 0);
        ObjectAnimator switchAlpha = ObjectAnimator.ofFloat(mImvSwitch, "Alpha", 1, 0.5f, 1);
        mCloseAnimatorSet.play(tvSwitchAlpha).with(tvWindSpeedAlpha).with(switchAlpha);
        mCloseAnimatorSet.setDuration(1000);
        mCloseAnimatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (mOpenAnimatorSet != null && mOpenAnimatorSet.isRunning()) {
                    mOpenAnimatorSet.cancel();
                }
                if (animatorSet != null && animatorSet.isRunning()) {
                    animatorSet.cancel();
                }
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                ll_model.setVisibility(View.GONE);
                rl_item.setVisibility(View.GONE);
                title_view.setVisibility(View.VISIBLE);
                bottom_view.setVisibility(View.VISIBLE);
                mImvAirconditioner.setVisibility(View.VISIBLE);
                mImvAirconditioner.setImageResource(R.drawable.ic_airconditioner_close);
                mImvSwitch.setImageResource(R.drawable.ic_bt_closed);
                mImvSwitch.setEnabled(true);

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        mCloseAnimatorSet.start();

    }

    /**
     * @Desc: 开启空调
     * @author:liangshan
     */
    private void openAirconditioner() {
        mImvAirconditioner.setImageResource(R.drawable.ic_airconditioner_open);
        mImvSwitch.setImageResource(R.drawable.ic_bt_open);
        ObjectAnimator airconditionerAlpha = ObjectAnimator.ofFloat(ll_model, "alpha", 0.5f, 1f);
        ObjectAnimator switchAlpha = ObjectAnimator.ofFloat(rl_item, "alpha", 0.5f, 1f);
        mOpenAnimatorSet = new AnimatorSet();
        mOpenAnimatorSet.play(airconditionerAlpha).with(switchAlpha);
        mOpenAnimatorSet.setDuration(1000);
        mOpenAnimatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (mCloseAnimatorSet != null && mCloseAnimatorSet.isRunning()) {
                    mCloseAnimatorSet.cancel();
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ll_model.setVisibility(View.VISIBLE);
                rl_item.setVisibility(View.VISIBLE);
//                mTvRt.setVisibility(View.VISIBLE);
                title_view.setVisibility(View.GONE);
                bottom_view.setVisibility(View.GONE);
                mImvSwitch.setImageResource(R.drawable.ic_bt_open);
                mImvAirconditioner.setImageResource(R.drawable.ic_airconditioner_open);
                animatorSet = new AnimatorSet();
                ObjectAnimator tvSwitchAlpha = ObjectAnimator.ofFloat(rl_item, "Alpha", 0, 1);
                ObjectAnimator tvRtAlpha = ObjectAnimator.ofFloat(ll_model, "Alpha", 0, 1);
                animatorSet.play(tvSwitchAlpha).with(tvRtAlpha);
                animatorSet.start();

                mImvSwitch.setEnabled(true);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        mOpenAnimatorSet.start();

    }


    private void stateChange(SmartPartStateTCP state) {
        if (!APP.DeviceId.equals(state.getDevice())) {
            //当消息的中控设备不是当前房子的中控设备
            return;
        }
        if (state.getCommand().getN().equals(mAirConditioner.getCode())) {
            Command command = state.getCommand();
            if (command.getA() != null) {
                mAirConditioner.getState().setA(command.getA());
                initSwitchState(); // 显示开关状态
            }
            if (command.getT() != null) {
                mAirConditioner.getState().setT(Integer.valueOf(command.getT()));
                initTemp(); // 初始化温度
            }
            if (command.getF() != null) {
                mAirConditioner.getState().setF(command.getF());
                initSpeedState();// 初始化风速
            }
            if (command.getM() != null) {
                mAirConditioner.getState().setM(command.getM());
                initModelState();// 初始化模式
            }
            if (command.getRt() != null) {
                mAirConditioner.getState().setRt(command.getRt());
                mTvRt.setText("室温：" + command.getRt() + "℃");
            }
        }
    }

    /**
     * @Desc: 修改风速
     * @author:liangshan
     */
    public void changeF(final String f) {

        final SmartPartCMD smartPartCMD = new SmartPartCMD();
        Command command = new Command();
        command.setN(mAirConditioner.getCode());
        command.setF(f);
        smartPartCMD.setPartId(mPartId);
        smartPartCMD.setCommand(command);
//        smartPartCMD.setPartId(mAirConditioner.getId());
        smartPartCMD.setHouseId(getHouseId());
        if (APP.isExperience){
            mAirConditioner.getState().setF(f + "");
            initSpeedState();
            return;
        }
        TcpClient.getInstacne().sendCMD(this, smartPartCMD, new TCPCMDResult() {
            @Override
            public void onSuccess(String result) {
                mAirConditioner.getState().setF(f + "");
                initSpeedState();
            }
            @Override
            public void onFail(String msg) {
                showToast(msg);
            }
        });
    }

    /**
     * @Desc:改模式
     * @author:liangshan
     */
    public void changM(final String m) {
        final SmartPartCMD smartPartCMD = new SmartPartCMD();
        Command command = new Command();
        command.setN(mAirConditioner.getCode());
        command.setM(m);
//        smartPartCMD.setPartId(mPartId);
        smartPartCMD.setCommand(command);
        smartPartCMD.setPartId(mAirConditioner.getId());
        smartPartCMD.setHouseId(getHouseId());

        if (APP.isExperience){
            mAirConditioner.getState().setM(m + "");
            initModelState();
            return;
        }
        TcpClient.getInstacne().sendCMD(this, smartPartCMD, new TCPCMDResult() {
            @Override
            public void onSuccess(String result) {
                mAirConditioner.getState().setM(m + "");
                initModelState();
            }

            @Override
            public void onFail(String msg) {
                showToast(msg);
            }
        });
    }

    /***
     * 显示风速动画
     */
    public void showAnimation(){
        mRotateAnimation.setDuration(1000);
        mRotateAnimation.setRepeatMode(Animation.RESTART);
        LinearInterpolator lin = new LinearInterpolator();//设置动画匀速运动
        mRotateAnimation.setInterpolator(lin);
        mRotateAnimation.setRepeatCount(-1);
        mRotateAnimation.setFillAfter(false);
        mRotateAnimation.start();
        imv_wind_speed2.setAnimation(mRotateAnimation);


    }


}


