package com.mxkj.econtrol.view.fragment;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.app.Config;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.response.CommunityContent;
import com.mxkj.econtrol.bean.response.ResGetCommunityList;
import com.mxkj.econtrol.contract.InformationContract;
import com.mxkj.econtrol.model.InformationModel;
import com.mxkj.econtrol.net.APIService;
import com.mxkj.econtrol.presenter.InformationPresenter;
import com.mxkj.econtrol.ui.adapter.InformationAdapter;
import com.mxkj.econtrol.utils.LoginUtil;
import com.mxkj.econtrol.view.activity.ArticleDetailsActivity;
import com.mxkj.econtrol.view.activity.InformationInfoActivity;
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
 * Created by ${  chenjun  } on 2018/7/3.
 * <p>
 * 图文广场
 */

public class FragmentInformation extends BaseMainFragment implements View.OnClickListener, InformationContract.View {

    private TextView tv_title_content;
    private TextView tv_title_left, tv_title_right;
    private RelativeLayout rl_title;
    //控件是否已经初始化
    private boolean isCreateView = false;
    //数据是否已被加载过一次
    private boolean mHasLoadOnce = false;
    private View rootView;

    private RecyclerView recyclerView;
    private InformationAdapter mAdapter;
    private List<CommunityContent> datalist = new ArrayList<>();
    private int mPosition;

    private SuperSwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;
    private int page = 0 ; // 页数
    private int rows = 10; // 每页加载数量
    private int totalPages = 0 ; // 总页数

    private InformationContract.Presenter mPresenter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_information, container, false);
            isCreateView = true;
            initView(rootView);
            initRefreshLayou(rootView);
            initData();
            initListener();
        }
        return rootView;
    }


    @Override
    public void initView() {

    }

    public void initData() {
        mPresenter = new InformationPresenter(this, new InformationModel());
        mPresenter.getCommunityList(page, rows);

    }


    public void initListener() {
        rl_title.setOnClickListener(this);
        tv_title_right.setOnClickListener(this);

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
                recyclerView.scrollToPosition(0);
            }
        }
    }


    private void initView(View rootView) {
        tv_title_content = rootView.findViewById(R.id.tv_title_content);
        rl_title = rootView.findViewById(R.id.rl_title);
        tv_title_left = rootView.findViewById(R.id.tv_title_left);
        tv_title_left.setVisibility(View.GONE);
        tv_title_right = rootView.findViewById(R.id.tv_title_right);
        tv_title_right.setText("我的");
        tv_title_content.setText("广场");
        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        mAdapter = new InformationAdapter(datalist, R.layout.adapter_information, this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void lazyLoad() {
        if (!isVisible || !isCreateView || mHasLoadOnce) {
            return;
        }
    }


    private void initRefreshLayou(View rootView) {
        swipeRefreshLayout = (SuperSwipeRefreshLayout) rootView.findViewById(R.id.superSwipeRefreshLayout);
        View child = LayoutInflater.from(swipeRefreshLayout.getContext()).inflate(R.layout.layout_pulltorefresh_head, null);
        View footerView = LayoutInflater.from(swipeRefreshLayout.getContext()).inflate(R.layout.layout_pulltorefresh_footerview, null);
        progressBar = (ProgressBar) child.findViewById(R.id.pb_view);
        textView = (TextView) child.findViewById(R.id.text_view);
        textView.setText( getString(R.string.put_refresh));
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
                        page= 0;
                        mPresenter.getCommunityList(page, rows);//  页码，条数
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

                        if (page<totalPages){
                            page+=1;
                            mPresenter.getCommunityList(page, rows);//  页码，条数
                        }else {
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

    private void setOnPullRefreshAndrLoadMore(boolean loadMore) {
        progressBar.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(loadMore);
        swipeRefreshLayout.setLoadMore(loadMore);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.rl_title:
                /*dataList.clear();
                mPresenter.getCommunityList(0, 10);*/
                onTvTitleDoubleClick(tv_title_content);
                break;
            case R.id.tv_title_right:  // 我的
               /* if (APP.isLogin) {

                } else {
                    startActivity(new Intent(getActivity(), LoginRegistActivity.class));
                }*/

                if (LoginUtil.isLogin(getActivity()))
                    startActivity(new Intent(getActivity(), InformationInfoActivity.class));
                break;
            // ---------------广场列表Adapter的点击事件--------------------
            case R.id.rl_type01:
            case R.id.rl_type02:
            case R.id.rl_type03:
            case R.id.rl_type04:
                CommunityContent communityContent = (CommunityContent) v.getTag();
                Intent intent01 = new Intent(getActivity(), ArticleDetailsActivity.class);
                intent01.putExtra("type", communityContent.getType());
                intent01.putExtra("title", communityContent.getTitle());
                intent01.putExtra("time", communityContent.getUpdateTime());
                intent01.putExtra("source", communityContent.getSource());
                intent01.putExtra("CommunityContent", (Serializable) communityContent);
                startActivity(intent01);
                break;
            case R.id.iv_video_comment:   // 跳转视频详情
                if (LoginUtil.isLogin(getActivity())) {
                    CommunityContent communityContent02 = (CommunityContent) v.getTag();
                    Intent intent02 = new Intent(getActivity(), ArticleDetailsActivity.class);
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
                Intent intent02 = new Intent(getActivity(), ArticleDetailsActivity.class);
                intent02.putExtra("type", communityContent02.getType());
                intent02.putExtra("title", communityContent02.getTitle());
                intent02.putExtra("time", communityContent02.getUpdateTime());
                intent02.putExtra("source", communityContent02.getSource());
                intent02.putExtra("CommunityContent", (Serializable) communityContent02);
                startActivity(intent02);
                break;
            case R.id.iv_video_thumbup:   // 视频点赞
                if (LoginUtil.isLogin(getActivity())) {
                    CommunityContent communityContent03 = (CommunityContent) v.getTag();
                    mPosition = communityContent03.getPosition();
                    mPresenter.publicCommunityThumbUp(communityContent03.getId(), APP.user.getUserId());
                }
                break;
            case R.id.tv_video_collection:   // 视频收藏
                if (LoginUtil.isLogin(getActivity())) {
                    CommunityContent communityContent04 = (CommunityContent) v.getTag();
                    mPosition = communityContent04.getPosition();
                    mPresenter.publicCommunityCollectionUp(communityContent04.getId(), APP.user.getUserId());
                }
                break;
            case R.id.iv_video_share:   // 视频分享
                CommunityContent communityContent04 = (CommunityContent) v.getTag();
                String share = Config.Base_URL + APIService.COMMUNITY_SHARE + communityContent04.getId();
                UMWeb web = new UMWeb(Config.Base_URL + APIService.COMMUNITY_SHARE + communityContent04.getId());
                web.setTitle(communityContent04.getTitle());
                web.setThumb(new UMImage(getActivity(), R.drawable.ic_logo04));
//                web.setDescription(communityContent04.getTitle());
                web.setDescription("Fiswink"); // 这里是描述
                WXShareListenerUtil wxShareListenerUtil = new WXShareListenerUtil(getActivity()); // 设置分享回调，封装成工具类了
                new ShareAction(getActivity()).withMedia(web)
                        .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setCallback(wxShareListenerUtil.shareListener).open();
                break;
        }
    }


    @Override
    public void setPresenter(InformationContract.Presenter presenter) {

    }
    @Override
    public void getCommunityListSuccess(ResGetCommunityList resGetTimeRanking) {
        if (page==0) {
            datalist.clear();
        }
        totalPages = resGetTimeRanking.getPage().getTotalPages();
        setOnPullRefreshAndrLoadMore(false);
        datalist.addAll(resGetTimeRanking.getPage().getContent());
        mAdapter.upDateAdapter(datalist);
    }

    @Override
    public void getCommunityListFali(String msg) {
        setOnPullRefreshAndrLoadMore(false);
    }

    /***收藏*/
    @Override
    public void publicCommunityCollectionUpSuccess(BaseResponse baseResponse) {

        if (baseResponse.getMsg().equals("收藏成功")) {// 是否收藏
            datalist.get(mPosition).setHaveCollectionUp(1);
        } else {
            datalist.get(mPosition).setHaveCollectionUp(0);
        }
        mAdapter.upDateAdapter(datalist);
    }

    @Override
    public void publicCommunityCollectionUpFali(String msg) {
        showToast(msg);
    }

    /**
     * 点赞  （对文章）
     **/
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
        showToast(msg);
    }
    @Override
    public void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
        //Change these two variables back
        JZVideoPlayer.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_SENSOR;
        JZVideoPlayer.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    }

}
