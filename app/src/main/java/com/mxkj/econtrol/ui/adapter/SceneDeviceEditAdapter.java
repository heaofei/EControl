package com.mxkj.econtrol.ui.adapter;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.base.CommonAdapter;
import com.mxkj.econtrol.base.CommonViewHolder;
import com.mxkj.econtrol.bean.response.ResGetPatternList;
import com.mxkj.econtrol.bean.response.ResGetScenePartList;
import com.mxkj.econtrol.bean.response.SmartPart;
import com.mxkj.econtrol.utils.AbToastUtil;
import com.mxkj.econtrol.utils.LogUtil;
import com.mxkj.econtrol.utils.TextUtil;

import java.util.List;

/**
 * Created by chanjun on 2018/10/15.
 *
 * @Description:
 */

public class SceneDeviceEditAdapter extends CommonAdapter<SmartPart> {
    private boolean isEdit = false;
    private boolean isSeekBarChange = false;
    private View.OnClickListener mListener;

    public SceneDeviceEditAdapter(List<SmartPart> mDatas, int mLayoutId, View.OnClickListener listener) {
        super(mDatas, mLayoutId);
        this.mListener = listener;
    }


    public void upDateAdapter(List<SmartPart> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();

    }

    @Override
    public void convert(CommonViewHolder viewHolder, final SmartPart smartPart) {

        TextView tv_room_name = viewHolder.getView(R.id.tv_room_name);
        final ImageView iv_close = viewHolder.getView(R.id.iv_close);
        final SeekBar seekbar_light = viewHolder.getView(R.id.seekbar_light);
        LinearLayout ll_brightness = viewHolder.getView(R.id.ll_brightness);
        tv_room_name.setText(TextUtil.setTest(smartPart.getName()));// 设备名字

        if (smartPart.getFunctions() != null) {
            if (smartPart.getFunctions().getD() == 1) {// 判断灯光有没有调光功能 // 判断灯光有没有调色功能
                ll_brightness.setVisibility(View.VISIBLE);
            } else {
                ll_brightness.setVisibility(View.GONE);
            }
        } else {
            ll_brightness.setVisibility(View.GONE);
        }

        if (smartPart.getFunctions().getD() == 1) {
            String d = smartPart.getState().getD();
            if (smartPart.getState().getD() != null && !smartPart.getState().getD().equals("00")) {
                smartPart.getState().setA("01");
                try {
                    int progress = Integer.valueOf(smartPart.getState().getD()) + 1;
                    seekbar_light.setProgress(progress * 10);
                } catch (Exception e) {

                }
            } else {
                smartPart.getState().setA("00");
                seekbar_light.setProgress(0);
            }
        }


        /*****开光状态***/
        if (smartPart.getState().getA().equals("00")) {
            iv_close.setImageResource(R.drawable.in_home_button_off);
//            tv_light_name.setTextColor(Color.parseColor("#999999"));
        } else {
            iv_close.setImageResource(R.drawable.in_home_button_on);
//            tv_light_name.setTextColor(Color.parseColor("#ff695a"));
        }
        // 档位选择监听
        seekbar_light.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (isSeekBarChange) {
                    if (progress >= 0 && progress <= 99) {
                        if (progress == 0) {
                            iv_close.setImageResource(R.drawable.in_home_button_off);
                        } else if (progress > 0) {
                            iv_close.setImageResource(R.drawable.in_home_button_on);
                        }
                    }
                    onSeekBarChangeListner.onCall(seekBar, progress, smartPart.getCode(), smartPart.getId());
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isSeekBarChange = true;
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                isSeekBarChange = false;
            }
        });
        int position = viewHolder.getPosition();
        smartPart.setPosition(position);
        iv_close.setTag(smartPart);
        iv_close.setOnClickListener(mListener);

    }


    //要定义一个按钮监听抽象接口和时间
    public interface OnSeekBarChangeListner {
        void onCall(View view, int position, String driveCode, String partId);
    }

    //定义一个监听 再activity中调用
    private OnSeekBarChangeListner onSeekBarChangeListner;

    public void setOnItemChrldListner(OnSeekBarChangeListner onSeekBarChangeListner) {
        this.onSeekBarChangeListner = onSeekBarChangeListner;
    }

}
