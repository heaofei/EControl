package com.mxkj.econtrol.ui.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.Config;
import com.mxkj.econtrol.base.CommonAdapter;
import com.mxkj.econtrol.base.CommonViewHolder;
import com.mxkj.econtrol.bean.response.CommentContent;
import com.mxkj.econtrol.utils.DateTimeUtil;

import java.util.List;

/**
 * Created by liangshan on 2017/4/21.
 *
 * @Description：
 */

public class CommentAdapter extends CommonAdapter<CommentContent> {
    private View.OnClickListener mListener;


    public CommentAdapter(List<CommentContent> mDatas, int mLayoutId, View.OnClickListener listener) {
        super(mDatas, mLayoutId);

        mListener = listener;
    }

    @Override
    public void convert(CommonViewHolder viewHolder, CommentContent commentContent) {
        if (!TextUtils.isEmpty(commentContent.getUser().getHeadPicture())) {
            viewHolder.setSrc(R.id.imv_user_pic, Config.BASE_PIC_URL + commentContent.getUser().getHeadPicture());
        }

        View view = viewHolder.getView(R.id.imv_user_pic);
        view.setTag(R.id.imv_user_pic, commentContent.getUser());
        view.setOnClickListener(mListener);
        viewHolder.setText(R.id.tv_time, DateTimeUtil.getTime(System.currentTimeMillis(), Long.valueOf(commentContent.getCreatTime())));
        if (commentContent.getReplyUser() == null) {
            //是评论并非回复
            viewHolder.setText(R.id.tv_user_name, commentContent.getUser().getNiceName());
            viewHolder.getView(R.id.tv_reply).setVisibility(View.GONE);
            viewHolder.getView(R.id.tv_reply_name).setVisibility(View.GONE);
        } else {
            viewHolder.getView(R.id.tv_reply).setVisibility(View.VISIBLE);
            TextView replyName = viewHolder.getView(R.id.tv_reply_name);
            replyName.setVisibility(View.VISIBLE);
            replyName.setText(commentContent.getReplyUser().getNiceName());
            viewHolder.setText(R.id.tv_user_name, commentContent.getUser().getNiceName());
        }
        viewHolder.setText(R.id.tv_comment, commentContent.getMsg());
    }


}
