package com.mxkj.econtrol.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.response.CommentContent;
import com.mxkj.econtrol.bean.response.ResGetCommunityCommentsMyList;
import com.mxkj.econtrol.contract.InformationCommentContract;
import com.mxkj.econtrol.contract.InformationContract;
import com.mxkj.econtrol.model.InformationCommentModel;
import com.mxkj.econtrol.presenter.InformationCommentPresenter;
import com.mxkj.econtrol.ui.adapter.MyCommentAdapter;
import com.mxkj.econtrol.widget.SuperSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 广场， 我的评论
 */
public class InformationCommentActivity extends BaseActivity implements InformationCommentContract.View {


    // 初始化title
    private TextView tv_title_left;
    private TextView tv_title_content;
    private ImageView iv_title_right;

    private TextView tv_comment_get;
    private TextView tv_comment_send;
    private View view_line_get, view_line_send;
    private RecyclerView recyclerView;
    private int type;

    private InformationCommentContract.Presenter mPresenter;
    private MyCommentAdapter mMyCommentAdapter;
    private List<ResGetCommunityCommentsMyList.PageBean.ContentBean> mList = new ArrayList<>();


    private SuperSwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;
    private int page = 0 ; // 页数
    private int rows = 20; // 每页加载数量
    private int totalPages = 0 ; // 总页数


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_information_comment);
        super.onCreate(savedInstanceState);

        initRefreshLayou();
    }

    @Override
    protected void initView() {
        tv_title_left = (TextView) findViewById(R.id.tv_title_left);
        tv_title_content = (TextView) findViewById(R.id.tv_title_content);
        iv_title_right = (ImageView) findViewById(R.id.iv_title_right);
        tv_title_content.setText("我的评论");


        tv_comment_get = findView(R.id.tv_comment_get);
        tv_comment_send = findView(R.id.tv_comment_send);
        view_line_get = findView(R.id.view_line_get);
        view_line_send = findView(R.id.view_line_send);
        recyclerView = findView(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mPresenter = new InformationCommentPresenter(this, new InformationCommentModel());
        mMyCommentAdapter = new MyCommentAdapter(mList, R.layout.layout_mycomment_item, this,type);
        recyclerView.setAdapter(mMyCommentAdapter);
    }

    @Override
    protected void initData() {
        type = MyCommentAdapter.TAG_GET_COMMENT;
        mPresenter.getCommunityCommentsMyList(page, rows, APP.user.getUserId(), "1");// type 1 收到的评论 2 发出的评论
    }



    private void initRefreshLayou( ) {
        swipeRefreshLayout = (SuperSwipeRefreshLayout) findViewById(R.id.superSwipeRefreshLayout);
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
                        setOnPullRefreshAndrLoadMore(false);
                    }
                    @Override
                    public void onPullDistance(int distance) {
                        System.out.println("debug:distance = " + distance);
                        //myAdapter.updateHeaderHeight(distance);
                    }
                    @Override
                    public void onPullEnable(boolean enable) {
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
                            if (type==MyCommentAdapter.TAG_GET_COMMENT){
                                mPresenter.getCommunityCommentsMyList(page, rows, APP.user.getUserId(), "1");// type 1 收到的评论 2 发出的评论
                            }else {
                                mPresenter.getCommunityCommentsMyList(page, rows, APP.user.getUserId(), "2");// type 1 收到的评论 2 发出的评论
                            }
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
        swipeRefreshLayout.setRefreshing(loadMore);
        swipeRefreshLayout.setLoadMore(loadMore);
    }


    @Override
    protected void initListener() {
        tv_title_left.setOnClickListener(this);
        tv_comment_get.setOnClickListener(this);
        tv_comment_send.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_title_left:
                finish();
                break;
            case R.id.tv_comment_get:
                page = 0 ;
                mList.clear();
                type = MyCommentAdapter.TAG_GET_COMMENT;
                mPresenter.getCommunityCommentsMyList(page, rows, APP.user.getUserId(), "1");// type 1 收到的评论 2 发出的评论
                tv_comment_get.setTextColor(getResources().getColor(R.color.text_orangereg02));
                tv_comment_send.setTextColor(getResources().getColor(R.color.text_black_666));
                view_line_get.setVisibility(View.VISIBLE);
                view_line_send.setVisibility(View.GONE);
                break;
            case R.id.tv_comment_send:
                page = 0 ;
                mList.clear();
                type = MyCommentAdapter.TAG_SEND_COMMENT;
                mPresenter.getCommunityCommentsMyList(page, rows, APP.user.getUserId(), "2");// type 1 收到的评论 2 发出的评论
                tv_comment_send.setTextColor(getResources().getColor(R.color.text_orangereg02));
                tv_comment_get.setTextColor(getResources().getColor(R.color.text_black_666));
                view_line_get.setVisibility(View.GONE);
                view_line_send.setVisibility(View.VISIBLE);
                break;
            case R.id.ll_comment_item:

                ResGetCommunityCommentsMyList.PageBean.ContentBean commentContent = (ResGetCommunityCommentsMyList.PageBean.ContentBean) v.getTag();
                Intent intent = new Intent(this, MyCommentDetailsActivity.class);
                intent.putExtra("type", type);
                intent.putExtra("commentContent",commentContent);
                startActivity(intent);

                break;
        }
    }

    @Override
    public void setPresenter(InformationCommentContract.Presenter presenter) {

    }
    @Override
    public void getCommunityCommentsMyListSuccess(ResGetCommunityCommentsMyList resGetCommunityCommentsMyList) {
        totalPages = resGetCommunityCommentsMyList.getPage().getTotalPages();
        mList.addAll(resGetCommunityCommentsMyList.getPage().getContent());
        mMyCommentAdapter.upDateAdapter(mList,type);

        setOnPullRefreshAndrLoadMore(false);
    }

    @Override
    public void getCommunityCommentsMyListFali(String msg) {
        setOnPullRefreshAndrLoadMore(false);
    }


}
