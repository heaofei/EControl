package com.mxkj.econtrol.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.Notify;
import com.mxkj.econtrol.bean.request.ReqGetAppPushMessageList;
import com.mxkj.econtrol.bean.request.ReqGetHouseControlLogList;
import com.mxkj.econtrol.bean.request.ReqGetSysAnnouncement;
import com.mxkj.econtrol.bean.response.AdapterHouseControlLogList;
import com.mxkj.econtrol.bean.response.ResGetHouseControlLogList;
import com.mxkj.econtrol.bean.response.ResGetSysAnnouncement;
import com.mxkj.econtrol.net.APISubcriber;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;
import com.mxkj.econtrol.ui.adapter.HouseControlLogListAdapter;
import com.mxkj.econtrol.utils.DateTimeUtil;
import com.mxkj.econtrol.utils.LogUtil;
import com.mxkj.econtrol.utils.SharedPreferencesUtils;
import com.mxkj.econtrol.widget.SuperSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 历史操作
 */
public class HouseControlLogActivity extends BaseActivity {

    // 初始化title
    private TextView tv_title_left;
    private TextView tv_title_content;


    private TextView tv_house_name;
    private ExpandableListView mList;
    private HouseControlLogListAdapter mAdapter;
    private List<AdapterHouseControlLogList> dataList = new ArrayList<>();

    //    private List<ResGetSysAnnouncement.PageBean.ContentBean> dataList = new ArrayList<>() ;
    private int page = 1; // 页数
    private int rows = 30; // 每页加载数量
    private int totalPages = 0; // 总页数

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_notify);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        mList = (ExpandableListView) findViewById(R.id.list_notify);
        mAdapter = new HouseControlLogListAdapter(HouseControlLogActivity.this, dataList);
        mList.setAdapter(mAdapter);

        tv_title_left = findView(R.id.tv_title_left);
        tv_title_content = findView(R.id.tv_title_content);
        tv_house_name = findView(R.id.tv_house_name);
        tv_title_content.setText("操作记录");
        initRefreshLayou();
    }

    @Override
    protected void initData() {

        GetHouseControlLogList(page,rows);

    }

    private void GetHouseControlLogList(int page,int rows) {
        ReqGetHouseControlLogList reqGetHouseControlLogList = new ReqGetHouseControlLogList(APP.user.getUserId(), getHouseId(), page, rows); // 1当前页，20000数量
        RetrofitUtil.getInstance().getmApiService()
                .getHouseControlLogList(reqGetHouseControlLogList.toJsonStr())
                .compose(new APITransFormer<ResGetHouseControlLogList>())
                .subscribe(new APISubcriber<ResGetHouseControlLogList>() {
                    @Override
                    public void onFail(ResGetHouseControlLogList baseResponse, String msg) {
                        swipeRefreshLayout.setLoadMore(false);
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onSuccess(ResGetHouseControlLogList result) {
                        swipeRefreshLayout.setLoadMore(false);
                        swipeRefreshLayout.setRefreshing(false);
                        totalPages=result.getHouseList().get(0).getTotalPages();
                        String content = new Gson().toJson(result);
                        List<ResGetHouseControlLogList.House.ControlLogList> houseList = result.getHouseList().get(0).getHouseControlLogList();
                        tv_house_name.setText(result.getHouseList().get(0).getHousingEstate() + result.getHouseList().get(0).getBuilding() + result.getHouseList().get(0).getHouseNo());
                      /*  //去掉没有日志的
                        Iterator<ResGetHouseControlLogList.House> iterator = houseList.iterator();
                        while (iterator.hasNext()) {
                            ResGetHouseControlLogList.House house = iterator.next();
                            if (house.getHouseControlLogList().size() == 0) {
                                iterator.remove();
                            }
                        }*/
                        screenData(result.getHouseList().get(0).getHouseControlLogList());
                    }
                });
    }


    private void screenData(List<ResGetHouseControlLogList.House.ControlLogList> houseList) {

        if (dataList.size()>0) {
//            houseList.addAll(  );
        }

        List<AdapterHouseControlLogList> adapterHouseControlLogLists = new ArrayList<>();// 用作把相同日期的操作日志放在同一个list里面，然后在adapter里面展示
        Map<String, List<ResGetHouseControlLogList.House.ControlLogList>> map = new LinkedHashMap<>(); // 用作判断所有接口获取到的log，然后相同的放在同一个list
        // 记得使用的是LinkedHashMap， 因为普通HanshMap是没有顺序的

        // 这里先拿时间作为key， 然后把对应的操作log作为value put进去map里面
        // 然后再 遍历获取到的列表，有相同key的， 添加到同一个list里面， 没有相同key的，则用新key put进去map里面
        int mapNum = 0;
        for (int i = 0; i < houseList.size(); i++) {
            String tmp = DateTimeUtil.getTime(houseList.get(i).getCreateTime());
            if (tmp.length() > 10) {
                tmp = tmp.substring(0, 10);
            }
//                            String tmp = houseList.get(i).getShow();
            List<ResGetHouseControlLogList.House.ControlLogList> ma = map.get(tmp);

            if (map == null || map.get(tmp) == null) {
                List<ResGetHouseControlLogList.House.ControlLogList> tmpList = new ArrayList<>();
                tmpList.add(houseList.get(i));
                map.put(tmp, tmpList);
                mapNum += 1;
            } else {
                List<ResGetHouseControlLogList.House.ControlLogList> tmpList = map.get(tmp);
                tmpList.add(houseList.get(i));
                map.put(tmp, tmpList);
            }
        }
        // 遍历HashMap所有值
        Iterator key = map.entrySet().iterator();
        while (key.hasNext()) {
            Map.Entry entry = (Map.Entry) key.next();
            List<ResGetHouseControlLogList.House.ControlLogList> valueList = (List<ResGetHouseControlLogList.House.ControlLogList>) entry.getValue();
            if (valueList.size() > 0) {
                AdapterHouseControlLogList adapterHouseControlLogList = null;
                List<AdapterHouseControlLogList.HouseControlLogList> houseControlLogLists = new ArrayList<>();
                for (int i = 0; i < valueList.size(); i++) {
//                                    String createTime = DateTimeUtil.getTime(valueList.get(i).getCreateTime());
//                                    if (createTime.length()>10){
//                                        createTime = createTime.substring(5,10);
//                                    }
//                                    System.out.println("===================createTime======"+createTime);

                    // 把数据遍历，add 进去list里面，数据格式为：AdapterHouseControlLogList
                    adapterHouseControlLogList = new AdapterHouseControlLogList();
                    AdapterHouseControlLogList.HouseControlLogList houseControlLogList = new AdapterHouseControlLogList.HouseControlLogList();
                    houseControlLogList.setCreateTime(valueList.get(i).getCreateTime());
                    houseControlLogList.setHeadPicture(valueList.get(i).getUser().getHeadPicture());
                    houseControlLogList.setId(valueList.get(i).getUser().getId());
                    houseControlLogList.setNiceName(valueList.get(i).getUser().getNiceName());
                    houseControlLogList.setShow(valueList.get(i).getShow());
                    houseControlLogList.setCommand(valueList.get(i).getCommand());
                    houseControlLogLists.add(houseControlLogList);
                    adapterHouseControlLogList.setControlLogLists(houseControlLogLists);
                }
                dataList.add(adapterHouseControlLogList);
            }
        }
//                        mList.setAdapter(new HouseControlLogListAdapter(HouseControlLogActivity.this, houseList, adapterHouseControlLogLists));
//                        Collections.reverse(adapterHouseControlLogLists); // 这里将list 的数据倒转一次，因为上面筛选数据的时候装进去map里面再取出来，是倒转来的
//        mList.setAdapter(new HouseControlLogListAdapter(HouseControlLogActivity.this, dataList));
        mAdapter.updateAdapter(dataList);
        for (int i = 0; i < dataList.size(); i++) {
            mList.expandGroup(i);
        }

    }


    @Override
    protected void initListener() {
        mList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });
        tv_title_left.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_title_left:
                finish();
                break;
        }
    }

    public String getHouseId() {
        return (String) SharedPreferencesUtils.getParam(this, "houseId", "");// 传房子id
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
        textView.setText(getString(R.string.put_refresh));
        textView.setTextColor(getResources().getColor(R.color.text_black_999));
        imageView = (ImageView) child.findViewById(R.id.image_view);
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageResource(R.drawable.ic_message_put_loading);
        progressBar.setVisibility(View.GONE);
        swipeRefreshLayout.setHeaderView(child);
        swipeRefreshLayout.setFooterView(footerView);
//        swipeRefreshLayout.setOnPullRefreshListener(null); // 下拉刷新监听
//        swipeRefreshLayout.setTargetScrollWithLayout(false);//设置下拉时，被包含的View是否随手指的移动而移动
//        swipeRefreshLayout.setEnabled(false);   // 设置是否启用

        swipeRefreshLayout   // 下拉刷新监听
                .setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {
                    @Override
                    public void onRefresh() {
                       /* textView.setText(getString(R.string.refresh));
                        page= 0;
                        mPresenter.getCommunityList(page, rows);//  页码，条数
                        imageView.setVisibility(View.GONE);
                        progressBar.setVisibility(View.VISIBLE);
                        System.out.println("debug:onRefresh");*/
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                swipeRefreshLayout.setRefreshing(false);
                                progressBar.setVisibility(View.GONE);
                                System.out.println("debug:stopRefresh");
                            }
                        }, 100);

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
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //set false when finished
                                swipeRefreshLayout.setLoadMore(false);
                            }
                        }, 5000);

                        if (page<totalPages){
                            page+=1;
                            GetHouseControlLogList(page,rows);
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


}
