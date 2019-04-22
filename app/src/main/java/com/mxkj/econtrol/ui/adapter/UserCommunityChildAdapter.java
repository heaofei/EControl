package com.mxkj.econtrol.ui.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.Config;
import com.mxkj.econtrol.base.CommonAdapter;
import com.mxkj.econtrol.base.CommonViewHolder;
import com.mxkj.econtrol.bean.response.CommunityContent;

import java.util.List;

/**
 * Created by liangshan on 2017/6/12.
 *
 * @Description：
 */

public class UserCommunityChildAdapter extends CommonAdapter<CommunityContent> {
    public UserCommunityChildAdapter(List<CommunityContent> mDatas, int mLayoutId) {
        super(mDatas, mLayoutId);
    }

    @Override
    public void convert(CommonViewHolder viewHolder, CommunityContent communityContent) {
        ImageView pic = viewHolder.getView(R.id.imv_pic);
        TextView content = viewHolder.getView(R.id.tv_content);
        if (TextUtils.isEmpty(communityContent.getPic())) {
            pic.setVisibility(View.GONE);
        } else {
            pic.setVisibility(View.VISIBLE);
            pic.setImageResource(R.drawable.pic_defaut_bg);
            Glide.with(pic.getContext()).load(Config.BASE_PIC_URL + communityContent.getPic())
                    .error(R.drawable.pic_defaut_bg)
                    .into(pic);
        }
        content.setText(communityContent.getMsg());
    }
}

