package com.mxkj.econtrol.view.activity;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.base.CommonAdapter;
import com.mxkj.econtrol.bean.request.ReqAppPushMessageReply;
import com.mxkj.econtrol.bean.request.ReqAppPushMessageTipRead;
import com.mxkj.econtrol.bean.request.ReqGetAppPushMessage;
import com.mxkj.econtrol.bean.request.ReqGetAppPushMessageList;
import com.mxkj.econtrol.bean.request.ReqGetSysAnnouncement;
import com.mxkj.econtrol.bean.response.ResAppPushMessageReply;
import com.mxkj.econtrol.bean.response.ResGetAppPushMessage;
import com.mxkj.econtrol.bean.response.ResGetAppPushMessageList;
import com.mxkj.econtrol.bean.response.ResGetAtHomeUserList;
import com.mxkj.econtrol.bean.response.ResGetSysAnnouncement;
import com.mxkj.econtrol.contract.HouseMainContract;
import com.mxkj.econtrol.contract.MessageListContract;
import com.mxkj.econtrol.model.MessageListModel;
import com.mxkj.econtrol.net.APISubcriber;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;
import com.mxkj.econtrol.presenter.MessageListPresenter;
import com.mxkj.econtrol.ui.adapter.MessageListAdapter;
import com.mxkj.econtrol.ui.adapter.MsgUserListAdapter;
import com.mxkj.econtrol.ui.adapter.SystemNotifiAdapter;
import com.mxkj.econtrol.ui.adapter.UseNumberAdapter;
import com.mxkj.econtrol.utils.SharedPreferencesUtils;
import com.mxkj.econtrol.widget.MyLinearLayout;
import com.mxkj.econtrol.widget.SuperSwipeRefreshLayout;
import com.mxkj.econtrol.widget.TipDialog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 消息列表
 */
public class MessageListActivity extends BaseActivity implements CommonAdapter.OnItemClickListener, MessageListContract.View {

    // 初始化title
    private TextView tv_title_left;
    private TextView tv_title_content;
    private TextView tv_title_right;

    private RecyclerView recyclerView;
    private MyLinearLayout ll_status;

    private String type;
    private SystemNotifiAdapter systemNotifiAdapter;
    private List<ResGetSysAnnouncement.PageBean.ContentBean> dataList = new ArrayList<>() ;

    private List<ResGetAppPushMessageList.PageBean.ContentBean> mMessageList = new ArrayList<>();
    private MessageListAdapter messageListAdapter;
    private MessageListContract.Presenter mPresenter;

    private int page = 1 ; // 页数
    private int rows = 20 ; // 每页加载数量
    private int totalPages = 0 ; // 总页数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_message_list);
        super.onCreate(savedInstanceState);


    }

    @Override
    protected void initView() {
        tv_title_left = (TextView) findViewById(R.id.tv_title_left);
        tv_title_content = (TextView) findViewById(R.id.tv_title_content);
        tv_title_right = (TextView) findViewById(R.id.tv_title_right);
        tv_title_right.setText("清空消息");
        ll_status = (MyLinearLayout) findViewById(R.id.ll_status);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mPresenter = new MessageListPresenter(this, new MessageListModel());


    }

    @Override
    protected void initData() {

        String id = APP.user.getUserId();
        type = getIntent().getStringExtra("type");
        if (type.equals("NOTICE")) {
            tv_title_content.setText(getString(R.string.message_notify));
            mPresenter.getSysAnnouncementPage(new ReqGetSysAnnouncement(page,rows));//  页码，条数
        } else {
            tv_title_content.setText(getString(R.string.message_message));
            mPresenter.getAppPushMessageList(new ReqGetAppPushMessageList(page, rows)); //  页码，条数
            messageListAdapter = new MessageListAdapter(this, mMessageList, R.layout.adapter_message_list_item, this);
            recyclerView.setAdapter(messageListAdapter);
        }
        initRefreshLayou();

    }



    private SuperSwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;

    private void initRefreshLayou() {
        swipeRefreshLayout = (SuperSwipeRefreshLayout) findViewById(R.id.superSwipeRefreshLayout);
        View child = LayoutInflater.from(swipeRefreshLayout.getContext()).inflate(R.layout.layout_pulltorefresh_head, null);
        View footerView = LayoutInflater.from(swipeRefreshLayout.getContext()).inflate(R.layout.layout_pulltorefresh_footerview, null);
        progressBar = (ProgressBar) child.findViewById(R.id.pb_view);
        textView = (TextView) child.findViewById(R.id.text_view);
        textView.setText( getString(R.string.put_refresh));
        textView.setTextColor(getResources().getColor(R.color.text_black_999));
        imageView = (ImageView) child.findViewById(R.id.image_view);
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageResource(R.drawable.ic_message_put_loading);
        progressBar.setVisibility(View.GONE);
        swipeRefreshLayout.setHeaderView(child);
        swipeRefreshLayout.setFooterView(footerView);
        swipeRefreshLayout   // 下拉刷新监听
                .setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {
                    @Override
                    public void onRefresh() {
                        textView.setText(getString(R.string.refresh));
                        if (type.equals("NOTICE")) {
                            dataList.clear();
                            mPresenter.getSysAnnouncementPage(new ReqGetSysAnnouncement(page,rows));//  页码，条数
                        } else {
                            mMessageList.clear();
                            mPresenter.getAppPushMessageList(new ReqGetAppPushMessageList(page,rows)); //  页码，条数
                        }
                        imageView.setVisibility(View.GONE);
                        progressBar.setVisibility(View.VISIBLE);
                        System.out.println("debug:onRefresh");

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                swipeRefreshLayout.setRefreshing(false);
                                progressBar.setVisibility(View.GONE);
                                System.out.println("debug:stopRefresh");
                            }
                        }, 2000);
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
                        if (type.equals("NOTICE")) {
                            mPresenter.getSysAnnouncementPage(new ReqGetSysAnnouncement(page,rows));//  页码，条数
                        } else {
                            mPresenter.getAppPushMessageList(new ReqGetAppPushMessageList(page,rows)); //  页码，条数
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

    @Override
    protected void initListener() {
        tv_title_left.setOnClickListener(this);
        tv_title_right.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_title_left:
                finish();
                break;
            case R.id.tv_title_right:
                mPresenter.deleteAppPushMessage();
                break;
            case R.id.tv_agree: // 同意加入
                ResGetAppPushMessageList.PageBean.ContentBean contentBean1 = (ResGetAppPushMessageList.PageBean.ContentBean) v.getTag();
                handlePushMessage(contentBean1, "1");   // 1通过， 2拒绝
                break;
            case R.id.tv_refuse: // 拒绝加入
                ResGetAppPushMessageList.PageBean.ContentBean contentBean = (ResGetAppPushMessageList.PageBean.ContentBean) v.getTag();
                handlePushMessage(contentBean, "2");   // 1通过， 2拒绝
                  /*  NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    manager.cancelAll();*/
                break;
        }
    }


    @Override
    public String getHouseId() {
        return (String) SharedPreferencesUtils.getParam(this, "houseId", "");// 传房子id
    }



    @Override
    public void getAppPushMessageListSuccess(ResGetAppPushMessageList resGetAppPushMessageList) {
        totalPages = resGetAppPushMessageList.getPage().getTotalPages();
        setOnPullRefreshAndrLoadMore(false);
        mMessageList.addAll(resGetAppPushMessageList.getPage().getContent());
        ResGetAppPushMessageList.PageBean.ContentBean rpc = new ResGetAppPushMessageList.PageBean.ContentBean();
        rpc.setContent("您已经您已经您已经您已经您已经您已经您已经您已经您已经您已经您已经您已经您已经您已经您已经您已");
        rpc.setInsertTime("2019-04-19 14:09:49.0");
//        mMessageList.add(rpc);
        if (mMessageList.size() > 0) {
            messageListAdapter.upDateAdapter(mMessageList);
            ll_status.showData();
            setMessageTipRead(resGetAppPushMessageList); // 设置消息已读
        } else {
            setEmptyView(); // 设置空状态view
        }
    }

    private   String pushId = "" ;
    public void setMessageTipRead(ResGetAppPushMessageList messageTipRead) {
        String content = new Gson().toJson(messageTipRead);

        for (int i = 0; i < messageTipRead.getPage().getContent().size(); i++) {
            pushId  += messageTipRead.getPage().getContent().get(i).getPushId()+",";
        }

        String [] ids = pushId.split(",");
        mPresenter.appPushMessageTipRead(new ReqAppPushMessageTipRead(ids));
    }

    @Override
    public void getAppPushMessageListFail(String msg) {
//        showToast(msg);
        setOnPullRefreshAndrLoadMore(false);
    }

    @Override
    public void getSysAnnouncementPageSuccess(ResGetSysAnnouncement resGetSysAnnouncement) {
        totalPages = resGetSysAnnouncement.getPage().getTotalPages();
        setOnPullRefreshAndrLoadMore(false);
        String content = new Gson().toJson(resGetSysAnnouncement);
        dataList.addAll( resGetSysAnnouncement.getPage().getContent());
        if (dataList.size()>0) {
        systemNotifiAdapter = new SystemNotifiAdapter( dataList , R.layout.adapter_notifi_list_item);
        systemNotifiAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(systemNotifiAdapter);
            ll_status.showData();
        }else {
            setEmptyView(); // 设置空状态view
        }

    }
    @Override
    public void getSysAnnouncementPageFail(String msg) {
//        showToast(msg);
        setOnPullRefreshAndrLoadMore(false);
    }

    @Override
    public void appPushMessageTipReadSuccess(BaseResponse response) {
//        String content = new Gson().toJson(response);
    }

    @Override
    public void appPushMessageTipReadFail(String msg) {
//        showToast(msg);
    }

    @Override
    public void deleteAppPushMessageSuccess(BaseResponse response) {
//        String content = new Gson().toJson(response);
//        showToast(content);
//        mPresenter.getAppPushMessageList(new ReqGetAppPushMessageList(1, 20)); //  页码，条数
        setEmptyView(); // 设置空状态view
    }

    @Override
    public void deleteAppPushMessageFail(String msg) {
        showToast(msg);
    }

    @Override
    public void setPresenter(MessageListContract.Presenter presenter) {

    }



    private void setOnPullRefreshAndrLoadMore(boolean loadMore) {
        swipeRefreshLayout.setRefreshing(loadMore);
        swipeRefreshLayout.setLoadMore(loadMore);
    }


    @Override
    public void onItemClick(View view, int position) {
        if (type.equals("NOTICE")) {       //系统通知
            Intent intent = new Intent(this, MessageDetailsActivity.class);
            intent.putExtra("type", type);
            intent.putExtra("id", dataList.get(position).getId());
            startActivity(intent);
        } else {                             // 消息提醒

        }


    }

    private void handlePushMessage(ResGetAppPushMessageList.PageBean.ContentBean contentBean, String state) {
        final ReqAppPushMessageReply request = new ReqAppPushMessageReply();
        request.setFromUserId(contentBean.getFromUser().getId());
        request.setPushId(contentBean.getPushId());
        request.setReplyState(state);    // 1通过， 2拒绝
        RetrofitUtil.getInstance().getmApiService()
                .appPushMessageReply(request.toJsonStr())
                .compose(new APITransFormer<ResAppPushMessageReply>())
                .subscribe(new APISubcriber<ResAppPushMessageReply>() {
                    @Override
                    public void onFail(ResAppPushMessageReply baseResponse,String msg) {
                        showToast(msg);
                    }
                    @Override
                    public void onSuccess(ResAppPushMessageReply response) {
                        String content = new Gson().toJson(response);
                        if (response.getPushResult() != null && response.getPushResult().getIsMember().equals("0")) {
                            //如果是处理了管理员申请的消息并且是同意后，显示是否删除其它原有控制用户，退出该activity
//                            showDeleteUser(response.getPushResult().getHouseBindUserId());
//                            showToast("是处理了管理员申请的消息并且是同意后，显示是否删除其它原有");
                        } else {
//                            mPresenter.getAtHomeUserList();
//                            showToast("其它原有");
                        }
                        mPresenter.getAppPushMessageList(new ReqGetAppPushMessageList(page,rows));
                    }
                });

    }


    /***
     * 设置空状态View
     **/
    private void setEmptyView() {
        tv_title_right.setText("");
        ll_status.setImage(R.drawable.ic_no_message);
        ll_status.setText(getString(R.string.message_nomessage));
        View view = ll_status.showEmpty(R.layout.custom_empty_view);
        Button btn_add = (Button) view.findViewById(R.id.btn_add);
        btn_add.setVisibility(View.GONE);
    }


}
