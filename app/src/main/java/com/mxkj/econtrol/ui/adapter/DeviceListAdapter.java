package com.mxkj.econtrol.ui.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.base.CommonAdapter;
import com.mxkj.econtrol.base.CommonViewHolder;
import com.mxkj.econtrol.bean.response.ResGetPatternList;
import com.mxkj.econtrol.bean.response.SmartPart;
import com.mxkj.econtrol.utils.TextUtil;

import java.util.List;

/**
 * Created by liangshan on 2018/7/26.
 * 新主页，设备列表Adapter
 *
 * @Description:
 */

public class DeviceListAdapter extends CommonAdapter<SmartPart> {
    private View.OnClickListener mListener;
    private boolean isEdit = false;

    public DeviceListAdapter(List<SmartPart> mDatas, int mLayoutId, View.OnClickListener listener) {
        super(mDatas, mLayoutId);
        this.mListener = listener;
    }

    public void upDateAdapter(List<SmartPart> mDatas, boolean isEditDevice) {
        this.mDatas = mDatas;
        this.isEdit = isEditDevice;
        notifyDataSetChanged();
    }

    @Override
    public void convert(CommonViewHolder viewHolder, SmartPart smartPart) {
        TextView tv_device_name = viewHolder.getView(R.id.tv_device_name);
        ImageView iv_open = viewHolder.getView(R.id.iv_open);
        ImageView iv_close = viewHolder.getView(R.id.iv_close);
        ImageView iv_settting = viewHolder.getView(R.id.iv_settting);
        ImageView iv_statue_timing = viewHolder.getView(R.id.iv_statue_timing);
        ImageView iv_air_mode = viewHolder.getView(R.id.iv_air_mode);
        ImageView iv_air_speed = viewHolder.getView(R.id.iv_air_speed);
        TextView tv_air_temp = viewHolder.getView(R.id.tv_air_temp);
        RelativeLayout rl_item = viewHolder.getView(R.id.rl_item);
        LinearLayout ll_device_statue = viewHolder.getView(R.id.ll_device_statue);
        tv_device_name.setText(TextUtil.setTest(smartPart.getName()));

        if (isEdit) { // 编辑状态
            iv_open.setVisibility(View.GONE);
            iv_close.setVisibility(View.GONE);
            iv_settting.setVisibility(View.VISIBLE);
            iv_settting.setImageResource(R.drawable.ic_home_device_edit);
        } else {
            if (smartPart.getType().equals("1")) {    //1-灯，2-空调，3-吹风机 4-窗帘 5-门锁 6- 雾化玻璃 7-地暖
                if (smartPart.getFunctions() != null) {
                    if (smartPart.getFunctions().getD() == 1 || smartPart.getFunctions().getRgb() == 1) {// 判断灯光有没有调光功能 // 判断灯光有没有调色功能
                        iv_open.setVisibility(View.VISIBLE);
                        iv_close.setVisibility(View.GONE);
                        iv_settting.setVisibility(View.VISIBLE);
                        iv_settting.setImageResource(R.drawable.ic_home_device_setting);
                    } else {
                        iv_open.setVisibility(View.VISIBLE);
                        iv_close.setVisibility(View.GONE);
                        iv_settting.setVisibility(View.INVISIBLE);
                    }
                } else {
                    iv_open.setVisibility(View.VISIBLE);
                    iv_close.setVisibility(View.GONE);
                    iv_settting.setVisibility(View.INVISIBLE);
                }
                if (smartPart.getFunctions().getD() == 1) {
                    if (smartPart.getState().getD()!=null && !smartPart.getState().getD().equals("00")) {
                        smartPart.getState().setA("01");
                    }else {
                        smartPart.getState().setA("00");
                    }
                }
            } else if (smartPart.getType().equals("2")) { //2-空调
                iv_open.setVisibility(View.VISIBLE);
                iv_close.setVisibility(View.GONE);
                iv_settting.setVisibility(View.VISIBLE);
                iv_settting.setImageResource(R.drawable.ic_home_device_setting);
            } else if (smartPart.getType().equals("3")) { //3-新风
                iv_open.setVisibility(View.VISIBLE);
                iv_close.setVisibility(View.GONE);
                iv_settting.setVisibility(View.VISIBLE);
            } else if (smartPart.getType().equals("4")) { //4-窗帘
                iv_open.setVisibility(View.VISIBLE);
                iv_close.setVisibility(View.VISIBLE);
                iv_settting.setVisibility(View.GONE);
            } else if (smartPart.getType().equals("5")) { // 5-门锁
                iv_open.setVisibility(View.GONE);
                iv_close.setVisibility(View.GONE);
                iv_settting.setVisibility(View.VISIBLE);
                iv_settting.setImageResource(R.drawable.ic_home_device_setting);
            } else if (smartPart.getType().equals("6")) { // 6- 雾化玻璃
                iv_open.setVisibility(View.VISIBLE);
                iv_close.setVisibility(View.GONE);
                iv_settting.setVisibility(View.INVISIBLE);
            } else if (smartPart.getType().equals("7")) { // 7-地暖
                iv_open.setVisibility(View.VISIBLE);
                iv_close.setVisibility(View.GONE);
                iv_settting.setVisibility(View.VISIBLE);
                iv_settting.setImageResource(R.drawable.ic_home_device_setting);
            }
        }

        /*****设备名字下各种状态显示****/
        if (smartPart.getType().equals("1")) {    //1-灯，2-空调，3-吹风机 4-窗帘 5-门锁 6- 雾化玻璃 7-地暖
            ll_device_statue.setVisibility(View.VISIBLE);
            if (smartPart.getStateTimer() != null && smartPart.getStateTimer().equals("1")) { //定时 0未定式 1定时
                iv_statue_timing.setVisibility(View.VISIBLE);
            } else {
                iv_statue_timing.setVisibility(View.GONE);
            }
            iv_air_mode.setVisibility(View.GONE);
            iv_air_speed.setVisibility(View.GONE);
            tv_air_temp.setVisibility(View.GONE);

        } else if (smartPart.getType().equals("2")) { //2-空调
            ll_device_statue.setVisibility(View.VISIBLE);
            if (smartPart.getStateTimer() != null && smartPart.getStateTimer().equals("1")) {//定时 0未定式 1定时
                iv_statue_timing.setVisibility(View.VISIBLE);
            } else {
                iv_statue_timing.setVisibility(View.GONE);
            }

            if (smartPart.getState().getA().equals("01")) { // 打开状态
                iv_air_mode.setVisibility(View.VISIBLE);
                iv_air_speed.setVisibility(View.VISIBLE);
                tv_air_temp.setVisibility(View.VISIBLE);
                String mCurMode = "";//当前模式：01、制冷；02、制热；03、送风；04、除湿；
                String mSpeed = "";//风速，01 高速  02中速  03低速
                if (smartPart.getState() != null) {//空调模式（首页展现的状态）
                    mCurMode = smartPart.getState().getM();
                }
                if (smartPart.getState()!= null) {//空调风速（首页展现的状态）
                    mSpeed = smartPart.getState().getF();
                }
                if (mCurMode.equals("01")) {
                    iv_air_mode.setImageResource(R.drawable.ic_home_device_status_cool);
                } else if (mCurMode.equals("02")) {
                    iv_air_mode.setImageResource(R.drawable.ic_home_device_status_hot);
                } else if (mCurMode.equals("03")) {
                    iv_air_mode.setImageResource(R.drawable.ic_home_device_status_air);
                } else if (mCurMode.equals("04")) {
                    iv_air_mode.setImageResource(R.drawable.ic_home_device_status_dry);
                }
                if (mSpeed.equals("01")) {
                    iv_air_speed.setImageResource(R.drawable.ic_home_device_status_air_windspeed_high);
                } else if (mSpeed.equals("02")) {
                    iv_air_speed.setImageResource(R.drawable.ic_home_device_status_air_windspeed_middle);
                } else if (mSpeed.equals("03")) {
                    iv_air_speed.setImageResource(R.drawable.ic_home_device_status_air_windspeed_low);
                }
                tv_air_temp.setText(TextUtil.setTest(smartPart.getState().getT()+"") + "℃");
            }else {
                iv_air_mode.setVisibility(View.GONE);
                iv_air_speed.setVisibility(View.GONE);
                tv_air_temp.setVisibility(View.GONE);
            }
        } else if (smartPart.getType().equals("4")) { //4-窗帘
            ll_device_statue.setVisibility(View.VISIBLE);
            if (smartPart.getStateTimer() != null && smartPart.getStateTimer().equals("1")) {//定时 0未定式 1定时
                iv_statue_timing.setVisibility(View.VISIBLE);
            } else {
                iv_statue_timing.setVisibility(View.GONE);
            }
            iv_air_mode.setVisibility(View.GONE);
            iv_air_speed.setVisibility(View.GONE);
            tv_air_temp.setVisibility(View.GONE);
        } else {
            ll_device_statue.setVisibility(View.GONE);
        }

        /*****开光状态***/
        if (smartPart.getState().getA().equals("00")) {
            iv_open.setImageResource(R.drawable.in_home_button_off);
//            tv_light_name.setTextColor(Color.parseColor("#999999"));
        } else {
            iv_open.setImageResource(R.drawable.in_home_button_on);
//            tv_light_name.setTextColor(Color.parseColor("#ff695a"));
        }

        /********窗帘开关****/
        if (smartPart.getType().equals("4")) { //4-窗帘
            if (smartPart.getTypeName().equals("opening")) {
                iv_open.setImageResource(R.drawable.ic_home_windowcurtains_stop);
                iv_close.setImageResource(R.drawable.ic_home_windowcurtains_close);
            } else if (smartPart.getTypeName().equals("closeding")) {
                iv_open.setImageResource(R.drawable.ic_home_windowcurtains_ope);
                iv_close.setImageResource(R.drawable.ic_home_windowcurtains_stop);
            } else {
                iv_open.setImageResource(R.drawable.ic_home_windowcurtains_ope);
                iv_close.setImageResource(R.drawable.ic_home_windowcurtains_close);
            }
        }


        int position = viewHolder.getPosition();
        smartPart.setPosition(position);
        iv_open.setTag(smartPart);
        iv_open.setOnClickListener(mListener);
        iv_close.setTag(smartPart);
        iv_close.setOnClickListener(mListener);
        iv_settting.setTag(smartPart);
        iv_settting.setOnClickListener(mListener);
        rl_item.setTag(smartPart);
        rl_item.setOnClickListener(mListener);

    }

}
