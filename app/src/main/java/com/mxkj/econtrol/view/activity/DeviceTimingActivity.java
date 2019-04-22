package com.mxkj.econtrol.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.response.ResGetScenePartTimerList;
import com.mxkj.econtrol.contract.DeviceTimingListContract;
import com.mxkj.econtrol.model.DeviceTimingListModel;
import com.mxkj.econtrol.presenter.DeviceTimingListPresenter;
import com.mxkj.econtrol.ui.adapter.DeviceTimingListAdapter;
import com.mxkj.econtrol.utils.SharedPreferencesUtils;
import com.mxkj.econtrol.widget.SuperSwipeRefreshLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/***
 * 设备定时设置，（新）
 * **/

public class DeviceTimingActivity extends BaseActivity implements DeviceTimingListContract.View {


    // 初始化title
    private TextView tv_title_left;
    private TextView tv_title_content;
    private TextView tv_title_right;

    private RecyclerView recycleView_timing;
    private ImageView iv_add_timing;
    private boolean isEdit = false; // 是否编辑模式

    private DeviceTimingListAdapter mAdapter;
    private List<ResGetScenePartTimerList.TimerListBean> timingList = new ArrayList<>();
    private DeviceTimingListContract.Presenter mPresenter;
    private String operatorId;

    private SuperSwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;
    private int page = 0 ; // 页数
    private int rows = 20; // 每页加载数量
    private int totalPages = 0 ; // 总页数


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_device_timing);
        super.onCreate(savedInstanceState);

        initRefreshLayou();

    }

    @Override
    protected void initView() {
        tv_title_left = (TextView) findViewById(R.id.tv_title_left);
        tv_title_content = (TextView) findViewById(R.id.tv_title_content);
        tv_title_right = (TextView) findViewById(R.id.tv_title_right);
        tv_title_content.setText("定时");
        tv_title_right.setText("编辑");


        iv_add_timing = findView(R.id.iv_add_timing);
        recycleView_timing = (RecyclerView) findViewById(R.id.recycleView_timing);
        recycleView_timing.setLayoutManager(new LinearLayoutManager(this));


        mAdapter = new DeviceTimingListAdapter(timingList, R.layout.adapter_device_timing_item, this,isEdit);
        recycleView_timing.setAdapter(mAdapter);

    }

    @Override
    protected void initData() {
        mPresenter = new DeviceTimingListPresenter(this, new DeviceTimingListModel());
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getScenePartTimerList(page, rows,getHouseId());
    }

    public String getHouseId() {
        return (String) SharedPreferencesUtils.getParam(this, "houseId", "");// 传房子id
    }


    private void initRefreshLayou( )  {
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
                        textView.setText(getString(R.string.refresh));
                        page= 0;
                        mPresenter.getScenePartTimerList(page, rows,getHouseId());//  页码，条数
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
                            mPresenter.getScenePartTimerList(page, rows,getHouseId());//  页码，条数
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
    protected void initListener() {
        tv_title_left.setOnClickListener(this);
        tv_title_right.setOnClickListener(this);
        iv_add_timing.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_title_left:
                finish();
                break;
            case R.id.tv_title_right:
                if (isEdit) {
                    isEdit = false;
                    tv_title_right.setText("编辑");
                } else {
                    isEdit = true;
                    tv_title_right.setText("保存");
                }
                mAdapter.upDateAdapter(timingList,isEdit);
                break;
            case R.id.iv_add_timing:
                Intent intent = new Intent(this, DeviceTimingEditActivity.class);
                intent.putExtra("type", "NEW");
                startActivity(intent);
                break;
            case R.id.item_layout:
                if (!isEdit) {
                    return;
                }
                ResGetScenePartTimerList.TimerListBean timerListBean01 = (ResGetScenePartTimerList.TimerListBean) v.getTag();
                operatorId = timerListBean01.getId();
                String partId = "";
                String partName = "";
                for (int i = 0; i < timerListBean01.getPartList().size(); i++) {
                    partId += timerListBean01.getPartList().get(i).getPartId() + ",";
                    partName += timerListBean01.getPartList().get(i).getPartName() + ",";
                }

                Intent intent01 = new Intent(this, DeviceTimingEditActivity.class);
                intent01.putExtra("type", "EDIT");
                intent01.putExtra("operatorId", operatorId);
                intent01.putExtra("week", timerListBean01.getWeek());
                intent01.putExtra("partId", partId);
                intent01.putExtra("partName", partName);
                intent01.putExtra("open", timerListBean01.getOpen());
                intent01.putExtra("time", timerListBean01.getTime());
                intent01.putExtra("TimerListBean", (Serializable) timerListBean01);
                startActivity(intent01);

                isEdit = false;
                tv_title_right.setText("编辑");

                break;
            case R.id.iv_switch:  // item 里面的定时开关
                ResGetScenePartTimerList.TimerListBean timerListBean = (ResGetScenePartTimerList.TimerListBean) v.getTag();
                if (!TextUtils.isEmpty(timerListBean.getStatus()) && timerListBean.getStatus().equals("0")) {
                    mPresenter.scenePartTimerStatusChange(timerListBean.getId(), "1"); // status 0 关 1开
                } else {
                    mPresenter.scenePartTimerStatusChange(timerListBean.getId(), "0");  //  status 0 关 1开
                }
                break;
        }
    }
    @Override
    public void setPresenter(DeviceTimingListContract.Presenter presenter) {
    }
    @Override
    public void getScenePartTimerListSuccess(ResGetScenePartTimerList resGetScenePartTimerList) {
        setOnPullRefreshAndrLoadMore(false);
        //定时总数
        totalPages = resGetScenePartTimerList.getTotalPages();
        if (page==0) {
            timingList.clear();
        }
        timingList.addAll(resGetScenePartTimerList.getTimerList());
        mAdapter.upDateAdapter(timingList,isEdit);
    }

    @Override
    public void getScenePartTimerListFail(String msg) {
        setOnPullRefreshAndrLoadMore(false);
    }

    @Override
    public void scenePartTimerStatusChangeSuccess(BaseResponse baseResponse) {
        mPresenter.getScenePartTimerList(page, rows,getHouseId());
    }

    @Override
    public void scenePartTimerStatusChangeFail(String msg) {
        showToast(msg);
    }


    @Override
    public void onBackPressed() {
        if (isEdit) {
            isEdit = false;
            tv_title_right.setText("编辑");
            mAdapter.upDateAdapter(timingList,isEdit);
            return;
        }else {
            finish();
        }
    }
}
