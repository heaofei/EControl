package com.mxkj.econtrol.ui.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.app.Config;
import com.mxkj.econtrol.base.CommonAdapter;
import com.mxkj.econtrol.base.CommonViewHolder;
import com.mxkj.econtrol.bean.response.HouseUser;
import com.mxkj.econtrol.bean.response.MyHouse;
import com.mxkj.econtrol.utils.TextUtil;

import java.util.List;

/**
 * Created by liangshan on 2017/4/17.
 *
 * @Description： 房子用户c操作列表适配器（删除跟转让）
 */

public class HouseUserListOperatetAdapter extends CommonAdapter<HouseUser> {
    private View.OnClickListener mListener;
    private String type;

    public HouseUserListOperatetAdapter(String type, List<HouseUser> mDatas, int mLayoutId, View.OnClickListener listener) {
        super(mDatas, mLayoutId);
        mListener = listener;
        this.type = type;
    }

    public void upDateAdapter(List<HouseUser> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }


    @Override
    public void convert(CommonViewHolder viewHolder, final HouseUser houseUser) {
//        viewHolder.setText(R.id.tv_count, houseUser.getUserName());
        if (TextUtils.isEmpty(houseUser.getName())) {
        viewHolder.setText(R.id.tv_nick_name, houseUser.getUserName());
        }else {
        viewHolder.setText(R.id.tv_nick_name, houseUser.getName());
        }
        ImageView iv_search = viewHolder.getView(R.id.iv_search);
        ImageView imv_header_pic = viewHolder.getView(R.id.imv_header_pic);
        RelativeLayout rl_item = viewHolder.getView(R.id.rl_item);

        if (!houseUser.isClick()) {
            iv_search.setImageResource(R.drawable.home_edit_choose_default);
        } else {
            iv_search.setImageResource(R.drawable.home_edit_choose_selected);
        }
        if (type.equals("DELETE_USER")) {
            iv_search.setVisibility(View.VISIBLE);
        } else {
            iv_search.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(houseUser.getHeadPicture())){
            Glide.with(APP.getInstance()).load(Config.BASE_PIC_URL + houseUser.getHeadPicture()).error(R.drawable.family_unsettled).into(imv_header_pic);
        }else {
            imv_header_pic.setImageResource(R.drawable.family_unsettled);
        }
//            viewHolder.setSrc(R.id.imv_header_pic, Config.BASE_PIC_URL + houseUser.getHeadPicture());

      /*  rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!type.equals("DELETE_USER"))return; //
                if (!houseUser.isClick()){
                    houseUser.setClick(true);
                }else {
                    houseUser.setClick(false);
                }
                notifyDataSetChanged();
            }
        });*/

    }
}
