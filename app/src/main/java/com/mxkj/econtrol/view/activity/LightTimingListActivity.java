package com.mxkj.econtrol.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.response.ResGetScenePartOperatorTimerList;
import com.mxkj.econtrol.contract.LightTimingListContract;
import com.mxkj.econtrol.model.LightTimingListModel;
import com.mxkj.econtrol.presenter.LightTimingListPresenter;
import com.mxkj.econtrol.ui.adapter.TimingListAdapter;
import com.mxkj.econtrol.widget.ItemRemoveRecyclerView;
import com.mxkj.econtrol.widget.MyLinearLayout;

import java.util.ArrayList;
import java.util.List;


/***
 * Created by ${  chenjun  } on 2017/7/31.
 * 灯光定时（开、关）
 */
public class LightTimingListActivity extends BaseActivity implements View.OnClickListener, LightTimingListContract.View {

    // 初始化title
    private TextView tv_title_left;
    private TextView tv_title_content;
    private ImageView iv_title_right;

    private ItemRemoveRecyclerView recycleView_timing;
    private MyLinearLayout ll_status;
    private TimingListAdapter mAdapter;
    private ArrayList<ResGetScenePartOperatorTimerList.ListBean> timingList = new ArrayList<>();
    private LightTimingListContract.Presenter mPresenter;
    private String operatorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_timing_list);
        super.onCreate(savedInstanceState);


    }

    @Override
    protected void initView() {
        tv_title_left = (TextView) findViewById(R.id.tv_title_left);
        tv_title_content = (TextView) findViewById(R.id.tv_title_content);
        iv_title_right = (ImageView) findViewById(R.id.iv_title_right);
        tv_title_content.setText("定时设置");
        iv_title_right.setImageResource(R.drawable.home_timing_navbar_add);
        ll_status = (MyLinearLayout) findViewById(R.id.ll_status);
        recycleView_timing = (ItemRemoveRecyclerView) findViewById(R.id.recycleView_timing);
        recycleView_timing.setLayoutManager(new LinearLayoutManager(this));


        mAdapter = new TimingListAdapter(this, timingList, R.layout.adapter_timing_item, this);
        recycleView_timing.setAdapter(mAdapter);

        mPresenter = new LightTimingListPresenter(this, new LightTimingListModel());

        operatorId = getIntent().getStringExtra("operatorId");
        if (!TextUtils.isEmpty(operatorId)) {
//            mPresenter.getScenePartOperatorTimerList(operatorId);
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getScenePartOperatorTimerList(operatorId);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        tv_title_left.setOnClickListener(this);
        iv_title_right.setOnClickListener(this);
        recycleView_timing.setOnItemClickListener(new ItemRemoveRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(LightTimingListActivity.this, LightTimingEditActivity.class);
                intent.putExtra("type", "EDIT");
                intent.putExtra("time", timingList.get(position).getTime());
                intent.putExtra("id", timingList.get(position).getId());
                intent.putExtra("operatorId", operatorId);
                startActivity(intent);
            }

            @Override
            public void onDeleteClick(int position) {
                mPresenter.scenePartOperatorTimerDelete(timingList.get(position).getId());
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
            case R.id.iv_title_right:
                Intent intent = new Intent(this, LightTimingEditActivity.class);
                intent.putExtra("type", "NEW");
                intent.putExtra("activity", "LightTimingListActivity");
                intent.putExtra("operatorId", operatorId);
                startActivity(intent);
                break;
            case R.id.iv_switch:  // item 里面的定时开关
                ResGetScenePartOperatorTimerList.ListBean listBean = (ResGetScenePartOperatorTimerList.ListBean) v.getTag();
                if (!TextUtils.isEmpty(listBean.getStatus()) && listBean.getStatus().equals("0")) {
                    mPresenter.scenePartOperatorTimerStatusChange(listBean.getId(), "1"); // 开
                } else {
                    mPresenter.scenePartOperatorTimerStatusChange(listBean.getId(), "0");  //  关
                }
                break;
        }
    }

    boolean isStar = true;

    @Override
    public void getScenePartOperatorTimerListSuccess(ResGetScenePartOperatorTimerList resGetScenePartOperatorTimerList) {
        String content = new Gson().toJson(resGetScenePartOperatorTimerList);

        timingList = (ArrayList<ResGetScenePartOperatorTimerList.ListBean>) resGetScenePartOperatorTimerList.getList();
        if (timingList.size() > 0) {
            mAdapter.upDateAdapter(timingList);
            ll_status.showData();
            isStar=false;
        } else {
            ll_status.setImage(R.drawable.ic_no_timing);
            ll_status.setText("暂无定时");
            View view = ll_status.showEmpty(R.layout.custom_empty_view);
            Button btn_add = (Button) view.findViewById(R.id.btn_add);
            btn_add.setVisibility(View.GONE);
        }
    }

    @Override
    public void getScenePartOperatorTimerListFail(String msg) {
        showToast(msg);
    }

    @Override
    public void scenePartOperatorTimerStatusChangeSuccess(BaseResponse baseResponse) {
        String content = new Gson().toJson(baseResponse);
        // 修改成功后刷新数据
        mPresenter.getScenePartOperatorTimerList(operatorId);
    }

    @Override
    public void scenePartOperatorTimerStatusChangeFail(String msg) {
        showToast(msg);
    }

    @Override
    public void scenePartOperatorTimerDeleteSuccess(BaseResponse baseResponse) {
        String content = new Gson().toJson(baseResponse);
        // 修改成功后刷新数据
        mPresenter.getScenePartOperatorTimerList(operatorId);
    }

    @Override
    public void scenePartOperatorTimerDeleteFail(String msg) {
        showToast(msg);
    }

    @Override
    public void setPresenter(LightTimingListContract.Presenter presenter) {

    }
}
