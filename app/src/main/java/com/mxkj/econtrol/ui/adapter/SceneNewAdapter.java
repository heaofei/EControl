package com.mxkj.econtrol.ui.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.base.CommonAdapter;
import com.mxkj.econtrol.base.CommonViewHolder;
import com.mxkj.econtrol.bean.response.MyHouse;
import com.mxkj.econtrol.bean.response.ResGetHouseAllPartList;
import com.mxkj.econtrol.bean.response.Room;
import com.mxkj.econtrol.bean.response.SmartPart;

import java.util.List;

/**
 * Created by chanjun on 2017/9/6.
 * @Description:
 */

public class SceneNewAdapter extends CommonAdapter<ResGetHouseAllPartList.SceneListBean> {
    private boolean isEdit = false;

    public SceneNewAdapter(List<ResGetHouseAllPartList.SceneListBean> mDatas, int mLayoutId) {
        super(mDatas, mLayoutId);
    }

    public void upDateAdapter(List<ResGetHouseAllPartList.SceneListBean> mDatas, boolean isEdit){
        this.mDatas =mDatas;
        this.isEdit =isEdit;
        notifyDataSetChanged();

    }
    @Override
    public void convert(CommonViewHolder viewHolder, ResGetHouseAllPartList.SceneListBean sceneListBean) {
        TextView tv_room_name = viewHolder.getView(R.id.tv_room_name);
        TextView tv_select_house_estate = viewHolder.getView(R.id.tv_select_house_estate);


        tv_room_name.setText(sceneListBean.getName());
    }
}
