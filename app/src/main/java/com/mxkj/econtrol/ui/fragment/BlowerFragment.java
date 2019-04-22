package com.mxkj.econtrol.ui.fragment;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.gson.Gson;
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

/**
 * Created by liangshan on 2017/3/31.
 *
 * @Description:
 */

public class BlowerFragment extends BaseFragment {
    private SmartPart mBlower;
    private View title_view;
    private View bottom_view;
    private TextView mTvPM; //pm值
    private TextView tv_wind_pm;
    private String mDevice; // 这个是部件id
    private ImageView mImvSwitch;
    private ImageView imv_blower2;
    private ImageView mImvBlower;
    private FrameLayout mRlWindSpeed;
    private FrameLayout fl_blower;
    private SeekBar win_seekbar;
    private ImageView iv_point_low, iv_point_midle, iv_point_high;
    private TextView tv_point_low, tv_point_midle, tv_point_high;
    private RotateAnimation mRotateAnimation;
    private int mProgress = 0;//  SeekBar 进度值

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_blower, container, false);
        super.createView(view);
        return view;
    }

    @Override
    public void initView() {
        imv_blower2 = findView(R.id.imv_blower2);
        mImvSwitch = findView(R.id.imv_switch);
        mImvBlower = findView(R.id.imv_blower);
        title_view = findView(R.id.title_view);
        mTvPM = findView(R.id.tv_pm);
        bottom_view = findView(R.id.bottom_view);
        tv_wind_pm = findView(R.id.tv_wind_pm);
        mRlWindSpeed = findView(R.id.fl_wind_speed);
        fl_blower = findView(R.id.fl_blower);
        win_seekbar = findView(R.id.win_seekbar);
        iv_point_low = findView(R.id.iv_point_low);
        iv_point_midle = findView(R.id.iv_point_midle);
        iv_point_high = findView(R.id.iv_point_high);
        tv_point_low = findView(R.id.tv_point_low);
        tv_point_midle = findView(R.id.tv_point_midle);
        tv_point_high = findView(R.id.tv_point_high);
        mDevice = getArguments().getString("device");

    }

    @Override
    public void initData() {
        mBlower = (SmartPart) getArguments().getSerializable("smartPart");
        mDevice = getArguments().getString("device");
        SmartPartState state = mBlower.getState();
        if (state.getA().equals("01")) {                  // 开状态
            mImvSwitch.setImageResource(R.drawable.ic_bt_open);
            mRlWindSpeed.setVisibility(View.VISIBLE);
            fl_blower.setVisibility(View.VISIBLE);
            mTvPM.setVisibility(View.VISIBLE);
            title_view.setVisibility(View.GONE);
            bottom_view.setVisibility(View.GONE);
            tv_wind_pm.setVisibility(View.VISIBLE);
            if (TextUtils.isEmpty(state.getPm())) {
                mTvPM.setText("--");
            } else {
                mTvPM.setText("" + state.getPm());
            }
            mImvBlower.setVisibility(View.VISIBLE);
            setWindSpeedState(state.getF());

        } else if (state.getA().equals("00")) {          // 关状态
            mRlWindSpeed.setVisibility(View.GONE);
            mTvPM.setVisibility(View.GONE);
            title_view.setVisibility(View.VISIBLE);
            bottom_view.setVisibility(View.VISIBLE);
            tv_wind_pm.setVisibility(View.GONE);
            mImvBlower.setVisibility(View.VISIBLE);
            mImvSwitch.setImageResource(R.drawable.ic_bt_closed);
        }

    }

    @Override
    public void initListener() {
        mImvSwitch.setOnClickListener(this);
        win_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mProgress = progress;
                iv_point_low.setVisibility(View.VISIBLE);
                iv_point_midle.setVisibility(View.VISIBLE);
                iv_point_high.setVisibility(View.VISIBLE);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (mProgress >= 0 && mProgress <= 7) {
                    win_seekbar.setProgress(0);
                    changeF("03");
                    onRefreshSeekBar(3);
                } else if (mProgress > 7 && mProgress < 14) {
                    win_seekbar.setProgress(10);
                    changeF("02");
                    onRefreshSeekBar(2);
                } else if (mProgress >= 14) {
                    win_seekbar.setProgress(20);
                    changeF("01");
                    onRefreshSeekBar(1);
                }
            }
        });
    }

    /***
     * 刷新SeekBar滑动视图
     *
     * @param type 风速档位 1 高 2 中 3 低
     */
    public void onRefreshSeekBar(int type) {
        if (type == 1) { // 高速
            iv_point_low.setVisibility(View.VISIBLE);
            iv_point_midle.setVisibility(View.VISIBLE);
            iv_point_high.setVisibility(View.GONE);
        } else if (type == 2) { // 中速
            iv_point_low.setVisibility(View.VISIBLE);
            iv_point_midle.setVisibility(View.GONE);
            iv_point_high.setVisibility(View.VISIBLE);
        } else if (type == 3) { // 低速
            iv_point_low.setVisibility(View.GONE);
            iv_point_midle.setVisibility(View.VISIBLE);
            iv_point_high.setVisibility(View.VISIBLE);
        }
    }

    /**
     * @Desc: 初始化开关状态
     * @author:liangshan
     */
    public void initSwitch() {
        SmartPartState state = mBlower.getState();
        if (state.getA().equals("01")) {
            openBlower();
            mImvSwitch.setImageResource(R.drawable.ic_bt_open);
            if (state.getF() != null && state.getF().equals("01")) {// 风速01：高；02：中；03：低

                showAnimation("01"); // 显示风速动画
            } else if (state.getF() != null && state.getF().equals("02")) {
                showAnimation("02"); // 显示风速动画
            } else if (state.getF() != null && state.getF().equals("03")) {
                showAnimation("03"); // 显示风速动画
            }


        } else if (state.getA().equals("00")) {
            closeBlower();
            if (mRotateAnimation!=null) {
                mRotateAnimation.cancel();
            }
        }
    }

    /**
     * @Desc:关闭新风机。
     * @author:liangshan
     */
    private void closeBlower() {

        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator windSpeedAlpha = ObjectAnimator.ofFloat(mRlWindSpeed, "Alpha", 1, 0);
        ObjectAnimator PMAlpha = ObjectAnimator.ofFloat(mTvPM, "Alpha", 1, 0);
        ObjectAnimator switchAlpha = ObjectAnimator.ofFloat(mImvSwitch, "Alpha", 1, 0.5f, 1);
        animatorSet.play(windSpeedAlpha).with(switchAlpha).with(PMAlpha);
        animatorSet.setDuration(1000);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mRlWindSpeed.setVisibility(View.GONE);
                mTvPM.setVisibility(View.GONE);
                title_view.setVisibility(View.VISIBLE);
                bottom_view.setVisibility(View.VISIBLE);
                tv_wind_pm.setVisibility(View.GONE);
                mImvBlower.setVisibility(View.VISIBLE);
                mImvSwitch.setImageResource(R.drawable.ic_bt_closed);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.start();
    }

    /**
     * @Desc: 开启新风机
     * @author:liangshan
     */
    private void openBlower() {
        mImvSwitch.setImageResource(R.drawable.ic_bt_open);
        ObjectAnimator imvBlowerAlpha = ObjectAnimator.ofFloat(mImvBlower, "alpha", 0, 1f);
        ObjectAnimator switchAlpha = ObjectAnimator.ofFloat(mImvSwitch, "alpha", 0.5f, 1f);
        ObjectAnimator PMAlpha = ObjectAnimator.ofFloat(mTvPM, "alpha", 0, 1f);
        AnimatorSet set = new AnimatorSet();
        set.play(imvBlowerAlpha).with(switchAlpha).with(PMAlpha);
        set.setDuration(1000);
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mRlWindSpeed.setVisibility(View.VISIBLE);
                mTvPM.setVisibility(View.VISIBLE);
                title_view.setVisibility(View.GONE);
                bottom_view.setVisibility(View.GONE);
                tv_wind_pm.setVisibility(View.VISIBLE);
                mImvBlower.setVisibility(View.VISIBLE);
                ObjectAnimator.ofFloat(mRlWindSpeed, "alpha", 0, 1f).setDuration(2000).start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        set.start();

    }

    /**
     * @Desc: 设置风速状态
     * @author:liangshan
     */
    private void setWindSpeedState(String speed) {
        tv_point_low.setTextColor(getResources().getColor(R.color.text_black_c3c3c3));
        tv_point_midle.setTextColor(getResources().getColor(R.color.text_black_c3c3c3));
        tv_point_high.setTextColor(getResources().getColor(R.color.text_black_c3c3c3));
        switch (speed) {
            case "01":
                tv_point_high.setTextColor(getResources().getColor(R.color.text_orangereg02));
                win_seekbar.setProgress(20);

                showAnimation("01"); // 显示风速动画
                onRefreshSeekBar(1);
                break;
            case "02":
                tv_point_midle.setTextColor(getResources().getColor(R.color.text_orangereg02));
                win_seekbar.setProgress(10);

                showAnimation("02"); // 显示风速动画
                onRefreshSeekBar(2);
                break;
            case "03":
                tv_point_low.setTextColor(getResources().getColor(R.color.text_orangereg02));
                win_seekbar.setProgress(0);

                showAnimation("03"); // 显示风速动画
                onRefreshSeekBar(3);

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
        command.setN(mBlower.getCode());
        if (mBlower.getState().getA().equals("00")) {
            command.setA("01");
        } else {
            command.setA("00");
        }
//        smartPartCMD.setPartId(mDevice);
        smartPartCMD.setCommand(command);
        smartPartCMD.setPartId(mBlower.getId());
        smartPartCMD.setHouseId(getHouseId());
        if (APP.isExperience) { // 体验账号下， 不做实际指令发送
            if (mBlower.getState().getA().equals("00")) {
                mBlower.getState().setA("01");
            } else {
                mBlower.getState().setA("00");
            }
            initSwitch();
            return;
        }
        TcpClient.getInstacne().sendCMD(this, smartPartCMD, new TCPCMDResult() {
            @Override
            public void onSuccess(String result) {
                if (mBlower.getState().getA().equals("00")) {
                    mBlower.getState().setA("01");
                } else {
                    mBlower.getState().setA("00");
                }
//                initSwitch();
            }

            @Override
            public void onFail(String msg) {
                showToast(msg);
            }
        });
    }

    public static String getHouseId() {
        return (String) SharedPreferencesUtils.getParam(APP.getInstance(), "houseId", "");
    }

    /**
     * @Desc: 修改风速
     * @author:liangshan
     */
    public void changeF(final String f) {

        final SmartPartCMD smartPartCMD = new SmartPartCMD();
        Command command = new Command();
        command.setN(mBlower.getCode());
        command.setF(f);
        smartPartCMD.setPartId(mDevice);
        smartPartCMD.setCommand(command);

        if (APP.isExperience) {  // 体验账号下， 不做实际指令发送
            mBlower.getState().setF(f);
            setWindSpeedState(f);
            return;
        }

        TcpClient.getInstacne().sendCMD(this, smartPartCMD, new TCPCMDResult() {
            @Override
            public void onSuccess(String result) {
                mBlower.getState().setF(f);
                setWindSpeedState(f);
            }

            @Override
            public void onFail(String msg) {
                showToast(msg);
            }
        });
    }

    private long exitTime = 0;

    @Override
    public void HandleUIMessage(EventBusUIMessage msg) {
        super.HandleUIMessage(msg);
        switch (msg.getMsgType()) {
            case Msg.SAMRT_PART_STATE_CHANGE:
                LogUtil.i("state:" + msg.getData());
                String contenew = new Gson().toJson((SmartPartStateTCP) msg.getData());
                if ((System.currentTimeMillis() - exitTime) > 2000) {
                    stateChange((SmartPartStateTCP) msg.getData());
                }
                break;
        }
    }

    private void stateChange(SmartPartStateTCP state) {
        if (!APP.DeviceId.equals(state.getDevice())) {
            //当消息的中控设备不是当前房子的中控设备
            return;
        }
        if (state.getCommand().getN().equals(mBlower.getCode())) {
            Command command = state.getCommand();
            if (command.getA() != null) {
                mBlower.getState().setA(command.getA());
                initSwitch();
            }

            if (command.getF() != null) {
                mBlower.getState().setF(command.getF());
                setWindSpeedState(command.getF());
            }
            if (command.getPm() != null) {
                mBlower.getState().setPm(command.getPm());
                mTvPM.setText("PM值：" + command.getPm());
            }
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.imv_switch:
                changeA();
                break;
        }
    }

    /***
     * 显示风速动画
     */
    public void showAnimation(String type) {

        imv_blower2.clearAnimation();
        if (mRotateAnimation==null) {
        mRotateAnimation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        }
        mRotateAnimation.setRepeatMode(Animation.RESTART);
        LinearInterpolator lin = new LinearInterpolator();//设置动画匀速运动
        mRotateAnimation.setInterpolator(lin);
        mRotateAnimation.setRepeatCount(-1);
        mRotateAnimation.setFillAfter(false);

        if (type.equals("03")) {
            mRotateAnimation.setDuration(1500);
            mRotateAnimation.start();
            imv_blower2.setAnimation(mRotateAnimation); // 设置旋转动画  (低速)
//            mRotateAnimation.startNow();

        } else if (type.equals("02")) {
            mRotateAnimation.setDuration(1200);
            mRotateAnimation.start();
            imv_blower2.setAnimation(mRotateAnimation); // 设置旋转动画  (中速)

        } else if (type.equals("01")) {
            mRotateAnimation.setDuration(800);
            mRotateAnimation.start();
            imv_blower2.setAnimation(mRotateAnimation); // 设置旋转动画  (高速)
        }



    }

}
