package com.mxkj.econtrol.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.app.Config;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.response.CommentList;
import com.mxkj.econtrol.bean.response.CommunityContent;
import com.mxkj.econtrol.bean.response.ResGetNewsDetail;
import com.mxkj.econtrol.contract.ArticleDetailContract;
import com.mxkj.econtrol.model.ArticleDetailModel;
import com.mxkj.econtrol.net.APIService;
import com.mxkj.econtrol.presenter.ArticleDetailPresenter;
import com.mxkj.econtrol.ui.adapter.ArticleCommentAdapter;
import com.mxkj.econtrol.utils.DateTimeUtil;
import com.mxkj.econtrol.utils.LogUtil;
import com.mxkj.econtrol.utils.LoginUtil;
import com.mxkj.econtrol.utils.MyDialogUtil;
import com.mxkj.econtrol.utils.TextUtil;
import com.mxkj.econtrol.widget.MyLinearLayout;
import com.mxkj.econtrol.widget.MyScrollView;
import com.mxkj.econtrol.widget.SuperSwipeRefreshLayout;
import com.mxkj.econtrol.wxapi.WXShareListenerUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.util.ArrayList;
import java.util.List;


import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

import static android.view.View.GONE;

/***
 *
 * 广场、图文详情页
 *
 */

public class ArticleDetailsActivity extends BaseActivity implements ArticleDetailContract.View {

    // 初始化title
    private TextView tv_title_left;
    private TextView tv_title_content;
    private ImageView iv_title_right;
    private RelativeLayout rl_title;
    private String id; // 文章Id
    private String url; // 文章连接
    private CommunityContent mCommunityContent; // 文章所有信息
    private int type; // 文章类型，4为视频，其他非视频

    private LinearLayout ll_video_item;
    private LinearLayout ll_details;
    private JZVideoPlayerStandard videoPlayer;
    private RecyclerView recyclerview_comment;
    private MyLinearLayout ll_status;
    private WebView webView;
    private TextView tv_content;
    private TextView tv_title;
    private TextView tv_source;
    private TextView tv_time;
    private TextView tv_send_comment; // 发送评论
    private RelativeLayout rl_share; // 点赞、分享、收藏布局
    private ImageView iv_collection; // 点赞
    private TextView tv_collection_count; // 点赞数
    private TextView tv_comment_count; //文章总评论数
    private ImageView iv_havethumb;
    private TextView tv_havethumb_count;
    private ImageView iv_share;
    private MyScrollView scrollView;

    private ResGetNewsDetail.SmCommunityBean smCommunityBean = new ResGetNewsDetail.SmCommunityBean();
    protected List<CommentList> mCommentList = new ArrayList<>();
    private ArticleCommentAdapter mArticleCommentAdapter;
    private int mPosition;//操作位置
    private int mCommThumbCount;//一级评论的点赞数
    private boolean isLoad = false;// 是否加载文章数据

    private ArticleDetailContract.Presenter mPresenter;
    private String communityId; // 文章id
    private int page = 0; // 页数
    private int rows = 5; // 每页加载数量
    private int totalPages = 0; // 总页数


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_article_details);
        super.onCreate(savedInstanceState);

//        initRefreshLayou();
    }

    @Override
    protected void initView() {
        tv_title_left = (TextView) findViewById(R.id.tv_title_left);
        tv_title_content = (TextView) findViewById(R.id.tv_title_content);
        iv_title_right = (ImageView) findViewById(R.id.iv_title_right);
        rl_title = (RelativeLayout) findViewById(R.id.rl_title);


        tv_content = (TextView) findViewById(R.id.tv_content);
        tv_send_comment = (TextView) findViewById(R.id.tv_send_comment);
        iv_collection = (ImageView) findViewById(R.id.iv_collection);
        tv_collection_count = (TextView) findViewById(R.id.tv_collection_count);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_source = (TextView) findViewById(R.id.tv_source);
        tv_time = (TextView) findViewById(R.id.tv_time);
        iv_havethumb = (ImageView) findViewById(R.id.iv_havethumb);
        tv_havethumb_count = (TextView) findViewById(R.id.tv_havethumb_count);
        iv_share = (ImageView) findViewById(R.id.iv_share);
        tv_comment_count = (TextView) findViewById(R.id.tv_comment_count);
        ll_video_item = (LinearLayout) findViewById(R.id.ll_video_item);
        rl_share = (RelativeLayout) findViewById(R.id.rl_share);
        ll_details = (LinearLayout) findViewById(R.id.ll_details);
        ll_status = (MyLinearLayout) findViewById(R.id.ll_status);
        videoPlayer = (JZVideoPlayerStandard) findViewById(R.id.videoplayer);
        scrollView = (MyScrollView) findViewById(R.id.scrollView);


        webView = findView(R.id.webView);
        //支持javascript
        webView.getSettings().setJavaScriptEnabled(true);
        //  重写 WebViewClient
        webView.setWebViewClient(new MyWebViewClient()); // 这个是为了设置图片自适应
        // 设置可以支持缩放
        webView.getSettings().setSupportZoom(false);
        // 设置出现缩放工具
        webView.getSettings().setBuiltInZoomControls(true);
        //webView
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); // 设置自适应
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY); //取消滚动条白边效果
        webView.getSettings().setDefaultTextEncodingName("UTF-8");
        webView.getSettings().setBlockNetworkImage(false);
//        webView.loadUrl(url);


        recyclerview_comment = findView(R.id.recyclerview_comment);
        //-----------设置这个是因为设置RecyclerView里面不给滑动，然后并且一定要在RecyclerView外层加一个RelativeLayout才有效果---------------
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);
        recyclerview_comment.setLayoutManager(layoutManager);
        recyclerview_comment.setHasFixedSize(true);
        recyclerview_comment.setNestedScrollingEnabled(false);
        //-----------------------------------------------------------------------------------------------------------------------------------
        mArticleCommentAdapter = new ArticleCommentAdapter(mCommentList, R.layout.layout_article_community_item, this);
        recyclerview_comment.setAdapter(mArticleCommentAdapter);


        scrollView.setScrollViewListener(new MyScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(MyScrollView context, int x, int y, int oldx, int oldy) {
                //-------------------- scrollView 滑动到最下面-------------
                if (page < totalPages) {
                    page += 1;
                    mPresenter.getArticleDetail(id, page, rows);//   page 0 rows 10 //切记，一定要是从第0页开始
                } else {
//                    showToast("最后一页");
                }
            }
        });

    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            //  html加载完成之后，调用js的方法
            imgReset();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    private void imgReset() { // 将富文本的图片设置为屏幕宽度的100%
        webView.loadUrl("javascript:(function(){"
                + "var objs = document.getElementsByTagName('img'); "
                + "for(var i=0;i<objs.length;i++)  " + "{"
                + "var img = objs[i];   "
                + "    img.style.width = '100%';   "
                + "    img.style.height = 'auto';   "
                + "}" + "})()");
    }


    @Override
    protected void initData() {
        mCommunityContent = (CommunityContent) getIntent().getSerializableExtra("CommunityContent");
        if (mCommunityContent != null) {
            id = mCommunityContent.getId();
            url = mCommunityContent.getMsg();
        }
        type = getIntent().getIntExtra("type", 0);
        if (type == 4) { // 视频类型
            ll_video_item.setVisibility(View.VISIBLE);
            webView.setVisibility(View.GONE);
            tv_title_content.setText("视频详情");
        } else {         // 非视频类型
            webView.setVisibility(View.VISIBLE);
            ll_video_item.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(url)) {
                url.replace("<img", "<img height=\"250px\"; width=\"100%\"");
                webView.loadDataWithBaseURL(null, url, "text/html", "UTF-8", null);
            }
            tv_title_content.setText("文章详情");
        }
        //改成编辑器一同编辑
        tv_title.setText(TextUtil.setTest(getIntent().getStringExtra("title")));
        tv_source.setText("来源：" + TextUtil.setTest(getIntent().getStringExtra("source")));
        long time = getIntent().getLongExtra("time", 153481641);
        if (time == 153481641) {
            tv_time.setText("");
        } else {
            tv_time.setText(DateTimeUtil.getTime(time));
        }


        mPresenter = new ArticleDetailPresenter(this, new ArticleDetailModel());
        mPresenter.getArticleDetail(id, page, rows);//   page 0 rows 10 //切记，一定要是从第0页开始
        if (APP.user != null) {
            mPresenter.publicCommunityReadingUp(id, APP.user.getUserId());
        } else {
            mPresenter.publicCommunityReadingUp(id, "");
        }
    }


    @Override
    protected void initListener() {
        rl_title.setOnClickListener(this);
        tv_title_left.setOnClickListener(this);
        iv_collection.setOnClickListener(this);
        iv_havethumb.setOnClickListener(this);
        iv_share.setOnClickListener(this);
        tv_send_comment.setOnClickListener(this);
        tv_content.setOnClickListener(this);

        // 软键盘监听 （收起、弹出）
        ll_details.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                ll_details.getWindowVisibleDisplayFrame(rect);
                int rootInvisibleHeight = ll_details.getRootView().getHeight() - rect.bottom;
                if (rootInvisibleHeight <= 100) {
                    //软键盘隐藏啦
                    rl_share.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (dialogUtil != null && dialogUtil.DialogGetShow()) {
                                dialogUtil.DialogDismiss();
                            }
                        }
                    }, 100);
                } else {
                    ////软键盘弹出啦

                }
            }
        });
    }


    //这里的startTime是为了记录第一次点击的时间，endTime是第二次点击的时间，doubleFlag是为了标记是第一次点击还是第二次点击
    public long startTime = 0;
    public long endTime = 0;
    public boolean doubleFlag = true;
    public void onTvTitleDoubleClick(View view){
        //获取第一次的点击时间，并改变标记
        if(doubleFlag){
            startTime = System.currentTimeMillis();
            doubleFlag = false;
            return;
        }
        //如果是第二次点击，计算两次点击时间差，如果小于350ms，就执行返回顶端的操作
        if(!doubleFlag){
            endTime = System.currentTimeMillis();
            doubleFlag = true;
            if((endTime - startTime) < 550){
                //Toast.makeText(MainActivity.this, (endTime - startTime)+"ms返回顶部", Toast.LENGTH_SHORT).show();
                //这里就是执行选中到顶端的操作，我这里是recycleView，你也可以是listView
                scrollView.scrollTo(0,0);
            }
        }
    }


    MyDialogUtil dialogUtil;
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.rl_title:
                /*dataList.clear();
                mPresenter.getCommunityList(0, 10);*/
                onTvTitleDoubleClick(rl_title);
                break;
            case R.id.tv_title_left:
                finish();
                break;
            case R.id.tv_content:// 弹出输入弹窗
                dialogUtil = new MyDialogUtil(this) {
                    @Override
                    public void confirm() {
                    }
                };
                dialogUtil.setInputDialogClicklistener(new MyDialogUtil.showInputDialogOnClickInterface() {
                    @Override
                    public void doEdit(String content) {
                        if (LoginUtil.isLogin(ArticleDetailsActivity.this)) {
                            mPresenter.publicCommunityComments(content, communityId, "");
                            hideSoftInput();// 隐藏键盘
                        }
                    }
                });
                dialogUtil.showInputCommentDialog("");
                break;
            case R.id.iv_collection:// 收藏
                if (LoginUtil.isLogin(this)) {
                    mPresenter.publicCommunityCollectionUp(communityId, APP.user.getUserId());
                }
                break;
            case R.id.iv_havethumb:// 点赞
                if (LoginUtil.isLogin(this)) {
                    mPresenter.publicCommunityThumbUp(communityId, APP.user.getUserId());
                }
                break;
            case R.id.iv_share:// 分享
                UMWeb web = new UMWeb(Config.Base_URL + APIService.COMMUNITY_SHARE + id);
                web.setTitle(getIntent().getStringExtra("title"));
                web.setThumb(new UMImage(ArticleDetailsActivity.this, R.drawable.ic_logo04));
                web.setDescription("Fiswink"); // 这里是描述
                WXShareListenerUtil wxShareListenerUtil = new WXShareListenerUtil(this); // 设置分享回调，封装成工具类了
                new ShareAction(ArticleDetailsActivity.this).withMedia(web)
                        .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setCallback(wxShareListenerUtil.shareListener).open();
                break;
            case R.id.imv_start://  对评论内容点赞
                if (LoginUtil.isLogin(this)) {
                    CommentList commentListBean = (CommentList) v.getTag();
                    mPosition = commentListBean.getPosition();
                    mCommThumbCount = Integer.valueOf(commentListBean.getCommThumbCount());
                    mPresenter.publicCommunityCommentThumbUp(commentListBean.getId(), APP.user.getUserId());
                    hideSoftInput();// 隐藏键盘
                }
                break;
            case R.id.ll_comment_item://  跳转去评论
                Intent intent = new Intent(this, CommentDetailsActivity.class);
                CommentList commentListBean02 = (CommentList) v.getTag();
                intent.putExtra("commentList", commentListBean02);
                intent.putExtra("smCommunityBean", smCommunityBean);
                intent.putExtra("type", ArticleCommentAdapter.TAG_GOTO_COMMENT);
                startActivity(intent);
                break;
            case R.id.tv_reply_count://  评论内容数量
                Intent intent2 = new Intent(this, CommentDetailsActivity.class);
                CommentList commentListBean03 = (CommentList) v.getTag();
                intent2.putExtra("commentList", commentListBean03);
                intent2.putExtra("smCommunityBean", smCommunityBean);
                intent2.putExtra("type", ArticleCommentAdapter.TAG_GOTO_COMMENT_LIST);//
                startActivity(intent2);
                break;
        }
    }

    @Override
    public void setPresenter(ArticleDetailContract.Presenter presenter) {
    }

    @Override
    public void getArticleDetailSuccess(ResGetNewsDetail resGetNewsDetail) {

        if (!isLoad) {
            communityId = resGetNewsDetail.getSmCommunity().getId();
            if (!TextUtils.isEmpty(resGetNewsDetail.getSmCommunity().getMsg())) {
                webView.loadDataWithBaseURL(null, resGetNewsDetail.getSmCommunity().getMsg(), "text/html", "UTF-8", null);
                tv_time.setText(DateTimeUtil.getTime(resGetNewsDetail.getSmCommunity().getCreatTime()));
                isLoad = true;
            }
            if (resGetNewsDetail.getSmCommunity() != null) {
                if (resGetNewsDetail.getSmCommunity().getCollectionCount() == 0) {
                    tv_collection_count.setText("");
                } else {
                    tv_collection_count.setText(resGetNewsDetail.getSmCommunity().getCollectionCount() + "");
                }
                if (resGetNewsDetail.getSmCommunity().getThumbUpCount() == 0) {
                    tv_havethumb_count.setText("");
                } else {
                    tv_havethumb_count.setText(resGetNewsDetail.getSmCommunity().getThumbUpCount() + "");
                }
                if (resGetNewsDetail.getSmCommunity().getHaveCollectionUp() == 1) {// 是否收藏
                    iv_collection.setImageResource(R.drawable.ic_information_list_collection_selected);
                } else {
                    iv_collection.setImageResource(R.drawable.ic_information_list_collection_default);
                }
                if (resGetNewsDetail.getSmCommunity().getHaveThumbUp() == 1) {// 是否点赞
                    iv_havethumb.setImageResource(R.drawable.ic_information_list_fabulous_selected);
                } else {
                    iv_havethumb.setImageResource(R.drawable.ic_information_list_fabulous_default);
                }
            }
            smCommunityBean = resGetNewsDetail.getSmCommunity();
            // 设置视频的标题、缩略图、播放url
            if (type == 4) { // 视频类型
                if (!TextUtils.isEmpty(resGetNewsDetail.getSmCommunity().getPic())) { // 设置缩略图
                    videoPlayer.thumbImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    Glide.with(APP.getInstance()).load(Config.BASE_PIC_URL + resGetNewsDetail.getSmCommunity().getPic()).error(R.drawable.ic_information_default_picture).into(videoPlayer.thumbImageView);
                }
                videoPlayer.tinyBackImageView.setVisibility(GONE);
//            videoPlayer.setPosition(getItemCount());//绑定Position
                videoPlayer.titleTextView.setText("");//清除标题,防止复用的时候出现
//                String url = Config.BASE_VIDEO_URL + resGetNewsDetail.getSmCommunity().getMsg();
                String url =  resGetNewsDetail.getSmCommunity().getMsg();// 后面改用西瓜视频的url之后
//                url.replace("<p>", "");
//                url.replace("</p>", "");
                String title = resGetNewsDetail.getSmCommunity().getTitle();
                videoPlayer.setUp(url, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "");
                JZVideoPlayer.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;// 设置可以视频全屏播放，必须加上这个
                JZVideoPlayer.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                isLoad = true;
            }
        }


        // 评论部分-----------------------------------------------------------------------------------
        totalPages = resGetNewsDetail.getTotalPages();
        if (resGetNewsDetail.getCommCount() != 0) {
            tv_comment_count.setText("评论(" + resGetNewsDetail.getCommCount() + ")");
        } else {
            tv_comment_count.setText("评论");
        }
        mCommentList.addAll(resGetNewsDetail.getCommentList());
        mArticleCommentAdapter.upDateAdapter(mCommentList);
        if (resGetNewsDetail.getCommCount() > 0) {
            recyclerview_comment.setVisibility(View.VISIBLE);
            ll_status.setVisibility(View.GONE);
        } else {
            recyclerview_comment.setVisibility(View.GONE);
            ll_status.setVisibility(View.VISIBLE);
            ll_status.setImage(R.drawable.ic_no_house);
            ll_status.setText("还没有人评论，来抢沙发吧。");
            View view = ll_status.showLoading(R.layout.custom_loading_view);
        }

        for (int i = 0; i < resGetNewsDetail.getCommentList().size(); i++) {
            LogUtil.i("==评论信息========================================" + resGetNewsDetail.getCommentList().get(i).getMsg());
        }


    }

    @Override
    public void getArticleDetailFali(String msg) {
        showToast(msg);
    }

    /**
     * 发送评论
     **/
    @Override
    public void publicCommunityCommentsSuccess(BaseResponse baseResponse) {
        mCommentList.clear();
        page = 0;
        mPresenter.getArticleDetail(id, 0, rows);//   page 0 rows 10 //切记，一定要是从第0页开始
    }

    @Override
    public void publicCommunityCommentsFali(String msg) {
        showToast(msg);
    }

    /***收藏*/
    @Override
    public void publicCommunityCollectionUpSuccess(BaseResponse baseResponse) {
        int collection ;
        if (!TextUtils.isEmpty(tv_collection_count.getText().toString().trim())) {
            collection = Integer.valueOf(tv_collection_count.getText().toString().trim());
        } else {
            collection = 0;
        }
        if (baseResponse.getMsg().equals("收藏成功")) {// 是否收藏
            iv_collection.setImageResource(R.drawable.ic_information_list_collection_selected);
            tv_collection_count.setText(collection + 1 + "");
        } else {
            iv_collection.setImageResource(R.drawable.ic_information_list_collection_default);

            if (collection - 1 == 0) {
                tv_collection_count.setText("");
            } else {
                tv_collection_count.setText(collection - 1 + "");
            }
        }
    }

    @Override
    public void publicCommunityCollectionUpFali(String msg) {
    }

    /***阅读数量+1 */
    @Override
    public void publicCommunityReadingUpSuccess(BaseResponse baseResponse) {
    }

    /**
     * 点赞  （对文章）
     **/
    @Override
    public void publicCommunityThumbUpSuccess(BaseResponse baseResponse) {
        int havethumb;
        if (!TextUtils.isEmpty(tv_havethumb_count.getText().toString().trim())) {
            havethumb = Integer.valueOf(tv_havethumb_count.getText().toString().trim());
        } else {
            havethumb = 0;
        }
        if (baseResponse.getMsg().equals("点赞成功")) {// 是否点赞
            iv_havethumb.setImageResource(R.drawable.ic_information_list_fabulous_selected);
            tv_havethumb_count.setText(havethumb + 1 + "");
        } else {
            iv_havethumb.setImageResource(R.drawable.ic_information_list_fabulous_default);
            if (havethumb - 1 == 0) {
                tv_havethumb_count.setText("");
            } else {
                tv_havethumb_count.setText(havethumb - 1 + "");
            }
        }
    }

    @Override
    public void publicCommunityThumbUpFali(String msg) {

    }

    /**
     * 点赞  （对评论）
     **/
    @Override
    public void publicCommunityCommentThumbUpSuccess(BaseResponse baseResponse) {

        if (baseResponse.getMsg().equals("点赞成功")) {// 是否点赞;
            mCommentList.get(mPosition).setHaveCommentThumbUp(1);
            mCommentList.get(mPosition).setCommThumbCount(mCommThumbCount + 1 + "");
        } else {
            mCommentList.get(mPosition).setHaveCommentThumbUp(0);
            mCommentList.get(mPosition).setCommThumbCount(mCommThumbCount - 1 + "");
        }
        mArticleCommentAdapter.upDateAdapter(mCommentList);
    }

    @Override
    public void publicCommunityCommentThumbUpFali(String msg) {
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();

        //Change these two variables back
        JZVideoPlayer.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_SENSOR;
        JZVideoPlayer.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    }

    // webview客户端对象
    private WebViewClient webClient = new WebViewClient() {
        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
            view.loadUrl(url);
            return true;
        }
    };

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
