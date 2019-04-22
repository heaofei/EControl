package com.mxkj.econtrol.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.app.Config;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.response.CommunityContent;
import com.mxkj.econtrol.bean.response.ResGetCommunityCommentsMyList;
import com.mxkj.econtrol.bean.response.ResGetCommunityList;
import com.mxkj.econtrol.contract.InformationCollectionContract;
import com.mxkj.econtrol.model.InformationCollectionModel;
import com.mxkj.econtrol.net.APIService;
import com.mxkj.econtrol.presenter.InformationCollectionPresenter;
import com.mxkj.econtrol.ui.adapter.InformationAdapter;
import com.mxkj.econtrol.utils.LoginUtil;
import com.mxkj.econtrol.widget.SuperSwipeRefreshLayout;
import com.mxkj.econtrol.wxapi.WXShareListenerUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JZVideoPlayer;

/**
 * 广场，我的收藏
 */
public class InformationCollectionActivity extends BaseActivity implements InformationCollectionContract.View {

    // 初始化title
    private TextView tv_title_left;
    private TextView tv_title_content;
    private ImageView iv_title_right;

    private RecyclerView recyclerView;
    private InformationAdapter mAdapter;
    private List<CommunityContent> datalist = new ArrayList<>();
    private InformationCollectionContract.Presenter mPresenter;

    private int mPosition;

    private SuperSwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;
    private int page = 0; // 页数
    private int rows = 4; // 每页加载数量
    private int totalPages = 0; // 总页数


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_information_collection);
        super.onCreate(savedInstanceState);

        initRefreshLayou();
    }

    @Override
    protected void initView() {
        tv_title_left = (TextView) findViewById(R.id.tv_title_left);
        tv_title_content = (TextView) findViewById(R.id.tv_title_content);
        iv_title_right = (ImageView) findViewById(R.id.iv_title_right);
        tv_title_content.setText("我的收藏");


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new InformationAdapter(datalist, R.layout.adapter_information, this);
        recyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void initData() {
        mPresenter = new InformationCollectionPresenter(this, new InformationCollectionModel());
        mPresenter.getCommunityCollectionList(page, rows, APP.user.getUserId());//  页码，条数

    }


    private void initRefreshLayou() {
        swipeRefreshLayout = (SuperSwipeRefreshLayout) findViewById(R.id.superSwipeRefreshLayout);
        View child = LayoutInflater.from(swipeRefreshLayout.getContext()).inflate(R.layout.layout_pulltorefresh_head, null);
        View footerView = LayoutInflater.from(swipeRefreshLayout.getContext()).inflate(R.layout.layout_pulltorefresh_footerview, null);
        progressBar = (ProgressBar) child.findViewById(R.id.pb_view);
        textView = (TextView) child.findViewById(R.id.text_view);
        textView.setText(getString(R.string.put_refresh));
        textView.setTextColor(getResources().getColor(R.color.text_black_999));
        imageView = (ImageView) child.findViewById(R.id.image_view);
        imageView.setImageResource(R.drawable.ic_message_put_loading);
        imageView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        swipeRefreshLayout.setHeaderView(child);
        swipeRefreshLayout.setFooterView(footerView);

        swipeRefreshLayout   // 下拉刷新监听
                .setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {
                    @Override
                    public void onRefresh() {
                        textView.setText(getString(R.string.refresh));
                        page = 0;
                        mPresenter.getCommunityCollectionList(page, rows, APP.user.getUserId());//  页码，条数
                        imageView.setVisibility(View.GONE);
                        progressBar.setVisibility(View.VISIBLE);
                        System.out.println("debug:onRefresh");

                       /* new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                swipeRefreshLayout.setRefreshing(false);
                                progressBar.setVisibility(View.GONE);
                                System.out.println("debug:stopRefresh");
                            }
                        }, 100);*/
                    }

                    @Override
                    public void onPullDistance(int distance) {
                        System.out.println("debug:distance = " + distance);
                        //myAdapter.updateHeaderHeight(distance);
                    }

                    @Override
                    public void onPullEnable(boolean enable) {
                        textView.setText(enable ? getString(R.string.release_refresh) : getString(R.string.put_refresh));
                        imageView.setVisibility(View.VISIBLE);
                        imageView.setRotation(enable ? 180 : 0);
                    }
                });


        swipeRefreshLayout  // 上拉加载更多监听
                .setOnPushLoadMoreListener(new SuperSwipeRefreshLayout.OnPushLoadMoreListener() {
                    @Override
                    public void onLoadMore() {

                       /* new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //set false when finished
                                swipeRefreshLayout.setLoadMore(false);
                            }
                        }, 5000);*/

                        if (page < totalPages) {
                            page += 1;
                            mPresenter.getCommunityCollectionList(page, rows, APP.user.getUserId());//  页码，条数
                        } else {
                            showToast("最后一页");
                            swipeRefreshLayout.setLoadMore(false);
                        }
                    }

                    @Override
                    public void onPushDistance(int distance) {// TODO 上拉距离

                    }

                    @Override
                    public void onPushEnable(boolean enable) {//TODO 上拉过程中，上拉的距离是否足够出发刷新

                    }
                });

    }


    @Override
    protected void initListener() {
        tv_title_left.setOnClickListener(this);

        recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {
            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
               /* JZVideoPlayer jzvd = view.findViewById(R.id.videoplayer_item);
                if (jzvd != null && JZUtils.dataSourceObjectsContainsUri(jzvd.dataSourceObjects, JZMediaManager.getCurrentDataSource())) {
                    JZVideoPlayer currentJzvd = JZVideoPlayerManager.getCurrentJzvd();
                    if (currentJzvd != null && currentJzvd.currentScreen != JZVideoPlayer.SCREEN_WINDOW_FULLSCREEN) {
                        JZVideoPlayer.releaseAllVideos();
                    }
                }*/
                JZVideoPlayer.releaseAllVideos();
            }
        });

    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_title_left:
                finish();
                break;
            // ---------------广场列表Adapter的点击事件--------------------
            case R.id.rl_type01:
            case R.id.rl_type02:
            case R.id.rl_type03:
                CommunityContent communityContent = (CommunityContent) v.getTag();
                Intent intent01 = new Intent(this, ArticleDetailsActivity.class);
                intent01.putExtra("type", communityContent.getType());
                intent01.putExtra("title", communityContent.getTitle());
                intent01.putExtra("time", communityContent.getUpdateTime());
                intent01.putExtra("source", communityContent.getSource());
                intent01.putExtra("CommunityContent", (Serializable) communityContent);
                startActivity(intent01);
                break;
            case R.id.iv_video_comment:   // 跳转视频详情
                if (LoginUtil.isLogin(this)) {
                    CommunityContent communityContent02 = (CommunityContent) v.getTag();
                    Intent intent02 = new Intent(this, ArticleDetailsActivity.class);
                    intent02.putExtra("type", communityContent02.getType());
                    intent02.putExtra("title", communityContent02.getTitle());
                    intent02.putExtra("time", communityContent02.getUpdateTime());
                    intent02.putExtra("source", communityContent02.getSource());
                    intent02.putExtra("CommunityContent", (Serializable) communityContent02);
                    startActivity(intent02);
                }
                break;
            case R.id.ll_video:   // 跳转视频详情
                CommunityContent communityContent02 = (CommunityContent) v.getTag();
                Intent intent02 = new Intent(this, ArticleDetailsActivity.class);
                intent02.putExtra("type", communityContent02.getType());
                intent02.putExtra("title", communityContent02.getTitle());
                intent02.putExtra("time", communityContent02.getUpdateTime());
                intent02.putExtra("source", communityContent02.getSource());
                intent02.putExtra("CommunityContent", (Serializable) communityContent02);
                startActivity(intent02);
                break;
            case R.id.iv_video_thumbup:   // 视频点赞
                if (LoginUtil.isLogin(this)) {
                    CommunityContent communityContent03 = (CommunityContent) v.getTag();
                    mPosition = communityContent03.getPosition();
                    mPresenter.publicCommunityThumbUp(communityContent03.getId(), APP.user.getUserId());
                }
                break;
            case R.id.tv_video_collection:   // 视频收藏
                if (LoginUtil.isLogin(this)) {
                    CommunityContent communityContent04 = (CommunityContent) v.getTag();
                    mPosition = communityContent04.getPosition();
                    mPresenter.publicCommunityCollectionUp(communityContent04.getId(), APP.user.getUserId());
                }
                break;
            case R.id.iv_video_share:   // 视频分享
                CommunityContent communityContent04 = (CommunityContent) v.getTag();
                UMWeb web = new UMWeb(Config.Base_URL + APIService.COMMUNITY_SHARE + communityContent04.getId());
                web.setTitle("我家智能，文章分享");
                web.setThumb(new UMImage(this, R.drawable.ic_logo04));
                web.setDescription("my description");
                WXShareListenerUtil wxShareListenerUtil = new WXShareListenerUtil(this); // 设置分享回调，封装成工具类了
                new ShareAction(this).withMedia(web)
                        .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setCallback(wxShareListenerUtil.shareListener).open();
                break;
        }
    }


    @Override
    public void setPresenter(InformationCollectionContract.Presenter presenter) {

    }

    @Override
    public void getCommunityCollectionListSuccess(ResGetCommunityList resGetCommunityList) {

        if (page == 0) {
            datalist.clear();
        }
        totalPages = resGetCommunityList.getPage().getTotalPages();

        datalist.addAll(resGetCommunityList.getPage().getContent());
        mAdapter.upDateAdapter(datalist);
        setOnPullRefreshAndrLoadMore(false);
    }

    @Override
    public void getCommunityCollectionListFali(String msg) {
        setOnPullRefreshAndrLoadMore(false);
    }

    @Override
    public void publicCommunityCollectionUpSuccess(BaseResponse baseResponse) {
        if (baseResponse.getMsg().equals("收藏成功")) {// 是否收藏
        } else {
            datalist.remove(mPosition);
            mAdapter.upDateAdapter(datalist);
        }
    }

    @Override
    public void publicCommunityCollectionUpFali(String msg) {

    }

    @Override
    public void publicCommunityThumbUpSuccess(BaseResponse baseResponse) {
        if (baseResponse.getMsg().equals("点赞成功")) {// 是否点赞
            datalist.get(mPosition).setHaveThumbUp(1);
        } else {
            datalist.get(mPosition).setHaveThumbUp(0);
        }
        mAdapter.upDateAdapter(datalist);
    }

    @Override
    public void publicCommunityThumbUpFali(String msg) {

    }


    private void setOnPullRefreshAndrLoadMore(boolean loadMore) {
        progressBar.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(loadMore);
        swipeRefreshLayout.setLoadMore(loadMore);
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }
}
