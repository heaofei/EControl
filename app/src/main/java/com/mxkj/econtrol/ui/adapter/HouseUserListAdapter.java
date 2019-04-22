package com.mxkj.econtrol.ui.adapter;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.app.Config;
import com.mxkj.econtrol.base.CommonAdapter;
import com.mxkj.econtrol.base.CommonViewHolder;
import com.mxkj.econtrol.bean.response.HouseUser;
import com.mxkj.econtrol.widget.CircleImageView;

import java.util.List;

/**
 * Created by liangshan on 2017/4/17.
 *
 * @Description： 绑定房子用户列表适配器
 */

public class HouseUserListAdapter extends CommonAdapter<HouseUser> {
    private View.OnClickListener mListener;

    public HouseUserListAdapter(List<HouseUser> mDatas, int mLayoutId, View.OnClickListener listener) {
        super(mDatas, mLayoutId);
        mListener = listener;
    }


    @Override
    public void convert(CommonViewHolder viewHolder, HouseUser houseUser) {
//        viewHolder.setText(R.id.tv_count, houseUser.getUserName());
        ImageView iv = viewHolder.getView(R.id.imv_header_pic);
        TextView tv_nick_name = viewHolder.getView(R.id.tv_nick_name);
        TextView tv_fangzhu = viewHolder.getView(R.id.tv_fangzhu);
        CircleImageView imv_header_pic = viewHolder.getView(R.id.imv_header_pic);

        if (TextUtils.isEmpty(houseUser.getName())) {
            tv_nick_name.setText(houseUser.getUserName());
        }else {
            tv_nick_name.setText(houseUser.getName());
        }

        if ( houseUser.getHeadPicture()!= null && houseUser.getHeadPicture().equals("delete") ){
            iv.setImageResource(R.drawable.icon_remove);
        }else {
             if (!TextUtils.isEmpty(houseUser.getHeadPicture())){
                 Glide.with(APP.getInstance()).load(Config.BASE_PIC_URL + houseUser.getHeadPicture()).error(R.drawable.family_unsettled).into(iv);
        }else {
            imv_header_pic.setImageResource(R.drawable.family_unsettled);
        }

//
            if (houseUser.getBindType().equals("0")) {  // 0、管理员；1、成员
                tv_nick_name.setTextColor(Color.parseColor("#ff695a"));
                tv_fangzhu.setVisibility(View.VISIBLE);
                imv_header_pic.setBorderWidth(3);
            }else {
                tv_nick_name.setTextColor(Color.parseColor("#666666"));
                tv_fangzhu.setVisibility(View.GONE);
                imv_header_pic.setBorderWidth(0);
            }


    }

}

}
