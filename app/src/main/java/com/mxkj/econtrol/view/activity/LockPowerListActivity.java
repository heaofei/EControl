package com.mxkj.econtrol.view.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.app.Msg;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.EventBusUIMessage;
import com.mxkj.econtrol.bean.request.ReqGatewayUnbindHouse;
import com.mxkj.econtrol.bean.request.ReqLockGetPartPermissionsList;
import com.mxkj.econtrol.bean.request.ReqLockPartPermissionsUpdate;
import com.mxkj.econtrol.bean.request.ReqPartBindSnid;
import com.mxkj.econtrol.bean.request.ReqPartUnbindGateway;
import com.mxkj.econtrol.bean.request.ReqPartUnbindSnid;
import com.mxkj.econtrol.bean.response.ResGetAtHomeUserList;
import com.mxkj.econtrol.bean.response.ResLockPowerList;
import com.mxkj.econtrol.contract.LockPowerListContract;
import com.mxkj.econtrol.model.LockPowerListModel;
import com.mxkj.econtrol.net.APISubcriber;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;
import com.mxkj.econtrol.presenter.LockPowerListPresenter;
import com.mxkj.econtrol.ui.adapter.LockPowerListAdapter;
import com.mxkj.econtrol.ui.adapter.SearchLockListAdapter;
import com.mxkj.econtrol.utils.MyDialogUtil;
import com.mxkj.econtrol.utils.SharedPreferencesUtils;
import com.mxkj.econtrol.widget.MyLinearLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/***
 *   开锁权限列表
 */
public class LockPowerListActivity extends BaseActivity implements LockPowerListContract.View {

    // 初始化title
    private TextView tv_title_left;
    private TextView tv_title_content;
    private ImageView iv_more;
    private TextView tv_title_right;

    private TextView tv_lock,tv_gateway;
    private RecyclerView recyclerView;
    private TextView tv_unbind_gateway, tv_unbind_lock;
    private LinearLayout ll_locksetting;
    private RelativeLayout rl_lock, rl_gateway;
    private MyLinearLayout ll_status;
    private LockPowerListAdapter mLockListAdapter;
    private List<ResLockPowerList.ListBean> mLockList = new ArrayList<>();
    private LockPowerListContract.Presenter mPresenter;
    private String lockId;
    private String gatewaySn;
    private String lockName;
    private boolean lockStatue;
    private boolean gatewayStatue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_lock_power_list);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        tv_title_left = findView(R.id.tv_title_left);
        tv_title_content = findView(R.id.tv_title_content);
        iv_more = findView(R.id.iv_more);
        tv_title_right = findView(R.id.tv_title_right);
        tv_title_content.setText("门锁设置");

        tv_unbind_gateway = findView(R.id.tv_unbind_gateway);
        tv_unbind_lock = findView(R.id.tv_unbind_lock);
        ll_locksetting = findView(R.id.ll_locksetting);
        rl_lock = findView(R.id.rl_lock);
        rl_gateway = findView(R.id.rl_gateway);
        ll_status = (MyLinearLayout) findViewById(R.id.ll_status);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        tv_lock = (TextView) findViewById(R.id.tv_lock);
        tv_gateway = (TextView) findViewById(R.id.tv_gateway);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        lockId = getIntent().getStringExtra("lockId");
        lockStatue = getIntent().getBooleanExtra("lockStatue", false);
        gatewayStatue = getIntent().getBooleanExtra("gatewayStatue", false);
        gatewaySn = getIntent().getStringExtra("gatewaySn");
        lockName = getIntent().getStringExtra("lockName");

        if (!TextUtils.isEmpty(gatewaySn)) {
            tv_gateway.setText("网关："+gatewaySn);
        }
        if (!TextUtils.isEmpty(lockName)) {
            tv_lock.setText(lockName);
        }
        if (!lockStatue && !gatewayStatue) {
            ll_locksetting.setVisibility(View.GONE);
        } else if (!lockStatue) {
            rl_lock.setVisibility(View.GONE);
        } else if (!gatewayStatue) {
            rl_gateway.setVisibility(View.GONE);
        }


        mPresenter = new LockPowerListPresenter(this, new LockPowerListModel());
        ReqLockGetPartPermissionsList reqLockGetPartPermissionsList = new ReqLockGetPartPermissionsList();
        reqLockGetPartPermissionsList.setHouseId(getHouseId());
        reqLockGetPartPermissionsList.setPartId(lockId);
        mPresenter.getPartPermissionsList(reqLockGetPartPermissionsList);

        mLockListAdapter = new LockPowerListAdapter(this, mLockList, R.layout.adapter_lock_power_list_item, this);
        recyclerView.setAdapter(mLockListAdapter);


    }

    @Override
    protected void initData() {


    }

    @Override
    protected void initListener() {
        tv_title_left.setOnClickListener(this);
        tv_unbind_gateway.setOnClickListener(this);
        tv_unbind_lock.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_title_left:
                finish();
                break;
            case R.id.iv_add:
                ResLockPowerList.ListBean contentBean = (ResLockPowerList.ListBean) v.getTag();
                String permissions = "";
                ReqLockPartPermissionsUpdate reqLockPartPermissionsUpdate = new ReqLockPartPermissionsUpdate();
                if (contentBean.getPermissions().indexOf("a") != -1) {
                    // 取消开锁权限
                    if (contentBean.getPermissions().indexOf(",a") != -1) {
                        permissions = contentBean.getPermissions().replace(",a", "");
                    } else if (contentBean.getPermissions().indexOf("a,") != -1) {
                        permissions = contentBean.getPermissions().replace("a,", "");
                    } else {
                        permissions = contentBean.getPermissions().replace("a", "");
                    }
                } else {
                    // 允许开锁权限
                    if (!TextUtils.isEmpty(contentBean.getPermissions())) {
                        permissions = contentBean.getPermissions() + ",a";
                    } else {
                        permissions = "a";
                    }
                }
                if (TextUtils.isEmpty(permissions)) { // 这里 要是permissions 为空时接口会报错， 要传个none进去， 与后接口定好
                    permissions = "none";
                }
                reqLockPartPermissionsUpdate.setPermissions(permissions);//权限标识，
                reqLockPartPermissionsUpdate.setPartId(lockId);
                reqLockPartPermissionsUpdate.setHouseId(getHouseId());
                reqLockPartPermissionsUpdate.setUserId(contentBean.getUser().getId()); // 对象Id
                mPresenter.partPermissionsUpdate(reqLockPartPermissionsUpdate);

                break;
            case R.id.tv_unbind_gateway:// 解绑网关
                MyDialogUtil myDialogUtil = new MyDialogUtil(this) {
                    @Override
                    public void confirm() {
                        // 旧接口，解绑的是网关跟门锁的关系，并没有解除房子跟网关的关系
                        /*ReqPartUnbindGateway reqPartUnbindGateway = new ReqPartUnbindGateway();
                        reqPartUnbindGateway.setPartId(lockId);
                        mPresenter.partUnbindGateway(reqPartUnbindGateway);*/

                        gatewayUnbindHouse();

                    }
                };
                myDialogUtil.showPerfectTipDialog("解绑网关",
                        "确定要解绑吗?解绑网关后需要重新绑定网关以及门锁才能正常使用",
                        "取消",
                        "解绑");
                break;
            case R.id.tv_unbind_lock:   // 解绑门锁

                MyDialogUtil myDialogUtil1 = new MyDialogUtil(this) {
                    @Override
                    public void confirm() {
                        ReqPartUnbindSnid reqPartUnbindSnid = new ReqPartUnbindSnid();
                        reqPartUnbindSnid.setPartId(lockId);
                        mPresenter.partUnbindSnid(reqPartUnbindSnid);
                    }
                };
                myDialogUtil1.showPerfectTipDialog("解绑门锁",
                        "解绑门锁后需要重新添加门锁才能正常使用",
                        "取消",
                        "解绑");
                break;

        }
    }


    public String getHouseId() {
        return (String) SharedPreferencesUtils.getParam(this, "houseId", "");// 传房子id
    }

    @Override
    public void getPartPermissionsListSuccess(ResLockPowerList resLockPowerList) {
        mLockList = resLockPowerList.getList();

        String content = new Gson().toJson(resLockPowerList);

        Iterator<ResLockPowerList.ListBean> iterator = mLockList.iterator();
        while (iterator.hasNext()) {
            ResLockPowerList.ListBean user = iterator.next();
            if (user.getUser().getId().equals(APP.user.getUserId())) {
                //移除自己
                iterator.remove();
            }
        }
        if (mLockList.size() > 0) {
            mLockListAdapter.upDateAdapter(mLockList);
        }
    }

    @Override
    public void getPartPermissionsListFali(String msg) {

    }

    @Override
    public void partPermissionsUpdateSuccess(BaseResponse baseResponse) {
        ReqLockGetPartPermissionsList reqLockGetPartPermissionsList = new ReqLockGetPartPermissionsList();
        reqLockGetPartPermissionsList.setHouseId(getHouseId());
        reqLockGetPartPermissionsList.setPartId(lockId);
        mPresenter.getPartPermissionsList(reqLockGetPartPermissionsList);
    }

    @Override
    public void partPermissionsUpdateFali(String msg) {
        showToast(msg);
    }

    @Override
    public void partUnbindGatewaySuccess(BaseResponse baseResponse) {
        showToast("解绑成功");
        finish();
    }

    @Override
    public void partUnbindGatewayFali(String msg) {
        showToast(msg);
    }

    @Override
    public void partUnbindSnidSuccess(BaseResponse baseResponse) {
        showToast("解绑成功");
        finish();
    }

    @Override
    public void partUnbindSnidFali(String msg) {
    }

    @Override
    public void setPresenter(LockPowerListContract.Presenter presenter) {

    }


    private void gatewayUnbindHouse() {
        final ReqGatewayUnbindHouse reqGatewayUnbindHouse = new ReqGatewayUnbindHouse();
        reqGatewayUnbindHouse.setGatewaySn(gatewaySn);
        reqGatewayUnbindHouse.setHouseId(getHouseId());

        RetrofitUtil.getInstance().getmApiService()
                .gatewayUnbindHouse(reqGatewayUnbindHouse.toJsonStr())
                .compose(new APITransFormer<BaseResponse>())
                .subscribe(new APISubcriber<BaseResponse>() {
                    @Override
                    public void onFail(BaseResponse baseResponse, String msg) {
                        showToast(msg);
                    }

                    @Override
                    public void onSuccess(BaseResponse response) {
                        showToast("解绑成功");
                        finish();
                    }
                });
    }
}
