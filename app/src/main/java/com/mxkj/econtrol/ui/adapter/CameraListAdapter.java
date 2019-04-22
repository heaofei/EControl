package com.mxkj.econtrol.ui.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.base.CommonAdapter;
import com.mxkj.econtrol.base.CommonViewHolder;
import com.mxkj.econtrol.bean.response.MyHouse;
import com.mxkj.econtrol.bean.response.ResGetUserCamera;
import com.mxkj.econtrol.utils.TextUtil;

import java.util.List;

/**
 * Created by chanjun on 2018/12/14.
 *
 * @Description:
 */

public class CameraListAdapter extends CommonAdapter<ResGetUserCamera.PageBean.ContentBean> {
//    private List<ResGetUserCamera.PageBean.ContentBean> mDatas;
    private View.OnClickListener mListener;
    private Context context;

    public CameraListAdapter(Context context, List<ResGetUserCamera.PageBean.ContentBean> mDatas, int mLayoutId, View.OnClickListener listener) {
        super(mDatas, mLayoutId);
        this.mDatas = mDatas;
        this.mListener = listener;
        this.context = context;
   /*     //添加新方案
        MyHouse addNew = new MyHouse();
        mDatas.add(addNew);*/
    }

    public void upDateAdapter(List<ResGetUserCamera.PageBean.ContentBean> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();

    }

    @Override
    public void convert(final CommonViewHolder viewHolder, final ResGetUserCamera.PageBean.ContentBean contentBean) {

        TextView tv_camera_title = viewHolder.getView(R.id.tv_camera_title);
        TextView tv_label = viewHolder.getView(R.id.tv_label);
        Button bt_change = viewHolder.getView(R.id.bt_change);
        ImageView iv_delete = viewHolder.getView(R.id.iv_delete);
        LinearLayout ll_camera_item = viewHolder.getView(R.id.ll_camera_item);//

        tv_camera_title.setText(TextUtil.setTest(contentBean.getName()));
        tv_label.setText(TextUtil.setTest(contentBean.getLable()));


//        ll_camera_item.setTag(contentBean); // 在activity里面做点击事件
//        ll_camera_item.setOnClickListener(mListener);
        iv_delete.setTag(contentBean); // 在activity里面做点击事件
        iv_delete.setOnClickListener(mListener);

        bt_change.setTag(contentBean); // 在activity里面做点击事件
        bt_change.setOnClickListener(mListener);



    }
}
