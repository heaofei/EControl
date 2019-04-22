package com.mxkj.econtrol.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.Config;
import com.mxkj.econtrol.bean.response.AdapterHouseControlLogList;
import com.mxkj.econtrol.bean.response.ResGetPatternDetail;
import com.mxkj.econtrol.utils.DateTimeUtil;
import com.mxkj.econtrol.utils.TextUtil;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by chan on 2018/4/10.
 *
 * @Description: 场景编辑，设备列表Adapter
 */

public class EditDeviceListAdapter extends BaseExpandableListAdapter {

    private List<ResGetPatternDetail.ScenePartBean> mDatas;
    private Context mContext;

    public EditDeviceListAdapter(Context context, List<ResGetPatternDetail.ScenePartBean> mDatas) {
        this.mContext = context;
        this.mDatas = mDatas;
    }

    @Override
    public int getGroupCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mDatas.get(groupPosition) == null ? 0 : mDatas.get(groupPosition).getPartSetting().size();
    }
    @Override
    public Object getGroup(int groupPosition) {
        return mDatas.get(groupPosition);
    }


    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mDatas.get(groupPosition).getPartSetting().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.adapter_editlist_group_item, parent, false);
        }
        TextView tv_group_name = (TextView) view.findViewById(R.id.tv_group_name);
        TextView tv_count = (TextView) view.findViewById(R.id.tv_count);
        ImageView iv_group_item = (ImageView) view.findViewById(R.id.iv_group_item);
        RelativeLayout rl_group_item = (RelativeLayout) view.findViewById(R.id.rl_group_item);
        View viewline = (View) view.findViewById(R.id.viewline);

        tv_group_name.setText(TextUtil.setTest(mDatas.get(groupPosition).getName()));


        int size =0 ;
        if (mDatas.get(groupPosition).getPartSetting().size()>0) {
            for (int i = 0; i < mDatas.get(groupPosition).getPartSetting().size(); i++) {
                if (mDatas.get(groupPosition).getPartSetting().get(i).getSwitchStatus().equals("1")) {
                    size = size+1;
                }
            }
        }
        if (size==0) {
            tv_count.setVisibility(View.GONE);
        }else {
            tv_count.setText(size+"");
            tv_count.setVisibility(View.VISIBLE);
        }

      /*  rl_group_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDatas.get(groupPosition).isClick()) {
                    mDatas.get(groupPosition).setClick(false);
                }else {
                    mDatas.get(groupPosition).setClick(true);
                }
                notifyDataSetChanged();
            }
        });
        if (mDatas.get(groupPosition).isClick()) {
            iv_group_item.setImageResource(R.drawable.home_edit_choose_selected);
        }else {
            iv_group_item.setImageResource(R.drawable.center_enter);
        }*/

        return view;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.adapter_editlist_child_item, parent, false);
            AutoUtils.auto(view);
        }
        TextView tv_child_name = (TextView) view.findViewById(R.id.tv_child_name);
        ImageView iv_child_item = (ImageView) view.findViewById(R.id.iv_child_item);
        RelativeLayout rl_child_item = (RelativeLayout) view.findViewById(R.id.rl_child_item);

        final ResGetPatternDetail.PartSettingBean partSettingBean = mDatas.get(groupPosition).getPartSetting().get(childPosition);

//        String time = DateTimeUtil.getTime(System.currentTimeMillis(), logList.getCreateTime());

        String times = "";

        tv_child_name.setText(TextUtil.setTest(partSettingBean.getPartName()));

        rl_child_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (partSettingBean.getSwitchStatus().equals("1")) {
//                    partSettingBean.setClick(false);
                    partSettingBean.setSwitchStatus("0");
                }else {
//                    partSettingBean.setClick(true);
                    partSettingBean.setSwitchStatus("1");
                }
                notifyDataSetChanged();
            }
        });

        if (partSettingBean.getSwitchStatus().equals("1")) { // 0关  1开
            iv_child_item.setImageResource(R.drawable.home_edit_choose_selected32);
        }else {
            iv_child_item.setImageResource(R.drawable.home_edit_choose_default32);
        }
        /*if (partSettingBean.isClick()) {
            iv_child_item.setImageResource(R.drawable.home_edit_choose_selected);
        }else {
            iv_child_item.setImageResource(R.drawable.home_edit_choose_default);
        }*/

        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
