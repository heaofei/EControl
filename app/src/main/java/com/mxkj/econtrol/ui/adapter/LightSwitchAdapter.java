package com.mxkj.econtrol.ui.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.base.CommonAdapter;
import com.mxkj.econtrol.base.CommonViewHolder;
import com.mxkj.econtrol.bean.response.Room;
import com.mxkj.econtrol.bean.response.SmartPart;

import java.util.List;

/**
 * Created by liangshan on 2017/3/31.
 *
 * @Description:
 */

public class LightSwitchAdapter extends CommonAdapter<SmartPart> {
    private View.OnClickListener mListener;

    public LightSwitchAdapter(List<SmartPart> mDatas, int mLayoutId,View.OnClickListener listener) {
        super(mDatas, mLayoutId);
        this.mListener =listener;
    }

    public void upDateAdapter(List<SmartPart> mDatas ){
        this.mDatas =mDatas;
        notifyDataSetChanged();

    }
    @Override
    public void convert(CommonViewHolder viewHolder, SmartPart contentBean) {
        TextView tv_light_name = viewHolder.getView(R.id.tv_light_name);
        ImageView iv_open = viewHolder.getView(R.id.iv_open);
        RelativeLayout rl_item = viewHolder.getView(R.id.rl_item);
        ImageView iv_statue = viewHolder.getView(R.id.iv_statue);
        ImageView imv_light_state = viewHolder.getView(R.id.imv_light_state);

        tv_light_name.setText(contentBean.getName());
        ImageView imageView = viewHolder.getView(R.id.imv_light_state);
        if(contentBean.getState().getA().equals("00")){
            iv_statue.setImageResource(R.drawable.iv_light_state_off);
            iv_open.setImageResource(R.drawable.ic_light_turnoff);
//            tv_light_name.setTextColor(Color.parseColor("#999999"));
        }else{
            iv_statue.setImageResource(R.drawable.iv_light_state_on);
            iv_open.setImageResource(R.drawable.ic_light_turnon);
//            tv_light_name.setTextColor(Color.parseColor("#ff695a"));
        }

        if (contentBean.getFunctions()!=null && contentBean.getFunctions().getRgb()==1) {
            imv_light_state.setImageResource(R.drawable.light_tiaose);
        }else if(contentBean.getFunctions()!=null &&  contentBean.getFunctions().getD()==1){
            imv_light_state.setImageResource(R.drawable.light_tiaoguang);
        }else {
            imv_light_state.setImageResource(R.drawable.light_ordinary);
        }

        iv_open.setTag(contentBean);
        iv_open.setOnClickListener(mListener);

        int position = viewHolder.getPosition();
        contentBean.setPosition(position);
        rl_item.setTag(contentBean);
        rl_item.setOnClickListener(mListener);


    }
}
