package com.mxkj.econtrol.view.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.Msg;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.base.TCPCMDResult;
import com.mxkj.econtrol.bean.EventBusMessage;
import com.mxkj.econtrol.bean.EventBusUIMessage;
import com.mxkj.econtrol.bean.request.ReqPartBindGateway;
import com.mxkj.econtrol.bean.request.ReqPartBindSnid;
import com.mxkj.econtrol.bean.response.ResGetAppPushMessageList;
import com.mxkj.econtrol.bean.response.ResGetSysAnnouncement;
import com.mxkj.econtrol.bean.response.TcpResSearchLockListRespones;
import com.mxkj.econtrol.bean.tcpcmd.Command;
import com.mxkj.econtrol.bean.tcpcmd.SmartPartCMD;
import com.mxkj.econtrol.net.APISubcriber;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;
import com.mxkj.econtrol.net.TcpClient;
import com.mxkj.econtrol.ui.activity.LockAddTipsActivity;
import com.mxkj.econtrol.ui.adapter.MessageListAdapter;
import com.mxkj.econtrol.ui.adapter.SearchLockListAdapter;
import com.mxkj.econtrol.utils.LogUtil;
import com.mxkj.econtrol.widget.MyLinearLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

/***
 * 搜索门锁列表页面
 */
public class SearchLockListActivity extends BaseActivity {

    // 初始化title
    private TextView tv_title_left;
    private TextView tv_title_content;
    private ImageView iv_more;
    private TextView tv_title_right;

    private GifImageView iv_gifView;
    private TextView tv_search;
    private TextView tv_size;
    private LinearLayout ll_statue;
    private boolean isSearch = true;
    private String mPartId;// 门锁部件id

    private RecyclerView recyclerView;
    private MyLinearLayout ll_status;
    private SearchLockListAdapter mLockListAdapter;
    private List<TcpResSearchLockListRespones> mLockList = new ArrayList<>();
    private String mGatewaySn;// 网关sn
    private int recLen = 30; // 门锁搜索设定时间 （秒）

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_search_lock_list);
        super.onCreate(savedInstanceState);

        //设置状态栏颜色
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = this.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(0xffff695a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initView() {
        mPartId = getIntent().getStringExtra("partId");
        mGatewaySn = getIntent().getStringExtra("GatewaySn");
        tv_title_left = findView(R.id.tv_title_left);
        tv_title_content = findView(R.id.tv_title_content);
        iv_more = findView(R.id.iv_more);
        tv_title_right = findView(R.id.tv_title_right);
        tv_title_content.setText("添加门锁");

        iv_gifView = findView(R.id.iv_gifView);
        tv_search = findView(R.id.tv_search);
        tv_size = findView(R.id.tv_size);
        ll_statue = findView(R.id.ll_statue);

        ll_status = (MyLinearLayout) findViewById(R.id.ll_status);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        mLockListAdapter = new SearchLockListAdapter(this, mLockList, R.layout.adapter_search_lock_list_item, this);


        sendCMD();// 一进来就发送tcp搜索门锁列表

    }


    /***搜索门锁*/
    private void sendCMD() {
        SmartPartCMD smartPartCMD = new SmartPartCMD();
        smartPartCMD.setAction("FEIBIT_DEVICE_SEARCH");// action为搜索门锁
        smartPartCMD.setDevice(mGatewaySn);
        Command command = new Command();
        smartPartCMD.setCommand(command);
        TcpClient.getInstacne().sendCMD(this, smartPartCMD, new TCPCMDResult() {
            @Override
            public void onSuccess(String result) {
            }
            @Override
            public void onFail(String msg) {
//                showToast(msg);
            }
        });
    }

    @Override
    protected void initData() {
        recyclerView.setAdapter(mLockListAdapter);

        handler.postDelayed(runnable, 1000);
    }




    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (recLen > 0) {
                recLen--;
                handler.postDelayed(this, 1000);
            } else {
                isSearch = false;
                iv_gifView.setImageResource(R.drawable.ic_searching_lock_bg);
                tv_search.setText("重新搜索");
                if (mLockList.size() == 0) {
                    recyclerView.setVisibility(View.GONE);
                    ll_statue.setVisibility(View.VISIBLE);
                }
            }
        }
    };


    @Override
    protected void initListener() {
        tv_title_left.setOnClickListener(this);
        iv_gifView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_title_left:
                finish();
                break;
            case R.id.tv_add:
                TcpResSearchLockListRespones tcpResSearchLockListRespones = (TcpResSearchLockListRespones) v.getTag();
                Binding(tcpResSearchLockListRespones);
                break;

            case R.id.iv_gifView:
                if (!isSearch) {   // 重新搜索
                    isSearch = true;
                    iv_gifView.setImageResource(R.drawable.ic_searching_lock);
                    tv_search.setText("正在搜索设备");
                    recLen = 30;
                    handler.postDelayed(runnable, 1000);

                    tv_size.setText("已经搜索到0" + "个设备");
                    recyclerView.setVisibility(View.VISIBLE);
                    ll_statue.setVisibility(View.GONE);

                    mLockList.clear();
                    mLockListAdapter.upDateAdapter(mLockList);
                    sendCMD();
                }
            default:
                break;
        }
    }

    private void Binding(TcpResSearchLockListRespones tcpResSearchLockListRespones) {

        final ReqPartBindSnid request = new ReqPartBindSnid();
        request.setPartId(mPartId);
        request.setSn(tcpResSearchLockListRespones.getCommand().getSn());
        request.setShortAddress(tcpResSearchLockListRespones.getCommand().getShortAddress());
        request.setIeeeAddress(tcpResSearchLockListRespones.getCommand().getIeeeAddress());
        request.setEndPoint(tcpResSearchLockListRespones.getCommand().getEndPoint());

        RetrofitUtil.getInstance().getmApiService()
                .partBindSn(request.toJsonStr())
                .compose(new APITransFormer<BaseResponse>())
                .subscribe(new APISubcriber<BaseResponse>() {
                    @Override
                    public void onFail(BaseResponse baseResponse, String msg) {
                        showToast(msg);
                    }
                    @Override
                    public void onSuccess(BaseResponse response) {
//                        String content = new Gson().toJson(response);
//                        showToast(response.getMsg());
                        EventBus.getDefault().post(new EventBusUIMessage(Msg.LOCK_ADD_SUCCESS));
                        finish();
                    }
                });
    }


    @Override
    protected void handleMessage(EventBusMessage msg) {
        super.handleMessage(msg);
        if (msg.getMsgType() == Msg.EVENBUS_SEARCH_LOCK_RESULT) { // 搜索到的门锁列表
            TcpResSearchLockListRespones tcpResSearchLockListRespones = (TcpResSearchLockListRespones) msg.getData();
            mLockList.add(tcpResSearchLockListRespones);
            tv_size.setText("已经搜索到" + mLockList.size() + "个设备");
            mLockListAdapter.upDateAdapter(mLockList);
        }
    }

}
