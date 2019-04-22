package com.mxkj.econtrol.ui.activity;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.bigkoo.pickerview.TimePickerView;
import com.google.gson.Gson;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.app.Config;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.base.CommonAdapter;
import com.mxkj.econtrol.bean.request.ReqGetHouseControlLogList;
import com.mxkj.econtrol.bean.request.ReqGetPartUseTime;
import com.mxkj.econtrol.bean.response.ResGetHouseControlLogList;
import com.mxkj.econtrol.bean.response.ResGetPartUseTime;
import com.mxkj.econtrol.net.APIService;
import com.mxkj.econtrol.net.APISubcriber;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;
import com.mxkj.econtrol.ui.adapter.UsageStatisticsSceneAdapter;
import com.mxkj.econtrol.utils.DateTimeUtil;
import com.mxkj.econtrol.utils.SharedPreferencesUtils;
import com.mxkj.econtrol.view.activity.ArticleDetailsActivity;
import com.mxkj.econtrol.widget.MyLinearLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 统计使用 :使用时长
 */
public class UsageStatisticsActivity extends BaseActivity {


    // 初始化title
    private TextView tv_title_left;
    private TextView tv_title_content;
    private ImageView iv_title_right;
    private MyLinearLayout ll_status;
    private RecyclerView recyclerView;
    private WebView webView;
    private TextView tv_day, tv_num, tv_time;

    private TimePickerView pvTime; // 生日时间选择器

    private String mHouseId;
    private String mSceneId;
    private String mDay;


    private UsageStatisticsSceneAdapter usageStatisticsSceneAdapter;
    private List<ResGetPartUseTime.DataBean.SceneListBean> dateList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_usage_statistics);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        tv_title_left = findView(R.id.tv_title_left);
        tv_title_content = findView(R.id.tv_title_content);
        tv_title_content.setText("使用时长");
        iv_title_right = findView(R.id.iv_title_right);
        iv_title_right.setImageResource(R.drawable.ic_date_bg);
        ll_status = (MyLinearLayout) findViewById(R.id.ll_status);

        recyclerView = findView(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        usageStatisticsSceneAdapter = new UsageStatisticsSceneAdapter(this, dateList, R.layout.adapter_usage_scene, this);
        recyclerView.setAdapter(usageStatisticsSceneAdapter);

        usageStatisticsSceneAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                for (int i = 0; i < dateList.size(); i++) {
                    dateList.get(i).setClick(false);
                }
                dateList.get(position).setClick(true);
                usageStatisticsSceneAdapter.upDateAdapter(dateList);

                mSceneId = dateList.get(position).getId();
                tv_num.setText("开关次数: " + dateList.get(position).getNum());
                tv_time.setText("用电时长: " + dateList.get(position).getUseTime() + "小时");
                String url = Config.Base_URL + APIService.getPartUseTimeShare + "?houseId=" + mHouseId + "&sceneId=" + mSceneId + "&day=" + mDay;
                webView.loadUrl(url);

            }
        });

    }


    @Override
    protected void initData() {

        tv_day = findView(R.id.tv_day);
        tv_num = findView(R.id.tv_num);
        tv_time = findView(R.id.tv_time);

        webView = findView(R.id.webView);
        //支持javascript
        webView.getSettings().setJavaScriptEnabled(true);
        //  重写 WebViewClient
//        webView.setWebViewClient(new UsageStatisticsActivity.MyWebViewClient()); // 这个是为了设置图片自适应
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


        //设置Web视图
//        webView.setWebViewClient(new webViewClient());
        // 支持js调用java   jsInAndroid相当于和H5端定义的协议
//        webView.addJavascriptInterface(new JSInterface(), "jsInAndroid");

        // 调用H5页面的方法  无参方法 getInfo方法名
//        webView.loadUrl("javascript:getInfo()");
        //有参方法
//         webView.loadUrl("javascript:getInfo("+"'参数'"+")");


        mHouseId = getHouseId();
        mDay = DateTimeUtil.getDate(System.currentTimeMillis());
        getPartUseTime(getHouseId(), mDay);

    }


    public void getPartUseTime(String houseId, final String day) {
        ReqGetPartUseTime reqGetPartUseTime = new ReqGetPartUseTime(houseId, day); //
        RetrofitUtil.getInstance().getmApiService()
                .getPartUseTime(reqGetPartUseTime.toJsonStr())
                .compose(new APITransFormer<ResGetPartUseTime>())
                .subscribe(new APISubcriber<ResGetPartUseTime>() {
                    @Override
                    public void onFail(ResGetPartUseTime baseResponse, String msg) {
                        showToast(msg);

                        View view = ll_status.showEmpty(R.layout.custom_empty_view2);
                        ImageView iv = (ImageView) view.findViewById(R.id.iv);
                        iv.setImageResource(R.drawable.ic_empty_usgae);
                        TextView tv = (TextView) view.findViewById(R.id.tv);
                        tv.setText("没有灯泡使用数据");

                    }

                    @Override
                    public void onSuccess(ResGetPartUseTime result) {
                        dateList = result.getData().getSceneList();


                        if (dateList.size() > 0) {
                            dateList.get(0).setClick(true);
                            tv_num.setText("开关次数: " + dateList.get(0).getNum());
                            tv_time.setText("用电时长: " + dateList.get(0).getUseTime() + "小时");
                            mSceneId = dateList.get(0).getId();
                            ll_status.showData();
                        }else {
                            tv_num.setText("开关次数: ");
                            tv_time.setText("用电时长: ");

                            View view = ll_status.showEmpty(R.layout.custom_empty_view2);
                            ImageView iv = (ImageView) view.findViewById(R.id.iv);
                            iv.setImageResource(R.drawable.ic_empty_usgae);
                            TextView tv = (TextView) view.findViewById(R.id.tv);
                            tv.setText("今天没有灯泡使用数据");

                        }




                            usageStatisticsSceneAdapter.upDateAdapter(dateList);
                        mDay = day;
                        tv_day.setText(mDay.replaceAll("-", "."));

                        String url = Config.Base_URL + APIService.getPartUseTimeShare + "?houseId=" + mHouseId + "&sceneId=" + mSceneId + "&day=" + mDay;
                        webView.loadUrl(url);

                    }
                });
    }


    public String getHouseId() {
        return (String) SharedPreferencesUtils.getParam(this, "houseId", "");// 传房子id
    }

    @Override
    protected void initListener() {
        tv_title_left.setOnClickListener(this);
        iv_title_right.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_title_left:
                finish();
                break;
            case R.id.iv_title_right:
                if (pvTime == null) {
                    //时间选择器
                    pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
                        @Override
                        public void onTimeSelect(Date date, View v) {//选中事件回调

                            if (DateTimeUtil.isBeforeToDay(date)) {
                                getPartUseTime(getHouseId(), getTime(date));
                            } else {
                                showToast("请选择今天之前的日期.");
                            }
                        }
                    })
                            .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                            .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒
                            .build();
                    pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                    pvTime.show();
                } else {
                    pvTime.show();
                }
                break;
        }
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
}
