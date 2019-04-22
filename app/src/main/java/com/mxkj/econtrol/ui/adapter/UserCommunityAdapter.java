package com.mxkj.econtrol.ui.adapter;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.Config;
import com.mxkj.econtrol.base.CommonAdapter;
import com.mxkj.econtrol.base.CommonViewHolder;
import com.mxkj.econtrol.bean.response.CommunityContent;
import com.mxkj.econtrol.bean.response.UserCommunity;
import com.mxkj.econtrol.utils.LogUtil;
import com.mxkj.econtrol.view.activity.PublicCommunityDetailActivity;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by liangshan on 2017/6/12.
 *
 * @Description：
 */

public class UserCommunityAdapter extends CommonAdapter<UserCommunity> {

    private CommunityContent.User mUser;

    public UserCommunityAdapter(List<UserCommunity> mDatas, int mLayoutId, CommunityContent.User user) {

        super(mDatas, mLayoutId);
        mUser = user;
    }

    @Override
    public void convert(final CommonViewHolder viewHolder, final UserCommunity userCommunities) {
        int indexOf = mDatas.indexOf(userCommunities);
        final View rlItem = viewHolder.getView(R.id.rl_item);
        if (indexOf == 0) {
            rlItem.setVisibility(View.GONE);
            viewHolder.getView(R.id.rl_item_header).setVisibility(View.VISIBLE);
            viewHolder.setText(R.id.tv_nice_name, mUser.getNiceName());
            if (!TextUtils.isEmpty(mUser.getHeadPicture())) {
                viewHolder.setSrc(R.id.imv_header, Config.BASE_PIC_URL + mUser.getHeadPicture());
            }
        } else {
            viewHolder.getView(R.id.rl_item_header).setVisibility(View.GONE);
            rlItem.setVisibility(View.VISIBLE);


            final ViewTreeObserver viewTreeObserver = rlItem.getViewTreeObserver();
//            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//                @Override
//                public void onGlobalLayout() {
//                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(2, rlItem.getHeight() - AutoUtils.getPercentHeightSize(54));
//                    params.setMargins(AutoUtils.getPercentWidthSize(50), 0, 0, 0);
//                    params.addRule(RelativeLayout.BELOW, R.id.tv_time);
//                    dashLine.setLayoutParams(params);
//                    LogUtil.i("000000000000000");
////                    viewTreeObserver.removeOnGlobalLayoutListener(this);
//                }
//            });


            RecyclerView recyclerCummunity = viewHolder.getView(R.id.recycler_cummunity);
            recyclerCummunity.setLayoutManager(new LinearLayoutManager(recyclerCummunity.getContext()));
            UserCommunityChildAdapter userCommunityChildAdapter = new UserCommunityChildAdapter(userCommunities.getCommunityContentList(), R.layout.layout_user_community_child_item);
            userCommunityChildAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(view.getContext(), PublicCommunityDetailActivity.class);
                    intent.putExtra("communityContent", userCommunities.getCommunityContentList().get(position));
                    view.getContext().startActivity(intent);
                }
            });
            recyclerCummunity.setAdapter(userCommunityChildAdapter);
            viewHolder.setText(R.id.tv_time, userCommunities.getTime());
            final View dashLine = viewHolder.getView(R.id.dash_line);
            recyclerCummunity.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(AutoUtils.getPercentWidthSize(2),recyclerCummunity.getMeasuredHeight() - AutoUtils.getPercentHeightSize(24));
            params.setMargins(AutoUtils.getPercentWidthSize(45), 0, 0, 0);
            params.addRule(RelativeLayout.BELOW, R.id.tv_time);
            dashLine.setLayoutParams(params);
            if (indexOf == mDatas.size() - 1) {
                dashLine.setVisibility(View.GONE);
            } else {
                dashLine.setVisibility(View.VISIBLE);
            }
        }


    }
}
