package com.mxkj.econtrol.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.app.Config;
import com.mxkj.econtrol.base.CommonAdapter;
import com.mxkj.econtrol.base.CommonViewHolder;
import com.mxkj.econtrol.bean.FileBean;
import com.mxkj.econtrol.bean.response.ResGetUserCamera;
import com.mxkj.econtrol.utils.TextUtil;

import java.util.List;

/**
 * Created by chanjun on 2018/12/14.
 *
 * @Description:
 */

public class CameraFileListAdapter extends CommonAdapter<FileBean> {
//    private List<ResGetUserCamera.PageBean.ContentBean> mDatas;
    private View.OnClickListener mListener;
    private Context context;
    private int fileType;

    public CameraFileListAdapter(Context context, List<FileBean> mDatas, int mLayoutId, View.OnClickListener listener, int fileType) {
        super(mDatas, mLayoutId);
        this.mDatas = mDatas;
        this.mListener = listener;
        this.context = context;
        this.fileType = fileType;
    }

    public void upDateAdapter(List<FileBean> mDatas , int fileType) {
        this.mDatas = mDatas;
        this.fileType = fileType;
        notifyDataSetChanged();

    }

    @Override
    public void convert(final CommonViewHolder viewHolder, final FileBean fileBean) {

        TextView tv_camera_title = viewHolder.getView(R.id.tv_camera_title);
        ImageView iv_bg = viewHolder.getView(R.id.iv_bg);
        ImageView iv_delete = viewHolder.getView(R.id.iv_delete);
        LinearLayout ll_camera_item = viewHolder.getView(R.id.ll_camera_item);//


        tv_camera_title.setText(TextUtil.setTest(fileBean.getFileName()));
        Glide.with(APP.getInstance()).load( fileBean.getFilePath()).error(R.drawable.ic_no_pic).into(iv_bg);

//        ll_camera_item.setTag(contentBean); // 在activity里面做点击事件
//        ll_camera_item.setOnClickListener(mListener);
        iv_delete.setTag(fileBean); // 在activity里面做点击事件
        iv_delete.setOnClickListener(mListener);



    }
}
