package com.mxkj.econtrol.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.app.Config;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.base.CommonAdapter;
import com.mxkj.econtrol.base.CommonViewHolder;
import com.mxkj.econtrol.bean.response.ResGetHouseAllPartList;
import com.mxkj.econtrol.bean.response.ResLockPowerList;
import com.mxkj.econtrol.widget.CircleImageView;

import java.util.List;

/**
 * Created by chanjun on 2018/1/15.
 *   门锁权限 列表adapter
 */

public class LockPowerListAdapter extends CommonAdapter<ResLockPowerList.ListBean> {

    private Context context;
    private View.OnClickListener mListener;

    public LockPowerListAdapter(Context context, List<ResLockPowerList.ListBean> mDatas, int mLayoutId, View.OnClickListener listener) {
        super(mDatas, mLayoutId);
        this.context = context;
        this.mListener =listener;
    }

    public void upDateAdapter(List<ResLockPowerList.ListBean> mDatas){
        this.mDatas =mDatas;
        notifyDataSetChanged();
    }

    @Override
    public void convert(CommonViewHolder viewHolder, ResLockPowerList.ListBean contentBean) {

        TextView tv_name = viewHolder.getView(R.id.tv_name);
        CircleImageView imv_header_pic = viewHolder.getView(R.id.imv_header_pic);
        ImageView iv_add = viewHolder.getView(R.id.iv_add);

        tv_name.setText(contentBean.getUser().getNiceName());
        Glide.with(APP.getInstance()).load(Config.BASE_PIC_URL + contentBean.getUser().getHeadPicture()).error(R.drawable.ic_no_head).into(imv_header_pic);

        if (contentBean.getPermissions().indexOf("a")!=-1) {
            iv_add.setImageResource(R.drawable.home_edit_choose_selected);
        }else {
            iv_add.setImageResource(R.drawable.home_edit_choose_default);
        }


        iv_add.setTag(contentBean); // 在activity里面做点击事件
        iv_add.setOnClickListener(mListener);


    }
}
