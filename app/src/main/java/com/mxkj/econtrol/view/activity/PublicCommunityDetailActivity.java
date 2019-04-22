package com.mxkj.econtrol.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.app.Config;
import com.mxkj.econtrol.app.Msg;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.base.CommonAdapter;
import com.mxkj.econtrol.bean.EventBusUIMessage;
import com.mxkj.econtrol.bean.request.ReqPublicCommunityComment;
import com.mxkj.econtrol.bean.response.CommentContent;
import com.mxkj.econtrol.bean.response.CommunityContent;
import com.mxkj.econtrol.contract.PublicCommunityDetailContract;
import com.mxkj.econtrol.model.PublicCommunityDetailModel;
import com.mxkj.econtrol.net.APIService;
import com.mxkj.econtrol.net.APISubcriber;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;
import com.mxkj.econtrol.presenter.PublicCommunityDetailPresenter;
import com.mxkj.econtrol.ui.activity.BrowsePicActivity;
import com.mxkj.econtrol.ui.adapter.CommentAdapter;
import com.mxkj.econtrol.utils.DateTimeUtil;
import com.mxkj.econtrol.widget.TipDialog;
import com.zhy.autolayout.utils.AutoUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 社区 （旧）
 */

public class PublicCommunityDetailActivity extends BaseActivity implements PublicCommunityDetailContract.View {

    private ImageView mImvHeader;
    private TextView mTvUserName;
    private TextView mTvContent;
    private TextView mTvTime;
    private TextView mTvTumpUP;
    private ImageView mImvTumpUP;
    private TextView mTvComment;
    private ImageView mImvPic;
    private CommunityContent mCommunityContent;
    private RecyclerView mRecycleComment;
    private PublicCommunityDetailContract.Presenter mPresenter;
    private EditText mEditComment;
    private TextView mTvSend;
    private TextView mTvCommentCount;
    private TextView mTvSendComment;
    private CommentContent.User mReplyUser;
    private TextView mTvEstate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_public_community_detail);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        mImvHeader = findView(R.id.imv_header_pic);
        mImvPic = findView(R.id.imv_community_pic);
        mTvUserName = findView(R.id.tv_user_name);
        mTvContent = findView(R.id.tv_content);
        mTvTime = findView(R.id.tv_time);
        mTvTumpUP = findView(R.id.tv_trumb_up);
        mImvTumpUP = findView(R.id.imv_trumb_up);
        mTvComment = findView(R.id.tv_comment);
        mRecycleComment = findView(R.id.recycle_comment);
        mRecycleComment.setLayoutManager(new LinearLayoutManager(this));
        mTvCommentCount = findView(R.id.tv_comment_count);
        mTvSendComment = findView(R.id.tv_send_comment);
        mEditComment = findView(R.id.edit_comment);
        mTvEstate = findView(R.id.tv_estate);


    }

    @Override
    protected void initData() {
        mPresenter = new PublicCommunityDetailPresenter(this, new PublicCommunityDetailModel());
        mCommunityContent = (CommunityContent) getIntent().getSerializableExtra("communityContent");
        if (TextUtils.isEmpty(mCommunityContent.getPic())) {
            mImvPic.setVisibility(View.GONE);
        } else {
            Glide.with(this)
                    .load(Config.BASE_PIC_URL + mCommunityContent.getPic())
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            int picWidth = resource.getWidth();
                            int picHeight = resource.getHeight();
                            int imageViewH = 0;
                            int imageViewW = 0;
                            imageViewH = AutoUtils.getPercentHeightSize(300);
                            float scale = picWidth / (float) picHeight;
                            imageViewW = (int) (imageViewH * scale);
                            ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(imageViewW, imageViewH);
                            mImvPic.setLayoutParams(params);
                            mImvPic.setBackgroundColor(0xfff5f5f5);
                            Glide.with(mImvPic.getContext()).load(Config.BASE_PIC_URL + mCommunityContent.getPic()).into(mImvPic);
                        }
                    });
        }
        Glide.with(this).load(Config.BASE_PIC_URL + mCommunityContent.getUser().getHeadPicture())
                .error(R.drawable.ic_no_head).into(mImvHeader);
        if (APP.user != null) {
            if (mCommunityContent.getHaveThumbUp() == 1) {
                mImvTumpUP.setImageResource(R.drawable.icon_have_start);
                mImvTumpUP.setEnabled(false);
            }
        }
        mTvUserName.setText(mCommunityContent.getUser().getNiceName());
        mTvContent.setText(mCommunityContent.getMsg() + "");
        mTvTumpUP.setText("" + mCommunityContent.getThumbUpCount());
        mTvComment.setText("" + mCommunityContent.getCommentsCount());
       /* long time = Long.parseLong(mCommunityContent.getCreatTime());
        mTvTime.setText(DateTimeUtil.getTime(System.currentTimeMillis(), time));
        if (mCommunityContent.getEstate() != null) {
            mTvEstate.setVisibility(View.VISIBLE);
            mTvEstate.setText(mCommunityContent.getEstate().getCity().getRegionName() + "·" + mCommunityContent.getEstate().getName());
        } else {
            mTvEstate.setVisibility(View.GONE);
        }*/

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getCommunityCommentsList(mCommunityContent.getId());
    }

    @Override
    protected void initListener() {
        mTvTumpUP.setOnClickListener(this);
        mImvTumpUP.setOnClickListener(this);
        mTvComment.setOnClickListener(this);
        mImvPic.setOnClickListener(this);
        mTvSendComment.setOnClickListener(this);
        findView(R.id.imv_back).setOnClickListener(this);
        mImvHeader.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_trumb_up:
                if (APP.user != null) {
                    //已登录
                    mPresenter.publicCommunityThumbUp(mCommunityContent.getId());
                } else {
                    showTip();
                }

                break;
            case R.id.imv_trumb_up:
                if (APP.user != null) {
                    //已登录
                    mPresenter.publicCommunityThumbUp(mCommunityContent.getId());
                } else {
                    showTip();
                }

                break;
            case R.id.tv_send_comment:
                if (APP.user != null) {
                    //已登录
                    addComment(mReplyUser);
                } else {
                    showTip();
                }

                break;
            case R.id.imv_community_pic:
                Intent intent = new Intent(this, BrowsePicActivity.class);
                intent.putExtra("picUrl", mCommunityContent.getPic());
                startActivity(intent);
                break;
            case R.id.imv_back:
                finish();
                break;
            case R.id.imv_header_pic:
                CommunityContent.User user = mCommunityContent.getUser();
                Intent userCommunityIntent = new Intent(this, UserCommunityActivity.class);
                userCommunityIntent.putExtra("user", user);
                startToActivity(userCommunityIntent);
                break;
            case R.id.imv_user_pic:
                CommentContent.User commentUser = (CommentContent.User) v.getTag(R.id.imv_user_pic);
                CommunityContent.User communityUser = new CommunityContent.User();
                communityUser.setNiceName(commentUser.getNiceName());
                communityUser.setUserName(commentUser.getUserName());
                communityUser.setHeadPicture(commentUser.getHeadPicture());
                communityUser.setId(commentUser.getId());
                Intent communityIntent = new Intent(this, UserCommunityActivity.class);
                communityIntent.putExtra("user", communityUser);
                startToActivity(communityIntent);
                break;
        }
    }

//    private void showComment() {
//        PopupWindow popupWindow = new PopupWindow(this);
//        View view = LayoutInflater.from(this).inflate(R.layout.layout_commnet, null);
//        mEditComment = (EditText) view.findViewById(R.id.edit_comment);
//        mTvSend = (TextView) view.findViewById(R.id.tv_send);
//        mTvSend.setOnClickListener(this);
//        popupWindow.setContentView(view);
//        popupWindow.showAtLocation(mRecycleComment, Gravity.CENTER, 0, 0);
//
//
//    }

    private void addComment(CommentContent.User replyUser) {

        String comment = mEditComment.getText().toString();
        if (TextUtils.isEmpty(comment)) {
            showToast(getString(R.string.comment_content_not_null));
            return;
        }
        ReqPublicCommunityComment reqPublicCommunityComment = new ReqPublicCommunityComment();
        reqPublicCommunityComment.setMsg(comment);
        reqPublicCommunityComment.setCommunityId(mCommunityContent.getId());
        reqPublicCommunityComment.setUserId(APP.user.getUserId());
        if (replyUser != null) {
            reqPublicCommunityComment.setReplyUserId(replyUser.getId());
        }
        RetrofitUtil.getInstance().getmApiService()
                .API(reqPublicCommunityComment.toJsonStr(), APIService.PUBLIC_COMMUNITY_COMMENTS)
                .compose(new APITransFormer<BaseResponse>())
                .subscribe(new APISubcriber<BaseResponse>() {
                    @Override
                    public void onFail(BaseResponse baseResponse,String msg) {
                        showToast(msg);
                    }

                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        showToast(getString(R.string.comment_success));
                        mEditComment.setHint(R.string.please_input_comment);
                        mEditComment.setText("");
                        mPresenter.getCommunityCommentsList(mCommunityContent.getId());
                        mReplyUser = null;
                    }
                });
    }


    /**
     * @Desc: 回复评论
     * @author:liangshan
     */
    private void replyComment(CommentContent.User replyUser) {

        mEditComment.setFocusableInTouchMode(true);
        mEditComment.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        mEditComment.setHint(getString(R.string.reply) + replyUser.getNiceName());
        mReplyUser = replyUser;

    }


    @Override
    public void setPresenter(PublicCommunityDetailContract.Presenter presenter) {

    }

    @Override
    public void setAdapter(final List<CommentContent> datas) {
        mTvCommentCount.setText(getString(R.string.comment) + "(" + datas.size() + ")");
        mTvComment.setText(datas.size() + "");
        final CommentAdapter commentAdapter = new CommentAdapter(datas, R.layout.layout_comment_item, this);
        mRecycleComment.setAdapter(commentAdapter);
        commentAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                CommentContent commentContent = datas.get(position);
                if (APP.user != null) {
                    //已登录
                    replyComment(commentContent.getUser());
                } else {
                    showTip();
                }

            }
        });

    }

    @Override
    public void showMsg(String msg) {
        showToast(msg);
    }

    @Override
    public void tumpUpSuccess() {
        mCommunityContent.setThumbUpCount(mCommunityContent.getThumbUpCount());
        mTvTumpUP.setText(("" + (mCommunityContent.getThumbUpCount() + 1)));
        ImageView imvTrumbUp = findView(R.id.imv_trumb_up);
        if (mCommunityContent.getHaveThumbUp() == 1) {
            imvTrumbUp.setImageResource(R.drawable.icon_good);
            mCommunityContent.setHaveThumbUp(0);

        } else {
            imvTrumbUp.setImageResource(R.drawable.icon_have_start);
            mCommunityContent.setHaveThumbUp(1);
        }

    }

    @Override
    public void tumpUpFail() {
//        showToast("您已经点过赞啦");
    }

    public void showTip() {
        TipDialog tipDialog = new TipDialog(this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startToActivity(LoginActivity.class);
                finish();
            }
        });

        tipDialog.setContent("该功能要登录才能操作，您要登录吗？");
        tipDialog.show();
    }
}
