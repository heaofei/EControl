package com.mxkj.econtrol.ui.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.Config;
import com.mxkj.econtrol.base.CommonAdapter;
import com.mxkj.econtrol.base.CommonViewHolder;
import com.mxkj.econtrol.bean.response.ResGetCommunityCommentsMyList;
import com.mxkj.econtrol.utils.DateTimeUtil;
import com.mxkj.econtrol.utils.TextUtil;
import com.mxkj.econtrol.widget.CircleImageView;

import java.util.List;

/**
 * Created by chan on 2018/7/16.
 *
 * @Description：我我发出的评论下，具体二级评论item
 */

public class MyCommentDetailsAdapter extends CommonAdapter<ResGetCommunityCommentsMyList.PageBean.ContentBean.ReplyListBean> {
    private View.OnClickListener mListener;

    public MyCommentDetailsAdapter(List<ResGetCommunityCommentsMyList.PageBean.ContentBean.ReplyListBean> mDatas, int mLayoutId, View.OnClickListener listener) {
        super(mDatas, mLayoutId);
        mListener = listener;
    }

    public void upDateAdapter(List<ResGetCommunityCommentsMyList.PageBean.ContentBean.ReplyListBean> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    @Override
    public void convert(CommonViewHolder viewHolder, ResGetCommunityCommentsMyList.PageBean.ContentBean.ReplyListBean commentContent) {


        CircleImageView imv_header_pic = viewHolder.getView(R.id.imv_header_pic);
        TextView tv_user_name = viewHolder.getView(R.id.tv_user_name);
        TextView tv_time = viewHolder.getView(R.id.tv_time);
        TextView tv_content = viewHolder.getView(R.id.tv_content);
        TextView tv_linkcontent = viewHolder.getView(R.id.tv_linkcontent);
        LinearLayout ll_comment_item = viewHolder.getView(R.id.ll_comment_item);

        if (commentContent.getUser() != null && !TextUtils.isEmpty(commentContent.getUser().getHeadPicture())) {
            viewHolder.setSrc(R.id.imv_header_pic, Config.BASE_PIC_URL + commentContent.getUser().getHeadPicture());
        }

        if (commentContent.getUser() != null) {
            String nickName = commentContent.getUser().getNiceName();
            if (TextUtil.isPhoneNumber(nickName)){
                nickName = nickName.substring(0,3)+"****"+nickName.substring(7,nickName.length());
                tv_user_name.setText(nickName);
            }else {
                tv_user_name.setText(TextUtil.setTest(commentContent.getUser().getNiceName()));
            }
        }


        ll_comment_item.setTag(commentContent);
        ll_comment_item.setOnClickListener(mListener);

        viewHolder.setText(R.id.tv_time, DateTimeUtil.getTime(/*System.currentTimeMillis(),*/ Long.valueOf(commentContent.getCreatTime())));
        tv_content.setText(TextUtil.setTest(commentContent.getMsg()));
    }


}
