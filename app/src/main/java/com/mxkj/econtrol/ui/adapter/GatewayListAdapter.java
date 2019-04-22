package com.mxkj.econtrol.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.base.CommonAdapter;
import com.mxkj.econtrol.base.CommonViewHolder;
import com.mxkj.econtrol.utils.gateway.DeviceInfo;

import java.util.List;

/**
 * Created by chanjun on 2018/1/15.
 *  搜索
 */

public class GatewayListAdapter extends CommonAdapter<DeviceInfo> {

    private Context context;
    private View.OnClickListener mListener;

    public GatewayListAdapter(Context context, List<DeviceInfo> mDatas, int mLayoutId, View.OnClickListener listener) {
        super(mDatas, mLayoutId);
        this.context = context;
        this.mListener =listener;
    }


    public void upDateAdapter(List<DeviceInfo> mDatas){
        this.mDatas =mDatas;
        notifyDataSetChanged();
    }

    @Override
    public void convert(CommonViewHolder viewHolder, DeviceInfo contentBean) {

        TextView tv_name = viewHolder.getView(R.id.tv_name);
        TextView tv_add = viewHolder.getView(R.id.tv_add);

        tv_name.setText("网关："+contentBean.getSn());

        tv_add.setTag(contentBean); // 在activity里面做点击事件
        tv_add.setOnClickListener(mListener);


    }
}
