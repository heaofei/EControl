package com.mxkj.econtrol.view.fragment;

import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.app.Msg;
import com.mxkj.econtrol.base.CommonAdapter;
import com.mxkj.econtrol.bean.EventBusMessage;
import com.mxkj.econtrol.bean.response.ResGetDurationRanking;
import com.mxkj.econtrol.bean.response.ResGetRankingDateList;
import com.mxkj.econtrol.bean.response.ResGetTimeRanking;
import com.mxkj.econtrol.contract.DeviceUseNumberContract;
import com.mxkj.econtrol.contract.DeviceUseTimeContract;
import com.mxkj.econtrol.model.DeviceUseNumberModel;
import com.mxkj.econtrol.model.DeviceUseTimeModel;
import com.mxkj.econtrol.presenter.DeviceUseNumberPresenter;
import com.mxkj.econtrol.presenter.DeviceUseTimePresenter;
import com.mxkj.econtrol.ui.adapter.RankingDateListAdapter;
import com.mxkj.econtrol.ui.adapter.UseNumberAdapter;
import com.mxkj.econtrol.ui.adapter.UseTimeAdapter;
import com.mxkj.econtrol.utils.SharedPreferencesUtils;
import com.mxkj.econtrol.widget.LinearListView;
import com.mxkj.econtrol.widget.SuperSwipeRefreshLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ${  chenjun  } on 2017/9/7.
 */

public class UseTimeFragment extends BaseMainFragment implements DeviceUseTimeContract.View {

    private ImageView iv_day_month;
    private TextView tv_number;
    private TextView tv_company;
    private TextView tv_add_number;
    private TextView tv_time;
    private RelativeLayout rl_item;
    private LinearListView linearListView;
    private UseTimeAdapter useTimeAdapter;
    private List<ResGetDurationRanking.DurationRankBean> dataList;

    private RankingDateListAdapter rankingDateListAdapter;
    private List<ResGetRankingDateList.RankingDateBean> rankDateList;
    private boolean isDay = true;
    private String getDate; // 获取当前时间

    //控件是否已经初始化
    private boolean isCreateView = false;
    //数据是否已被加载过一次
    private boolean mHasLoadOnce = false;
    private View rootView;

    private DeviceUseTimeContract.Presenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_use_time, container, false);
            isCreateView = true;
            initView(rootView);
            initData();
            initListener();
        }
        return rootView;
    }

    private void initView(View view) {
        iv_day_month = (ImageView) view.findViewById(R.id.iv_day_month);
        tv_number = (TextView) view.findViewById(R.id.tv_number);
        tv_company = (TextView) view.findViewById(R.id.tv_company);
        tv_add_number = (TextView) view.findViewById(R.id.tv_add_number);
        tv_time = (TextView) view.findViewById(R.id.tv_time);
        rl_item = (RelativeLayout) view.findViewById(R.id.rl_item);
        linearListView = (LinearListView) view.findViewById(R.id.linearListView);


    }

    @Override
    public void initView() {

    }

    public void initData() {
        dataList = new ArrayList<>();
        rankDateList = new ArrayList<>();
        useTimeAdapter = new UseTimeAdapter(getActivity(), dataList, R.layout.adapter_use_number_item);
        rankingDateListAdapter = new RankingDateListAdapter(rankDateList, R.layout.adapter_rank_date_item);
        linearListView.setAdapter(useTimeAdapter);

        rankingDateListAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                getDate = rankDateList.get(position).getDateValue();
                for (int i = 0; i < rankDateList.size(); i++) {
                    rankDateList.get(i).setClick(false);
                }
                rankDateList.get(position).setClick(true);
                rankingDateListAdapter.upDateAdapter(rankDateList);
                if (isDay) {
                    tv_time.setText(rankDateList.get(position).getDateText());
                } else {
                    int index = rankDateList.get(position).getDateText().indexOf("月");
                    tv_time.setText(rankDateList.get(position).getDateText().substring(0, index + 1));
                }
                getDurationRanking();// 获取全部设备使用时长
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            }
        });

        initRefreshLayou();

        mPresenter = new DeviceUseTimePresenter(this, new DeviceUseTimeModel());
        onRefreshDate(); // 获取新数据
        mPresenter.getRankingDateList();// 获取控制排名的日期信息
    }


    private void onRefreshDate() {
        getDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        tv_time.setText(new SimpleDateFormat("yyyy年MM月dd日").format(new Date()));
        if (!TextUtils.isEmpty(getHouseId())) {
            mPresenter.getDurationRanking(); // 获取全部设备使用时长
        } else {
            dataList.clear();
            useTimeAdapter.upDateAdapter(dataList);
        }
    }

    private void getDurationRanking() {
        if (!TextUtils.isEmpty(getHouseId())) {
            mPresenter.getDurationRanking(); // 获取全部设备使用时长
        }
    }


    private SuperSwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;

    private void initRefreshLayou() {
        swipeRefreshLayout = (SuperSwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh);
        View child = LayoutInflater.from(swipeRefreshLayout.getContext()).inflate(R.layout.layout_pulltorefresh_head02, null);
        progressBar = (ProgressBar) child.findViewById(R.id.pb_view);
        textView = (TextView) child.findViewById(R.id.text_view);
        textView.setText(getString(R.string.put_refresh));
        imageView = (ImageView) child.findViewById(R.id.image_view);
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageResource(R.drawable.ic_refresh_pull_down);
        progressBar.setVisibility(View.GONE);
//        progressBar.setBackgroundResource(R.drawable.ic_refresh_loading);
//        progressBar.setIndeterminateDrawable(getResources().getDrawable(R.anim.com_progressbar_loading03));
        swipeRefreshLayout.setHeaderView(child);
        swipeRefreshLayout
                .setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {
                    @Override
                    public void onRefresh() {
                        getDurationRanking();// 获取全部设备使用时长

                        textView.setText(getString(R.string.refresh));
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
                        }, 1500);
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

    }

    public void initListener() {
        iv_day_month.setOnClickListener(this);
        tv_time.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        lazyLoad();
    }

    @Override
    protected void lazyLoad() {
        if (!isVisible || !isCreateView || mHasLoadOnce) {
            return;
        }
        mHasLoadOnce = true;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.iv_day_month:
                if (isDay) {
                    iv_day_month.setImageResource(R.drawable.ic_open_month);
                    isDay = false;

                    getDate = new SimpleDateFormat("yyyy-MM").format(new Date());
                    tv_time.setText(new SimpleDateFormat("yyyy年MM月").format(new Date()));
                } else {
                    iv_day_month.setImageResource(R.drawable.ic_open_day);
                    isDay = true;

                    getDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                    tv_time.setText(new SimpleDateFormat("yyyy年MM月dd日").format(new Date()));
                }
                mPresenter.getDurationRanking(); // 获取全部设备使用时长
                mPresenter.getRankingDateList();// 获取控制排名的日期信息
                break;
            case R.id.tv_time:
                showPopWindow();
                break;
        }
    }

    /***
     * 更多月份
     */
    private PopupWindow popupWindow;

    private void showPopWindow() {
        if (popupWindow == null) {
            popupWindow = new PopupWindow(getActivity());
//            setBackgroundAlpha(0.5f);//设置屏幕透明度
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            popupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
            popupWindow.setHeight(WindowManager.LayoutParams.MATCH_PARENT);
            View view = getActivity().getLayoutInflater().inflate(R.layout.popwindow_layout_more_usetime, null);
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
            LinearLayout ll_item = (LinearLayout) view.findViewById(R.id.ll_item);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(rankingDateListAdapter);

            popupWindow.setContentView(view);
            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(true);
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    popupWindow = null;
                }
            });
            ll_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                }
            });

            if (Build.VERSION.SDK_INT >= 24) { // Android 7.x中,PopupWindow高度为match_parent时,会出现兼容性问题,需要处理兼容性
                int[] location = new int[2]; // 记录anchor在屏幕中的位置
                rl_item.getLocationOnScreen(location);
                int offsetY = location[1] + rl_item.getHeight();
                    if (Build.VERSION.SDK_INT >= 25) { // Android 7.1中 和 8.0 ，PopupWindow高度为 match_parent 时，会占据整个屏幕
                    // 故而需要在 Android 7.1上再做特殊处理
                    int screenHeight = APP.screenHeight;// 获取屏幕高度
                    popupWindow.setHeight(screenHeight - offsetY); // 重新设置 PopupWindow 的高度
                }
                popupWindow.showAtLocation(rl_item, Gravity.NO_GRAVITY, 0, offsetY);
            } else {
                popupWindow.showAsDropDown(rl_item);
            }
        } else {
            if (Build.VERSION.SDK_INT >= 24) { // Android 7.x中,PopupWindow高度为match_parent时,会出现兼容性问题,需要处理兼容性
                int[] location = new int[2]; // 记录anchor在屏幕中的位置
                rl_item.getLocationOnScreen(location);
                int offsetY = location[1] + rl_item.getHeight();
                    if (Build.VERSION.SDK_INT >= 25) { // Android 7.1中 和 8.0 ，PopupWindow高度为 match_parent 时，会占据整个屏幕
                    // 故而需要在 Android 7.1上再做特殊处理
                    int screenHeight = APP.screenHeight;// 获取屏幕高度
                    popupWindow.setHeight(screenHeight - offsetY); // 重新设置 PopupWindow 的高度
                }
                popupWindow.showAtLocation(rl_item, Gravity.NO_GRAVITY, 0, offsetY);
            } else {
                popupWindow.showAsDropDown(rl_item);
            }
        }
    }


    /***
     * 获取全部设备使用时长
     *
     * @param resGetDurationRanking
     *
     */
    @Override
    public void getDurationRankingSuccess(ResGetDurationRanking resGetDurationRanking) {

        String content = new Gson().toJson(resGetDurationRanking);

        tv_number.setText(resGetDurationRanking.getTotalCount());
        if (resGetDurationRanking.getList() != null && resGetDurationRanking.getList().size() > 10) {
            dataList.clear();
            for (int i = 0; i < 10; i++) {
                dataList.add(resGetDurationRanking.getList().get(i));
            }
        } else {
            dataList = resGetDurationRanking.getList();
        }

    /*    int number =0;
        for (int i = 0; i <resGetDurationRanking.getList().size() ; i++) {
            int index = Integer.valueOf(resGetDurationRanking.getList().get(i).getCount());
            number +=index;
        }
        tv_number.setText(resGetDurationRanking.getTotalCount()+"=="+number);*/


        useTimeAdapter.upDateAdapter(dataList);
    }

    @Override
    public void getDurationRankingFali(String msg) {
//        showToast(msg);

    }

    @Override
    public void getRankingDateListSuccess(ResGetRankingDateList resGetRankingDateList) {
        String content = new Gson().toJson(resGetRankingDateList);

        rankDateList = resGetRankingDateList.getList();
//        rankDateList.addAll(rankDateList);
        if (rankDateList.size() > 0) {
            rankDateList.get(0).setClick(true);
        }
        rankingDateListAdapter.upDateAdapter(resGetRankingDateList.getList());


    }

    @Override
    public void getRankingDateListFali(String msg) {

    }

    @Override
    public String getHouseId() {
        return (String) SharedPreferencesUtils.getParam(getActivity(), "houseId", "");
    }

    @Override
    public String getDateValue() {//时间值( yyyy-MM-dd)

        return getDate;
    }

    @Override
    public String getDateType() { //时间类型（month-月，day-日）
        if (isDay) {
            return "day";
        } else {
            return "month";
        }

    }

    /**
     * 处理eventBus消息
     *
     * @param msg
     */
    protected void handleMessage(EventBusMessage msg) {
        if (msg.getMsgType() == Msg.SWITCH_HOUSE) { // 切换房子数据
            onRefreshDate();
            mPresenter.getRankingDateList();// 获取控制排名的日期信息
        }

      /*  else if (msg.getMsgType() == Msg.MY_HOUSE_CHANGE){// 房子信息、数量发生变化
            onRefreshDate();
            mPresenter.getRankingDateList();// 获取控制排名的日期信息
        }else if(msg.getMsgType() == Msg.EVENBUS_HOUSE_MESSAGE_5553){// 自己退出默认房子，刷新当天界面
            onRefreshDate();
            mPresenter.getRankingDateList();// 获取控制排名的日期信息
        }else if(msg.getMsgType() == Msg.EVENBUS_HOUSE_MESSAGE_5554){// 修改默认房子
            onRefreshDate();
            mPresenter.getRankingDateList();// 获取控制排名的日期信息
        }*/

    }

    @Override
    public void setPresenter(DeviceUseTimeContract.Presenter presenter) {

    }
}
