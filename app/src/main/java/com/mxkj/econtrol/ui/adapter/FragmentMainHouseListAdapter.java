package com.mxkj.econtrol.ui.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.base.CommonAdapter;
import com.mxkj.econtrol.base.CommonViewHolder;
import com.mxkj.econtrol.bean.response.MyHouse;
import com.mxkj.econtrol.utils.SharedPreferencesUtils;
import com.mxkj.econtrol.view.activity.HouseUserListActivity;

import java.util.List;

/**
 * Created by chanjun on 2017/8/4.
 * 房子列表Adapter
 * @Description:
 */

public class FragmentMainHouseListAdapter extends CommonAdapter<MyHouse> {
    public FragmentMainHouseListAdapter(List<MyHouse> mDatas, int mLayoutId) {
        super(mDatas, mLayoutId);
    }

    public void upDateAdapter(List<MyHouse> mDatas){
        this.mDatas =mDatas;
        notifyDataSetChanged();

    }

    @Override
    public void convert(final CommonViewHolder viewHolder, final MyHouse myHouse) {
        TextView tv_name = viewHolder.getView(R.id.tv_name);
        RelativeLayout rl_item = viewHolder.getView(R.id.rl_item);

        tv_name.setText( myHouse.getHousingEstate()+"-"+myHouse.getBuilding()+"-"+myHouse.getHouseNo());
        String id = myHouse.getId();
        String id02 = (String) SharedPreferencesUtils.getParam(APP.getInstance(),"houseId","");
        if (!TextUtils.isEmpty(myHouse.getId()) && myHouse.getId().equals((String) SharedPreferencesUtils.getParam(APP.getInstance(),"houseId",""))){
            tv_name.setTextColor(Color.parseColor("#ff695a")); // 选中的房间名字改颜色
        }else {
            tv_name.setTextColor(Color.parseColor("#999999"));
        }
        if (mDatas.indexOf(myHouse)==0){
            rl_item.setBackgroundResource(R.drawable.photo_gallery_selector); //
        }else {
            rl_item.setBackgroundResource(R.drawable.white_gray_bg_selector); // 普通点击效果
        }

    }
}
