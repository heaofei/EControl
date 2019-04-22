package com.mxkj.econtrol.view.activity;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.app.Config;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.response.CommentList;
import com.mxkj.econtrol.bean.response.CommunityCommentsInfoList;
import com.mxkj.econtrol.bean.response.ResGetCommunityCommentsMyList;
import com.mxkj.econtrol.bean.response.ResGetNewsDetail;
import com.mxkj.econtrol.contract.CommentDetailsContract;
import com.mxkj.econtrol.model.CommentDetailsModel;
import com.mxkj.econtrol.presenter.MyCommentDetailsPresenter;
import com.mxkj.econtrol.ui.adapter.ArticleCommentAdapter;
import com.mxkj.econtrol.ui.adapter.ArticleCommentDetailsAdapter;
import com.mxkj.econtrol.utils.DateTimeUtil;
import com.mxkj.econtrol.utils.LoginUtil;
import com.mxkj.econtrol.utils.MyDialogUtil;
import com.mxkj.econtrol.utils.TextUtil;
import com.mxkj.econtrol.widget.MyScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * 文章的 评论,详情
 */
public class CommentDetailsActivity extends BaseActivity implements CommentDetailsContract.View {


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
    private MyScrollView serollview;
    private TextView tv_comment_count;
//    private TextView tv_send_comment;// 发布评论
    private RelativeLayout rl_bottom; // 评论输入
    private android.support.v7.widget.RecyclerView recyclerView;


    protected List<CommunityCommentsInfoList.CommentListBean.ReplyListBean> mCommentList = new ArrayList<>();
    private ArticleCommentDetailsAdapter mArticleCommentAdapter;
    private int mPosition;// 操作的位置

    private ResGetNewsDetail.SmCommunityBean smCommunityBean = new ResGetNewsDetail.SmCommunityBean();
    private CommentList commentListBean = new CommentList();
    private List<ResGetCommunityCommentsMyList.PageBean.ContentBean> mList = new ArrayList<>();
    private CommentDetailsContract.Presenter mPresenter;

    private int page = 0 ; // 页数
    private int rows = 5; // 每页加载数量
    private int totalPages = 0 ; // 总页数

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        setContentView(R.layout.activity_comment_details);
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
        rl_bottom = (RelativeLayout) findViewById(R.id.rl_bottom);
        serollview = (MyScrollView) findViewById(R.id.serollview);
        recyclerView = (android.support.v7.widget.RecyclerView) findViewById(R.id.recyclerView);


        if (getIntent().getIntExtra("type", 0) == ArticleCommentAdapter.TAG_GOTO_COMMENT) {      // 评论动作
//            tv_title_content.setText(""+getIntent().getIntExtra("type",0)+"条回复");
            commentListBean = (CommentList) getIntent().getSerializableExtra("commentList");
            smCommunityBean = (ResGetNewsDetail.SmCommunityBean) getIntent().getSerializableExtra("smCommunityBean");

            if (commentListBean.getUser() != null && !TextUtils.isEmpty(commentListBean.getUser().getHeadPicture())) {
                Glide.with(APP.getInstance()).load(Config.BASE_PIC_URL + commentListBean.getUser().getHeadPicture()).error(R.drawable.family_unsettled).into(imv_header_pic);
            } else {
                imv_header_pic.setImageResource(R.drawable.family_unsettled);
            }
            tv_content.setText(commentListBean.getMsg());
            tv_linkcontent.setText(smCommunityBean.getTitle());
            if (commentListBean.getUser() != null) {
                String nickName = commentListBean.getUser().getNiceName();
                if (TextUtil.isPhoneNumber(nickName)){
                    nickName = nickName.substring(0,3)+"****"+nickName.substring(7,nickName.length());
                    tv_user_name.setText(nickName);
                }else {
                    tv_user_name.setText(TextUtil.setTest(commentListBean.getUser().getNiceName()));
                }
            }
            tv_time.setText(DateTimeUtil.getTime(commentListBean.getCreatTime()));
            tv_thumbUpCount.setText(commentListBean.getCommThumbCount() + "点赞");

        } else if (getIntent().getIntExtra("type", 0) == ArticleCommentAdapter.TAG_GOTO_COMMENT_LIST) {        // 查看评论列表
//            tv_title_content.setText(""+getIntent().getIntExtra("type",0)+"条回复");
            commentListBean = (CommentList) getIntent().getSerializableExtra("commentList");
            smCommunityBean = (ResGetNewsDetail.SmCommunityBean) getIntent().getSerializableExtra("smCommunityBean");
            if (commentListBean.getUser() != null && !TextUtils.isEmpty(commentListBean.getUser().getHeadPicture())) {
                Glide.with(APP.getInstance()).load(Config.BASE_PIC_URL + commentListBean.getUser().getHeadPicture()).error(R.drawable.family_unsettled).into(imv_header_pic);
            } else {
                imv_header_pic.setImageResource(R.drawable.family_unsettled);
            }
            tv_content.setText(commentListBean.getMsg());
            tv_linkcontent.setText(smCommunityBean.getTitle());
            tv_user_name.setText(commentListBean.getUser().getNiceName());
            tv_time.setText(DateTimeUtil.getTime(commentListBean.getCreatTime()));
            tv_thumbUpCount.setText(commentListBean.getCommThumbCount() + "点赞");
        }

        mArticleCommentAdapter = new ArticleCommentDetailsAdapter(mCommentList, R.layout.layout_article_community_item, this);


        //-----------设置这个是因为设置RecyclerView里面不给滑动，然后并且一定要在RecyclerView外层加一个RelativeLayout才有效果---------------
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        //-----------------------------------------------------------------------------------------------------------------------------------
        recyclerView.setAdapter(mArticleCommentAdapter);

        serollview.setScrollViewListener(new MyScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(MyScrollView context, int x, int y, int oldx, int oldy) {
                    //-------------------- scrollView 滑动到最下面-------------
                if (page<totalPages){
                    page+=1;
                    mPresenter.getCommunityCommentsInfoList(page, rows, commentListBean.getId());
                }else {
//                    showToast("最后一页");
                }
            }
        });


    }





@Override
    protected void initData() {
        mPresenter = new MyCommentDetailsPresenter(this, new CommentDetailsModel());
        mPresenter.getCommunityCommentsInfoList(page, rows, commentListBean.getId());
    }

    @Override
    protected void initListener() {
        tv_title_left.setOnClickListener(this);
        rl_bottom.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_title_left:
                finish();
                break;
            case R.id.rl_bottom:
                MyDialogUtil dialogUtil = new MyDialogUtil(this) {
                    @Override
                    public void confirm() {
                    }
                };
                dialogUtil.setInputDialogClicklistener(new MyDialogUtil.showInputDialogOnClickInterface() {
                    @Override
                    public void doEdit(String content) {
                        if (LoginUtil.isLogin(CommentDetailsActivity.this)) {
                            mPresenter.publicCommunityComments(content, smCommunityBean.getId(), commentListBean.getId());
                            hideSoftInput();// 隐藏键盘
                        }
                    }
                });
                dialogUtil.showInputCommentDialog("");
                break;
            case R.id.imv_start://  对评论内容点赞
                if (LoginUtil.isLogin(this)) {
                    CommunityCommentsInfoList.CommentListBean.ReplyListBean replyListBean = (CommunityCommentsInfoList.CommentListBean.ReplyListBean) v.getTag();
                    mPosition = replyListBean.getPosition();
                    mPresenter.publicCommunityCommentThumbUp(replyListBean.getId(), APP.user.getUserId());
                }
                break;
        }
    }


    @Override
    public void setPresenter(CommentDetailsContract.Presenter presenter) {
    }

    @Override
    public void publicCommunityCommentsSuccess(BaseResponse baseResponse) {
        mCommentList.clear();
        mPresenter.getCommunityCommentsInfoList(0, 10, commentListBean.getId());

    }

    @Override
    public void publicCommunityCommentsFali(String msg) {
        showToast(msg);
    }

    @Override
    public void publicCommunityCommentThumbUpSuccess(BaseResponse baseResponse) {
        if (baseResponse.getMsg().equals("点赞成功")) {// 是否点赞
            mCommentList.get(mPosition).setHaveCommentThumbUp(1);
            int thumb = mCommentList.get(mPosition).getCommThumbCount() + 1;
            mCommentList.get(mPosition).setCommThumbCount(thumb);
        } else {
            mCommentList.get(mPosition).setHaveCommentThumbUp(0);
            int thumb = mCommentList.get(mPosition).getCommThumbCount() - 1;
            mCommentList.get(mPosition).setCommThumbCount(thumb);
        }
        mArticleCommentAdapter.upDateAdapter(mCommentList);
//        showToast(baseResponse.getMsg());
    }

    @Override
    public void publicCommunityCommentThumbUpFali(String msg) {

    }

    @Override
    public void getCommunityCommentsInfoListSuccess(CommunityCommentsInfoList commentList) {
            totalPages = commentList.getTotalPages();
        if (commentList.getCommentList() != null && commentList.getCommentList().get(0).getReplyList() != null && commentList.getCommentList().size() > 0) {
//            mCommentList.clear();
            mCommentList.addAll(commentList.getCommentList().get(0).getReplyList());
            mArticleCommentAdapter.upDateAdapter(mCommentList);

            tv_comment_count.setText("评论(" + commentList.getCommCount() + ")");
            tv_title_content.setText(commentList.getCommCount() + "条回复");
        }
    }

    @Override
    public void getCommunityCommentsInfoListFali(String msg) {
        showToast(msg);
    }


    /**
     * 动态隐藏软键盘
     */
    private void hideSoftInput() {
        InputMethodManager imm =
                (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        View view = getCurrentFocus();
        if (view == null) view = new View(this);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
