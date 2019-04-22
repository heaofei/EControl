package com.mxkj.econtrol.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.Config;
import com.mxkj.econtrol.base.CommonAdapter;
import com.mxkj.econtrol.base.CommonViewHolder;
import com.mxkj.econtrol.bean.response.ResGetAppPushMessageList;
import com.mxkj.econtrol.bean.response.ResGetApplyBindHouseList;
import com.mxkj.econtrol.bean.response.SmartPart;
import com.mxkj.econtrol.widget.CircleImageView;

import java.util.List;

/**
 * Created by chanjun on 2018/1/4.
 * @Description:
 *   申请成为家庭成员列表adapter
 */

public class ApplyBindHouseListAdapter extends CommonAdapter<ResGetApplyBindHouseList.PageBean.ContentBean> {

    private Context context;
    private View.OnClickListener mListener;

    public ApplyBindHouseListAdapter(Context context, List<ResGetApplyBindHouseList.PageBean.ContentBean> mDatas, int mLayoutId, View.OnClickListener listener) {
        super(mDatas, mLayoutId);
        this.context = context;
        this.mListener =listener;
    }

    public void upDateAdapter(List<ResGetApplyBindHouseList.PageBean.ContentBean> mDatas){
        this.mDatas =mDatas;
        notifyDataSetChanged();
    }

    @Override
    public void convert(CommonViewHolder viewHolder, ResGetApplyBindHouseList.PageBean.ContentBean contentBean) {


        CircleImageView imv_header_pic = viewHolder.getView(R.id.imv_header_pic);
        TextView tv_content = viewHolder.getView(R.id.tv_content);
        TextView tv_nickname = viewHolder.getView(R.id.tv_nickname);

        TextView tv_agree = viewHolder.getView(R.id.tv_agree);
        TextView tv_refuse = viewHolder.getView(R.id.tv_refuse);



            if (!TextUtils.isEmpty(contentBean.getHeadPicture())){
                Glide.with(context).load(Config.BASE_PIC_URL + contentBean.getHeadPicture()).into(imv_header_pic);
            }else {
                imv_header_pic.setImageResource(R.drawable.ic_no_head);
            }

            tv_nickname.setText(contentBean.getUserName());
            tv_content.setText("申请成为"+contentBean.getEstateName()+contentBean.getBuildingName()+contentBean.getHouseNo()+"的成员");


        tv_agree.setTag(contentBean); // 在activity里面做点击事件
        tv_agree.setOnClickListener(mListener);
        tv_refuse.setTag(contentBean); // 在activity里面做点击事件
        tv_refuse.setOnClickListener(mListener);



  /*      if (atHomeUser.getPushList().size() > 0) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
*/
    }
}
