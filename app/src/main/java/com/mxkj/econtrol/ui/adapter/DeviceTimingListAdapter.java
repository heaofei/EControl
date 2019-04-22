package com.mxkj.econtrol.ui.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.Config;
import com.mxkj.econtrol.base.CommonAdapter;
import com.mxkj.econtrol.base.CommonViewHolder;
import com.mxkj.econtrol.bean.response.CommentContent;
import com.mxkj.econtrol.bean.response.ResGetScenePartTimerList;
import com.mxkj.econtrol.utils.DateTimeUtil;
import com.mxkj.econtrol.utils.TextUtil;

import java.util.List;

/**
 * Created by liangshan on 2017/4/21.
 *
 * @Description：
 */

public class DeviceTimingListAdapter extends CommonAdapter<ResGetScenePartTimerList.TimerListBean> {
    private View.OnClickListener mListener;
    private boolean isEdit = false; // 是否编辑模式

    public DeviceTimingListAdapter(List<ResGetScenePartTimerList.TimerListBean> mDatas, int mLayoutId, View.OnClickListener listener,boolean isEdit) {
        super(mDatas, mLayoutId);

        mListener = listener;
        this.isEdit = isEdit;
    }
    public void upDateAdapter(List<ResGetScenePartTimerList.TimerListBean> mDatas,boolean isEdit){
        this.mDatas = mDatas;
        this.isEdit = isEdit;
        notifyDataSetChanged();
    }

    @Override
    public void convert(CommonViewHolder viewHolder, ResGetScenePartTimerList.TimerListBean timerListBean) {
        TextView tv_week;
        TextView tv_device_name;
        ImageView iv_switch;
        TextView tv_time;
        TextView tv_right;
        TextView tv_timing_type;
        LinearLayout item_layout;

        tv_week = (TextView) viewHolder.getView(R.id.tv_week);
        tv_device_name = viewHolder.getView(R.id.tv_device_name);
        iv_switch = viewHolder.getView(R.id.iv_switch);
        tv_time = viewHolder.getView(R.id.tv_time);
        tv_timing_type = viewHolder.getView(R.id.tv_timing_type);
        tv_right = viewHolder.getView(R.id.tv_right);
        item_layout = viewHolder.getView(R.id.item_layout);



        tv_week.setText(TextUtil.setTest(timerListBean.getWeek()));
        tv_time.setText(TextUtil.setTest(timerListBean.getTime()));
        tv_device_name.setText(TextUtil.setTest(timerListBean.getName()));

        if (timerListBean.getOpen()==1) {
            tv_timing_type.setText("定时开启");
        }else {
            tv_timing_type.setText("定时关闭");

        }
        if (timerListBean.getStatus().equals("1")) {
            iv_switch.setImageResource(R.drawable.icon_btn_on);
        }else {
            iv_switch.setImageResource(R.drawable.icon_btn_off);
        }

        if (isEdit) {
            iv_switch.setVisibility(View.GONE);
            tv_right.setVisibility(View.VISIBLE);
        }else {
            iv_switch.setVisibility(View.VISIBLE);
            tv_right.setVisibility(View.GONE);
        }
//        view.setOnClickListener(mListener);
        iv_switch.setTag(timerListBean);
        iv_switch.setOnClickListener(mListener);
        item_layout.setTag(timerListBean);
        item_layout.setOnClickListener(mListener);


    }


}
