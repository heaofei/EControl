package com.mxkj.econtrol.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.base.CommonAdapter;
import com.mxkj.econtrol.base.CommonViewHolder;
import com.mxkj.econtrol.bean.response.ResGetHouseAllPartList;
import com.mxkj.econtrol.bean.response.Room;
import com.mxkj.econtrol.bean.response.SmartPart;

import java.util.List;

/**
 * Created by chanjun on 2017/9/6.
 * @Description:
 */

public class SceneEditRoomLightAdapter extends CommonAdapter<ResGetHouseAllPartList.SceneListBean.PartListBean> {
    private boolean isEdit = false;

    public SceneEditRoomLightAdapter(List<ResGetHouseAllPartList.SceneListBean.PartListBean> mDatas, int mLayoutId) {
        super(mDatas, mLayoutId);
    }

    public void upDateAdapter(List<ResGetHouseAllPartList.SceneListBean.PartListBean> mDatas, boolean isEdit){
        this.mDatas =mDatas;
        this.isEdit =isEdit;
        notifyDataSetChanged();

    }
    @Override
    public void convert(CommonViewHolder viewHolder, final ResGetHouseAllPartList.SceneListBean.PartListBean partListBean) {
        TextView tv_name = viewHolder.getView(R.id.tv_name);
        LinearLayout item_layout = viewHolder.getView(R.id.item_layout);
        ImageView iv_select_light_estate = viewHolder.getView(R.id.iv_select_light_estate);

        if (partListBean.getSwitchStatus().equals("1")){
            iv_select_light_estate.setImageResource(R.drawable.home_edit_choose_selected);
        }else {
            iv_select_light_estate.setImageResource(R.drawable.home_edit_choose_default);
        }

        tv_name.setText(partListBean.getName());

        iv_select_light_estate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (partListBean.getSwitchStatus().equals("1")){
                    partListBean.setSwitchStatus("0");
                }else {
                    partListBean.setSwitchStatus("1");
                }
                notifyDataSetChanged();
            }
        });
        item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (partListBean.getSwitchStatus().equals("1")){
                    partListBean.setSwitchStatus("0");
                }else {
                    partListBean.setSwitchStatus("1");
                }
                notifyDataSetChanged();
            }
        });

    }
}
