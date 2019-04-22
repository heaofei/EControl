package com.mxkj.econtrol.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.base.CommonAdapter;
import com.mxkj.econtrol.base.CommonViewHolder;
import com.mxkj.econtrol.bean.response.ResGetPartUseTime;
import com.mxkj.econtrol.utils.gateway.DeviceInfo;

import java.util.List;

/**
 * Created by chanjun on 2018/1/15.
 *  使用统计，房间列表adapter
 */

public class UsageStatisticsSceneAdapter extends CommonAdapter<ResGetPartUseTime.DataBean.SceneListBean> {

    private Context context;
    private View.OnClickListener mListener;

    public UsageStatisticsSceneAdapter(Context context, List<ResGetPartUseTime.DataBean.SceneListBean> mDatas, int mLayoutId, View.OnClickListener listener) {
        super(mDatas, mLayoutId);
        this.context = context;
        this.mListener =listener;
    }


    public void upDateAdapter(List<ResGetPartUseTime.DataBean.SceneListBean> mDatas){
        this.mDatas =mDatas;
        notifyDataSetChanged();
    }

    @Override
    public void convert(CommonViewHolder viewHolder, ResGetPartUseTime.DataBean.SceneListBean contentBean) {

        TextView tv_name = viewHolder.getView(R.id.tv_name);
        View view_line = viewHolder.getView(R.id.view_line);
        RelativeLayout ll_item = viewHolder.getView(R.id.ll_item);

        tv_name.setText(""+contentBean.getName());

        if (contentBean.isClick()) {
            view_line.setVisibility(View.VISIBLE);
        }else {
            view_line.setVisibility(View.INVISIBLE);
        }
       /* ll_item.setTag(contentBean); // 在activity里面做点击事件
        ll_item.setOnClickListener(mListener);*/


    }
}
