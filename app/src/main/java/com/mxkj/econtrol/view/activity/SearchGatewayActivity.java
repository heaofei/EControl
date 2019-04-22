package com.mxkj.econtrol.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqPartBindGateway;
import com.mxkj.econtrol.net.APISubcriber;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;
import com.mxkj.econtrol.ui.activity.LockAddTipsActivity;
import com.mxkj.econtrol.ui.adapter.GatewayListAdapter;
import com.mxkj.econtrol.utils.RSAPKCS8X509Utils;
import com.mxkj.econtrol.utils.gateway.DeviceInfo;
import com.mxkj.econtrol.utils.gateway.GatewayService;
import com.mxkj.econtrol.utils.gateway.IAddGatewayCallback;
import com.mxkj.econtrol.widget.MyLinearLayout;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

/***
 * 搜索网关列表页面（飞比网关）
 */
public class SearchGatewayActivity extends BaseActivity {

    // 初始化title
    private TextView tv_title_left;
    private TextView tv_title_content;
    private ImageView iv_more;
    private TextView tv_title_right;
    private String mPartId;// 门锁部件id

    private MyLinearLayout ll_status;
    private RecyclerView recyclerView;

    private RelativeLayout rl_item;
    private LinearLayout ll_statue;
    private GifImageView iv_gifView;
    private TextView tv_tips;
    private Button btn_search;

    private GatewayService service;


    private GatewayListAdapter mGatewayListAdapter;
    private List<DeviceInfo> mLockList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_search_gateway);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        mPartId  = getIntent().getStringExtra("partId");
        tv_title_left = findView(R.id.tv_title_left);
        tv_title_content = findView(R.id.tv_title_content);
        iv_more = findView(R.id.iv_more);
        tv_title_right = findView(R.id.tv_title_right);
        tv_title_content.setText("添加网关");

        ll_status = findView(R.id.ll_status);
        recyclerView = findView(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        rl_item = findView(R.id.rl_item);
        ll_statue = findView(R.id.ll_statue);
        iv_gifView = findView(R.id.iv_gifView);
        tv_tips = findView(R.id.tv_tips);
        btn_search = findView(R.id.btn_search);


        service = new GatewayService();

    }

    @Override
    protected void initData() {
        mGatewayListAdapter = new GatewayListAdapter(this, mLockList, R.layout.adapter_search_gateway_list_item, this);
        recyclerView.setAdapter(mGatewayListAdapter);
    }


    @Override
    protected void initListener() {
        tv_title_left.setOnClickListener(this);
        btn_search.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_title_left:
                finish();
                break;
            case R.id.btn_search:
          /*      ll_status.setVisibility(View.VISIBLE);
                rl_item.setVisibility(View.GONE);
                recyclerView.setAdapter(mLockListAdapter);
*/
                showLoading();
                doSearchGateway();

                break;
            case R.id.tv_add:
                DeviceInfo deviceInfo = (DeviceInfo) v.getTag();
                Binding(deviceInfo);
                break;
            default:
                break;
        }
    }

    private void doSearchGateway() {
        service.addGateway(this, new IAddGatewayCallback() {
            @Override
            public void onFailed(final String failedMessage) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dismissLoading();
                        selectFail();// 搜索网关失败显示
                        Toast.makeText(SearchGatewayActivity.this, failedMessage, Toast.LENGTH_LONG).show();
                    }
                });
            }
            @Override
            public void onSuccess(final DeviceInfo deviceInfo) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Toast.makeText(SearchGatewayActivity.this, " uuid --> " + uuid, Toast.LENGTH_LONG).show();
//                        Binding(uuid);

                        mLockList.add(deviceInfo);
                        ll_status.setVisibility(View.VISIBLE);
                        rl_item.setVisibility(View.GONE);
                        mGatewayListAdapter.upDateAdapter(mLockList);
                        dismissLoading();
                    }

                });
            }
        });
    }


    private void Binding(final DeviceInfo deviceInfo) {
        final ReqPartBindGateway request = new ReqPartBindGateway();
        request.setPartId(mPartId);
        request.setGatewayId(deviceInfo.getId());
        request.setGatewaySn(deviceInfo.getSn());
        request.setGatewayName("网关："+deviceInfo.getSn());
        request.setGatewayPassword(RSAPKCS8X509Utils.getInstance().encryptWithBase64(deviceInfo.getPassword()));
        request.setGatewayType("00"); // type 00--飞比网关

        RetrofitUtil.getInstance().getmApiService()
                .partBindGateway(request.toJsonStr())
                .compose(new APITransFormer<BaseResponse>())
                .subscribe(new APISubcriber<BaseResponse>() {
                    @Override
                    public void onFail(BaseResponse baseResponse,String msg) {
                        showToast(msg);
                    }
                    @Override
                    public void onSuccess(BaseResponse response) {
//                        String content = new Gson().toJson(response);
//                        showToast(response.getMsg());
                        showToast("添加成功");

                        finish();
                        Intent  intent = new Intent(SearchGatewayActivity.this,LockAddTipsActivity.class);
                        intent.putExtra("partId",mPartId);
                        intent.putExtra("GatewaySn",deviceInfo.getSn());
                        startActivity(intent);

                    }
                });
    }


    private void selectFail() {
        ll_status.setVisibility(View.GONE);
        rl_item.setVisibility(View.VISIBLE);
        ll_statue.setVisibility(View.VISIBLE);
        iv_gifView.setVisibility(View.GONE);
        tv_tips.setVisibility(View.INVISIBLE);

        btn_search.setText("再试一次");

    }


}
