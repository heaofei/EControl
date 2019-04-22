package com.mxkj.econtrol.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.base.CommonAdapter;
import com.mxkj.econtrol.base.CommonViewHolder;
import com.mxkj.econtrol.bean.response.ResGetPatternDetail;
import com.mxkj.econtrol.bean.response.SmartPart;

import java.util.List;

/**
 * Created by chanjun on 2017/9/6.
 * @Description:
 */

public class SceneEditRoomLightAdapter02 extends CommonAdapter<ResGetPatternDetail.PartSettingBean> {
    private boolean isEdit = false;

    public SceneEditRoomLightAdapter02(List<ResGetPatternDetail.PartSettingBean> mDatas, int mLayoutId) {
        super(mDatas, mLayoutId);
    }

    public void upDateAdapter(List<ResGetPatternDetail.PartSettingBean> mDatas, boolean isEdit){
        this.mDatas =mDatas;
        this.isEdit =isEdit;
        notifyDataSetChanged();

    }
    @Override
    public void convert(CommonViewHolder viewHolder, final ResGetPatternDetail.PartSettingBean partSettingBean) {
        TextView tv_name = viewHolder.getView(R.id.tv_name);
        LinearLayout item_layout = viewHolder.getView(R.id.item_layout);
        ImageView iv_select_light_estate = viewHolder.getView(R.id.iv_select_light_estate);

        String switchStatus = partSettingBean.getSwitchStatus(); // // 0关  1开
        if (switchStatus.equals("1")){
            iv_select_light_estate.setImageResource(R.drawable.home_edit_choose_selected);
        }else {
            iv_select_light_estate.setImageResource(R.drawable.home_edit_choose_default);
        }
        tv_name.setText(partSettingBean.getPartName());

        iv_select_light_estate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (partSettingBean.getSwitchStatus().equals("1")){
                    partSettingBean.setSwitchStatus("0");
                }else {
                    partSettingBean.setSwitchStatus("1");
                }
                notifyDataSetChanged();
            }
        });

        item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (partSettingBean.getSwitchStatus().equals("1")){
                    partSettingBean.setSwitchStatus("0");
                }else {
                    partSettingBean.setSwitchStatus("1");
                }
                notifyDataSetChanged();
            }
        });
    }
}
