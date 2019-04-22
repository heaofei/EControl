package com.mxkj.econtrol.view.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.Config;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.bean.response.ResGetCommunityCommentsMyList;
import com.mxkj.econtrol.ui.adapter.MyCommentAdapter;
import com.mxkj.econtrol.ui.adapter.MyCommentDetailsAdapter;
import com.mxkj.econtrol.utils.DateTimeUtil;
import com.mxkj.econtrol.utils.TextUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的评论,详情 (发出的评论，收到的评论)
 */
public class MyCommentDetailsActivity extends BaseActivity {


    // 初始化title
    private TextView tv_title_left;
    private TextView tv_title_content;
    private ImageView iv_title_right;

    private com.mxkj.econtrol.widget.CircleImageView imv_header_pic;
    private TextView tv_user_name;
    private TextView tv_time;
    private TextView tv_thumbUpCount;
    private TextView tv_content;
    private TextView tv_linkcontent;
    private LinearLayout ll_comment_item;
    private TextView tv_comment_count;
    private android.support.v7.widget.RecyclerView recyclerView;
    private  MyCommentDetailsAdapter myCommentDetailsAdapter;
    private List<ResGetCommunityCommentsMyList.PageBean.ContentBean.ReplyListBean> dataList= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_comment_details);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        tv_title_left = (TextView) findViewById(R.id.tv_title_left);
        tv_title_content = (TextView) findViewById(R.id.tv_title_content);
        iv_title_right = (ImageView) findViewById(R.id.iv_title_right);

        imv_header_pic = (com.mxkj.econtrol.widget.CircleImageView) findViewById(R.id.imv_header_pic);
        tv_user_name = (TextView) findViewById(R.id.tv_user_name);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_thumbUpCount = (TextView) findViewById(R.id.tv_thumbUpCount);
        tv_content = (TextView) findViewById(R.id.tv_content);
        tv_linkcontent = (TextView) findViewById(R.id.tv_linkcontent);
        ll_comment_item = (LinearLayout) findViewById(R.id.ll_comment_item);
        tv_comment_count = (TextView) findViewById(R.id.tv_comment_count);
        recyclerView = (android.support.v7.widget.RecyclerView) findViewById(R.id.recyclerView);
        myCommentDetailsAdapter = new MyCommentDetailsAdapter(dataList, R.layout.layout_article_community_item, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myCommentDetailsAdapter);


        ResGetCommunityCommentsMyList.PageBean.ContentBean commentContent =
                (ResGetCommunityCommentsMyList.PageBean.ContentBean) getIntent().getSerializableExtra("commentContent");
        showDeta(commentContent);

    }

    private void showDeta(ResGetCommunityCommentsMyList.PageBean.ContentBean commentContent) {
        tv_thumbUpCount = (TextView) findViewById(R.id.tv_thumbUpCount);
        tv_content = (TextView) findViewById(R.id.tv_content);
        tv_linkcontent = (TextView) findViewById(R.id.tv_linkcontent);

        if (!TextUtils.isEmpty(commentContent.getUser().getHeadPicture())) {
            Glide.with(this).load(Config.BASE_PIC_URL + commentContent.getUser().getHeadPicture()).into(imv_header_pic);
        } else {
            imv_header_pic.setImageResource(R.drawable.ic_no_head);
        }
//        tv_user_name.setText(TextUtil.setTest(commentContent.getUser().getNiceName()));

        if (commentContent.getUser() != null) {
            String nickName = commentContent.getUser().getNiceName();
            if (TextUtil.isPhoneNumber(nickName)){
                nickName = nickName.substring(0,3)+"****"+nickName.substring(7,nickName.length());
                tv_user_name.setText(nickName);
            }else {
                tv_user_name.setText(TextUtil.setTest(commentContent.getUser().getNiceName()));
            }
        }

        tv_time.setText(TextUtil.setTest(DateTimeUtil.getTime(commentContent.getCreatTime())));
        tv_thumbUpCount.setText(TextUtil.setTest(commentContent.getCommThumbCount()) + "点赞");
        tv_content.setText(TextUtil.setTest(commentContent.getMsg()));

        if (getIntent().getIntExtra("type", 0) == MyCommentAdapter.TAG_GET_COMMENT) {
            tv_title_content.setText("收到的评论");
            tv_linkcontent.setText(TextUtil.setTest(commentContent.getTitle()));
            tv_content.setText(TextUtil.setTest(commentContent.getMyCommList().getMsg()));

            if (!TextUtils.isEmpty(commentContent.getUser().getHeadPicture())) {
                Glide.with(this).load(Config.BASE_PIC_URL + commentContent.getMyCommList().getUser().getHeadPicture()).into(imv_header_pic);
            } else {
                imv_header_pic.setImageResource(R.drawable.ic_no_head);
            }
//            tv_user_name.setText(TextUtil.setTest(commentContent.getMyCommList().getUser().getNiceName()));

            if (commentContent.getMyCommList().getUser() != null) {
                String nickName = commentContent.getMyCommList().getUser().getNiceName();
                if (TextUtil.isPhoneNumber(nickName)){
                    nickName = nickName.substring(0,3)+"****"+nickName.substring(7,nickName.length());
                    tv_user_name.setText(nickName);
                }else {
                    tv_user_name.setText(TextUtil.setTest(commentContent.getMyCommList().getUser().getNiceName()));
                }
            }


            ll_comment_item.setVisibility(View.VISIBLE);
            ResGetCommunityCommentsMyList.PageBean.ContentBean.ReplyListBean replyListBean = new ResGetCommunityCommentsMyList.PageBean.ContentBean.ReplyListBean();
            replyListBean.setUser(commentContent.getUser());
            replyListBean.setCreatTime(commentContent.getCreatTime());
            replyListBean.setMsg(commentContent.getMsg());
            dataList.add(replyListBean);
            myCommentDetailsAdapter.upDateAdapter(dataList);

        } else if (getIntent().getIntExtra("type", 0) == MyCommentAdapter.TAG_SEND_COMMENT) {
            tv_title_content.setText("发出的评论");
            if (commentContent.getReplyUser().equals("0")) { // 对文章发出的评论
                tv_linkcontent.setText(TextUtil.setTest(commentContent.getTitle()));

                ll_comment_item.setVisibility(View.VISIBLE);
                dataList = commentContent.getReplyList();
                myCommentDetailsAdapter.upDateAdapter(dataList);

            } else {                                          // 对一级评论发出的评论
                if (commentContent.getMyCommList() != null ) {
                    tv_linkcontent.setText(TextUtil.setTest(commentContent.getMyCommList().getMsg()));
                }else {
                    tv_linkcontent.setText("");
                }

            }
        }


    }

    @Override
    protected void initData() {


    }

    @Override
    protected void initListener() {
        tv_title_left.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_title_left:
                finish();
                break;
           /* case R.id.tv_send_comment:
                mPresenter.publicCommunityComments(ed_content.getText().toString().trim(), smCommunityBean.getId(), commentListBean.getId());
                break;
            case R.id.imv_start://  对评论内容点赞
                CommentList commentListBean = (CommentList) v.getTag();
                mPresenter.publicCommunityCommentThumbUp(commentListBean.getId(), APP.user.getUserId());
                break;*/
        }
    }

}
