package com.mxkj.econtrol.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqApplyBindHouseAudit;
import com.mxkj.econtrol.bean.request.ReqGetApplyBindHouseList;
import com.mxkj.econtrol.bean.response.ResGetAppPushMessageList;
import com.mxkj.econtrol.bean.response.ResGetApplyBindHouseList;
import com.mxkj.econtrol.contract.ApplyBindHouseListContract;
import com.mxkj.econtrol.contract.MessageListContract;
import com.mxkj.econtrol.model.ApplyBindHouseListModel;
import com.mxkj.econtrol.presenter.ApplyBindHouseListPresenter;
import com.mxkj.econtrol.ui.adapter.ApplyBindHouseListAdapter;
import com.mxkj.econtrol.ui.adapter.SystemNotifiAdapter;
import com.mxkj.econtrol.utils.SharedPreferencesUtils;
import com.mxkj.econtrol.widget.MyLinearLayout;

import java.util.ArrayList;
import java.util.List;

/***
 *  业主审核申请绑定房子列表
 */
public class ApplyBindHouseListActivity extends BaseActivity implements ApplyBindHouseListContract.View {

    // 初始化title
    private TextView tv_title_left;
    private TextView tv_title_content;
    private TextView tv_title_right;

    private RecyclerView recyclerView;
    private MyLinearLayout ll_status;
    private String houseId;
    private SystemNotifiAdapter systemNotifiAdapter;

    private ApplyBindHouseListContract.Presenter mPresenter;
    private ApplyBindHouseListAdapter adapter;
    private List<ResGetApplyBindHouseList.PageBean.ContentBean> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_apply_bind_house_list);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        tv_title_left = (TextView) findViewById(R.id.tv_title_left);
        tv_title_content = (TextView) findViewById(R.id.tv_title_content);
        tv_title_content.setText("申请列表");

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void initData() {
        mPresenter = new ApplyBindHouseListPresenter(this,new ApplyBindHouseListModel());
        houseId = getIntent().getStringExtra("houseId");

        mList = new ArrayList<>();
        adapter = new ApplyBindHouseListAdapter(this,mList,R.layout.adapter_apply_bindhouse_list_item,this);
        recyclerView.setAdapter(adapter);

        getApplyBindHouseList();

    }


    public void getApplyBindHouseList() {
        if (mList!=null) {
            mList.clear();
        }
        if (!TextUtils.isEmpty(houseId) && houseId.indexOf(";")!=-1){
            String [] ids = houseId.split(";");
            for (int i = 0; i <ids.length ; i++) {
                mPresenter.getApplyBindHouseList(new ReqGetApplyBindHouseList(1,300,ids[i],"0")); // status;    //0、未审核，1、审核，2、拒绝'
            }
        }else {
            mPresenter.getApplyBindHouseList(new ReqGetApplyBindHouseList(1,300,houseId,"0")); // status;    //0、未审核，1、审核，2、拒绝'
        }
    }

    @Override
    protected void initListener() {
        tv_title_left.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_title_left:
                finish();
                break;
            case R.id.tv_agree: // 同意加入
                ResGetApplyBindHouseList.PageBean.ContentBean contentBean1 = (ResGetApplyBindHouseList.PageBean.ContentBean) v.getTag();
                mPresenter.applyBindHouseAudit(new ReqApplyBindHouseAudit(contentBean1.getApplyNo(),"1")); // applyNo;  申请编码 status;  审核状态（ '1、通过，2、拒绝',）
                break;
            case R.id.tv_refuse: // 拒绝加入
                ResGetApplyBindHouseList.PageBean.ContentBean contentBean = (ResGetApplyBindHouseList.PageBean.ContentBean) v.getTag();
                mPresenter.applyBindHouseAudit(new ReqApplyBindHouseAudit(contentBean.getApplyNo(),"2")); // applyNo;  申请编码 status;  审核状态（ '1、通过，2、拒绝',）
                break;
        }
    }

    @Override
    public void getApplyBindHouseListSuccess(ResGetApplyBindHouseList resGetApplyBindHouseList) {
        String content = new Gson().toJson(resGetApplyBindHouseList);

//        mList = resGetApplyBindHouseList.getPage().getContent();
        mList.addAll(resGetApplyBindHouseList.getPage().getContent());
        adapter.upDateAdapter(mList);

    }

    @Override
    public void getApplyBindHouseListFail(String msg) {
//        showToast(msg);
    }

    @Override
    public void applyBindHouseAuditSuccess(BaseResponse baseResponse) {
        String content = new Gson().toJson(baseResponse);
        getApplyBindHouseList();// 重新获取申请列表
    }

    @Override
    public void applyBindHouseAuditFail(String msg) {
        showToast(msg);
    }

    @Override
    public void setPresenter(ApplyBindHouseListContract.Presenter presenter) {

    }

}
