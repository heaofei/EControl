package com.mxkj.econtrol.ui.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.base.CommonAdapter;
import com.mxkj.econtrol.base.CommonViewHolder;
import com.mxkj.econtrol.bean.response.ResGetPatternDetail;
import com.mxkj.econtrol.bean.response.ResGetPatternList;

import java.util.List;

/**
 * Created by chanjun on 2017/9/6.
 * @Description:
 */

public class SceneEditListAdapter extends CommonAdapter<ResGetPatternList.ListBeaner> {
    private boolean isEdit = false;

    public SceneEditListAdapter(List<ResGetPatternList.ListBeaner> mDatas, int mLayoutId) {
        super(mDatas, mLayoutId);
    }


    public void upDateAdapter(List<ResGetPatternList.ListBeaner> mDatas){
        this.mDatas =mDatas;
        notifyDataSetChanged();

    }
    @Override
    public void convert(CommonViewHolder viewHolder, ResGetPatternList.ListBeaner listBeaner) {
        TextView tv_room_name = viewHolder.getView(R.id.tv_room_name);
        /*TextView tv_select_house_estate = viewHolder.getView(R.id.tv_select_house_estate);

        if (viewHolder.getPosition()==0 || viewHolder.getPosition()==1){
            tv_select_house_estate.setVisibility(View.GONE);
        }else {
            tv_select_house_estate.setVisibility(View.VISIBLE);
        }*/
        if (TextUtils.isEmpty(listBeaner.getMsName())) {
                tv_room_name.setText(listBeaner.getName());
        }else {
            tv_room_name.setText(listBeaner.getMsName());
        }
    }
}
