package com.mxkj.econtrol.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.Config;
import com.mxkj.econtrol.base.CommonAdapter;
import com.mxkj.econtrol.base.CommonViewHolder;
import com.mxkj.econtrol.bean.response.ResAppTodoList;
import com.mxkj.econtrol.bean.response.ResGetApplyBindHouseList;
import com.mxkj.econtrol.widget.CircleImageView;

import java.util.List;

/**
 * Created by chanjun on 2018/2/26.
 * @Description:
 *
 *   APP 待处理消息列表（房子转让）
 */

public class AppTodoListAdapter extends CommonAdapter<ResAppTodoList.SwitchMasterTodoBean> {

    private Context context;
    private View.OnClickListener mListener;

    public AppTodoListAdapter(Context context, List<ResAppTodoList.SwitchMasterTodoBean> mDatas, int mLayoutId, View.OnClickListener listener) {
        super(mDatas, mLayoutId);
        this.context = context;
        this.mListener =listener;
    }

    public void upDateAdapter(List<ResAppTodoList.SwitchMasterTodoBean> mDatas){
        this.mDatas =mDatas;
        notifyDataSetChanged();
    }

    @Override
    public void convert(CommonViewHolder viewHolder, ResAppTodoList.SwitchMasterTodoBean contentBean) {


        CircleImageView imv_header_pic = viewHolder.getView(R.id.imv_header_pic);
        TextView tv_content = viewHolder.getView(R.id.tv_content);
        TextView tv_nickname = viewHolder.getView(R.id.tv_nickname);

        TextView tv_agree = viewHolder.getView(R.id.tv_agree);
        TextView tv_refuse = viewHolder.getView(R.id.tv_refuse);



            if (!TextUtils.isEmpty(contentBean.getHeadpicture())){
                Glide.with(context).load(Config.BASE_PIC_URL + contentBean.getHeadpicture()).into(imv_header_pic);
            }else {
                imv_header_pic.setImageResource(R.drawable.ic_no_head);
            }
            tv_nickname.setText(contentBean.getNiceName());
            tv_content.setText(contentBean.getTodoMsg());

        int position = viewHolder.getPosition();
        int layoutPosition = viewHolder.getLayoutPosition();

        tv_agree.setTag(contentBean); // 在activity里面做点击事件
        tv_agree.setTag(position);
        tv_agree.setOnClickListener(mListener);
        tv_refuse.setTag(position); // 在activity里面做点击事件
        tv_refuse.setOnClickListener(mListener);

    }


}
