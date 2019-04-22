package com.mxkj.econtrol.view.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.base.CommonAdapter;
import com.mxkj.econtrol.bean.request.ReqHouseManagerSwitchHandle;
import com.mxkj.econtrol.bean.response.ResAppTodoList;
import com.mxkj.econtrol.net.APISubcriber;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;
import com.mxkj.econtrol.ui.adapter.AppTodoListAdapter;
import com.mxkj.econtrol.widget.MyLinearLayout;

import java.util.List;

/***
 *  APP房子信息待处理列表
 */
public class AppTodoListActivity extends BaseActivity  {

    // 初始化title
    private TextView tv_title_left;
    private TextView tv_title_content;
    private TextView tv_title_right;

    private RecyclerView recyclerView;
    private MyLinearLayout ll_status;
    private String houseId;

    private AppTodoListAdapter adapter;
    private List<ResAppTodoList.SwitchMasterTodoBean> mList;
    private int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_app_house_todo_list);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        tv_title_left = (TextView) findViewById(R.id.tv_title_left);
        tv_title_content = (TextView) findViewById(R.id.tv_title_content);
        tv_title_content.setText("未处理列表");

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mList = (List<ResAppTodoList.SwitchMasterTodoBean>) getIntent().getSerializableExtra("todoList");

    }

    @Override
    protected void initData() {
        houseId = getIntent().getStringExtra("houseId");

        adapter = new AppTodoListAdapter(this,mList,R.layout.adapter_apply_bindhouse_list_item,this);
        adapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mPosition = position;
            }
        });
        recyclerView.setAdapter(adapter);

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
            case R.id.tv_agree: // 同意
//                ResAppTodoList.SwitchMasterTodoBean contentBean1 = (ResAppTodoList.SwitchMasterTodoBean) v.getTag();
//                int position = contentBean1.getPosition();
                 mPosition = (int)v.getTag();
                houseManagerSwitchAssign(mList.get(mPosition).getHouseId(),"1");// accepted 0-拒绝，1-同意

                break;
            case R.id.tv_refuse: // 拒绝

//                ResAppTodoList.SwitchMasterTodoBean contentBean = (ResAppTodoList.SwitchMasterTodoBean) v.getTag();
                mPosition = (int)v.getTag();

                houseManagerSwitchAssign(mList.get(mPosition).getHouseId(),"0");// accepted 0-拒绝，1-同意
                break;
        }
    }


    // 请求处理业主确认转让权限
    private void houseManagerSwitchAssign(String houseId, String accepted) {
        final ReqHouseManagerSwitchHandle request = new ReqHouseManagerSwitchHandle();
        request.setHouseId(houseId);
        request.setAccepted(accepted);
        RetrofitUtil.getInstance().getmApiService()
                .houseManagerSwitchHandle(request.toJsonStr())
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
                        mList.remove(mPosition);
                        adapter.upDateAdapter(mList);
                    }
                });
    }


}
