package com.mxkj.econtrol.ui.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.base.CommonAdapter;
import com.mxkj.econtrol.base.CommonViewHolder;
import com.mxkj.econtrol.bean.response.SmartPart;

import java.util.List;

/**
 * Created by chan on 2018/2/2.
 *
 * @Description:
 */

public class AGSwitchAdapter extends CommonAdapter<SmartPart> {
    private boolean isEdit = false;

    public AGSwitchAdapter(List<SmartPart> mDatas, int mLayoutId) {
        super(mDatas, mLayoutId);
    }

    public void upDateAdapter(List<SmartPart> mDatas, boolean isEdit){
        this.mDatas =mDatas;
        this.isEdit =isEdit;
        notifyDataSetChanged();

    }
    @Override
    public void convert(CommonViewHolder viewHolder, SmartPart s) {
        ImageView imv_ag_state = viewHolder.getView(R.id.imv_ag_state);
        ImageView iv_edit = viewHolder.getView(R.id.iv_edit);
        TextView tv_ag_name = viewHolder.getView(R.id.tv_ag_name);
        RelativeLayout rl_item = viewHolder.getView(R.id.rl_item);

        tv_ag_name.setText(s.getName());

        if(s.getState().getA().equals("00")){
            imv_ag_state.setImageResource(R.drawable.ic_window_default);
            tv_ag_name.setTextColor(Color.parseColor("#999999"));
            rl_item.setBackgroundResource(R.drawable.layout_ag_radius_gray_bg);
        }else{
            imv_ag_state.setImageResource(R.drawable.ic_window_selected);
            tv_ag_name.setTextColor(Color.parseColor("#ff695a"));
            rl_item.setBackgroundResource(R.drawable.layout_ag_radius_reg_bg);
        }
        if (isEdit){
            iv_edit.setVisibility(View.VISIBLE);
            tv_ag_name.setVisibility(View.INVISIBLE);
        }else {
            iv_edit.setVisibility(View.INVISIBLE);
            tv_ag_name.setVisibility(View.VISIBLE);
        }

    }
}
