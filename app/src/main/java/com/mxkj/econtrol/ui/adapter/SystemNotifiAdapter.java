package com.mxkj.econtrol.ui.adapter;

import android.widget.TextView;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.base.CommonAdapter;
import com.mxkj.econtrol.base.CommonViewHolder;
import com.mxkj.econtrol.bean.response.ResGetSysAnnouncement;

import java.util.List;

/**
 * Created by chanjun on 2017/9/6.
 * @Description:
 * 消息中心，系统通知adapter
 */

public class SystemNotifiAdapter extends CommonAdapter<ResGetSysAnnouncement.PageBean.ContentBean> {
    private boolean isEdit = false;

    public SystemNotifiAdapter(List<ResGetSysAnnouncement.PageBean.ContentBean> mDatas, int mLayoutId) {
        super(mDatas, mLayoutId);
    }
/*
    public void upDateAdapter(List<String> mDatas, boolean isEdit){
        this.mDatas =mDatas;
        this.isEdit =isEdit;
        notifyDataSetChanged();

    }*/
    @Override
    public void convert(CommonViewHolder viewHolder, ResGetSysAnnouncement.PageBean.ContentBean contentBean) {
        TextView tv_time = viewHolder.getView(R.id.tv_time);
        TextView tv_content = viewHolder.getView(R.id.tv_content);
//        TextView tv_title = viewHolder.getView(R.id.tv_title);

//        tv_title.setText(contentBean.getTitle());
        tv_time.setText(contentBean.getCreateTime());
        tv_content.setText(contentBean.getDescriptions());


    }
}
