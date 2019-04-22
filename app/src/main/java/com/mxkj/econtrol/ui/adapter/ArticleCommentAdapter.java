package com.mxkj.econtrol.ui.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fingercrystal.iot.net.callback.IFailure;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.app.Config;
import com.mxkj.econtrol.base.CommonAdapter;
import com.mxkj.econtrol.base.CommonViewHolder;
import com.mxkj.econtrol.bean.response.CommentContent;
import com.mxkj.econtrol.bean.response.CommentList;
import com.mxkj.econtrol.bean.response.ResGetNewsDetail;
import com.mxkj.econtrol.utils.DateTimeUtil;
import com.mxkj.econtrol.utils.TextUtil;
import com.mxkj.econtrol.utils.gateway.DeviceInfo;
import com.mxkj.econtrol.widget.CircleImageView;

import java.util.List;

/**
 * Created by chan on 2018/7/13.
 *
 * @Description：  文章详情，一级评论adapter
 */

public class ArticleCommentAdapter extends CommonAdapter<CommentList> {
    private View.OnClickListener mListener;
    public static int TAG_GOTO_COMMENT =  3; // 跳转去评论
    public static int TAG_GOTO_COMMENT_LIST =  4; // 查看评论列表

    public ArticleCommentAdapter(List<CommentList> mDatas, int mLayoutId, View.OnClickListener listener) {
        super(mDatas, mLayoutId);

        mListener = listener;
    }

    public void upDateAdapter(List<CommentList> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    @Override
    public void convert(CommonViewHolder viewHolder, CommentList commentListBean) {

        LinearLayout ll_comment_item = viewHolder.getView(R.id.ll_comment_item);
        CircleImageView imv_header_pic = viewHolder.getView(R.id.imv_header_pic);
        TextView tv_user_name = viewHolder.getView(R.id.tv_user_name);
        TextView tv_content = viewHolder.getView(R.id.tv_content);
        TextView tv_time = viewHolder.getView(R.id.tv_time);
        TextView tv_reply_count = viewHolder.getView(R.id.tv_reply_count);
        TextView tv_start_num = viewHolder.getView(R.id.tv_start_num);// 一级评论点赞个数
        ImageView imv_start = viewHolder.getView(R.id.imv_start);

        if (commentListBean.getUser() != null && !TextUtils.isEmpty(commentListBean.getUser().getHeadPicture())) {
            Glide.with(APP.getInstance()).load(Config.BASE_PIC_URL + commentListBean.getUser().getHeadPicture()).error(R.drawable.family_unsettled).into(imv_header_pic);
        } else {
            imv_header_pic.setImageResource(R.drawable.family_unsettled);
        }
        if (commentListBean.getHaveCommentThumbUp()==1) {
            imv_start.setImageResource(R.drawable.ic_information_list_fabulous_selected);
        }else {
            imv_start.setImageResource(R.drawable.ic_information_list_fabulous_default);
        }


        if (commentListBean.getUser() != null) {
            String nickName = commentListBean.getUser().getNiceName();
            if (TextUtil.isPhoneNumber(nickName)){
                nickName = nickName.substring(0,3)+"****"+nickName.substring(7,nickName.length());
                tv_user_name.setText(nickName);
            }else {
                tv_user_name.setText(TextUtil.setTest(commentListBean.getUser().getNiceName()));
            }
        }

        if (commentListBean.getReplyCount()!=0) {
            tv_reply_count.setText("共"+commentListBean.getReplyCount()+"条回复");
            tv_reply_count.setVisibility(View.VISIBLE);
        }else {
            tv_reply_count.setVisibility(View.GONE);
        }
        tv_content.setText(TextUtil.setTest(commentListBean.getMsg()));
        tv_start_num.setText(TextUtil.setTest(commentListBean.getCommThumbCount()+""));// 点赞个数
        tv_time.setText(TextUtil.setTest(DateTimeUtil.getTime(commentListBean.getCreatTime())));

        commentListBean.setPosition(viewHolder.getPosition());
        imv_start.setTag(commentListBean);
        imv_start.setOnClickListener(mListener);
        ll_comment_item.setTag(commentListBean);
        ll_comment_item.setOnClickListener(mListener);

        tv_reply_count.setTag(commentListBean);
        tv_reply_count.setOnClickListener(mListener);



    }


}
