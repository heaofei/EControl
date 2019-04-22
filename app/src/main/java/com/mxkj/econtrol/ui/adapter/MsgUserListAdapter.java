package com.mxkj.econtrol.ui.adapter;

import android.text.TextUtils;
import android.view.View;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.Config;
import com.mxkj.econtrol.base.CommonAdapter;
import com.mxkj.econtrol.base.CommonViewHolder;
import com.mxkj.econtrol.bean.response.ResGetAtHomeUserList;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by liangshan on 2017/4/27.
 *
 * @Description： 消息列表中的用户列表适配器
 */

public class MsgUserListAdapter extends CommonAdapter<ResGetAtHomeUserList.AtHomeUser> {

    public MsgUserListAdapter(List<ResGetAtHomeUserList.AtHomeUser> mDatas, int mLayoutId) {
        super(mDatas, mLayoutId);
    }

    @Override
    public void convert(CommonViewHolder viewHolder, ResGetAtHomeUserList.AtHomeUser atHomeUser) {
        if (!TextUtils.isEmpty(atHomeUser.getHeadPicture()))
            viewHolder.setSrc(R.id.imv_header_pic, Config.BASE_PIC_URL + atHomeUser.getHeadPicture());
        View view = viewHolder.getView(R.id.imv_red_point);
        if (atHomeUser.getPushList().size() > 0) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
        if (atHomeUser.getPushList().size() > 0 || atHomeUser.getIsAtHome() == 1) {
            //在家或者有消息的就显示出来

        } else {
//            viewHolder.getView(R.id.imv_header_pic).setVisibility(View.GONE);
        }

    }
}
