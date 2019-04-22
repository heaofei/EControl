package com.mxkj.econtrol.view.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.app.Msg;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.base.CommonAdapter;
import com.mxkj.econtrol.bean.EventBusUIMessage;
import com.mxkj.econtrol.bean.request.ReqGetApplyBindHouseList;
import com.mxkj.econtrol.bean.response.HouseUser;
import com.mxkj.econtrol.bean.response.ResGetApplyBindHouseList;
import com.mxkj.econtrol.contract.HouseUserListContract;
import com.mxkj.econtrol.model.HouseUserListModel;
import com.mxkj.econtrol.presenter.HouseUserListPresenter;
import com.mxkj.econtrol.ui.activity.QRCodeActivity;
import com.mxkj.econtrol.ui.adapter.HouseUserListAdapter;
import com.mxkj.econtrol.utils.MyDialogUtil;
import com.mxkj.econtrol.utils.SharedPreferencesUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

/**
 * 家庭成员界面
 */
public class HouseUserListActivity extends BaseActivity implements HouseUserListContract.View, CommonAdapter.OnItemClickListener {


    // 初始化title
    private TextView tv_title_left;
    private TextView tv_title_content;
    private ImageView iv_more;
    private TextView tv_title_right;

    private HouseUserListContract.Presenter mPresenter;
    private String mHouseId;
    private RecyclerView mRecyclerView;
    private List<HouseUser> mUsers;
    private HouseUser mUser;//要操作的用户
    private Dialog managerUserDialog;

    private RelativeLayout rl_qrcode;// 房子二维码
    private RelativeLayout rl_transfer; // 转让管理权
    private RelativeLayout rl_bind_user; // 用户申请绑定房子列表
    private RelativeLayout rl_exit; // 退出住宅
    private LinearLayout ll_item; // 业主
    private TextView tv_count;// 待审核数量
    private boolean isOwner = false; // 是否管理员(房主)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_house_user_list);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        tv_title_left = findView(R.id.tv_title_left);
        tv_title_content = findView(R.id.tv_title_content);
        iv_more = findView(R.id.iv_more);
        tv_title_right = findView(R.id.tv_title_right);
        tv_title_content.setText(getString(R.string.house_user));
        rl_qrcode = findView(R.id.rl_qrcode);
        rl_transfer = findView(R.id.rl_transfer);
        rl_bind_user = findView(R.id.rl_bind_user);
        ll_item = findView(R.id.ll_item);
        rl_exit = findView(R.id.rl_exit);
        tv_count = findView(R.id.tv_count);

        mRecyclerView = findView(R.id.recycle_user_list);
        mRecyclerView.setLayoutManager(new android.support.v7.widget.GridLayoutManager(this, 5));

    }

    @Override
    protected void initData() {
        mPresenter = new HouseUserListPresenter(this, new HouseUserListModel());
//        mHouseId = APP.user.getDefaultHouseId();//ff8080815cc99db0015d4f3be3bf02d9  富力1001
        mHouseId = (String) SharedPreferencesUtils.getParam(this, "houseId", "");
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_title_left:
                finish();
                break;
            case R.id.rl_qrcode:
                Intent intent01 = new Intent(this, QRCodeActivity.class);
                intent01.putExtra("houseId", mHouseId);
                intent01.putExtra("housingEstate", APP.user.getDefaultEstateName());  // 默认房子的 ，小区名， 栋数， 房子名
                intent01.putExtra("building", APP.user.getDefaultBuidingName());
                intent01.putExtra("houseNo", APP.user.getDefaultHouseName());

                startActivity(intent01);
                break;
            case R.id.rl_transfer:
                Intent intent = new Intent(this, HouseUserLisOperatetActivity.class);
                intent.putExtra("userList", (Serializable) mUsers);
                intent.putExtra("type", "TRANSFER_HOUSE");
                startActivity(intent);
                break;
            case R.id.rl_bind_user:

                Intent intent1 = new Intent(this, ApplyBindHouseListActivity.class);
                intent1.putExtra("houseId", mHouseId);
                startActivity(intent1);

                break;
            case R.id.rl_exit: // 退出住宅

                Iterator<HouseUser> iterator = mUsers.iterator();
                String houseUserId = "";
                while (iterator.hasNext()) {
                    HouseUser user = iterator.next();
                    if (APP.user.getUserId().equals(user.getUserId())) {//  找出业主id ，跟自己的id匹配，如果相同，显示转让管理权先
                        houseUserId = user.getId();
                        break;
                    }
                }
                showConfirmDialog(houseUserId);
                break;
        }
    }

    /***
     * 确认退出房子弹窗
     */
    private void showConfirmDialog(final String houseUserId) {
        MyDialogUtil dialog = new MyDialogUtil(this) {
            @Override
            public void confirm() {
                mPresenter.unbindHouseUser4Reject(houseUserId);
            }
        };
        dialog.showTipDialog(getString(R.string.house_user_exit), getString(R.string.house_user_exit_tips));

    }

    @Override
    protected void initListener() {
        tv_title_left.setOnClickListener(this);
        rl_qrcode.setOnClickListener(this);
        rl_transfer.setOnClickListener(this);
        rl_bind_user.setOnClickListener(this);
        rl_exit.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getHouseUserList(mHouseId);
        mPresenter.getApplyBindHouseList(new ReqGetApplyBindHouseList(1,3000,mHouseId,"0")); // status;    //0、未审核，1、审核，2、拒绝'

    }

    @Override
    public void setPresenter(HouseUserListContract.Presenter presenter) {

    }

    @Override
    public void getHouseUserListFail(String msg) {

    }

    @Override
    public void getHouseUserListSuccess(List<HouseUser> houseUsers) {
//        String content = new Gson().toJson(houseUsers);
        mUsers = houseUsers;
        Iterator<HouseUser> iterator = mUsers.iterator();
        while (iterator.hasNext()) {
            HouseUser user = iterator.next();
            if (user.getBindType().equals("0")) {
                if (user.getUserId().equals(APP.user.getUserId())) { // 判断管理员的userId 与自己id一样，显示 操作
                    isOwner = true;
                    HouseUser houseUser = new HouseUser();
                    houseUser.setHeadPicture("delete");
                    houseUser.setBindType("1");
                    mUsers.add(houseUser);// 设置减号
                    ll_item.setVisibility(View.VISIBLE); //   显示转让管理权线
                } else {
                    isOwner = false;
                    ll_item.setVisibility(View.GONE); //   隐藏转让管理权线
                }
                //移除管理员
//                iterator.remove();
                break;
            }
        }

        HouseUserListAdapter adapter = new HouseUserListAdapter(mUsers, R.layout.layout_house_user_item, this);
        adapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(adapter);
    }


    @Override
    public void unbindHouseUser4RejectSuccess() {
        showToast(getString(R.string.house_user_exit_success));

        EventBusUIMessage message = new EventBusUIMessage();
        message.setMsgType(Msg.EVENBUS_HOUSE_MESSAGE_5553);

        EventBus.getDefault().post(message); // 发送evenbus
        finish();
    }

    @Override
    public void unbindHouseUser4RejectFail(String msg) {
        showToast(msg);
        finish();
    }


    @Override
    public void getApplyBindHouseListSuccess(ResGetApplyBindHouseList resGetApplyBindHouseList) {
        String content = new Gson().toJson(resGetApplyBindHouseList);

        if (resGetApplyBindHouseList.getWaiting() > 0) {
            tv_count.setVisibility(View.VISIBLE);
            tv_count.setText(resGetApplyBindHouseList.getWaiting()+"");
        } else {
            tv_count.setVisibility(View.GONE);
        }
    }

    @Override
    public void getApplyBindHouseListFail(String msg) {
//        showToast(msg);
    }


    @Override
    public void onItemClick(View view, int position) {
        if (isOwner && position == mUsers.size() - 1) {
            Intent intent = new Intent(this, HouseUserLisOperatetActivity.class);
            intent.putExtra("userList", (Serializable) mUsers);
            intent.putExtra("type", "DELETE_USER");
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, MemberDataActivity.class);
            intent.putExtra("userId", mUsers.get(position).getUserId());
            startActivity(intent);
        }
    }


}
